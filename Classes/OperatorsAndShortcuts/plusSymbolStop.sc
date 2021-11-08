/* 24 May 2021 16:03

*/

+ Symbol {
	stop { | fadeTime = 1.0 |
		currentEnvironment[this].stop(fadeTime);
	}
	play { this.start } // synonym for start
	start { currentEnvironment[this].start; }
}

+ Synth {
	stop { | fadeTime = 1.0 | this.release(fadeTime) }
	start {
		if (this.isPlaying) {
			postf("% is already playing\n", this);
		}{ 
			this.startInEnvir;
		}
	}
}