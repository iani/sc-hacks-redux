//:map control inputs of a synth made by a function to busses,
// play synthfuncs into these buses by name.
//
// Function:play arguments:
// target, outbus: 0, fadeTime: 0.02, addAction: 'addToHead', args

BusEnvir {
	var <synthFunc, target, outbus, fadeTime, addAction, args;
	var <mainSynth, <buses, <controlSynths, controlNames;

	*new { | synthFunc, target, outbus = 0, fadeTime = 0.02, addAction = \addToHead, args |
		^this.newCopyArgs(synthFunc, target, outbus, fadeTime, addAction, args).init;
	}

	init {

	}
}