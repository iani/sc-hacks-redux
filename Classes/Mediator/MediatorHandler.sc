/* 25 Jan 2021 20:37
Implements the behavior of Mediator in response to put messages.

Main goal: stop Synths and EventStreamPlayers before replacing them.

*/

MediatorHandler : AbstractFunction {
	var <>envir;
	value { | key, newValue |
		var currentValue;
		currentValue = envir.at(key);
		currentValue.handleReplacement(envir, key, newValue);
		//		envir.prPut(key, newValue);
	}
}

+ Object {
	handleReplacement { | argEnvir, argKey, argNewValue |		
		argEnvir.prPut(argKey, argNewValue);
	}	
}

+ Synth {
	handleReplacement { | argEnvir, argKey, argNewValue |
		this.release; // if this does not have gate then free ... ?
		super.handleReplacement(argEnvir, argKey, argNewValue);
	}
}

+ EventStreamPlayer {
	handleReplacement {| argEnvir, argKey, argNewValue |
		this.stop; //
		super.handleReplacement(argEnvir, argKey, argNewValue);
	}
}
