//:
SynthDef(\add, {arg freq=440, gate=1, mul=0.1, out=0;
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
(freq: [400, 200, 800].prand, dur: [1/4, 1/2].prand, mul: 0.1) +> \test;
//:
\add +> (freq: [400, 200, 800].prand, dur: [1/4, 1/2].prand, mul: 0.1) +> \test;
//:
(mul: [0.01, 0.1].pwhite) ++> \test;
//:
\add +> () +> \test;
//:
(mul: [0.01, 0.1].pwhite) ++> \test;
//:
(dur: 0.1) ++> \test;
//:
(dur: [0.1, 0.2].prand) ++> \test;
//:
(freq: [400, 200, 800].prand) ++> \test;
//:
(freq: (50..100).midicps.pseq * [0.9, 1.1].pwhite) ++> \test;
//:
\add +> (dur: (50..1).normalize(0.01, 0.3).pseq) ++> \test;
//:

//:
(freq: (50..100).midicps.pseq * [0.9, 1.1].pwhite) ++> \test;
//:====================:
\add +> (dur: (1..50).normalize(0.01, 0.3).pseq) ++> \test2
//:
(freq: (100..30).midicps.pseq) ++> \test2;
//:
(mul: (0.01, 0.1).pwhite) ++> \test;
//:
(dur: (50..1).normalize(0.01, 0.3).pseq) ++> \test;
//:====================
\add +> (quant: 2, dur: 2) +> \b1;
\add +> (quant: 2, dur: 2, freq: 1000) +> \b2;

//: