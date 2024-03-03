/* 27 Feb 2022 09:50

*/

+ Symbol {
	// DRAFT! "ur": User Rate. Create gui view for setting the bus.
	*ur { | spec = \amp, envir = \default |
		spec = spec.asSpec;
		^HLayout(
			StaticText().string_(this),
			NumberBox().action_({ | me | me.postln; }),
			Slider().orientation_(\horizontal)
			.action_({ | me |
				[me.value, spec.map(me.value)].postln;
				format(""); /// . share .. ....
			})
		)
	}
	// create multiple controls
	brdup { | iter = 1, default = 1 |
		^{ | i |
			var ctl;
			ctl = (this ++ i).asSymbol;
			ctl.br(currentEnvironment.at(ctl) ?? { default })
		} ! iter
	}
	brdupOld { | iter = 1, default = 1 |
		^(1..iter) collect: { | i |
			var ctl;
			ctl = (this ++ i).asSymbol;
			ctl.postln.br(currentEnvironment.at(ctl) ?? { default })
		}
	}
	// convert to specs for use by Param
	gt { | thresh = 0.5 |
		^this.sr > thresh;
	}
	// TODO: this may become gt!
	gt2 { | thresh = 0.5 |
		var src;
		src = this.sr > thresh;
		src.sendReply(this.asOscMessage, 1);
		(1 - src).sendReply(this.asOscMessage, 0);
		^src;
	}
	// TODO: maybe delete after testing and
	// transferring to gt / gt2
	gtreply { | thresh = 0.5 |
		var src;
		src = this.sr > thresh;
		src.sendReply(this.asOscMessage, 1);
		(1 - src).sendReply(this.asOscMessage, 0);
		^src;
	}
	ampSlope { | attack = 0.001, release = 1 |
		^Amplitude.kr(Slope.kr(this.sr), attack, release)
	}
	linlin { | envir, param, lo, hi |
		format(
			"{ %%.sr.lin(%, %) } @>.% %%;", "%", this, lo, hi, envir, "%", param
		).share;
	}
	ps { | lo = 0, hi = 1.0, default = 1, map = \linear, step = 0 |
		^[lo, hi, map, step, default, this].asSpec;
	}
	play { | playFunc, event |
		// play playfunc in event envir of Mediator named by me
		^Mediator.at(this).play(playFunc, event);
	}

	putGlobal { | object | Mediator.putGlobal(this, object) }

	//============================================================/
	// add bus and target to environment
	addBus { | bus, name = \outbus |
		this.envir[name] = bus ?? { Bus.audio };
	}
	addTarget { | target = \default_group, name = \target |
		this.envir[name] = target;
	}
	//=================================================================
	//=================================================================

	asEnvir { ^Mediator.at(this) } // doubtful synonym?
	asPlayer { ^this.at(nil) }

	// ================================================================
	// player operators
	playingp { | envir |
		^Mediator.wrap({
			currentEnvironment[this].isPlaying;
		}, envir)
	}

	player { | envir |
		var player;
		Mediator.wrap({
			player = currentEnvironment[this];
		}, envir ? this);
		^player;
	}

	envir { ^Mediator.at(this) }

	playInEnvir { | player, envir |
		var synth;
		envir = envir ? player;
		Mediator.wrap({
			// enable storing of source code:
			Function.changed(\player, envir, player, Main.elapsedTime,
				format("% +>.% %", this.asCompileString, envir, player.asCompileString);
			);
			if (Server.default.serverRunning) {
				currentEnvironment.addSynth(player,
					synth = Synth(this, target: ~target.asTarget));
			}{
				Server.default.waitForBoot({
					currentEnvironment.addSynth(player,
						synth = Synth(this, target: ~target.asTarget));
				})
			}
		}, envir);
		^synth;
	}

	pfree { | envir | // may be obsolete
		^this.player(envir ? this).free;
	}

	free { Mediator.at(this).free; }

	reset { | envir |
		var player;
		player = Mediator.at(envir ? this).at(this);
		player.reset;
	}

	stop { | fadeTime, envir | this.stopPlayer(envir ? this, fadeTime) }

	freePlayer { | envir, followUpFunc |
		// immediately free. Needed when setting bus to a number
		// Fri  7 Jul 2023 15:47 - replacing this with code in SimpleNumber:@>
		var player;
		envir = Mediator.at(envir ?? { currentEnvironment.name });
		// postln("envir is:" + envir);
		player = envir.at(this);
		if (player isKindOf: Synth) {
			// testing how to find out when the player frees:
			// player onEnd: { player.changed(\ended); };
			\test.addNotifierOneShot(player, \ended, followUpFunc);
			player.free;
		}{
			player.stop;
			followUpFunc.value
		}
	}

	stopPlayer { | envir, fadeTime |
		var player;
		// postln("Stop player" + this + "for envir" + envir);
		envir = Mediator.at(envir ?? { currentEnvironment.name });
		// postln("envir is:" + envir);
		player = envir.at(this);
		// postln("player is:" + player);
		// postln("player " + player + " is playing? : " + player.isPlaying );
		if (player.isPlaying) {
			// postln("player" + player + "is playing");
			if (player isKindOf: Synth) {
				// postln("player" + player + "is a synth. I will release it");
				fadeTime ?? { fadeTime = envir[\fadeTime] ? 1.0 };
				player release: fadeTime;
			}{
				// postln("player" + player + "is NOT synth. I will stop it");
				player.stop;
			};
		}
	}

	// ================================================================
	// EventStream played actions
	// Actions run every time an EventStream plays its next event

	addEventStreamAction { | func, envir |
		Mediator.wrap({
			this.addNotifier(currentEnvironment[this], \played, { | n, event, stream |
				func.(event, stream);
			})
		}, envir)
	}

	sendEvents { | netAddress, envir, message = '/eventPlayed' |
		netAddress ?? { netAddress = NetAddr.localAddr };
		this.addEventStreamAction({ | event, eventStream |
			// postln("Address" + netAddress);
			// postln("Event" + event);
			// postln("Stream" + eventStream.stream);
			var keys, data;

			keys = eventStream.stream.keys ?? { [\dur] };
			data = [\player, this, \envir, currentEnvironment.name,
				\time, Process.elapsedTime];
			keys do: { | key |
				data = data add: key;
				data = data add: event[key]
			};
			netAddress.sendMsg(message, *data);
		}, envir)
	}

	// ================================================================
	// ============================ Beats UNTESTED!!!
	// |> { | beatKey |
	// 	beatKey.beat.addDependant(currentEnvironment[this]);
	// }
	// addBeat { | beatKey |
	// 	this |> (beatKey ? this);
	// }

	// removeBeat { | beatKey |
	// 	currentEnvironment[this].removeBeat(beatKey ? this);
	// }

	// ================================================================
	// bus operators

	//

	push {
		^Mediator.fromLib(this).push;
	}

	synth { | func, key |
		var envir, defName;
		key = key ? this;
		envir = this.push; // Stops what is replaced, if playing:
		defName = (envir ++ '_' ++ key).asSymbol;
		envir[key] = func.asSynth(envir, defName, \addToHead);
	}

	stream { } // evstream?

	get { ^this.bus.get }
	index { ^this.bus.index }
	// in { ^In.kr(this.index) }
	krin { | index = 0 | ^In.kr(this.kr(index)) }

	bkr { ^In.kr(this.bus.index) }

	// Buffer play ugens ================

	playbuf { arg loop = 0, bufnum = \bufnum, rate = \rate,
		trig = \trig, startPos = \startPos, argLoop = \loop;
		// , \startPos = startPos, argLoop = \loop;
		var bnum, numChannels;
		bnum = this.buf.bufnum;
		numChannels = this.buf.numChannels;
		^PlayBuf.ar(
			numChannels,
			bufnum.br(bnum),
			rate.br(1),
			trig.br(1),
			startPos.br(0),
			argLoop.br(0),
		)
	}

	numFrames { | player |
		^this.buf(nil, player).numFrames;
	}

	sampleRate { | player |
		^this.buf(nil, player).sampleRate
	}
	bufdur { | player |
		var buf;
		buf = this.buf(nil, player);
		^buf.numFrames / buf.sampleRate
	}

	grainBuf {}

	lfree { Library.at(this).free; }
}

+ Nil {
	envir { ^currentEnvironment }
	// see OperatorFix240222.sc
	// +> { | player, envir |
	// 	Mediator.wrap(
	// 		{
	// 			// currentEnvironment[player].playNext;
	// 			// postln("debugging Nil+>. player is:" + currentEnvironment[player]);
	// 			// currentEnvironment.postln;
	// 			currentEnvironment[player].free;
	// 		},
	// 		envir ? player
	// 	)
	// }
}