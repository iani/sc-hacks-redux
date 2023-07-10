
Resonz_ : UGenFunc {
	*ar { | input |
		^Resonz.ar(input,
			\ringfreq.br(~ringfreq ? 500),
			\rq.br(~rq ~ 0.05)
		)
	}
}