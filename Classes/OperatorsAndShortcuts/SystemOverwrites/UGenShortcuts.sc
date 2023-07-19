/* 30 Jan 2022 10:44
Adding some commmonly used ugens to a signal source:

Basic:
	Gated adsr envelope

To explore:
	PV effects
	Grain effects
	Pan (various versions)
	Resonance and filters
*/

+ UGen {
	mapdur { | buf | // map from 0-1 to scale matching duration of buffer selection
		^this.linlin(0, 1,
				\startframe.br(~startframe ? 0) / buf.sampleRate,
				\endframe.br(~endframe ?? { buf.numFrames }) / buf.sampleRate)
	}
	+> { | ugenfunc | ^ugenfunc.ar(this) } // play as input to other ugen
	br {} // return self. enable ugen args in synth func shortcuts
	// value mapping + comparing shortcuts
	lin { | lo = 0.0, hi = 1.0 | ^this.linlin(0.0, 1.0, lo, hi) }
	exp { | lo = 0.0, hi = 1.0 | ^this.linexp(0.0, 1.0, lo, hi) }
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
		^this *
		Env.perc(attackTime, releaseTime, level, curve)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
	}

	bout { | busName | // send kr signal to a named kr bus
		Out.kr(busName.bus.index, this);
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

// Make this work also with UGenArrays (usuall obtained from multichannel expansion)
+ Array {
	+> { | ugenfunc | ^ugenfunc.ar(this) } // play as input to other ugen
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

+ Symbol {
	trigFilter { | trigger, start = 0, step = 1 |
		// filter the triggers by multiplying them with
		// your elements values in sequence
		^trigger * Demand.kr(trigger, 0, Dbufrd(this.buf, Dseries(start, step, inf)))
	}

	in { | numChannels = 2 | ^In.ar(this.ab(numChannels)) }
	ab { | numChannels = 2 | ^AudioBus(this, numChannels) }
	abc { | numChannels = 2 | ^\out.kr(AudioBus(this, numChannels).index.postln) }
	asAudioBus {  | numChannels = 2 | ^this.ab(numChannels) }
}

+ Nil {
	asAudioBus { ^0 }
}

+ SimpleNumber {
	asAudioBus { ^this.asInteger }
}

+ Bus {
	asAudioBus { ^this }
}

+ Env {
	*square { | level = 0.1, duration = 0.1 |
		^this.step([0, level, 0], [0.0, duration, 0.0]);
	}

	// flat adsr
	*fdsr { | attackTime = 0.01, releaseTime = 1.0, peakLevel = 1.0, curve = -4.0, bias = 0.0 |
		// attackTime=0.01, decayTime=0.3, sustainLevel=0.5,
		// releaseTime=1.0, peakLevel=1.0, curve = -4.0, bias = 0.0;
		^this.adsr(attackTime, 0.3, 1, releaseTime, peakLevel, curve, bias);
	}

	gate { | gate = \gate, start = 1 | // if start is 1, triggers immediately
		^this.kr(gate: gate.kr(start)); // if symbol, read from bus;
	}

	bgate { | bus = \bus | // does not set bus
		// will be silent unless bus is already > 0.
		^this.kr(gate: bus.br)
	}
}