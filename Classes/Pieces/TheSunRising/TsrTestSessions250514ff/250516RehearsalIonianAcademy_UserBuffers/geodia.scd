//Document for geodia created at 250515_102734
//:[9078.373995458] geodia geodia 250515_211747
(
// Testing TsrDocument;
TsrDocument(\geodia);
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
	(instrument: \pinch, freq: char*2, ffreq: char / 2).play;
}, \geodia);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
	~verses[index].postln;
}, \geodia);
)
//:[9138.253947917] geodia geodia 250515_211847
(
// Testing TsrDocument;
TsrDocument(\geodia);
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
	(instrument: \pinch, freq: char*2, ffreq: char / 2).play;
	(instrument: \pinch, freq: char*4, ffreq: char).play;
}, \geodia);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	// postln("====== index" + index);
	// postln("====== verse ======" + verse);
	// postln("====== incipit" + incipit);
	~verses[index].postln;
}, \geodia);
)
//:[9227.286814875] geodia geodia 250515_212016
TypingListener();
//:[9283.839654125] geodia geodia 250515_212113
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[9327.344778083] geodia geodia 250515_212156
TsrDocument(\geodia);
//:[27.526827083] geodia geodia 250515_213214
)
//:[80.905686792] geodia geodia 250515_213307
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	// postln("====== index" + index);
	// postln("====== verse ======" + verse);
	// postln("====== incipit" + incipit);
}, \geodia);

//:[1584.358399625] geodia geodia 250515_215810
	signal = SinOsc.ar(freq) * env;
//:[1589.362963583] geodia geodia 250515_215815
SynthDef(\pinch, { |pos=0.0, amp=1.0|
	var signal, env;
	env = EnvGen.ar(Env.perc(0.01, 0.1), doneAction: 2);
	signal = SinOsc.ar(freq) * env;
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;

//:[1878.769933583] geodia geodia 250515_220305
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
//:[1881.020093667] geodia geodia 250515_220307
(
//:[2264.055810458] geodia geodia 250515_220930
72
//:[2283.93133275] geodia geodia 250515_220950
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[2293.929104125] geodia geodia 250515_221000
TypingListener();
//:[2294.876955208] geodia geodia 250515_221001
TsrDocument(\geodia);
//:[2304.256670542] geodia geodia 250515_221010
)
//:[2316.969766042] geodia geodia 250515_221023
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
}, \geodia);

//:[2446.59364975] geodia geodia 250515_221233
~texts
//:[2449.59619125] geodia geodia 250515_221236
~ints
//:[2454.689102792] geodia geodia 250515_221241
~floats
//:[2690.417984583] geodia geodia 250515_221636
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[2801.121878792] geodia geodia 250515_221827
TypingListener();
//:[2801.781296375] geodia geodia 250515_221828
TsrDocument(\geodia);
//:[2806.396496667] geodia geodia 250515_221832
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
}, \geodia);

