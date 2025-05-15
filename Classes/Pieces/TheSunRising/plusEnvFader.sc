// 水 14  5 2025 18:09
// An easy way to create releasable synths

+ Env {
	*fader {
		^this.adsr.kr(\gate.kr(1), doneAction: 2)
	}
}

/* Example
a = { SinOsc.ar(400, 0, 0.2) * Env.fader }.play;
a release: 2;

*/