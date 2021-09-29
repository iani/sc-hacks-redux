/* 25 Jan 2021 20:30
EnvironmentRedirect that handles state of objects before replacing them
in its keys. Setting the value of an environment variable in this environment will cause whatever was previously in that variable to stop. This is a simple way
to guarante that any patterns or synths will stop playing when the reference to them from an environment variable is lost.

See README.
*/

Mediator : EnvironmentRedirect {
	classvar default;
	var <name;

	*new { | name, envir |
			^this.newCopyArgs(
				envir ?? { Environment.new(32, Environment.new) },
				nil, name
			).dispatch = MediatorHandler();
			// ^super.newCopyArgs(envir ?? { Environment.new(32, Environment.new) })
	}
		//		^super.new(envir).dispatch = MediatorHandler();


	init { | ... args |
		// postf("my iit args are: %\n", args);
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << "<" << this.name << ">:[ " ;
		envir.printItemsOn(stream);
		stream << " ]" ;
	}

	*initClass {
		StartUp add: { this.push }
	}

	*push { this.default.push }
	*pop { this.default.pop }
	*default { ^default ?? { default = this.fromLib(\default) } }
	put { | key, obj |
		dispatch.value(key, obj);
		this.changed(key, obj);
	}
	prPut { | key, obj |
		envir.put (key, obj);
	}

	
}

MediatorHandler {
	var <>envir;
	value { | key, newValue |
		var currentValue;
		currentValue = envir.at(key);
		envir use: { currentValue.handleReplacement(newValue); };
		envir.prPut(key, newValue
			// trackState is done in asSynth. Other stuff?
			//	newValue.trackState(key, envir)
		);
	}
}

// other objects add more complex behavior
+ Object {
	handleReplacement {
		
		this.stop;
	}
	// asSynth handles this. Check!?:
	// trackState {} // Synth uses this to register with nodewatcher
}
+ Synth {
	handleReplacement {
		// requires synth state to be tracked with with onStart
		if (this.isPlaying) { this.release(~release); }
	}
}

+ Bus {
	handleReplacement { this.free }
}

+ Function {
	// redefinition of +> operator
	playIn { | key = \default |  // as of  9 Jun 2021 21:37
		// trying to substitute SynthPlayer with plain synth.
		// target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args
		currentEnvironment use: {
			//, outbus, fadeTime, addAction, args ...
			// SynthPlayer: UNDER DEVELOPMENT 
			//  9 Jun 2021 17:52 - may be avoided... just use synth?
			currentEnvironment.put(key, SynthPlayer(this, key))
		};
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


