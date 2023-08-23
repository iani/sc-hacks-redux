/*  8 Jul 2023 11:26
Sum Amplitude of Slope of x, y, z
*/

AmpSlopeXyz {
	*new { | id = 1, lag = 0.5, attack = 0.001, release = 1, mul = 0.1, sub = 0.1, lag2 = 0.5 |
		// ^[\x, \y, \z].collect({ | s | Amplitude.kr(Slope.kr(s.snum(id).sr), 0.001, 1).lag(lag)}).sum;
		^[\x, \y, \z].collect({ | s | s.snum(id).ampSlope(attack, release).lag(lag)})
		.sum * mul - sub max: 0 lag: lag2;
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