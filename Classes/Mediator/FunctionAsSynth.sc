/*  9 Jun 2021 15:38
Create synth from function, initializing control + argument values from
currentEnvironment.

Setup the synth to update parameters or map busses from the environment,
and to disconnect when ends.

NOTE: See  Node:onFree !!!
*/

+ Function {
	asSynth { | envir, addAction = \addToHead |
		//  9 Jun 2021 15:52 stub implementation
		// arguments from synthdef to be added later.
		var synth;
		envir = envir ? currentEnvironment;
		// arg target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args
		synth = this.play(
			envir[\target].asTarget, envir[\outbus] ? 0,
			envir[\fadeTime] ? 0.02, addAction // TODO: obtain args from envir
		);
		synth.register;
		synth.addDependant({ | argSynth, argState |
			switch(argState,
				\n_end, {
					// postf("% ended. Removing it from envir\n", argSynth);
					argSynth.releaseDependants;
					envir.removeDependant(argSynth);
				},
				\n_go, {
					// postf("% started. ADDING it TO envir\n", argSynth);
					envir addDependant: argSynth;
				}
			);
		});
		^synth;
	}
}

+ Synth {
	update { | envir, key, object |
		//		postf("% still considering what to do with %\n", this, args);
		object.setSynthParam(this, key);
	}
}

+ Object {
	setSynthParam {} // only numbers and busses do something
}

+ SimpleNumber {
	setSynthParam { | synth, key | synth.set(key, this) } 
}

+ Bus {
	setSynthParam { | synth, key | synth.map(key, this.index) } 
}