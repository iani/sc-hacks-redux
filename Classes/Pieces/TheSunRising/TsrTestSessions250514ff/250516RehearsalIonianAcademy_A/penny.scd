//Document for penny created at 250516_194900
//:[80678.788717458] penny penny 250516_195625
" !! δεν ξερω αν ακουω, βασιλη γιωργο στελενετε τεστ? !! ".postln;
//:[80678.996516333] penny penny 250516_195625
" !! δεν ξερω αν ακουω, βασιλη γιωργο στελενετε τεστ? !! ".postln;
//:[80737.270705458] penny penny 250516_195723
" !! πως θα ξερω αν λαμβανω??? !! ".postln;
//:[80737.407213625] penny penny 250516_195723
" !! πως θα ξερω αν λαμβανω??? !! ".postln;
//:[80737.569696917] penny penny 250516_195724
" !! πως θα ξερω αν λαμβανω??? !! ".postln;
//:[80752.695733667] penny penny 250516_195739
" !! οκκκ γιαννη !! ".postln;
//:[80768.675130542] penny penny 250516_195755
" !! ηχο?? !! ".postln;
//:[80780.036159833] penny penny 250516_195806
" !! θενξξ!! ".postln;
//:[81108.291627833] penny penny 250516_200334
" !! na bgc apo zoooom? !! ".postln;
//:[81108.679080167] penny penny 250516_200335
" !! na bgc apo zoooom? !! ".postln;
//:[81113.329309083] penny penny 250516_200339
" !! na bgw apo zoooom? !! ".postln;
//:[81126.321864208] penny penny 250516_200352
" !! ok !! ".postln;
//:[81332.824117083] penny penny 250516_200719
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
		// 1.1 * WhiteNoise.kr(1.1).round(0.1),
		Dseq([1, 0.9, -1, \detune.kr(1.2), 1], inf), //Dwhite(0.8, 1.2, 1)
		//1 **WhiteNoise.kr(1.1).round(0.05),
		\cpos.kr(2) + SinOsc.kr(0.5).range(-4, 4),  //centerPos ± (grainDuration/2)
		\dur.kr(1.5)/trig,  // dur of grain
		// WhiteNoise.kr(\pan.kr(0.8)),
		Dseq([\p1.kr(-1),1,-1,1], inf),
		\amp.ar(0.1),
		2
	);
	env = Env.asr(0.15,3,0.5).kr(2, \gate.kr(1));
	Out.ar(\out.ar(0), sig *env);
}).add;
)
//:[81345.867711542] penny penny 250516_200732
x = Synth(\p, [\buf, b, \l, 0, \amp, 0.1]).register;
//:[81371.640260375] penny penny 250516_200758
x = Synth(\p, [\buf, b, \l, 0, \amp, 0.1]).register;
//:[81403.235678167] penny penny 250516_200829
x.set(\l, 1);
//:[81440.473994542] penny penny 250516_200906
x.set(\rate, -1);
//:[81446.272893375] penny penny 250516_200912
x.set(\rate, 1);
//:[81449.862909958] penny penny 250516_200916
x.set(\rate, -1);
//:[81451.700604708] penny penny 250516_200918
x.set(\rate, 1);
//:[81459.861077958] penny penny 250516_200926
(
r = Routine({
	var currentPan = 0.0, nextPan, steps, stepSize;
	loop {
		nextPan = rrand(-1.0, 1.0);
		steps = 30;
		stepSize = (nextPan - currentPan) / steps;

		steps.do {
			currentPan = currentPan + stepSize;
			x.set(\pan, currentPan);
			0.01.wait;
		};
	}
}).play;
)
//:[81463.998672958] penny penny 250516_200930
(
r = Routine({
	var currentPan = 0.0, nextPan, steps, stepSize;
	loop {
		nextPan = rrand(-1.0, 1.0);
		steps = 30;
		stepSize = (nextPan - currentPan) / steps;

		steps.do {
			currentPan = currentPan + stepSize;
			x.set(\pan, currentPan);
			0.001.wait;
		};
	}
}).play;
)
//:[81467.771536042] penny penny 250516_200934
(
r = Routine({
	var currentPan = 0.0, nextPan, steps, stepSize;
	loop {
		nextPan = rrand(-1.0, 1.0);
		steps = 30;
		stepSize = (nextPan - currentPan) / steps;

		steps.do {
			currentPan = currentPan + stepSize;
			x.set(\pan, currentPan);
			0.0001.wait;
		};
	}
}).play;
)
//:[81472.172063042] penny penny 250516_200938
r.stop;
//:[81474.21306825] penny penny 250516_200940
r.stop;
//:[81480.106835125] penny penny 250516_200946
x.release(10);
//:[81502.49945875] penny penny 250516_201009
y = Synth(\grn1, [\buf, b, \tr, 0.2]);
//:[81526.649220667] penny penny 250516_201033
y.set(\tr,4);
//:[81530.947059792] penny penny 250516_201037
y.set(\tr,5);
//:[81548.009995542] penny penny 250516_201054
y.set(\tr,6);
//:[81556.315239292] penny penny 250516_201102
y.set(\tr,10);
//:[81568.893322583] penny penny 250516_201115
y.set(\dur,5); //5
//:[81590.8435275] penny penny 250516_201137
y.set(\dur,0.8); //5
//:[81599.59466325] penny penny 250516_201146
y.set(\tr,12);
//:[81611.791511417] penny penny 250516_201158
y.set(\tr,1);
//:[81637.6061745] penny penny 250516_201224
SynthDef(\grn1, {
	arg buf=0, mode=0;
	var sig, env, trig, dur;
	trig = \tr.kr(2);
	sig = TGrains.ar(
		2,
		Impulse.ar(trig),
		buf,
		// 1.1 * WhiteNoise.kr(1.1).round(0.1),
		Dseq([1, 0.9, -1, \detune.kr(1.2), 1], inf), //Dwhite(0.8, 1.2, 1)
		//1 **WhiteNoise.kr(1.1).round(0.05),
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
//:[81648.396999833] penny penny 250516_201234
y.release(5);
//:[81655.869282625] penny penny 250516_201242
y = Synth(\grn1, [\buf, b, \tr, 0.2]);
//:[81734.37098575] penny penny 250516_201400
y.set(\cpos,2);
//:[81755.38214175] penny penny 250516_201421
SynthDef(\grn1, {
	arg buf=0, mode=0;
	var sig, env, trig, dur;
	trig = \tr.kr(2);
	sig = TGrains.ar(
		2,
		Impulse.ar(trig),
		buf,
		// 1.1 * WhiteNoise.kr(1.1).round(0.1),
		// Dseq([1, 0.9, -1, \detune.kr(1.2), 1], inf), //Dwhite(0.8, 1.2, 1)
		1 **WhiteNoise.kr(1.1).round(0.05),
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
//:[81765.432165708] penny penny 250516_201431
y.release(5);
//:[81767.874970875] penny penny 250516_201434
y = Synth(\grn1, [\buf, b, \tr, 0.2]);
//:[81778.5410015] penny penny 250516_201445
y.set(\tr,0.05);
//:[81783.036834667] penny penny 250516_201449
y.set(\tr,0.005);
//:[81792.057560333] penny penny 250516_201458
y.set(\tr,0.5);
//:[81817.481181375] penny penny 250516_201523
y.set(\dur,5); //5
//:[81826.79861175] penny penny 250516_201533
y.set(\dur,10); //5
//:[81837.996714625] penny penny 250516_201544
y.set(\tr,0.1);
//:[81847.871021] penny penny 250516_201554
y.set(\tr,0.09);
//:[81858.782248375] penny penny 250516_201605
y.set(\dur, 5); //5
//:[81879.485626167] penny penny 250516_201625
y.set(\tr, 0.001);
//:[81885.312602375] penny penny 250516_201631
y.set(\cpos,3);
//:[81907.906868167] penny penny 250516_201654
y.set(\dur, 1); //5
//:[81918.770269625] penny penny 250516_201705
y.set(\tr, 2);
//:[81924.392553583] penny penny 250516_201710
y.set(\tr, 4);
//:[81978.722951917] penny penny 250516_201805
y.set(\tr,9);
//:[81998.460809917] penny penny 250516_201824
y.set(\tr,5);
//:[82029.307832625] penny penny 250516_201855
y.set(\pan,1); //5
//:[82039.940743792] penny penny 250516_201906
y.set(\tr,10);
//:[82050.026091792] penny penny 250516_201916
y.set(\dur,10); //5
//:[82050.906901625] penny penny 250516_201917
y.set(\tr,1);
//:[82070.392979458] penny penny 250516_201936
y.set(\dur,5); //5
//:[82096.519867667] penny penny 250516_202003
y.set(\tr,0.5);
//:[82107.283912833] penny penny 250516_202013
y.set(\tr,0.1);
//:[82113.972498375] penny penny 250516_202020
y.set(\dur,1);
//:[82173.166911125] penny penny 250516_202119

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
//:[82178.4699] penny penny 250516_202124
y.release(5);
//:[82182.496652875] penny penny 250516_202129
y = Synth(\grn1, [\buf, b, \tr, 0.2]);
//:[82252.967639458] penny penny 250516_202239
y.set(\dur,3);
//:[82265.696845542] penny penny 250516_202252
y.set(\tr,10);
//:[82266.878712667] penny penny 250516_202253
y.set(\dur,10);
//:[82271.454714833] penny penny 250516_202257
y.release(15);