//:[2894.676730167] geodia geodia 250515_222001
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[2926.825968917] geodia geodia 250515_222033
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[2927.995790625] geodia geodia 250515_222034
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[2929.052319625] geodia geodia 250515_222035
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[2931.147623208] geodia geodia 250515_222037
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[2931.531233667] geodia geodia 250515_222038
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[2948.3922575] geodia geodia 250515_222054
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[2949.472104917] geodia geodia 250515_222055
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[3137.617505792] geodia geodia 250515_222404
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[3304.225507333] geodia geodia 250515_222650
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[3310.086883792] geodia geodia 250515_222656
TypingListener();
//:[3310.883668042] geodia geodia 250515_222657
TsrDocument(\geodia);
//:[3317.699039125] geodia geodia 250515_222704
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
//:[4082.834394792] geodia geodia 250515_223949
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[4182.012731625] geodia geodia 250515_224128
TypingListener();
//:[4182.712263625] geodia geodia 250515_224129
TsrDocument(\geodia);
//:[4185.231809417] geodia geodia 250515_224131
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[4395.611734] geodia geodia 250515_224502
72
//:[5292.714751583] geodia geodia 250515_225959
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[5345.410952667] geodia geodia 250515_230051
TypingListener();
//:[5346.176144458] geodia geodia 250515_230052
TsrDocument(\geodia);
//:[5348.723278875] geodia geodia 250515_230055
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[5363.012117917] geodia geodia 250515_230109
Busy old fool, unruly Sun,
//:[5779.187054417] geodia geodia 250515_230805
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[5779.515718667] geodia geodia 250515_230806
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[5780.015659542] geodia geodia 250515_230806
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[5781.277375125] geodia geodia 250515_230807
(instrument: \pinch, freq: 220, ffreq: 22).play;
//:[6079.875199083] geodia geodia 250515_231306
(instrument: \pinch, freq: 1220, ffreq: 22).play;
//:[6080.436891625] geodia geodia 250515_231306
(instrument: \pinch, freq: 1220, ffreq: 22).play;
//:[6080.734458375] geodia geodia 250515_231307
(instrument: \pinch, freq: 1220, ffreq: 22).play;
//:[6080.964869667] geodia geodia 250515_231307
(instrument: \pinch, freq: 1220, ffreq: 22).play;
//:[6383.104647083] geodia geodia 250515_231809
~all
//:[6422.958921125] geodia geodia 250515_231849
~all
//:[6425.729663542] geodia geodia 250515_231852
~all
//:[6498.383863083] geodia geodia 250515_232004
~incipit
//:[6513.089560375] geodia geodia 250515_232019
~incipits
//:[6524.436420458] geodia geodia 250515_232030
~incipits[0]
//:[6534.6735615] geodia geodia 250515_232041
~incipits[0].class
//:[6562.213126583] geodia geodia 250515_232108
\busy
//:[6622.774064958] geodia geodia 250515_232209
("~" + ~incipits[0] + "1").interpret
//:[6632.729656042] geodia geodia 250515_232219
("~" ++ ~incipits[0] ++ "1").interpret
//:[6644.574255458] geodia geodia 250515_232231
'("~" ++ ~incipits[0] ++ "1").interpret'.postln;
//:[7315.847745042] geodia geodia 250515_233342
~vrs.stop
//:[7361.376049875] geodia geodia 250515_233427
Cmd.period
//:[7377.466260958] geodia geodia 250515_233443
72
//:[7762.538558292] geodia geodia 250515_234109
~all
//:[7931.919440083] geodia geodia 250515_234358
~incipits[0]
//:[8331.475755125] geodia geodia 250515_235037
\something !!! "this is GD"
//:[8340.63572] geodia geodia 250515_235047
\something
//:[8377.600773208] geodia geodia 250515_235124
f = "this is GD"
//:[8451.777135] geodia geodia 250515_235238
b
//:[8470.485611917] geodia geodia 250515_235256
currentEnvironment[(~incipits[0]++1).asSymbol].play;
//:[8706.092620208] geodia geodia 250515_235652
~georgios = 1;
//:[11178.376757875] geodia geodia 250516_003804
~verses1
//:[11181.809083833] geodia geodia 250516_003808
~verses1.size
//:[11377.6618695] geodia geodia 250516_004124
b
//:[11380.770312125] geodia geodia 250516_004127
b.play
//:[11442.619705333] geodia geodia 250516_004229
b.play;
//:[12224.111243833] geodia geodia 250516_005530
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[12237.633258083] geodia geodia 250516_005544
TypingListener();
//:[12238.432300125] geodia geodia 250516_005544
TsrDocument(\geodia);
//:[12241.347540292] geodia geodia 250516_005547
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[12323.8401105] geodia geodia 250516_005710
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
	(instrument: \pinch, freq: char*5, ffreq: char / 2).play;
	(instrument: \pinch, freq: char*8, ffreq: char / 3).play;
}, \geodia);

