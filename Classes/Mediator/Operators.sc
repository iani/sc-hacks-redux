/*  4 Jun 2021 17:56
All sc-hacks-redux operators for all classes, in one file.
*/

+ Nil {
	addEvent { | event, key |
		event +> key
	}
}

+ Synth {
	addEvent { | event, key |
		event +> key
	}
	notifyIdOnStart { | notifier = \synth |
		this.onStart({ | synth |
			// track state and broadcast id for use with tr
			notifier.changed(\synth, synth.nodeID)
		})
	}
}

+ SimpleNumber {
	+> { | envir, param |
		param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		envir.push.put(param, this);
	}
}

+ Function {
	+> { | player, envir |
		// TODO: add arguments setting, bus mapping
		Mediator.wrap({
			currentEnvironment[player] = this.play.notifyIdOnStart(player)
		}, envir);
	}
}

+ Symbol {
	+> { | player, envir |
		Mediator.wrap({
			currentEnvironment[player] = Synth(this).notifyIdOnStart(player); // onStart: init running status!
		}, envir);
    }

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
