//:difference of using linen vs adsr in envelopes,
//:linen
SynthDef(\linen, { | gate = 1, amp = 0.1 |
	var env, src;
	env = Env.linen(1, 1, 1);
	src = WhiteNoise.ar(amp).dup;
	Out.ar(0,
		src * env.kr(2, gate);
	)
}).add;
//:
Env.linen(1, 1, 1).plot;
//:
(instrument: \linen) +> \test;
//:
(dur: 0.1) ++> \test;
//:
(dur: 1) ++> \test;
//:adsr
SynthDef(\adsr, { | gate = 1, amp = 0.1 |
	var env, src;
	env = Env.linen(1, 1, 1);
	src = WhiteNoise.ar(amp).dup;
	Out.ar(0,
		src * env.kr(2, gate);
	)
}).add;
//:
(instrument: \adsr) +> \adsr;
//:
(instrument: \linen) +> \linen;
//:
(dur: 0.1) ++> \adsr;
//:
(dur: 0.1) ++> \linen;
//:
Env.linen(1, 1, 1).plot;
Env.adsr(1, 1, releaseTime: 0.1, curve: 0).plot;