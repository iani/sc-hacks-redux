/* 27 Feb 2022 09:50

*/

+ Symbol {
	// ================================================================
	// playeer operators
	playingp { | envir |
		^Mediator.wrap({
			currentEnvironment[this].isPlaying;
		}, envir)
	}
	+> { | player, envir |
		^this.playInEnvir(player, envir);
    }
	// \srdiv <+.peb1 0.05;
	<+ { | value, player |
		// .set(this, value);
		currentEnvironment[player].set(this, value)
	}

	playInEnvir { | player, envir |
		var synth;
		Mediator.wrap({
			currentEnvironment[player] = synth = Synth(this).notify(player, envir);
		}, envir);
		^synth;
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
	|> { | beatKey |
		beatKey.beat.addDependant(currentEnvironment[this]);
	}
	addBeat { | beatKey |
		this |> (beatKey ? this);
	}

	removeBeat { | beatKey |
		currentEnvironment[this].removeBeat(beatKey ? this);
	}

	// ============================ Toggle. UNTESTED!!!
	+>? { | player, envir |
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

	bus { | rate = \control, numchans = 1, server |
		var bus;
		server = server.asTarget.server;
		bus = Library.at(Bus, server, rate, this);
		bus ?? {
			bus = Bus.perform(\control, server, numchans);
			Library.put(Bus, server, rate, this, bus);
		};
		^bus;
	}

	get { ^this.bus.get }
	index { ^this.bus.index }
	in { ^In.kr(this.index) }

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