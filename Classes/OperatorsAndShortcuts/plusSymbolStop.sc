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
		"Synth does not know how to start yet. Use this only with streams".postln;
	}
}