/* 13 Aug 2023 15:53

*/

// Make this work also with UGenArrays (usuall obtained from multichannel expansion)
+ Array {
	+> { | ugenfunc | ^ugenfunc.ar(this) } // play as input to other ugen
	// use example: WhiteNoise.ar(0.1).dup +> Klank_
	// To put array in an environment, use *>  (<array> *>.envir \parameter)
	fader { | fadeTime = 0.02, amp = 1 |
		^this * Fader(fadeTime) * \amp.br(amp); // add amplitude control on bus
	}

	fader2 { | fin = 0.02, fout = 0.3, amp = 1 |
		^this * Fader2(fin, fout, amp) * \amp.br(amp); // add amplitude control on bus
	}

	fout {  | bus = \outbus, numChannels = 2, fin = 0.1, fout = 0.3, amp = 1 |
		^this.fader(fin, fout, amp).out(bus, numChannels);
	}
	// fout {  | bus = \outbus, numChannels = 2 |
	// 	^this.fader.out(bus, numChannels)
	// }

	out { | bus = \outbus, numChannels = 2 |
		^Out.ar(bus.abc(numChannels), this)
	}

	adsr { | attackTime=0.01, decayTime=0.3, sustainLevel=1, releaseTime=1.0,
		peakLevel=1.0, curve = -4.0, bias = 0.0, doneAction = 2, gate = 1 |
		^this *
		Env.adsr(attackTime, decayTime, sustainLevel, releaseTime, peakLevel, curve, bias)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
	}

	// hack: use this for control rate functions, to ensure fast release
	kdsr { | attackTime=0.001, decayTime=0.001, sustainLevel=1, releaseTime=1.0,
		peakLevel=1.0, curve = -4.0, bias = 0.0,
		doneAction = 2, gate = 1 |
		^this *
		Env.adsr(attackTime, decayTime, sustainLevel, releaseTime, peakLevel, curve, bias)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
	}
	// attackTime=0.01, releaseTime=1.0, level=1.0, curve = -4.0
	perc { | attackTime=0.01, releaseTime=1.0, level=1.0, curve = -4.0
		doneAction = 2, gate = 1 |
		^this *
		Env.perc(attackTime, releaseTime, level, curve)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
	}

	mix { ^Mix(this) }
	amix { // adjust amplitude
		^Mix(this) * this.size.reciprocal
	}

	lag { | lag = 0.1 | ^Lag.kr(this, lag); }
	amp { | attack = 0.01, decay = 0.1 | ^Amplitude.kr(this, attack, decay); }
}