/* 27 Feb 2022 12:09

*/
MediatorHandler {
	var <>envir;
	value { | key, newValue |
		var currentValue;
		currentValue = envir.at(key);
		envir use: { currentValue.handleReplacement(newValue, envir, key); };
		// envir.changed(key, newValue); // Obsolete in v2.
		// Mediator calls changed(\key..) in prPut method. Use that instead!
		envir.prPut(key, newValue);
	}
}