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
}

+ SimpleNumber {
	+> { | envir, param |
		param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		envir.push.put(param, this);
	}
}

+ Function {
	+> {| envir, player |
		// TODO: add arguments setting, bus mapping
		currentEnvironment[envir] = this.play.onStart({}); // onStart: init running status!
	}
}

+ Symbol {
	+> { | envir, player |
		currentEnvironment[envir] = Synth(this).onStart({}); // onStart: init running status!
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

	playBuf {}

	grainBuf {}

	lfree { Library.at(this).free; }
}
