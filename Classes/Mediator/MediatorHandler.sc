/* 27 Feb 2022 12:09

*/
MediatorHandler {
	var <>envir;
	value { | key, newValue |
		var currentValue;
		currentValue = envir.at(key);
		envir use: { currentValue.handleReplacement(newValue); };
		// envir.changed(key, newValue);
		envir.prPut(key, newValue
		);
	}
}