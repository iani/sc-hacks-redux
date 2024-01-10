/*  3 Dec 2022 13:18
Shortcut for adding fade-in and fade-out (releasable) to a synthdef

a = { WhiteNoise.ar(0.1).dup * Fader() }.play;
a release: 1;

a = { WhiteNoise.ar(0.1).dup * Fader() }.play(args: [fin: 5]);
a release: 0.1;

b = { WhiteNoise.ar(0.1).dup * Fader(1) }.play;
b release: 3;
*/

// older version using Line has an abruptly ending fadeout
// TODO: Replace this with curved envelope shape.

FaderOld {
	*new { | fin = 0.01, fout = 0.3, amp = 1 |
		var finl, foutl;
		finl = Linen.kr(1, fin, 1, 1, 0);
		foutl = Linen.kr(\gate.kr(1), 0.0, 1.0, fout, 2);
		// convert amp argument to control - or keep it if it is bus-based control:
		// could not write def: ...  error ????
		// if (amp isKindOf: SimpleNumber) { amp =  \amp.kr(amp) };
		// ^finl * foutl * \amp.kr(amp);
		^finl * foutl * \amp.br(amp);
	}
}

// Prototype for new version:
// Using default GraphBuilder envelope
// This is much smoother than Fader with Linen,
// and produces a decent fadeout.
// Note: It uses my custom tweaked envelope curve with a -5 fadeout.
// See GraphBuilder:makeFadeEnv method in the present library.
// File plusGraphBuilderMakeEnv.sc
Fader {
	*new { | fadeTime = 0.02 |
		^GraphBuilder.makeFadeEnv(fadeTime);
	}
}

// Alternative version, with different fadeIn and fadeOut times.
Fader2 {
	* new {  | fadeIn = 0.02, fadeOut = 0.02, amp = 1 |
		var startVal = 0;
		if (fadeIn <= 0) { startVal = 1 };
		^Env([startVal, 1, 1, 0], [fadeIn, 1, fadeOut], [0.1, 0, -5], 2).kr(2, \gate.kr(1))
	}
}