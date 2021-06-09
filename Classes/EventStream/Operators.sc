/*  4 Jun 2021 17:56
All sc-hacks-redux operators for all classes, in one file.
*/

+ SimpleNumber {
	+> { | envir, param |
		param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		envir.push.put(param, this);
	}
}

+ Function {
	+> { | envir, player |
		
	}
}

+ Event {
	+> { | envir, player |
		
	}
}


+ Symbol {
	push {
		^Mediator.fromLib(this).push;
	}

	synth { | func, varName |
		var envir;
		envir = this.push; // Stops what is replaced, if playing:
		envir[varName ? this] = func.asSynth(envir, \addToHead);
	}

	src {
		
		
	}

	fx {
		
		
	}

	stream { } // evstream?

	bus {}

	playBuf {}

	grainBuf {}

}
	
