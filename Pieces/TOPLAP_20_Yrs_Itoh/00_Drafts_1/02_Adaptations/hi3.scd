(
SynthDef(\hi3, {arg freq=50, gate=1, mul=2, out=0, cp=1;
	var env, env2, osc1 ,limiter, fil1;

	env=Env.perc(0, 0.1, 0.7, 0).kr;
	env2=Env.pairs([[0, 1],[0.05, 0.1], [0.05,0]], 0).kr(2, gate);


	osc1=WhiteNoise.ar(env2)!2;
	fil1=HPF.ar(osc1, Env.pairs([[0, 0],[0.01,1000]], 0).kr, 0.5, 0);
	limiter=Limiter.ar(fil1, 0.5, 0.0001);

	Out.ar(out, fil1);
}).add
)

Synth(\hi3,);

(
    Pbind(*[
        instrument: \hi3,

	    out: 0,

	    cp: Prand([0.8, 0.9, 1.1, 1.4], inf),

	    dur: Pseq([1/4, 1/4, 1/4, 1/4], inf),


]).play(TempoClock(125 / 60));
)