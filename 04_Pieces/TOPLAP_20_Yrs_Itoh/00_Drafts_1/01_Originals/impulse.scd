(
SynthDef(\impulse, {arg freq=50, gate=1, mul=1, out=0;
	var env, env2, osc1, osc2, bpf, bpf2;

	env=Env.pairs([[0, 0.5],[0,0]], 0).kr(2, gate);
	env2=Env.perc(0, 2, 10000, -300).kr;

	osc1=Impulse.ar(1, 0, 0.5, env, 0)!2;

	osc2=WhiteNoise.ar(env2)!2;
	bpf=BPF.ar(osc2, 3000, 2, 1, 0).softclip*0.5;
	bpf2=BPF.ar(bpf, 3000, 2, 1, 0).softclip*0.5;

	Out.ar(out, osc1);
}).add
)

Synth(\impulse);


(
    Pbind(*[
        instrument: \impulse,

	    out: 0,

	    dur: Pseq([1, 1, 1, 1], inf),


]).play(TempoClock(125 / 60));
)

Env.pairs([[0, 1], [0.0000001, 0.5],  [3, 1.4]], 0).plot;