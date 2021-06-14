/* 24 May 2021 16:03

*/

+ Symbol {
	stop { currentEnvironment[this].stop; }
	play { this.start } // synonym for start
	start { currentEnvironment[this].start; }
}

+ Synth {
	stop { this.release }
	start {
		if (this.isPlaying) {
			postf("% is already playing\n", this);
		}{ 
			this.startInEnvir;
		}
	}
}