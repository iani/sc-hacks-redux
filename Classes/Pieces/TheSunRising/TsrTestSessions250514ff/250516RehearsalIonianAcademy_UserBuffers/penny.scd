//Document for penny created at 250515_102734
//:[5938.604076] penny penny 250515_231045
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
}, \penny);
//:[5961.493474208] penny penny 250515_231108
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
}, \penny);
//:[5975.435376167] penny penny 250515_231121
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
}, \penny);
//:[6036.218730583] penny penny 250515_231222
Tsr.doOnVerse({ | index, verse, incipit |
	"PENNY".postln;
	~verses[index].postln;
}, \penny);
//:[6156.207070375] penny penny 250515_231422
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
	(instrument: \pinch, freq: char*2, ffreq: char / 2).play;
	(instrument: \pinch, freq: char*4, ffreq: char).play;
}, \penny);
//:[6198.0053965] penny penny 250515_231504
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
}, \penny);
//:[6235.123938875] penny penny 250515_231541
Tsr.undoOnType({}, \penny);
//:[6241.642404667] penny penny 250515_231548
Tsr.undoOnType({}, \penny);
//:[6241.991289917] penny penny 250515_231548
Tsr.undoOnType({}, \penny);
//:[6242.188836583] penny penny 250515_231548
Tsr.undoOnType({}, \penny);
//:[6242.356936458] penny penny 250515_231548
Tsr.undoOnType({}, \penny);
//:[6252.89010475] penny penny 250515_231559
Tsr.undoOnType;
//:[6258.73201025] penny penny 250515_231605
Tsr.undoOnType();
//:[6260.51793275] penny penny 250515_231607
Tsr.undoOnType();
//:[6260.694796083] penny penny 250515_231607
Tsr.undoOnType();
//:[6263.233625333] penny penny 250515_231609
Tsr.undoOnType({});
//:[6315.188367833] penny penny 250515_231701
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
}, \penny);
//:[6364.341807375] penny penny 250515_231750
~incipits;
//:[6367.811752792] penny penny 250515_231754
~verses;
//:[6373.076335875] penny penny 250515_231759
~verses;
//:[6380.030989542] penny penny 250515_231806
~incipits;
//:[6414.172289792] penny penny 250515_231840
~all;
//:[6417.888974625] penny penny 250515_231844
~all;
//:[6428.715057208] penny penny 250515_231855
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
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

	   	Select.kr(mode, [
			1,
			Demand.kr(trig, 0, Dwhite(0.8, 1.2, inf)),
			1.1 * WhiteNoise.kr(1.1).round(0.1)
		]),
		1, // Dseq([1, 0.9, -1, \detune.kr(1.2), 1], inf), //Dwhite(0.8, 1.2, 1)
		//1 **WhiteNoise.kr(1.1).round(0.05),
		\cpos.kr(2) + SinOsc.kr(0.5).range(-5, 5),  //centerPos ± (grainDuration/2)
		\dur.kr(1.5)/trig,  // dur of grain
		// WhiteNoise.kr(\pan.kr(0.8)),
		Dseq([-1,1], inf),
		\amp.ar(0.1),
		2
	);
	env = Env.asr(0.15,3,0.5).kr(2, \gate.kr(1));
	Out.ar(\out.ar(0), sig *env);
}).add;
//:[6440.156069125] penny penny 250515_231906
~vrs = Synth(\p, [\buf, ~busy1, \l, 1, \amp, 0.05]);
//:[6442.963743625] penny penny 250515_231909
~vrs.release(3);
//:[6506.865580833] penny penny 250515_232013
~incipits; //0-29
//:[6538.506965083] penny penny 250515_232045
~incipits.at(0);
//:[6611.37467075] penny penny 250515_232157
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
}, \penny);
//:[6656.96640775] penny penny 250515_232243
("~" ++ ~incipits[0] ++ "1").interpret
//:[6674.689022833] penny penny 250515_232301
("~" ++ ~incipits[0] ++ "1").interpret
//:[6690.710388417] penny penny 250515_232317
~vrs = Synth(\p, [\buf, ("~" ++ ~incipits[0] ++ "1").interpret, \l, 1, \amp, 0.05]);
//:[6694.942081125] penny penny 250515_232321
~vrs.release(3);
//:[6714.77243475] penny penny 250515_232341
("~" ++ ~incipits[0] ++ "1").interpret.play
//:[6738.348489667] penny penny 250515_232404
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
	("~" ++ ~incipits[0] ++ "1").interpret;
}, \penny);
//:[6803.599315125] penny penny 250515_232510
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
	~actual = ("~" ++ ~incipits[0] ++ "1").interpret;
}, \penny);
//:[6853.753278917] penny penny 250515_232600
~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.05]);
//:[6888.5527175] penny penny 250515_232635
~vrs.release(3);
//:[6891.781270375] penny penny 250515_232638
~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
//:[6903.467022792] penny penny 250515_232649
~vrs.set(\amp, 0.1);
//:[6907.469991375] penny penny 250515_232653
~vrs.set(\amp, 0.05);
//:[7035.934925583] penny penny 250515_232902
~vrs.release(3);
//:[7116.843486833] penny penny 250515_233023
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
	~actual = ("~" ++ ~incipits[index] ++ "1").interpret;
	~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[7159.560320625] penny penny 250515_233106
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
	~actual = ("~"++~incipits[index]++"1").interpret;
	~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);

