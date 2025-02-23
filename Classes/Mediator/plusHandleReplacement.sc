/* 27 Feb 2022 12:07

	handleReplacement Called by
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
		if (this.isPlaying.not) { ^this }; // skip if already stopped
		//  free if control rate, else release!:
		switch(Library.at(\sdefrates, defName),
			\audio, { this.release(~release ?? { ~fadeTime ? 0.02 }); },
			\control, { this.free },
			{ this.free }
		)
	}
	isPlayer { ^true }
}

+ Bus {
	// when a bus is replaced, synths should unmap any controls mapped to it
	handleReplacement { | newValue, envir, key |
		postln("Bus" + this + "Handling replacement for key:" + key);
		envir.changed(\busfree, key, this);
	}
}


// Source code Moved to EventStream file:
// + EventStream {
// 	isPlayer { ^true }
// }
