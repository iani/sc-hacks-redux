/* 27 Feb 2022 12:07

*/
// other objects add more complex behavior
+ Object {
	handleReplacement {
		this.stop;
	}
}
+ Synth {
	handleReplacement {
		// requires synth state to be tracked with with onStart
		// release 0.001 stops trigger kr synths fast to prevent overlaps
		if (this.isPlaying) { this.release(~release ? 0.001); }
	}
}

+ Bus {
	handleReplacement { this.free }
}
/*
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
*/