//:[7188.912525917] penny penny 250515_233135
~vrs.release(3);
//:[7301.783502292] penny penny 250515_233328
	~vrs.release(1);
//:[7377.414600958] penny penny 250515_233443
~actual.postln;
//:[7380.33734] penny penny 250515_233446
~actual.postln;
//:[7383.279658875] penny penny 250515_233449
~actual.postln;
//:[7386.802405875] penny penny 250515_233453
~actual.postln;
//:[7408.508630417] penny penny 250515_233515
~actual.postln;
//:[7425.667415708] penny penny 250515_233532
Tsr.doOnVerse({ | index, verse, incipit |
	~verses[index].postln;
	~actual = ("~"++~incipits[index]++"1").interpret;
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[7427.850956917] penny penny 250515_233534
~actual.postln;
//:[7449.722265042] penny penny 250515_233556
s.meter
//:[7479.530559792] penny penny 250515_233626
~actual.postln;
//:[7550.00588625] penny penny 250515_233736
	"penny".postln;
//:[7552.711403833] penny penny 250515_233739
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index].postln;
	~actual = ("~"++~incipits[index]++"1").interpret;
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[7555.016940042] penny penny 250515_233741
~actual.postln;
//:[7613.9189455] penny penny 250515_233840
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[7620.666932958] penny penny 250515_233847
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index].postln;
	~actual = ("~"++~incipits[index]++"1").interpret;
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[7629.602614708] penny penny 250515_233856
~actual.postln;
//:[7640.734721667] penny penny 250515_233907
~actual.postln;
//:[7663.46093325] penny penny 250515_233929
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index].postln;
	~actual = ("~"++~incipits[index]++"1").interpret;
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[7676.879201083] penny penny 250515_233943
~actual.postln;
//:[7681.840231625] penny penny 250515_233948
~actual;
//:[7721.146326625] penny penny 250515_234027
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index.asInteger].postln;
	~actual = ("~"++~incipits[index]++"1").interpret;
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[8027.249802917] penny penny 250515_234533
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index.asInteger].postln;
	~actual = currentEnvironment[(~incipits[index]++1).asSymbol];
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);

//:[8044.571814333] penny penny 250515_234551
~actual;
//:[8062.228037042] penny penny 250515_234608
~actual;
//:[8090.06510275] penny penny 250515_234636
~actual;
//:[8109.677323167] penny penny 250515_234656
~actual;
//:[8169.586325292] penny penny 250515_234756
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index.asInteger].postln;
	~actual = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[8194.654958083] penny penny 250515_234821
~actual;
//:[8236.808166167] penny penny 250515_234903
~actual;
//:[8250.0619215] penny penny 250515_234916
~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
//:[8256.531155042] penny penny 250515_234923
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(0), sig *env);
}).add;
//:[8260.688964458] penny penny 250515_234927
~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
//:[8274.385291917] penny penny 250515_234940
~vrs.release(3);
//:[8387.170762208] penny penny 250515_235133
f
//:[8389.684795667] penny penny 250515_235136
f
//:[8406.881753083] penny penny 250515_235153
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[8424.207564667] penny penny 250515_235210
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.2]);
//:[8426.91439375] penny penny 250515_235213
~vrs.release(3);
//:[8442.32475075] penny penny 250515_235228
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.2]);
//:[8529.411486375] penny penny 250515_235355
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[8535.175432167] penny penny 250515_235401
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[8539.682724375] penny penny 250515_235406
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(0), sig *env);
}).add;
//:[8551.900002583] penny penny 250515_235418
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.2]);
//:[8562.593557917] penny penny 250515_235429
~vrs.release(3);
//:[8603.576196208] penny penny 250515_235510
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index.asInteger].postln;
	~verseBuf = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);