//:[12439.120093167] geodia geodia 250516_005905
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env;
	env = EnvGen.ar(Env.perc(0.01, 0.1), doneAction: 2);
	signal = SinOsc.ar(freq+SinOsc.ar(ffreq,0,freq)) * env;
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[13101.728617208] geodia geodia 250516_011008
72
//:[13939.11600125] geodia geodia 250516_012405
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[13962.703717042] geodia geodia 250516_012429
TypingListener();
//:[13966.653015125] geodia geodia 250516_012433
TsrDocument(\geodia);
//:[13978.079860375] geodia geodia 250516_012444
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[14030.437528292] geodia geodia 250516_012536
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[14036.741198583] geodia geodia 250516_012543
TypingListener();
//:[14042.27662025] geodia geodia 250516_012548
TsrDocument(\geodia);
//:[14050.171514542] geodia geodia 250516_012556
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[14762.181644792] geodia geodia 250516_013748
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[14769.96710275] geodia geodia 250516_013756
TypingListener();
//:[14771.734482458] geodia geodia 250516_013758
TsrDocument(\geodia);
//:[14779.814099958] geodia geodia 250516_013806
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	~verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[14868.004500083] geodia geodia 250516_013934
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[15425.365160083] geodia geodia 250516_014851
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[15437.010319417] geodia geodia 250516_014903
TypingListener();
//:[15439.48869575] geodia geodia 250516_014905
TsrDocument(\geodia);
//:[15447.991490167] geodia geodia 250516_014914
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[15959.027303458] geodia geodia 250516_015745
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[15969.714902125] geodia geodia 250516_015756
TypingListener();
//:[15974.168005208] geodia geodia 250516_015800
TsrDocument(\geodia);
//:[15983.927295] geodia geodia 250516_015810
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[16097.042740417] geodia geodia 250516_020003
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[16386.46714675] geodia geodia 250516_020452
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[16881.928879792] geodia geodia 250516_021308
TypingListener();
//:[16883.485613917] geodia geodia 250516_021309
TsrDocument(\geodia);
//:[16888.82334375] geodia geodia 250516_021315
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[17130.117053542] geodia geodia 250516_021716
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[17143.934753667] geodia geodia 250516_021730
TypingListener();
//:[17145.483651625] geodia geodia 250516_021731
TsrDocument(\geodia);
//:[17151.765092583] geodia geodia 250516_021738
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[17388.028157375] geodia geodia 250516_022134
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[17417.842849708] geodia geodia 250516_022204

postln("Disabled User class
FAILURE IN SERVER /s_new Group 1 not found
FAILURE IN SERVER /s_new Group 1 not found
FAILURE IN SERVER /s_new Group 1 not found
FAILURE IN SERVER /s_new Group 1 not found
")
//:[17487.547978667] geodia geodia 250516_022314
TypingListener();
//:[17488.991097333] geodia geodia 250516_022315
TsrDocument(\geodia);
//:[17493.245669292] geodia geodia 250516_022319
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[17883.243744125] geodia geodia 250516_022949
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[17888.375542292] geodia geodia 250516_022954
TypingListener();
//:[17893.16893325] geodia geodia 250516_022959
TsrDocument(\geodia);
//:[17899.778553167] geodia geodia 250516_023006
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);

