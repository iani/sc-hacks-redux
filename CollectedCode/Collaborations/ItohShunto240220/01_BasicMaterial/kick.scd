

//:
(
SynthDef(\kick1, {arg freq=50, gate=1, mul=2, out=0;
	var env, env2, env3,  env4, imp, imp2, sin, sin2, pul2, saw, white, bpf, lpf, lpf2, lpf3, clip, limiter, comp;

	env=Env.perc(0, 0.01, 3, -4).kr;
	env2=Env.perc(0.0, 0.15, 1.5, -1).kr(2,gate);
	env3=Env.perc(0.0, 0.06, 1, 0).kr;
	env4=Env.perc(0.0, 0.05, 1, 0).kr;


	imp=Impulse.ar(1, 1, 0.5, env, 0)!2;

	sin=SinOsc.ar(freq, 3, env2, 0)!2;

	sin2=SinOsc.ar(100, 3, env3, 0)!2;
	clip=Clip.ar(sin2, -0.6, 0.6);

	imp2=Dust2.ar(20000, env4, 0)!2;
	bpf=BPF.ar(imp2, 5000, 0.35, 0.4, 0).distort*0.03;

	Out.ar(out, imp+sin+clip+bpf*0.3);
}).add
)
//:
Synth(\kick1);
//:
(
    Pbind(*[
        instrument: \kick1,

	    out: 0,

        freq: 45,

	    dur: Pseq([1, 1, 1, 1], inf),


]).play(TempoClock(125 / 60));
)
//:
(instrument: \kick1, freq: 45) +> \test;
//:
(dur: [0.5, 0.25, 0.25].pseq) ++> \test;
//:
(dur: [0.5, 0.25, 0.25].prand) ++> \test;
//:
(freq: (40..80).pseq.midicps) ++> \test;
//:
(freq: (40..80).prand.midicps) ++> \test;
//:

(degree: 10, dur: 5).play;
//:
(instrument: \kick1).play;
//:
(instrument: \kick1) +> \test;
//:
(instrument: \kick1) +> \test;
(instrument: \kick1) +> \test2;
//:
(freq: 60.midicps) ++> \test2;
(freq: 55.midicps) ++> \test;

(dur: 0.5) ++> \test;

//:
(
SynthDef(\kick2, {arg freq=50, gate=1, mul=2, out=0;
	var env, env2, env3,  env4, imp, imp2, sin, sin2, pul2, saw, white, bpf, lpf, lpf2, lpf3, clip, limiter, comp, hi;

	env=Env.perc(0, 0.0, 2.5, 0).kr;
	env2=Env.perc(0, 0.15, 2, 0).kr(2,gate);
	env3=Env.perc(0.0, 0.06, 1.1, 1).kr;
	env4=Env.perc(0.0, 0.05, 1, 1).kr;


	imp=Impulse.ar(1, 1, 0.5, env, 0)!2;

	imp2=Crackle.ar(1.95, Env.perc(0, 0.002, 100, 0).kr)!2;
	limiter=Limiter.ar(imp2, 0.5, 0.0001);

	sin=SinOsc.ar(Env.pairs([[0, freq*2],[0.03, freq]]).kr, 2, env2, 0)!2;

	sin2=SinOsc.ar(100, 3, env3, 0)!2;
	clip=Clip.ar(sin2, -0.6, 0.6);

	hi=WhiteNoise.ar(env4)!2;
	bpf=BPF.ar(hi, 1000, 1, 100000, 0).softclip*0.003;

	Out.ar(out, imp+limiter+sin+clip*0.3);
}).add
)

{SinOsc.ar(50, 2, 0.5)}.plot;

Env.pairs([[0, 50],[0.05, 50*2]]).plot;

Synth(\kick2);

(
    Pbind(*[
        instrument: \kick2,

	    out: 0,

        freq: 45,

	    dur: Pseq([1, 1, 1, 1], inf),


]).play(TempoClock(125 / 60));
)


a={Crackle.ar(1.99, Env.perc(0, 0, 1000, 0).kr)!2 }
a.play;