//:[8616.685909333] penny penny 250515_235523
~vrs = Synth(\p, [\buf, ~verseBuf, \l, 1, \amp, 0.2]);
//:[8630.184037083] penny penny 250515_235536
~vrs.release(3);
//:[8637.130388458] penny penny 250515_235543
~vrs = Synth(\p, [\buf, ~verseBuf, \l, 1, \amp, 0.4]);
//:[8643.229895542] penny penny 250515_235549
~vrs.release(3);
//:[8659.161405958] penny penny 250515_235605
Tsr.doOnVerse({ | index, verse, incipit |
	"penny".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
}, \penny);
//:[8676.060657625] penny penny 250515_235622
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.4]);
//:[8680.802584583] penny penny 250515_235627
~vrs.release(3);
//:[8721.781911333] penny penny 250515_235708
~georgios
//:[8787.763001333] penny penny 250515_235814
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[8792.62671325] penny penny 250515_235819
~vrs.set(\rate, 1);
//:[8798.558006958] penny penny 250515_235825
~vrs.set(\rate, -1);
//:[8805.07832825] penny penny 250515_235831
~vrs.release(3);
//:[8809.731635917] penny penny 250515_235836
~vrs2 = Synth(\grn1, [\buf, b, \tr, 0.2]);
//:[8815.375040667] penny penny 250515_235841
SynthDef(\grn1, {
	arg buf=0, mode=0;
	var sig, env, trig, dur;
	trig = \tr.kr(2);
	sig = TGrains.ar(
		2,
		Impulse.ar(trig),
		buf,

	   	Select.kr(mode, [
			1,
			Demand.kr(trig, 0, Dwhite(0.8, 1.2, inf)),
			1.1 * WhiteNoise.kr(1.1).round(0.1)
		]),
		1, // Dseq([1, 0.9, -1, \detune.kr(1.2), 1], inf), //Dwhite(0.8, 1.2, 1)
		//1 **WhiteNoise.kr(1.1).round(0.05),
		\cpos.kr(2) + SinOsc.kr(0.5).range(-5, 5),  //centerPos ± (grainDuration/2)
		\dur.kr(1.5)/trig,  // dur of grain
		// WhiteNoise.kr(\pan.kr(0.8)),
		Dseq([-1,1], inf),
		\amp.ar(0.1),
		2
	);
	env = Env.asr(0.15,3,0.5).kr(2, \gate.kr(1));
	Out.ar(\out.ar(0), sig *env);
}).add;
//:[8819.444490917] penny penny 250515_235845
~vrs2 = Synth(\grn1, [\buf, b, \tr, 0.2]);
//:[8828.257840458] penny penny 250515_235854
~vrs2.set(\tr,4);
//:[8832.554555792] penny penny 250515_235859
~vrs2.release(5);
//:[9024.044611417] penny penny 250516_000210
Ndef(\grn2, {
	arg buf=0;
    var trate, clk, pos;

	trate = MouseY.kr(0.01,120,1);
	// trate = SinOsc.kr(0.04).range(0.1, 150);
    clk = Impulse.kr(trate);

	pos = MouseX.kr(0,BufDur.kr(buf) + TRand.kr(0, 0.01, clk));
	// pos = 10 + TRand.kr(0, 0.2, clk);

    TGrains.ar(
		2,
		clk,
		buf,
		1,
		pos,
		2 / trate, // dur of grain
		WhiteNoise.kr(0.6),
		0.3);
});

