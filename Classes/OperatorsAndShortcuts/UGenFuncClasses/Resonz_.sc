
Resonz_ : UGenFunc {
	*ar { | input |
		^Resonz.ar(input,
			\ringfreq.br(500),
			\rq.br(0.05)
		)
	}
}