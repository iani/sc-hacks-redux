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

// Event ++> should restart
// Event +> should modify.
// Note: In the case of Event and EventStream:
// If an EventStream is already playing, it should *not*
// be restarted.  It should be modified instead.
// This is to avoid inadvertently restarting EventStreams
// when copy-pasting a line previously addressing the
// same player in order to modify it.

+ Event {

	+> { | player, envir |
		// transferred here from +> on Tue 25 Feb 2025 14:56
		// Set all key-value pairs of the receiver to the object at key/envir
		// If object is EventStream: set keys of the Event.
		// Else if object is Synth, set all parameters corresponding to the keys
		Mediator.setEvent(this, player, envir);
		// var p;
		// Mediator.wrap({
		// 	p = currentEnvironment[key];
		// 	p ?? {
		// 		p = EventStream(this);
		// 		currentEnvironment.put(key, p);
		// 	};
		// 	// EventSream and Synth handle this differently:
		// 	currentEnvironment[key].setEvent(this);
		// }, envir);
	}
}

// Mon 24 Feb 2025 01:41 V2:
// Keeping this as one may want to construct an event
// stream and play it (instead of writing an Event).
+ EventStream {
		+> { | player, envir |
			// "This is EventStream+>ugenfunc!!!!!!!".postln;
		^this.pushPlayInEnvir(player, envir ? player, true)
		}
}

// FunctionOperators.sc      Function-+>
+ Function {
		+> { | player, envir |
			^this.pushPlayInEnvir(player, envir ? player)
}

+ Nil {
	+> { | player, envir |
		// "This is Nil+>player, envir!!!!!!!".postln;
		Mediator.wrap(
			{
				// currentEnvironment[player].playNext;
				// postln("debugging Nil+>. player is:" + currentEnvironment[player]);
				// currentEnvironment.postln;
				currentEnvironment[player] release: (~fadeTime ? 0.02);
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
		// 240224: Hack for setting instrument key in events.
		if (player isKindOf: Event) {
			^player.put(\instrument, this);
		}{
			^this.pushPlayInEnvir(player, envir);
		}
    }
}
// UGenShortcuts.sc          UGen-+>

+ UGen {

	+> { | ugenfunc |
		// "This is Symbol+>ugenfunc !!!!!!!".postln;
		^ugenfunc.ar(this) } // play as input to other ugen
}