//:[9027.963891958] penny penny 250516_000214
Ndef(\grn2).play;
//:[9041.825360042] penny penny 250516_000228
Ndef(\grn2).clear(10);
//:[9065.879858458] penny penny 250516_000252
Ndef(\grn2).play;
//:[9088.888916833] penny penny 250516_000315
Ndef(\grn2).clear(10);
//:[9099.607657875] penny penny 250516_000326
Ndef(\grn2, {
	arg buf=b;
    var trate, clk, pos;

	trate = MouseY.kr(0.01,120,1);
	// trate = SinOsc.kr(0.04).range(0.1, 150);
    clk = Impulse.kr(trate);

	pos = MouseX.kr(0,BufDur.kr(buf) + TRand.kr(0, 0.01, clk));
	// pos = 10 + TRand.kr(0, 0.2, clk);

    TGrains.ar(
		2,
		clk,
		buf,
		1,
		pos,
		2 / trate, // dur of grain
		WhiteNoise.kr(0.6),
		0.3);
});

//:[9114.784470208] penny penny 250516_000341
Ndef(\grn2).play;
//:[9119.215929917] penny penny 250516_000345
Ndef(\grn2).set(\buf, b);
//:[9131.100307042] penny penny 250516_000357
Ndef(\grn2).clear(10);
//:[9144.016867792] penny penny 250516_000410
s.meter
//:[9152.956545458] penny penny 250516_000419
Ndef(\grn2).play;
//:[9155.223247583] penny penny 250516_000421
Ndef(\grn2).set(\buf, b);
//:[9171.010812792] penny penny 250516_000437
Ndef(\grn2).play;
//:[9174.3968995] penny penny 250516_000440
Ndef(\grn2).play;
//:[9182.256664417] penny penny 250516_000448
Ndef(\grn2, {
	arg buf=0;
    var trate, clk, pos;

	trate = MouseY.kr(0.01,120,1);
	// trate = SinOsc.kr(0.04).range(0.1, 150);
    clk = Impulse.kr(trate);

	pos = MouseX.kr(0,BufDur.kr(buf) + TRand.kr(0, 0.01, clk));
	// pos = 10 + TRand.kr(0, 0.2, clk);

    TGrains.ar(
		2,
		clk,
		buf,
		1,
		pos,
		2 / trate, // dur of grain
		WhiteNoise.kr(0.6),
		0.3);
});


//:[9187.712617625] penny penny 250516_000454
Ndef(\grn2).play;
//:[9189.691250542] penny penny 250516_000456
Ndef(\grn2).fadeTime = 2;
//:[9220.367727833] penny penny 250516_000526
Ndef(\grn2).clear(10);
//:[9331.700413875] penny penny 250516_000718
~vrs.release(3);
//:[9347.2839255] penny penny 250516_000733
~vrs2.set(\buf, b);
//:[9438.716440708] penny penny 250516_000905
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[9453.350735583] penny penny 250516_000919
~vrs.set(\buf, b);
//:[9471.130624042] penny penny 250516_000937
~vrs.set(\buf, b);
//:[9476.300328625] penny penny 250516_000942
~vrs.release(3);
//:[9498.454621458] penny penny 250516_001004
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[9501.58672825] penny penny 250516_001008
~vrs.set(\buf, b);
//:[9502.820618958] penny penny 250516_001009
~vrs.set(\buf, b);
//:[9503.93830575] penny penny 250516_001010
~vrs.set(\buf, b);
//:[9504.707225875] penny penny 250516_001011
~vrs.set(\buf, b);
//:[9504.834891625] penny penny 250516_001011
~vrs.set(\buf, b);
//:[9505.007522083] penny penny 250516_001011
~vrs.set(\buf, b);
//:[9505.123362792] penny penny 250516_001011
~vrs.set(\buf, b);
//:[9505.256300625] penny penny 250516_001011
~vrs.set(\buf, b);
//:[9505.356166083] penny penny 250516_001011
~vrs.set(\buf, b);
//:[9505.457302583] penny penny 250516_001011
~vrs.set(\buf, b);
//:[9505.573794292] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9505.673133] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9505.771423542] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9505.910777458] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9505.987764083] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9506.088227917] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9506.221723708] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9506.323651083] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9506.421531583] penny penny 250516_001012
~vrs.set(\buf, b);
//:[9506.523038583] penny penny 250516_001013
~vrs.set(\buf, b);
//:[9506.638469625] penny penny 250516_001013
~vrs.set(\buf, b);
//:[9506.753645125] penny penny 250516_001013
~vrs.set(\buf, b);
//:[9506.870627708] penny penny 250516_001013
~vrs.set(\buf, b);
//:[9506.986734167] penny penny 250516_001013
~vrs.set(\buf, b);
//:[9507.072538417] penny penny 250516_001013
~vrs.set(\buf, b);
//:[9508.850100042] penny penny 250516_001015
~vrs.release(3);
//:[9556.505978042] penny penny 250516_001103
	if...
