//Fri 23 Feb 2024 09:21
// EventOperators.sc
// SymbolOperators.sc

+ Event {
	++> { | key, envir |
		// Set all key-value pairs of the receiver to the object at key/envir
		// If object is EventStream: set keys of the Event.
		// Else if object is Synth, set all parameters corresponding to the keys
		Mediator.setEvent(this, key, envir);
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

+ Symbol {
	// Sat 11 Nov 2023 08:18 - cancel !+> --- too cumbersome.
	// Mon 13 Nov 2023 22:29: Substitute for earlier !+> or +>
	++> { | param, envir |
		envir.envir.put(param, this);
	}
}