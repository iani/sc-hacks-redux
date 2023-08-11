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
		// TODO: kr synths should ignore fadeTime. HOW?
		// TRYING HERE: TEMPORARY!
		// postln("Synth handleReplacement defName:" + defName);
		// postln("Rate is???" + Library.at(\sdefrates, defName));
		if (this.isPlaying.not) { ^this }; // skip if already stopped
		switch(Library.at(\sdefrates, defName),
			\audio, { this.release(~release ?? { ~fadeTime ? 0.001 }); },
			\control, { this.free },
			{ this.free }
		)
	}
	isPlayer { ^true }
}

+ Bus {
	handleReplacement { this.free }
}

// Source code Moved to EventStream file:
// + EventStream {
// 	isPlayer { ^true }
// }
