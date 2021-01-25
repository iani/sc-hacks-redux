/* 25 Jan 2021 20:37
Implements the behavior of Mediator in response to put messages.

Main goal: stop Synths and EventStreamPlayers before replacing them.

*/

MediatorHandler : AbstractFunction {
	var <>envir;
	value { | key, newValue |
		var currentValue;
		currentValue = envir.at(key);
		currentValue.handleReplacement(newValue);
		envir.prPut(key, newValue);
	}
}

// other objects add more complex behavior
+ Object { handleReplacement {} }

// if this does not have gate then free ... ?
+ Synth { handleReplacement { this.release } }

+ EventStreamPlayer { handleReplacement { this.stop; } }

+ EventStream { handleReplacement { this.stop; } }

