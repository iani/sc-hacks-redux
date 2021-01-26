/* 25 Jan 2021 20:30
EnvironmentRedirect that handles state of objects before replacing them
in its keys.

See README.
*/

Mediator : EnvironmentRedirect {
	classvar default;
	
	*new { | envir |
		^super.new(envir).dispatch = MediatorHandler();
	}

	*push { this.default.push }
	*pop { this.default.pop }
	*default { ^default ?? { default = this.new } }
	put { | key, obj | dispatch.value(key, obj); }
	prPut { | key, obj | envir.put (key, obj) }
}

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
+ Object { handleReplacement { this.stop } }
// if this does not have gate then free ... ?
+ Synth { handleReplacement { this.release } }


