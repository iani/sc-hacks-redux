(
SynthDef(\rock, {arg freq=1000, gate=1, mul=1.5, out=0;
	var env, osc, fil, fil2;

	env=Env.perc(0.01, 0.3, 0.5, -4).kr(2,gate);

	osc=BrownNoise.ar(env)!2;
	fil=LPF.ar(osc, freq-10, 1);
	fil2=HPF.ar(fil, freq+20, 1);
	Out.ar(out, fil2*mul);

}).add;
)

Synth(\rock,[\freq,1200,\mul,7]);

//:
(
Pbind(*[
		instrument: \rock,
	freq: Prand([800,1000,500,2000,],inf),
	dur: Pseq([1/4,1/4,1/4,1/4,1/8,1/8,1/4,1/4,1/4,Rest(2/4),1/8,1/8,1/4], inf),
		mul: 1,
	out: 3
]).play
);
//:
\rock +> () +> \test