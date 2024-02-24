{Crackle.ar(2, Env.perc(0, 0, 100, -100).kr)!2}.play;

(
SynthDef(\hi, {arg cp=0.8, gate=1, mul=1, out=0;
	var env, env2, osc1, lim;

	env=Env.perc(0, 0.5, 100, -100).kr;
	env2=Env.pairs([[0, 1],[6, 0]], -10).kr(2,gate);

	osc1=Crackle.ar(Env.pairs([[0, 0.5],[1, 0.4]], 0).kr, env2)!2;

	lim=Limiter.ar(osc1, 1, 0.0001);

	Out.ar(out, lim);
}).add
)

Synth(\hi);


(
    Pbind(*[
        instrument: \hi,

	    out: 0,

	    cp: Prand([0.8, 0.4, 0.2, 0.1], inf),

	    dur: Pseq([1/4, 1/4, 1/4, 1/4], inf),


]).play(TempoClock(125 / 60));
)