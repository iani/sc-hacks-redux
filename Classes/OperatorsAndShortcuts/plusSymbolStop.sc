/* 24 May 2021 16:03

*/

+ Symbol {
	stop { currentEnvironment[this].stop; }
	start { currentEnvironment[this].start; }
	play { this.start }
}

+ Synth {
	stop { this.release }
	start {
		"Synth does not know how to start yet. Use this only with streams".postln;
	}
}