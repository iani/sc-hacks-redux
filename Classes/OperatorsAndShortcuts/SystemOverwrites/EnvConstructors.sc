/* 13 Aug 2023 15:56

*/

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