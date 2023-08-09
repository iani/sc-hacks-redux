/*  8 Jul 2023 11:26
Sum Amplitude of Slope of x, y, z
*/

AmpSlopeXyz {
	*new { | id = 1, lag = 0.5 |
		^[\x, \y, \z].collect({ | s | Amplitude.kr(Slope.kr(s.snum(1).sr), 0.001, 1).lag(lag)}).sum;
	}
}

// revised version - should be in different name
/*
AmpSlopeXyz {
	*new { | id = 1, lag = 0.5 |
		var a, b;
		a = [\x, \y, \z].collect({ | s | Amplitude.kr(Slope.kr(s.snum(1).sr), 0.001, 1).lag(lag)}).sum;
		b = [\x, \y, \z].collect({ | s | Amplitude.kr(Slope.kr(s.snum(2).sr), 0.001, 1).lag(lag)}).sum;
		^a + b
	}
}
*/

// Shortcut:

Xyz : AmpSlopeXyz {}
//: