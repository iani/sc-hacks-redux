//Fri 23 Feb 2024 12:16
//SymbolOperators
/* V2 Notes Mon 24 Feb 2025 01:26:  This operator should be
	rewritten to that of <== .
<@ explicitly maps a control named by Symbol in a synth
	player to a bus named by the argument bus.
However
*/
+ Symbol {
	// Old code (v1):
	/*
	<@ { | bus, player |
		currentEnvironment[player].map(this, bus.bus)
	}
	*/
	// For V2, use this code instead:
	// If object at key is a Bus, then set its value.
	// Else store value at key in envir.
	<@ { | argValue, envir |
		var storedObject;
		if (envir.isNil) {
			envir = currentEnvironment
		}{
			envir = envir.envir
		};
		storedObject = envir at: this;
		if (storedObject isKindOf: Bus) {
			storedObject.set(argValue);
		}{
			envir.put(this, argValue);
		}
	}

}
