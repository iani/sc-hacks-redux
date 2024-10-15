//:
SynthDef(\add, {arg freq=440, gate=1, mul=1, out=0;
	var sin1, sin2, sin3, sin4, sin5, sin6, allsin, hpf, lpf,env;

	env=Env.perc(0.001, 0.5, 0.1, -5).kr(2,gate);

	sin1=SinOsc.ar(freq, 0, env).distort!3;
	sin2=SinOsc.ar(freq+80.589, 0, env).distort*5;
	sin3=SinOsc.ar(freq+150.522, 0, env).distort*10.5;
	sin4=SinOsc.ar(freq+220.983, 0, env).distort*20.2;
	sin5=SinOsc.ar(freq+313.134, 0, env).distort*30.47;
	sin6=SinOsc.ar(freq+429.134, 0, env).distort*40.17;
	allsin=sin1+sin2+sin3+sin4+sin5+sin6;
	hpf=HPF.ar(allsin, freq+50, 1);
	lpf=LPF.ar(hpf, freq-50, 1);

	Out.ar(out, lpf*mul);
}).add;
//:
//:
Synth(\add,[\freq,400,\mul,0.03]);
//:
(
Pbind(*[
	    instrument: \add,
	freq: Prand([400, 200, 800],inf),
		dur: Prand([Rest(7),Rest(1/2),1/4,1/4], inf),
		mul: 0.1
]).play;
)
//:
(freq: [400, 200, 800].prand, dur: [1/4, 1/2].prand, mul: 0.1) +> \test;
//:
\add +> (freq: [400, 200, 800].prand, dur: [1/4, 1/2].prand, mul: 0.1) +> \test;
//:
\add +> () +> \test;
//:
[1/4, 1/2].prand isKindOf: Pattern;