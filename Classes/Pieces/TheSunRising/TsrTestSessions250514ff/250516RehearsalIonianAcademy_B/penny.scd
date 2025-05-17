//Document for penny created at 250516_202332
//:[85207.706255125] penny penny 250516_211154
(
b = ~busy1;
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	// ~vrs ?? {~vrs.set(\buf, b)};
	if (x.isPlaying) {
		x.postln;
		x.set(\buf, b);
	};
}, \penny);


SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0.1).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.kr(0.5));
	Out.ar(\out.ar(0), sig *env);
}).add;

SynthDef(\grn1, {
	arg buf=0, mode=0;
	var sig, env, trig, dur;
	trig = \tr.kr(2);
	sig = TGrains.ar(
		2,
		Impulse.ar(trig),
		buf,
		Dseq([1, 0.9, -1, \detune.kr(1.2), 1], inf), //Dwhite(0.8, 1.2, 1)
		// 1 **WhiteNoise.kr(1.1).round(0.05),
		\cpos.kr(2) + SinOsc.kr(0.5).range(-4, 4),  //centerPos ± (grainDuration/2)
		\dur.kr(1.5)/trig,  // dur of grain
		WhiteNoise.kr(\pan.kr(0.8)),
		// Dseq([\p1.kr(-1),1,-1,1], inf),
		\amp.ar(0.1),
		2
	);
	env = Env.asr(0.15,3,0.5).kr(2, \gate.kr(1));
	Out.ar(\out.ar(0), sig *env);
}).add;
)
//:[85424.757391292] penny penny 250516_211531
x.set(\l, 1);
