(
SynthDef(\mid, {arg freq=5000, gate=1, mul=1, out=0;
	var env, env2, osc1, osc2, bpf, bpf2;

	env=Env.perc(0, 0, mul, 0).kr;
	env2=Env.perc(0, 2, 10000, -300).kr(2, gate);

	osc1=Impulse.ar(1, 0, 0.5, env, 0)!2;

	osc2=WhiteNoise.ar(env2)!2;
	bpf=BPF.ar(osc2, 3000, 2, 1, 0).softclip*0.5;
	bpf2=BPF.ar(bpf, 3000, 2, 1, 0).softclip*0.5;

	Out.ar(out, bpf2);
}).add
)

Synth(\mid);

(
    Pbind(*[
        instrument: \mid,

	    out: 0,

	    dur: Pseq([1, 1, 1, 1], inf),


]).play(TempoClock(125 / 60));
)
