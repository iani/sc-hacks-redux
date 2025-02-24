(
SynthDef(\fm, {arg freq=1000, gate=1, mul=1, mul1=1000, mul2=1000, mul3=1000, out=0;
	var env, env2, osc1, osc2, osc3, osc4, osc5, bpf, bpf2;

	env=Env.perc(0, 1, mul, -10).kr(2, gate);
	env2=Env.perc(0, 0, 1, 0).kr;

	osc1=SinOsc.ar(freq*2.201, 0, mul1, 0)!2;

	osc2=SinOsc.ar(freq*1.021+osc1, 0, mul2, 0);

	osc3=SinOsc.ar(freq*3.052+osc2, 0, mul3, 0);

	osc4=SinOsc.ar(freq+osc3, 0, SinOsc.kr(4, 0 ,env, 0)+SinOsc.kr(6.910, 0 ,env, 0), 0);

	osc5=Impulse.ar(1, 0, env2, 0)!2;




	Out.ar(out, osc4);
}).add
)

Synth(\fm, [\freq, 4, \mul, 0.8, \mul1, 900, \mul2, 750, \mul3, 4000]);

(
    Pbind(*[
        instrument: \fm,

	    mul: 0.5,

	    mul1: Prand([500, 700, 1000], inf),

	    mul2: Prand([2000, 3000, 4000], inf),

	    mul3: Prand([2000, 4000, 5000], inf),

	    freq: 4,

	    out: 0,

	    dur: Pseq([1/4, 1/4, 1/4, 1/4], inf),


]).play(TempoClock(125 / 60));
)
