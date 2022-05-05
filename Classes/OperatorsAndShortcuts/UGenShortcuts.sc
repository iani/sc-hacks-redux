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
	// early test: return self. Just making sure this works
	idem { ^this }

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
		^this *
		Env.adsr(attackTime, decayTime, sustainLevel, releaseTime, peakLevel, curve, bias)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
	}
	// attackTime=0.01, releaseTime=1.0, level=1.0, curve = -4.0
	perc { | attackTime=0.01, releaseTime=1.0, level=1.0, curve = -4.0,
		doneAction = 2, gate = 1 |
		^this *
		Env.perc(attackTime, releaseTime, level, curve)
		.kr(doneAction: doneAction, gate: \gate.kr(gate))
	}

	// for SynthDef code:
	out { | out = 0 |
		^Out.perform((audio: \ar, control: \kr)[this.rate],
			\out.kr(out),
			this
		)
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

	lag { | lag = 0.1 | ^Lag.kr(this, lag); }
	amp { | attack = 0.01, decay = 0.1 | ^Amplitude.kr(this, attack, decay); }
}

+ Symbol {
	blag { | lag = 0.1 | ^this.bin.lag(lag) }
	bamp { | attack = 0.01, decay = 0.1 | ^this.bin.amp(attack, decay); }
	bin { ^this.busIn } // input from a named kr bus.
	//synonym. (sic!)
	busIn {
		// bus in
		^In.kr(this.bus.index)
	}

	trigFilter { | trigger, start = 0, step = 1 |
		// only filter the triggers by multiplying them with
		// your elements values in sequence
		^trigger * Demand.kr(trigger, 0, Dbufrd(this.buf, Dseries(start, step, inf)))
	}
}

+ Env {
	*square { | lo = 0, hi = 0.1, duration = 0.1 |
		^this.new([lo, hi, lo], [0.0, duration], \hold);
	}
}