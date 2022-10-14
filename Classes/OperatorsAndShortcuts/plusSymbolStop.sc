/* 24 May 2021 16:03

*/

+ Symbol {
	stop { | fadeTime = 1.0 |
		currentEnvironment[this].stop(fadeTime);
	}
	play { this.start } // synonym for start
	// start { currentEnvironment[this].start; }
	// 14 Oct 2022 23:17: enable Synth restart!
	start { currentEnvironment[this].restart(currentEnvironment, this); }
}

+ Synth {
	stop { | fadeTime = 1.0 |
		if (this.isPlaying) {
			this.release(fadeTime);
		}{
			this.onStart({ this.release(fadeTime) })
		}
	}

	restart { | envir, playerName |
		format("% +>.% %",
			SynthHistory.at(envir.name, playerName).last[1],
			envir.name, playerName.asCompileString
		).interpret;
	}
	// cannot make this work: !
	/*
	start {
		if (this.isPlaying) {
			postf("% is already playing\n", this);
		}{ 
			this.startInEnvir;
		}
	}
	*/
}

+ Nil {
	restart { | envir, playerName |
		"cannot restart:".postln;
		postln("There is no player named" + playerName + "in environment" + envir);
	}
}