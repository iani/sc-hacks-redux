// [x] ArrayUGenShortcuts.sc     Array-+>
// EventOperators.sc         Event-+>
// EventStream.sc            EventStream-+>
// FunctionOperators.sc      Function-+>
// SymbolOperators.sc        Nil-+>
// SimpleNumberOperators.sc  SimpleNumber-+>
// SymbolOperators.sc        Symbol-+>
// UGenShortcuts.sc          UGen-+>

// ~/Dev/SCdev/LibsByMe/sc-hacks-redux/Classes/OperatorsAndShortcuts/SystemOverwrites/ArrayUGenShortcuts.sc

+ Array {
	+> { | ugenfunc |
		// "This is Array+>ugenfunc!!!!!!!".postln;
		^ugenfunc.ar(this) } // play as input to other ugen
}

+ Event {

	+> { | player, envir |
		// "This is Event+>ugenfunc!!!!!!!".postln;
		^this.pushPlayInEnvir(player, envir ? player, true)
	}
}

+ EventStream {
		+> { | player, envir |
			// "This is EventStream+>ugenfunc!!!!!!!".postln;
			^this.pushPlayInEnvir(player, envir ? player, true) }

}

// FunctionOperators.sc      Function-+>
+ Function {
		+> { | player, envir |
			// "This is Function+>ugenfunc!!!!!!!".postln;
			^this.pushPlayInEnvir(player, envir ? player, true)
		}
	// older version:
		// See OperatorFix240222.sc
	// +> { | player, envir |
	// 	^this.pushPlayInEnvir(player, envir);
	// }


}

// SymbolOperators.sc        Nil-+>

+ Nil {
	+> { | player, envir |
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
}

// SimpleNumberOperators.sc  SimpleNumber-+>
+ SimpleNumber {
	+> { | param, envir |
		// "This is SimpleNumber+>player, envir!!!!!!!".postln;

		param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		envir = envir ? \default;
		envir.envir.put(param, this);
	}
}
// SymbolOperators.sc        Symbol-+>

+ Symbol {
	+> { | player, envir |
		// "This is Symbol+>player, envir!!!!!!!".postln;
	 	^this.pushPlayInEnvir(player, envir);
    }
}
// UGenShortcuts.sc          UGen-+>

+ UGen {

	+> { | ugenfunc |
		// "This is Symbol+>ugenfunc !!!!!!!".postln;
		^ugenfunc.ar(this) } // play as input to other ugen
}