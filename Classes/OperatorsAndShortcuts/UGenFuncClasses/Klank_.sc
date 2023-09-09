/* Fri  7 Jul 2023 21:15

0 < stretch: < (10?)

shift by frequency cycles - or bins????
Tried out vaoues: -400, +400
*/

Klank_ : UGenFunc {
	*ar { | in |
		var freqs, amps, decays;
		freqs = ~freqs;
		amps = ((~amp ? 1) * freqs.size.reciprocal).dup(freqs.size);
		decays = (~decay ? 1).dup(freqs.size);
		^Klank.ar(`[freqs, amps, decays], in,
			(~freqscale ? 1.0),
			(~freqoffset ? 0.0),
			(~decayscale ? 1.0)
		)
	}
}