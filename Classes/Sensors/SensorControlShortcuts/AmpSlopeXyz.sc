/*  8 Jul 2023 11:26
Sum Amplitude of Slope of x, y, z
*/

AmpSlopeXyz {
	*new { | id = 1, lag = 0.15 |
		^[\x, \y, \z].collect({ | s |
			Amplitude.kr(Slope.kr(s.snum(id).sr)).lag(lag)
		}).sum
	}
}

// Shortcut:

Xyz : AmpSlopeXyz {}