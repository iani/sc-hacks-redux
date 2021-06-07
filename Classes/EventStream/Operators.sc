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
	+> { | envir |
		
	}
}

+ Event {
	+> { | envir |
		
	}
}


+ Symbol {
	push {
		^Mediator.fromLib(this).push;
	}

	synth { }

	src {
		
		
	}

	fx {
		
		
	}

	stream { } // evstream?

	bus {}

	playBuf {}

	grainBuf {}

}
	
