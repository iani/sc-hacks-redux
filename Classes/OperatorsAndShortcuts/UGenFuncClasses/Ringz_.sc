/*  4 Jul 2023 20:07
Experimental - for testing
*/

Ringz_ : UGenFunc {
	*ar { | input |
		^Ringz.ar(input,
			\ringfreq.br(500),
			\ringtime.br(0.05)
		)
	}
}