//:[20665.897744042] geodia geodia 250516_031612
TsrDocument(\geodia);
//:[20670.288990208] geodia geodia 250516_031616
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[20672.987278542] geodia geodia 250516_031619
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[20955.128565542] geodia geodia 250516_032101
TsrDocument(\geodia);
//:[20960.977696792] geodia geodia 250516_032107
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[20963.209849042] geodia geodia 250516_032109
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[21017.137922125] geodia geodia 250516_032203
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[21022.71192775] geodia geodia 250516_032209
TypingListener();
//:[21024.8943115] geodia geodia 250516_032211
TsrDocument(\geodia);
//:[21029.408666208] geodia geodia 250516_032215
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[21079.2122305] geodia geodia 250516_032305
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[21085.937658333] geodia geodia 250516_032312
TypingListener();
//:[21087.803286125] geodia geodia 250516_032314
TsrDocument(\geodia);
//:[21091.795193083] geodia geodia 250516_032318
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[21197.348822417] geodia geodia 250516_032503
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[21249.837166167] geodia geodia 250516_032556
TypingListener();
//:[21253.707779208] geodia geodia 250516_032600
TsrDocument(\geodia);
//:[21260.485086125] geodia geodia 250516_032606
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[21334.745840208] geodia geodia 250516_032721
TypingListener();
//:[21413.281574333] geodia geodia 250516_032839
TypingListener();
//:[21517.053571583] geodia geodia 250516_033023
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[21533.679621958] geodia geodia 250516_033040
TypingListener();
//:[21535.480307458] geodia geodia 250516_033041
TsrDocument(\geodia);
//:[21538.55986875] geodia geodia 250516_033045
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[21599.31169825] geodia geodia 250516_033145
TypingListener();
//:[21682.048193375] geodia geodia 250516_033308
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[21699.222433542] geodia geodia 250516_033325
TypingListener();
//:[21700.898624708] geodia geodia 250516_033327
TsrDocument(\geodia);
//:[21705.244552833] geodia geodia 250516_033331
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[21783.240762208] geodia geodia 250516_033449
TypingListener();
//:[22042.9379215] geodia geodia 250516_033909
Synth(\grainAn);
//:[22071.748664] geodia geodia 250516_033938
Synth(\grainAn, [\sndbuf, ~i1.bufnum]);
//:[22076.76931525] geodia geodia 250516_033943
~i1.bufnum
//:[22081.387643375] geodia geodia 250516_033947
~i1
//:[22344.132286167] geodia geodia 250516_034410
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[22345.993426375] geodia geodia 250516_034412
TypingListener();
//:[22348.334104208] geodia geodia 250516_034414
TsrDocument(\geodia);
//:[22350.991521667] geodia geodia 250516_034417
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[22474.334578875] geodia geodia 250516_034620
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[22488.184551875] geodia geodia 250516_034634
TypingListener();
//:[22490.433264458] geodia geodia 250516_034636
TsrDocument(\geodia);
//:[22494.463606667] geodia geodia 250516_034640
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[22560.262766] geodia geodia 250516_034746
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[22592.089577125] geodia geodia 250516_034818
TypingListener();
//:[22594.139778292] geodia geodia 250516_034820
TsrDocument(\geodia);
//:[22597.55139975] geodia geodia 250516_034824
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[22768.265145208] geodia geodia 250516_035114
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[22776.550920083] geodia geodia 250516_035123
TypingListener();
//:[22778.188238292] geodia geodia 250516_035124
TsrDocument(\geodia);
//:[22780.938187583] geodia geodia 250516_035127
(
Tsr.doOnVerse({ | index, verse, incipit |
	">> New verse received: ".post;
	TsrPoem.verses[index].postln;
	">> Class: ".post; verse.class.postln;
}, \geodia);
)
//:[77933.061830417] geodia geodia 250516_191039
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/100).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo/2), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, WhiteNoise.ar),0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[77944.6988835] geodia geodia 250516_191051
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/100).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, WhiteNoise.ar),0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[78005.721662625] geodia geodia 250516_191152
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/100).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, PinkNoise.ar), 0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[78108.092859042] geodia geodia 250516_191334
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/100).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, BrownNoise.ar), 0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[78145.948983458] geodia geodia 250516_191412
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/10).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, BrownNoise.ar), 0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[79932.302584625] geodia geodia 250516_194358
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.554543542] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.588457375] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.625701875] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.6583445] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.690837417] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.721275792] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.756107833] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.788879458] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.82072] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79932.876297125] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.088228792] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.328751375] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.360560917] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.394049875] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.438747042] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.472814625] geodia geodia 250516_194359
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.503862167] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.537291375] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.57525425] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.60439825] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.637316417] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.677492875] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.704212625] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.7377645] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.772078875] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.794552958] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.828553833] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.861095083] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.904599167] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.93762675] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79933.970502042] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79934.0036145] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79934.037983375] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79934.071553375] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79934.106079958] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79934.137587625] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79934.170388542] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79934.204022167] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79934.237171708] geodia geodia 250516_194400
"IGNORE COMPILE ERRORS FROM DIFFUSION"
//:[79977.641878083] geodia geodia 250516_194444
"AKOUME"
//:[79977.909791] geodia geodia 250516_194444
"AKOUME"
//:[79977.931154833] geodia geodia 250516_194444
"AKOUME"
//:[79977.962418708] geodia geodia 250516_194444
"AKOUME"
//:[79977.996746375] geodia geodia 250516_194444
"AKOUME"
//:[79978.029619458] geodia geodia 250516_194444
"AKOUME"
//:[79978.062240958] geodia geodia 250516_194444
"AKOUME"
//:[79978.094822958] geodia geodia 250516_194444
"AKOUME"
//:[79978.128803458] geodia geodia 250516_194444
"AKOUME"
//:[79978.175486917] geodia geodia 250516_194444
"AKOUME"
//:[79978.1953735] geodia geodia 250516_194444
"AKOUME"
//:[79978.230275583] geodia geodia 250516_194444
"AKOUME"
//:[79978.264100167] geodia geodia 250516_194444
"AKOUME"
//:[79978.295880167] geodia geodia 250516_194444
"AKOUME"
//:[79978.345790042] geodia geodia 250516_194444
"AKOUME"
//:[79978.361778458] geodia geodia 250516_194444
"AKOUME"
//:[79978.394764333] geodia geodia 250516_194444
"AKOUME"
//:[79978.428952708] geodia geodia 250516_194444
"AKOUME"
//:[79978.461085583] geodia geodia 250516_194444
"AKOUME"
//:[80008.28626675] geodia geodia 250516_194514
"DOULEUEI AS mhn kanoume recompile, unless necessary"
//:[80008.682175292] geodia geodia 250516_194515
"DOULEUEI AS mhn kanoume recompile, unless necessary"
//:[80009.065673292] geodia geodia 250516_194515
"DOULEUEI AS mhn kanoume recompile, unless necessary"
//:[80011.446567042] geodia geodia 250516_194517
"GD: DOULEUEI AS mhn kanoume recompile, unless necessary"
//:[80011.625741375] geodia geodia 250516_194518
"GD: DOULEUEI AS mhn kanoume recompile, unless necessary"
//:[80014.506451333] geodia geodia 250516_194521
"GD: DOULEUEI AS mhn kanoume recompile, unless necessary"
//:[80021.525874208] geodia geodia 250516_194528
"GD: OK"
//:[80021.667335458] geodia geodia 250516_194528
"GD: OK"
//:[80021.8781985] geodia geodia 250516_194528
"GD: OK"
//:[80027.375011625] geodia geodia 250516_194533
"GD: GOGOGO"
//:[80027.554891083] geodia geodia 250516_194534
"GD: GOGOGO"
//:[80028.301315583] geodia geodia 250516_194534
"GD: GOGOGO"
//:[80028.480182458] geodia geodia 250516_194534
"GD: GOGOGO"
//:[80034.824354958] geodia geodia 250516_194541
"GD: XEKINAW"
//:[80035.307479583] geodia geodia 250516_194541
"GD: XEKINAW"
//:[80035.421347958] geodia geodia 250516_194541
"GD: XEKINAW"
//:[80035.578520958] geodia geodia 250516_194542
"GD: XEKINAW"
//:[80035.760875833] geodia geodia 250516_194542
"GD: XEKINAW"
//:[80036.130005375] geodia geodia 250516_194542
"GD: XEKINAW"
//:[80036.28302775] geodia geodia 250516_194542
"GD: XEKINAW"
//:[80036.390854292] geodia geodia 250516_194542
"GD: XEKINAW"
//:[80036.565445] geodia geodia 250516_194543
"GD: XEKINAW"
//:[80036.633122333] geodia geodia 250516_194543
"GD: XEKINAW"
//:[80036.748407125] geodia geodia 250516_194543
"GD: XEKINAW"
//:[80036.907588292] geodia geodia 250516_194543
"GD: XEKINAW"
//:[80037.279833417] geodia geodia 250516_194543
"GD: XEKINAW"
//:[80037.440118333] geodia geodia 250516_194543
"GD: XEKINAW"
//:[80037.640927708] geodia geodia 250516_194544
"GD: XEKINAW"
//:[80037.729362208] geodia geodia 250516_194544
"GD: XEKINAW"
//:[80037.912069208] geodia geodia 250516_194544
"GD: XEKINAW"
//:[80038.075189583] geodia geodia 250516_194544
"GD: XEKINAW"
//:[80038.273646333] geodia geodia 250516_194544
"GD: XEKINAW"
//:[80041.259462958] geodia geodia 250516_194547
"GD: 5"
//:[80042.562282917] geodia geodia 250516_194549
"GD: 4"
//:[80043.8006915] geodia geodia 250516_194550
"GD: 3"
//:[80044.970469375] geodia geodia 250516_194551
"GD: 2"
//:[80046.2293705] geodia geodia 250516_194552
"GD: 1"
//:[80062.744297417] geodia geodia 250516_194609
"PAIZOUME"
//:[80062.916818708] geodia geodia 250516_194609
"PAIZOUME"
//:[80063.668810042] geodia geodia 250516_194610
"PAIZOUME"
//:[80063.891244583] geodia geodia 250516_194610
"PAIZOUME"
//:[80064.149795417] geodia geodia 250516_194610
"PAIZOUME"
//:[80067.319000958] geodia geodia 250516_194613
"GD: PAIZOUME"
//:[80067.519117208] geodia geodia 250516_194614
"GD: PAIZOUME"
//:[80067.882784292] geodia geodia 250516_194614
"GD: PAIZOUME"
//:[80074.300301458] geodia geodia 250516_194620
"GD: EXOUME HXO EDW"
//:[80083.299376625] geodia geodia 250516_194629
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80083.4476915] geodia geodia 250516_194629
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80083.705118208] geodia geodia 250516_194630
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80084.293299833] geodia geodia 250516_194630
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80085.370534583] geodia geodia 250516_194631
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80085.576912833] geodia geodia 250516_194632
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80085.77511025] geodia geodia 250516_194632
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80086.720610167] geodia geodia 250516_194633
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80087.110305667] geodia geodia 250516_194633
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80087.350865042] geodia geodia 250516_194633
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80088.303468417] geodia geodia 250516_194634
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80089.82514425] geodia geodia 250516_194636
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80091.068759042] geodia geodia 250516_194637
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80093.602951125] geodia geodia 250516_194640
"GD: EXOUME HXO EDW, GIANNI EXEIS HXO?"
//:[80099.804293125] geodia geodia 250516_194646
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/10).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, BrownNoise.ar), 0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[80146.27862075] geodia geodia 250516_194732
"TA EXW TA CLKCIK"
//:[80156.773456] geodia geodia 250516_194743
"GDL: TA EXW TA CLKCIK -- den ta akoute ekei?"
//:[80156.8897315] geodia geodia 250516_194743
"GDL: TA EXW TA CLKCIK -- den ta akoute ekei?"
//:[80158.83409425] geodia geodia 250516_194745
"GD: TA EXW TA CLKCIK -- den ta akoute ekei?"
//:[80158.946523125] geodia geodia 250516_194745
"GD: TA EXW TA CLKCIK -- den ta akoute ekei?"
//:[80159.156104125] geodia geodia 250516_194745
"GD: TA EXW TA CLKCIK -- den ta akoute ekei?"
//:[80204.665222] geodia geodia 250516_194831
TypingListener();
//:[80206.396797292] geodia geodia 250516_194832
TsrDocument(\geodia);
//:[80208.248460667] geodia geodia 250516_194834
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[80217.074528625] geodia geodia 250516_194843
">>> GD: Ekana reboot"
//:[80217.256620333] geodia geodia 250516_194843
">>> GD: Ekana reboot"
//:[80217.413534375] geodia geodia 250516_194843
">>> GD: Ekana reboot"
//:[80217.822160958] geodia geodia 250516_194844
">>> GD: Ekana reboot"
//:[80217.975819292] geodia geodia 250516_194844
">>> GD: Ekana reboot"
//:[80221.510991542] geodia geodia 250516_194848
(
Tsr.doOnVerse({ | index, verse, incipit |
	"\n>> New verse received: ".post;
	TsrPoem.verses[index].postln;
}, \geodia);
)
//:[80277.334755417] geodia geodia 250516_194943
"
//:[80278.435997083] geodia geodia 250516_194944
">>> GD: Akous click, Iani?"
//:[80278.592998167] geodia geodia 250516_194945
">>> GD: Akous click, Iani?"
//:[80278.727447875] geodia geodia 250516_194945
">>> GD: Akous click, Iani?"
//:[80279.111853583] geodia geodia 250516_194945
">>> GD: Akous click, Iani?"
//:[80280.42957125] geodia geodia 250516_194946
">>> GD: Akous click, Iani?"
//:[80280.605126167] geodia geodia 250516_194947
">>> GD: Akous click, Iani?"
//:[80280.804265958] geodia geodia 250516_194947
">>> GD: Akous click, Iani?"
//:[80311.161224667] geodia geodia 250516_195017
">>> GD: Kanw recompile"
//:[80311.278834417] geodia geodia 250516_195017
">>> GD: Kanw recompile"
//:[80311.47912325] geodia geodia 250516_195017
">>> GD: Kanw recompile"
//:[80311.64533575] geodia geodia 250516_195018
">>> GD: Kanw recompile"
//:[80311.782273708] geodia geodia 250516_195018
">>> GD: Kanw recompile"
//:[80311.928605167] geodia geodia 250516_195018
">>> GD: Kanw recompile"
//:[80312.016770292] geodia geodia 250516_195018
">>> GD: Kanw recompile"
//:[80325.033141042] geodia geodia 250516_195031
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[80383.780086333] geodia geodia 250516_195130
TypingListener();
//:[80385.312589042] geodia geodia 250516_195131
TsrDocument(\geodia);
//:[80393.716053625] geodia geodia 250516_195140
(
Tsr.doOnVerse({ | index, verse, incipit |
	"\n>> New verse received: ".post;
	TsrPoem.verses[index].postln;
}, \geodia);
)
//:[80406.407232583] geodia geodia 250516_195152
Busy old fool, unruly Sun,
//:[80438.286729083] geodia geodia 250516_195224
">>> GD: Akous click, Iani?"
//:[80438.497712042] geodia geodia 250516_195225
">>> GD: Akous click, Iani?"
//:[80546.804622958] geodia geodia 250516_195413
">>> GD: H Penny einai ONLINE"
//:[80547.299839667] geodia geodia 250516_195413
">>> GD: H Penny einai ONLINE"
//:[80577.733997458] geodia geodia 250516_195444
">>> GD: Nai"
//:[80687.490305667] geodia geodia 250516_195633
"Oxi "
//:[80688.074929333] geodia geodia 250516_195634
"Oxi "
//:[80688.236981708] geodia geodia 250516_195634
"Oxi "
//:[80703.227434375] geodia geodia 250516_195649
"Perimenoume ton Iani na prologisei thn enarxh"
//:[80750.348233667] geodia geodia 250516_195736
"Penny den stelnoume kati"
//:[80753.546878292] geodia geodia 250516_195740
"Perimenoume ton Iani na prologisei thn enarxh"
//:[80953.785374583] geodia geodia 250516_200100
"Pou einai to LIve?"
//:[81120.262671417] geodia geodia 250516_200346
"oxi"
//:[81389.428765042] geodia geodia 250516_200815
(
Tsr.doOnVerse({ | index, verse, incipit |
	"\n>> GD: New verse received: ".post;
	TsrPoem.verses[index].postln;
}, \geodia);
)
//:[81555.768733375] geodia geodia 250516_201102
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/10).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, BrownNoise.ar), 0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[81690.650907042] geodia geodia 250516_201317
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/10).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo/3), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, BrownNoise.ar), 0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[81719.908872417] geodia geodia 250516_201346
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/10).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo/3), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, WhiteNoise.ar), 0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[81739.639998292] geodia geodia 250516_201406
(
SynthDef(\pinch, { |freq=330, ffreq=33, pos=0.0, amp=1.0|
	var signal, env, lfo;
	lfo = SinOsc.kr(ffreq/10).exprange(0.1,0.9);
	env = EnvGen.ar(Env.perc(0.01, lfo/2), doneAction: 2);
	signal = LFSaw.ar(freq+LFSaw.ar(ffreq,0,freq)).fold(lfo.neg,lfo);
	signal = Limiter.ar(Convolution.ar(signal, WhiteNoise.ar), 0.95);
	signal = FreeVerb.ar(signal * env, 0.25, 0.25, 0.25);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)
//:[81746.304348625] geodia geodia 250516_201412
(
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
	(instrument: \pinch, freq: char*5, ffreq: char / 2).play;
	(instrument: \pinch, freq: char*8, ffreq: char / 3).play;
}, \geodia);
)