//:[9620.577189333] penny penny 250516_001207
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
	// if...
}, \penny);

//:[9677.995459792] penny penny 250516_001304
	"Verse changed".postln;
//:[9913.289873458] penny penny 250516_001659
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	// ~vrs.release(1);
	// ~vrs = Synth(\p, [\buf, ~actual, \l, 1, \amp, 0.2]);
	~vrs ?? {~vrs.set(\buf, b)};
}, \penny);
//:[9918.78789875] penny penny 250516_001705
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[9951.165241625] penny penny 250516_001737
~vrs.release(3);
//:[9995.951314375] penny penny 250516_001822
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	~vrs ?? {~vrs.set(\buf, b)};
}, \penny);

//:[10001.688202583] penny penny 250516_001828
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[10029.052704792] penny penny 250516_001855
~vrs.set(\buf, b);
//:[10034.607682083] penny penny 250516_001901
~vrs.release(3);
//:[10066.378737167] penny penny 250516_001932
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	~vrs.postln;
	~vrs ?? {~vrs.set(\buf, b)};
}, \penny);

//:[10071.3762645] penny penny 250516_001937
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[10102.735034417] penny penny 250516_002009
~vrs.release(3);
//:[10207.321948083] penny penny 250516_002153
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	~vrs.postln;
	~vrs ?? {~vrs.set(\buf, b)};
}, \penny);
//:[10211.732622292] penny penny 250516_002158
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(~actual0), sig *env);
}).add;
//:[10217.698541833] penny penny 250516_002204
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[10219.087697708] penny penny 250516_002205
~vrs.release(3);
//:[10301.167526833] penny penny 250516_002327
s.meter;
//:[10359.045200833] penny penny 250516_002425
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	~vrs.postln;
	~vrs ?? {~vrs.set(\buf, b)};
}, \penny);
//:[10804.966705083] penny penny 250516_003151
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	~vrs.postln;
	~vrs ?? {~vrs.set(\buf, b)};
}, \penny);
//:[10864.679632958] penny penny 250516_003251
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	~vrs.postln;
	// ~vrs ?? {~vrs.set(\buf, b)};
	if (~vrs.isPlaying) {
		~vrs.set(\buf, ~verses1[index]);
	};

}, \penny);

