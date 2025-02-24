(
SynthDef(\hi2, {arg freq=50, gate=1, mul=2, out=0, cp=1;
	var env, env2, env3,  env4, imp, imp2, sin, sin2, pul2, saw, white, bpf, lpf, lpf2, lpf3, clip, limiter, comp, hi;

	env=Env.perc(0, 1, 2, -25).kr;

	imp2=Crackle.ar(cp, env)!2;
	limiter=Limiter.ar(imp2, 0.5, 0.0001);

	Out.ar(out, imp2);
}).add
)

Synth(\hi2, [\cp, 0.8]);

(
    Pbind(*[
        instrument: \hi2,

	    out: 0,

	    cp: Prand([0.8, 0.9, 1.1, 1.4], inf),

	    dur: Pseq([1/4, 1/4, 1/4, 1/4], inf),


]).play(TempoClock(125 / 60));
)