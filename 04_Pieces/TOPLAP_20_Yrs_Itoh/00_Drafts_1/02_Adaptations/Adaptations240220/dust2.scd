//:Prototype

SynthDef(\dust, {arg freq=50, gate=1, mul=1, out=0;
	var env, env2, osc1, osc2, bpf, bpf2;

	env=Env.linen(0, 1, 0.2, 6, 0).kr(2, gate);
	env2=Env.perc(0, 0.2, 0.5, -50).kr;

	osc1=Dust.ar(1000, env, 0)!2;
	bpf=BPF.ar(osc1, SinOsc.kr(6, 0, 300, 300)+SinOsc.kr(10, 0, 200, 200), 0.5, 1, 0);

	osc2=Impulse.ar(20000, 0, env2, 0)!2;


	Out.ar(out, bpf);
}).add

//:
Synth(\dust);
//:Frequency range controls

(
SynthDef(\dust2, {arg lo1 = 0, hi1 = 600,
	lo2 = 0, hi2 = 400,
	gate=1, mul=0.5, out=0, amp = 0.1;
	var ampEnv, dustEnv, osc1, osc2, bpf, bpf2;

	ampEnv=Env.linen(0, 1, 0.2, 6, 0);
	// dustEnv=Env.perc(0, 0.2, mul, -50).kr;

	osc1=Dust.ar(1000, amp, 0)!2;
	bpf=BPF.ar(
		osc1,
		SinOsc.kr(6, 0).range(lo1, hi1)
		+SinOsc.kr(10, 0).range(lo2, hi2),
		0.5, 1, 0
	);

	Out.ar(out, bpf * ampEnv.kr(2, gate));
}).add
)
//:
Synth(\dust2);
//:
(instrument: \dust2, clock: TempoClock(125 / 60)) +> \dust2;
//:
(hi1: 100, hi2: 50) ++> \dust2;
//:
(hi1: Pwhite(10, 30, inf), hi2: Pwhite(100, 500, inf)) ++> \dust2;
//:
(dur: [1, 2, 0.5, 0.5].pseq) ++> \dust2;
//:
(dur: [1, 2, 0.5, 0.5].pseq, freq: (1..10).normalize()) ++> \dust2;
//:
(
    Pbind(*[
        instrument: \dust2,

	    out: 0,

	    dur: Pseq([1, 1, 1, 1], inf),


]).play(TempoClock(125 / 60));
)