//: 日  4  5 2025 06:38
// See also Symbol:play in SymbolOperators.sc

+ Object {
	play { | args |
		^this.playArgs(args);
	}
	playArgs { | args |
		// return args to be stored in envir by Symbol:play
		// ^args;
		// postln("playArgs receiver" + this + "args" + args);
		// postln("playOrMerge arg0" + args[0] + "restargs" + args[1..]);
		^args[0].playOrMerge(args[1..]);
	}
	playOrMerge { | args |
		^this
		// ^args[0]
	}
}

+ Symbol {
	play { | ... args |
		// Play something at key in currentEnvironment.
		// If key previously contains a Synth, release it.
		// If it contains an EventStream, merge it or replace it
		// Store the result in the new at currenEnvironment.
		currentEnvironment[this] = currentEnvironment[this].playArgs(args);
	}
	play2 { | playFunc, event |
		// THIS IS OLD play METHOD OF V1, USED TO PLAY SynthTemplate
		// See Mediator play.
		// play playfunc in event envir of Mediator named by me
		// Use this	 only in v1 of this libary
		^Mediator.at(this).play(playFunc, event);
	}
}

+ Nil {
	// playArgs { | args | ^args; }
	playOrMerge { | args |
		^args[0]
	}
}

+ Synth {
	playArgs { | args |
		if (this.isPlaying) { this release: (~fadeTime ? 0.1); };
		// postln("Synth released" + this + "now will play" + args[0]
		// + "withArgs" + args[1..]);
		^args[0].playArgs(args[1..]);
	}
}

+ EventStream {
	playArgs { | args |
		postln("TO be done:" + this + "will play" + args);
	}
}

+ Function {
	playArgs { | args |
		// postln("Function playargs. Receiver" + this + "args" + args);
		^this.play(*args).register;
	}
	playOrMerge { | args |
		// postln("Function" + this + "received these args:" + args);
		// TODO: Review this to add more options for merging with other objects
		^this.play(*args).register;
	}
}