// [x] ArrayUGenShortcuts.sc     Array-+>
// EventOperators.sc         Event-+>
// EventStream.sc            EventStream-+>
// FunctionOperators.sc      Function-+>
// SymbolOperators.sc        Nil-+>
// SimpleNumberOperators.sc  SimpleNumber-+>
// SymbolOperators.sc        Symbol-+>
// UGenShortcuts.sc          UGen-+>

// ~/Dev/SCdev/LibsByMe/sc-hacks-redux/Classes/OperatorsAndShortcuts/SystemOverwrites/ArrayUGenShortcuts.sc

+ Event {
	// TODO: Merge this code with EventStream:+>
	+> { | player, envir |
		// merge into player EventStream if exists.
		// else start new EventStream
		var previous;
		previous = envir.envir[player];
		// if EventStream already exists, then merge current Event into it.
		if (previous isKindOf: EventStream) {
			previous mergeEvent: this;
			previous.startIfNotRunning; //
			^previous;
		};
		// Else start new EventStream
		^this.pushPlayInEnvir(player, envir ? player, true)
	}
}

// What is a good way to make the +> operator have the same
// code for Event and Event stream, without copy-pasting the code?
+ EventStream {
	+> { | player, envir |
		// merge into player EventStream if exists.
		// else start new EventStream
		var previous;
		previous = envir.envir[player];
		if (previous isKindOf: EventStream) {
			previous mergeEvent: this;
			previous.startIfNotRunning; //
			^previous;
		};
		^this.pushPlayInEnvir(player, envir ? player, true)
	}
}

+ Function {
		+> { | player, envir |
			// "This is Function+>ugenfunc!!!!!!!".postln;
			^this.pushPlayInEnvir(player, envir ? player)
		}
	// older version:
		// See OperatorFix240222.sc
	// +> { | player, envir |
	// 	^this.pushPlayInEnvir(player, envir);
	// }


}

+ Nil {
	// TODO: Review!
	+> { | player, envir |
		player = envir.envir[player];
		player.stop;
		// cannot put nil in an environment at a key:
		// envir[player] = nil;
	}
	// old version:
	/*	+> { | player, envir |
		// "This is Nil+>player, envir!!!!!!!".postln;
		Mediator.wrap(
			{
				// currentEnvironment[player].playNext;
				// postln("debugging Nil+>. player is:" + currentEnvironment[player]);
				// currentEnvironment.postln;
				currentEnvironment[player].free;
			},
			envir ? player
		)
	}
	*/
}

/*============================================================
	Ad-hoc use. Needs review.
*/

// UGen and Array +> operator is useful for using
// ugenfuncs as templates inside other ugenfuncs.
// See class SynthTemplate !!!

+ UGen {
	+> { | ugenfunc |
		^ugenfunc.ar(this) } // play as input to other ugen
}

+ Array {
	+> { | ugenfunc |
		// assume that this is an Array of outputProxy
		^ugenfunc.ar(this) } // play as input to other ugen
}

+ SimpleNumber {
	+> { | param, envir |
		// "This is SimpleNumber+>player, envir!!!!!!!".postln;

		param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		envir = envir ? \default;
		envir.envir.put(param, this);
	}
}

// Play symbol as name of SynthDef. Synth(this);
+ Symbol {
	+> { | player, envir |
		// 240224: Hack for setting instrument key in events.
		if (player isKindOf: Event) {
			^player.put(\instrument, this);
		}{
			^this.pushPlayInEnvir(player, envir);
		}
    }
}
