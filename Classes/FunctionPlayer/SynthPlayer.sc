/* 31 May 2021 21:01
Hold a synth and the function or symbol from which it was created.
Restart the synth from the function.

(Planned for later:
Get the values of synth arguments, function fadeTime, target arguments,
from the environment. 
)
*/

SynthPlayer {
	var <sourceFunc; // produces the synth with .play
	var <synth;      // the synth produced by the function

	*new { | sourceFunc |
		^this.newCopyArgs(sourceFunc).play;
	}

	play { // TODO: should play in envir, getting parameters from it
		if (this.isPlaying) { this.stop };
		synth = sourceFunc.play;
		NodeWatcher.register(synth);
	}
	
	
	isPlaying {
		^synth.isPlaying;	
	}

	handleReplacement { this.stop }

	stop { // get fadeTime?
		if (synth.isPlaying) { synth.release }
	}

	start { // [always re]start[?]
		this.play;
	}
	
}