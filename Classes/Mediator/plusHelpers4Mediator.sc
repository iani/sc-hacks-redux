/* 27 Feb 2022 12:07

*/
// other objects add more complex behavior
+ Object {
	handleReplacement {
		this.stop;
	}
	isPlayer { ^false }
}
+ Synth {
	handleReplacement {
		// requires synth state to be tracked with with onStart
		// release 0.001 stops trigger kr synths fast to prevent overlaps
		if (this.isPlaying) { this.release(~release ? 0.001); }
	}
	isPlayer { ^true }
}

+ Bus {
	handleReplacement { this.free }
}

+ EventStream {
	isPlayer { ^true }
}
