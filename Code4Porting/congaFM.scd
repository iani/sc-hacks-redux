// https://recarteblog.wordpress.com/
//congaFM

// 1. Añadir al servidor

(
SynthDef(\congaFM, {
	arg freq=392, mRatio3=3.48, index3=1, mRatio2=0.58, index2=8, mRatio1=0.75, index1=3, cRatio1=0.51, amp=0.8, pan=0;
	var envM3, envM2, envM1, envC1, mod3, mod2, mod1, car1, sig, rel;
	rel = LFNoise1.ar(1).range(0, 0.055);
	envM1 = EnvGen.ar(Env.perc(0, 0.055+rel, curve: -5));
	envM2 = EnvGen.ar(Env.perc(0, 0.015, curve: -5));
	envM3 = EnvGen.ar(Env.perc(0, 0.01+rel, curve: -5));
	envC1 = EnvGen.ar(Env.perc(0, 0.65-rel, curve: -5), doneAction:2);
	mod3 = SinOsc.ar((freq * mRatio3), mul:freq * mRatio3 * index3) * envM3;
	mod2 = SinOsc.ar(((freq * mRatio2) + mod3), mul:freq * mRatio2 * index2) * envM2;
	mod1 = SinOsc.ar(((freq * mRatio1) + mod2), mul:freq * mRatio1 * index1) * envM1;
	car1 = SinOsc.ar(freq * cRatio1 + mod1) * envC1;
	sig = car1 * amp;
	sig = Pan2.ar(sig, pan);
	Out.ar(0, sig);
}).add;
)

Synth(\congaFM);

// 2. Ejecutar

(
 Pbind(
	\instrument, \congaFM,
	\midinote, Pseq([Pslide([67, 69, 62, 74, 70, 65], 4, 3, 1), Pslide([67, 69, 62, 74, 70, 69], 4, 3, 1) ], inf),
	\dur, Pseq([Pn(0.25, 128), Pgeom(0.25, 1.1, 8)], 1),
	\pan, Pseq([-0.5,-0.6, 0.5, -0.5, 0.6], inf),
	\amp, Pseq([0.6, Pexprand(0.2, 0.5, 7)], inf)
).play;
)