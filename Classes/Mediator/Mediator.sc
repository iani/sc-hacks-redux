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
		//	envir use: { newValue = currentValue.handleReplacement(newValue); };
		/* 	NOTE: strictly use simpler version NOT THE ONE ABOVE!
			If you want to NOT replace some value, then you should 
			NOT use the = syntax, but send a message to that value. 
			Trying to override the = syntax goes against
			language semantics and opens up a can of unstoppable worms.
		*/
		envir.prPut(key, newValue.trackState(key, envir));
	}
}

// other objects add more complex behavior
+ Object {
	handleReplacement { this.stop; }
	trackState {} // Synth uses this to register with nodewatcher
}
+ Synth {
	handleReplacement { if (this.isPlaying) { this.release(~release) }; }
	trackState { NodeWatcher.register(this) }
}

+ Function {
	// redefinition of +> operator
	playIn { | key = \default,
		target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args |
		currentEnvironment use: {
			currentEnvironment.put(key,
				this.play(target, outbus, fadeTime, addAction, args)
			)
		}
	}
	// just because I want a different name:
	splay { | key = \default | this.playIn(key) }

	tplay { | key = \default, clock |
		^currentEnvironment use: {
			currentEnvironment[key] = Task(this).play(clock ? SystemClock);
		};
	}

	+> { | key = \default | this.playIn(key) }
	*> { | key = \default | ^this.tplay(key) }
}


