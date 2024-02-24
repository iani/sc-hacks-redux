(
SynthDef(\impulse2, {arg freq=440, gate=1, mul=1, out=0;
	var env, env2, osc1, osc2, bpf, bpf2, bpf3;

	env=Env.pairs([[0, 1],[3,0]], -100).kr(2, gate);
	env2=Env.perc(0, 3, 1, -50).kr;

	osc1=WhiteNoise.ar(env)!2;
	bpf=BPF.ar(osc1, 500, 1.5, 1, 0);
	bpf2=BPF.ar(osc1, 500, 1.5, 1, 0);
	bpf3=BPF.ar(osc1, 500, 1.5, 1, 0);

	osc2=Impulse.ar(2000, SinOsc.kr(100), env2, 0)!2;


	Out.ar(out, osc2);
}).add
)

Synth(\impulse2);

(
    Pbind(*[
        instrument: \impulse2,

	    out: 0,

	    dur: Pseq([1, 1, 1, 1], inf),


]).play(TempoClock(125 / 60));
)