/*  3 Dec 2022 13:18
Shortcut for adding fade-in and fade-out (releasable) to a synthdef

a = { WhiteNoise.ar(0.1).dup * Fader() }.play;
a release: 1;

a = { WhiteNoise.ar(0.1).dup * Fader() }.play(args: [fin: 5]);
a release: 0.1;

b = { WhiteNoise.ar(0.1).dup * Fader(1) }.play;
b release: 3;
*/

Fader {
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
