/* 13 Aug 2023 15:51

*/

+ UGen {
	amplify { | amp = 0.1 | ^this * \amp.br(~amp ? amp) }
	amplifyl { | amp = 0.1, lim = 0.5 | ^this * (\amp.br(~amp ? amp) min: lim) }
	vol { | vol = 1 | ^this * \vol.br(~vol ? vol) }
	pan { | pan = 0 | ^Pan2.ar(this, \pan.br(~pan ? pan)) }
	panAz { | numchans = 8, pan = 0 | ^PanAz.ar(numchans, this, \pan.br(~pan ? pan)) }
	sendReply { | oscmessage = \trigger, values = 0, replyId = 1 |
		^SendReply.kr(this, oscmessage.asOscMessage, values, replyId);
	}
	mapdur { | buf | // map from 0-1 to scale matching duration of buffer selection
		^this.linlin(0, 1,
				\startframe.br(~startframe ? 0) / BufSampleRate.kr(buf),
				\endframe.br(~endframe ?? { BufFrames.kr(buf) }) / BufSampleRate.kr(buf))
	}
	// See OperatorFix240222
	// +> { | ugenfunc | ^ugenfunc.ar(this) } // play as input to other ugen
	@> { | bus, envir |
		this.bout(bus, envir)
	}
	bout { | busName, envirName | // send kr signal to a named kr bus
		Out.kr(busName.bus(nil, envirName).index, this);
	}

	br {} // return self. enable ugen args in synth func shortcuts
	// value mapping + comparing shortcuts
	lin { | lo = 0.0, hi = 1.0 | ^this.linlin(0.0, 1.0, lo, hi) }
	// exp { | lo = 0.0, hi = 1.0 | ^this.linexp(0.0, 1.0, lo, hi) }
	lt { | thresh = 0.5, lag = 0 | ^(this < thresh).lag(lag); }
	gt { | thresh = 0.5, lag = 0 | ^(this > thresh).lag(lag); }
	slope { ^Slope.kr(this) }

	// for SynthDef code:
	fout {  | bus = \outbus, numChannels = 2, fin = 0.1, fout = 0.3, amp = 1 |
		^this.fader(fin, fout, amp).out(bus, numChannels);
	}

	fader { | fadeTime = 0.01, amp = 1 |
		^this * Fader(fadeTime) * \amp.br(amp); // add amplitude control on bus
	}

	fader2 { | fin = 0.1, fout = 0.3, amp = 1 |
		^this * Fader2(fin, fout, amp) * \amp.br(amp); // add amplitude control on bus
	}

	out { | bus = \outbus, numChannels = 2 |
		^Out.ar(bus.abc(numChannels), this)
	}

	// envelope shortcuts
	adsr { | attackTime=0.01, decayTime=0.3, sustainLevel=1, releaseTime=1.0,
		peakLevel=1.0, curve = -4.0, bias = 0.0,
		doneAction = 2, gate = 1 |
		^this *
		Env.adsr(attackTime, decayTime, sustainLevel, releaseTime, peakLevel, curve, bias)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
	}

	// hack: use this for control rate functions, to ensure fast release
	kdsr { | attackTime=0.001, decayTime=0.001, sustainLevel=1, releaseTime=1.0,
		peakLevel=1.0, curve = -4.0, bias = 0.0,
		doneAction = 2, gate = 1 |
	// KEEP sustainLevel to the end. Avoid changing the control signal value!
		^this *
		Env.new(
			[0, sustainLevel, sustainLevel, sustainLevel] + bias,
			[attackTime, decayTime, releaseTime],
			curve,
			2
		)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
		// Env.adsr(attackTime, decayTime, sustainLevel, releaseTime, peakLevel, curve, bias)
	}
	// attackTime=0.01, releaseTime=1.0, level=1.0, curve = -4.0
	perc { | attackTime=0.01, releaseTime=1.0, level=1.0, curve = -4.0,
		doneAction = 2, gate = 1 |
		// postln("debugging ugen perc" + attackTime + releaseTime + level + curve + doneAction + gate);
		^this *
		Env.perc(attackTime, releaseTime, level, curve)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
	}

	decay { | dt = 0.25 | ^Decay.kr(this, dt) }

	perctr { | attackTime=0.01, releaseTime=1.0, level=1.0, curve = -4.0, gate = 1 |
		^Env.perc(attackTime, releaseTime, level, curve).kr(doneAction: 0, gate: this)
	}

	inrange {  | inMin = 0.47, inMax = 0.53, outMin = 0.0, outMax = 1.0, clip = \minmax |
		// scale input from named bus from estimated range of sensestage sensors
		// to the range specified.
		^In.kr(this.bus.index).linlin(inMin, inMax, outMin, outMax, clip);
	}
	lag { | lag = 0.1 | ^Lag.kr(this, lag); }
	amp { | attack = 0.01, decay = 0.1 | ^Amplitude.kr(this, attack, decay); }
}