/* 31 May 2021 21:01
Hold a synth and the function or symbol from which it was created.
Restart the synth from the function.

(Planned for later:
Get the values of synth arguments, function fadeTime, target arguments,
from the environment. 
)
*/

SynthPlayer {
	var <def; // produces the synth with .play
	var <name;
	var <desc, <args;
	var <synth;      // the synth produced by the function

	*new { | sourceFunc, name |
		^this.newCopyArgs(sourceFunc, name.asSymbol).initDef.play;
	}

	initDef { // replace function initially stored in def by new def
		def = def.asSynthDef(fadeTime: ~fadeTime, name: name);
		desc = def.asSynthDesc;
		postf("Created def: %, named: % and got desc: %\n", def, name, desc);
	}

	play { // TODO: should play in envir, getting parameters from it
		"This is SynthPlayer:play. I am doing nothing for now.".postln;
		//		this.stop; // always replace previous synth
		// synth = sourceFunc.playInEnvir;
		// NodeWatcher.register(synth);
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