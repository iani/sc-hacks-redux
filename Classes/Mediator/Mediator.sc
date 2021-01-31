/* 25 Jan 2021 20:30
EnvironmentRedirect that handles state of objects before replacing them
in its keys. Setting the value of an environment variable in this environment will cause whatever was previously in that variable to stop. This is a simple way
to guarante that any patterns or synths will stop playing when the reference to them from an environment variable is lost.

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

MediatorHandler {
	var <>envir;
	value { | key, newValue |
		var currentValue;
		currentValue = envir.at(key);
		envir use: { currentValue.handleReplacement(newValue); };
		envir.prPut(key, newValue.trackState(key, envir));
	}
}

// other objects add more complex behavior
+ Object {
	handleReplacement { this.stop }
	trackState {} // Synth and Node use this to register with nodewatcher
}
+ Synth {
	handleReplacement { if (this.isPlaying) { this.release(~release) } }
	trackState { NodeWatcher.register(this) }
}



