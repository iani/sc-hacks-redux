/* 27 Feb 2022 09:50

*/

+ Symbol {

	//=================================================================

	@ { | envir | ^this.at(envir) }
	at { | envir | // has same effect as Symbol:player below?
		^Mediator.at(envir ? currentEnvironment.name).at(this);
	}

	asPlayer { ^this.at(nil) }

	// NOTE: If possible, avoid adding more operators, like the following:
	@|> { "@|> works".postln; }
	|@|> { "|@|> works".postln; }
	|@| { "|@| works".postln; }
	|> { "|> works".postln; }

	//=================================================================
	-- { | envir, time = 1 |
		// Stop player from specified envir
		Mediator.at(envir).at(this).stop(time);
	}
	&> { | value, envir = \default |
		envir.addKey(this, value);
	}
	addKey { | key, value | // add key to a player environment
		Mediator.at(this).put(key, value);
	}
	// ================================================================
	// player operators
	playingp { | envir |
		^Mediator.wrap({
			currentEnvironment[this].isPlaying;
		}, envir)
	}
	+> { | player, envir |
		^this.pushPlayInEnvir(player, envir);
    }

	+>! { | player, envir |
		// osc message triggers play next event of an EventStream player
		this >>> {
			Mediator.wrap(
				{ currentEnvironment[player].playNext },
				envir ? \default
			)
		}
	}

	<+ { | value, player |
		currentEnvironment[player].set(this, value)
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
		// TODO: add arguments setting, bus mapping
		var synth;
		// envir = envir ? currentEnvironment.name;
		// Each player has its own envir, which also stores its busses
		envir = envir ? player;
		Mediator.wrap({
			// enable storing of source code:
			Function.changed(\player, envir, player, Main.elapsedTime,
				format("% +>.% %", this.asCompileString, envir, player.asCompileString);
			);
			if (Server.default.serverRunning) {
				currentEnvironment.addSynth(player, synth = Synth(this));
			}{
				Server.default.waitForBoot({
					currentEnvironment.addSynth(player, synth = Synth(this));
				})
			}
		}, envir);
		^synth;
	}

	pfree { | envir | // may be obsolete
		^this.player(envir ? this).free;
	}

	reset { | envir |
		var player;
		player = Mediator.at(envir ? this).at(this);
		player.reset;
	}

	stop { | fadeTime, envir | this.stopPlayer(envir ? this, fadeTime) }
	stopPlayer { | envir, fadeTime |
		var player;
		envir = Mediator.at(envir ?? { currentEnvironment.name });
		player = envir.at(this);
		// postln("player " + player + " is playing? : " + player.isPlaying );
		if (player.isPlaying) {
			if (player isKindOf: Synth) {
				fadeTime ?? { fadeTime = envir[\fadeTime] ? 1.0 };
				player release: fadeTime;
			}{
				player.stop;
			};
		}
	}

	// ================================================================
	// EventStream played actions
	// Actions run every time an EventStream plays its next event
	<! { | func, envir | this.addEventStreamAction(func, envir); }

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

	// ============================ Toggle. UNTESTED!!!
	<?> { | player, envir |
		^this.toggle(player, envir);
	}

	toggle { | player, envir |
		var process;
		Mediator.wrap({
			process = currentEnvironment[this];
			if (process.isPlaying) {
				process.stop
			}{
				process = player.playInEnvir(this, envir);
			}
		}, envir);
		^process;
	}
	// ================================================================
	// bus operators
	<+@ { | value |
		this.bus.set(value)
	}

	<@ { | bus, player |
		currentEnvironment[player].map(this, bus.bus)
	}

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

	src {


	}

	fx {


	}

	stream { } // evstream?

	get { ^this.bus.get }
	index { ^this.bus.index }
	in { ^In.kr(this.index) }
	krin { | index = 0 | ^In.kr(this.kr(index)) }

	bkr { ^In.kr(this.bus.index) }

	// Buffer play ugens ================

	playBuf { | loop = 0 |
		var bufnum, numChannels;
		bufnum = this.buf.bufnum;
		numChannels = this.buf.numChannels;
		^PlayBuf.ar(
			numChannels,
			\bufnum.kr(bufnum),
			\rate.kr(1),
			\trigger.tr(1),
			\startPos.kr(0),
			\loop.kr(loop),
		)
	}

	grainBuf {}

	lfree { Library.at(this).free; }
}

+ Nil {
	envir { ^currentEnvironment }
}