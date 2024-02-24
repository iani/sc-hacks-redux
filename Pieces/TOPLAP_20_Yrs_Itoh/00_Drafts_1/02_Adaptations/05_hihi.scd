// {Crackle.ar(2, Env.perc(0, 0, 100, -100).kr)!2}.play;

//:

(
SynthDef(\hihi, {arg cp=0.8, gate=1, mul=1, out=0;
	var env, env2, osc1, lim;

	env=Env.perc(0, 0.5, 100, -100).kr;
	env2=Env.pairs([[0, 1],[6, 0]], -10).kr(2,gate);

	osc1=Crackle.ar(cp, env)!2;



	lim=Limiter.ar(osc1, 1, 0.0001);

	Out.ar(out, LeakDC.ar(lim));
}).add
)
//:

//:
(instrument: \hihi,
cp: [0.2, 0.8, 1, 2].prand,
dur: ([1/4, 1/4, 1/4, 1/4]).normalize(1, 2).pseq,
clock: TempoClock(170 / 60)
) +> \hihi
//:
(instrument: \hihi,
cp: ([0.2, 0.8, 1, 2]).normalize(0.01, 0.04).pseq,
dur: ([1/4, 1, 2, 4].flat).normalize(4, 9).pseq,
clock: TempoClock(170 / 60)
) +> \hihi
//:
(instrument: \hihi,
cp: [0.2, 0.8, 1, 2].prand,
dur: [1/4, 1/4, 1/4, 1/4].pseq,
clock: TempoClock(170 / 60)
) +> \hihi
//:

nil +> \hihi;