//:[10913.476453375] penny penny 250516_003339
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[10962.146703625] penny penny 250516_003428
~vrs.release(3);
//:[10994.234251542] penny penny 250516_003500
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[11012.566449042] penny penny 250516_003519
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11031.045258667] penny penny 250516_003537
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(~actual0), sig *env);
}).add;
//:[11033.707258167] penny penny 250516_003540
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(~actual0), sig *env);
}).add;
//:[11055.233382792] penny penny 250516_003601
~vrs.release(3);
//:[11068.334328333] penny penny 250516_003614
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11102.372276833] penny penny 250516_003648
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(~actual0), sig *env);
}).add;
//:[11106.151108875] penny penny 250516_003652
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	~vrs.postln;
	// ~vrs ?? {~vrs.set(\buf, b)};
	if (~vrs.isPlaying) {
		~vrs.set(\buf, ~verses1[index]);
	};

}, \penny);
//:[11114.19425325] penny penny 250516_003700
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11121.100944875] penny penny 250516_003707
~vrs.release(3);
//:[11196.984214083] penny penny 250516_003823
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	~vrs.postln;
	// ~vrs ?? {~vrs.set(\buf, b)};
	if (~vrs.isPlaying) {
		~vrs.set(\buf, b);
	};

}, \penny);
//:[11205.966781583] penny penny 250516_003832
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(~actual0), sig *env);
}).add;
//:[11209.224117375] penny penny 250516_003835
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11216.046424042] penny penny 250516_003842
~vrs.release(3);
//:[11221.0156335] penny penny 250516_003847
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11293.132797083] penny penny 250516_003959
~vrs.release(3);
//:[11302.45602425] penny penny 250516_004008
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11341.9887665] penny penny 250516_004048
~vrs.release(3);
//:[11392.439949042] penny penny 250516_004138
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];

	// ~vrs ?? {~vrs.set(\buf, b)};
	if (~vrs.isPlaying) {
		~vrs.postln;
		~vrs.set(\buf, b);
	};

}, \penny);
//:[11597.295908625] penny penny 250516_004503
x = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11604.125285167] penny penny 250516_004510
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
//:[11621.648270125] penny penny 250516_004528
x = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11653.12644325] penny penny 250516_004559
x = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
//:[11680.452586167] penny penny 250516_004626
~vrs.release(3);
//:[11683.05931525] penny penny 250516_00462//:[80167.774290208] penny penny 250516_194754
" !!!! AKOYSA TOY VASILI GIA LIGO !!!! ~penny".postln;
//:[80213.732170917] penny penny 250516_194840
(1..17) collect: { | i | ("mbus"++i).asSymbol };
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
//:[80104.631775458] penny penny 250516_194651
(1.5)
//:[80106.5563195] penny penny 250516_194653
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
//:[79702.689521625] penny penny 250516_194009
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
		Dseq([-1,1], inf),
		\amp.ar(0.1),
		2
	);
	env = Env.asr(0.15,3,0.5).kr(2, \gate.kr(1));
	Out.ar(\out.ar(0), sig *env);
}).add;
)
//:[79776.595911917] penny penny 250516_194123
x = Synth(\p, [\buf, b, \l, 0, \amp, 0.1]).register;
//:[79784.36551025] penny penny 250516_194130
x.set(\l, 1);
//:[79811.077846583] penny penny 250516_194157
x.set(\spos, 1);
//:[79812.530013083] penny penny 250516_194159
x.set(\rate, -1);
//:[79815.255113417] penny penny 250516_194201
x.release(3);
//:[79829.225163958] penny penny 250516_194215
x.release(10);
//:[79895.408504] penny penny 250516_194321
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[79974.6761565] penny penny 250516_194441
"clarooooooo".postln;
//:[79974.903544042] penny penny 250516_194441
"clarooooooo".postln;
//:[79975.054888167] penny penny 250516_194441
"clarooooooo".postln;
//:[79975.173452583] penny penny 250516_194441
"clarooooooo".postln;
//:[79975.271347583] penny penny 250516_194441
"clarooooooo".postln;
//:[80036.535584] penny penny 250516_194543
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
//:[80080.432816958] penny penny 250516_194626
"den exv hxo!!!!!!!!!!!".postln;
//:[80080.736166208] penny penny 250516_194627
"den exv hxo!!!!!!!!!!!".postln;
//:[80080.835979458] penny penny 250516_194627
"den exv hxo!!!!!!!!!!!".postln;
//:[80080.936259333] penny penny 250516_194627
"den exv hxo!!!!!!!!!!!".postln;
//:[80081.053513458] penny penny 250516_194627
"den exv hxo!!!!!!!!!!!".postln;
//:[80081.169572542] penny penny 250516_194627
"den exv hxo!!!!!!!!!!!".postln;
//:[80081.285559125] penny penny 250516_194627
"den exv hxo!!!!!!!!!!!".postln;
//:[80081.387482792] penny penny 250516_194627
"den exv hxo!!!!!!!!!!!".postln;
//:[80081.485028375] penny penny 250516_194627
"den exv hxo!!!!!!!!!!!".postln;
//:[80081.585320958] penny penny 250516_194628
"den exv hxo!!!!!!!!!!!".postln;
//:[80087.699605833] penny penny 250516_194634
"den exv hxo!!!!!!!!!!! penny".postln;
//:[80087.842302333] penny penny 250516_194634
"den exv hxo!!!!!!!!!!! penny".postln;
//:[80087.960360583] penny penny 250516_194634
"den exv hxo!!!!!!!!!!! penny".postln;
//:[80088.075024] penny penny 250516_194634
"den exv hxo!!!!!!!!!!! penny".postln;
//:[80088.173909458] penny penny 250516_194634
"den exv hxo!!!!!!!!!!! penny".postln;
//:[80088.290863208] penny penny 250516_194634
"den exv hxo!!!!!!!!!!! penny".postln;
//:[80088.409169792] penny penny 250516_194634
"den exv hxo!!!!!!!!!!! penny".postln;
9
~vrs.release(3);
//:[11688.387225] penny penny 250516_004634
x.release(3);
