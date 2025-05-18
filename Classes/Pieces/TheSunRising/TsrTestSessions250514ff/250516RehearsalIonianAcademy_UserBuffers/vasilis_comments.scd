//:[81483.187111708] vasilis vasilis 250516_200949
//: trying with LiveText2

Tsr.doOnVerse({ | index, verse, incipit|
	LiveText2.text(verse);
	LiveText2.ints.postln;
	LiveText2.floats.postln;
})

//:
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);

\sines1 play: (\instrument: \sines);

\sines1 set: (dur: 2, degree: (1..10).pseq);
\sines1 set: (dur: 2, \degree: (1..10).reverse.pseq);

\sines1 set: (\freq: (LiveText.ints*60).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*80).pseq(1),
\vol: 0.2,
	\fade: (LiveText.floats)*2.pseq(1), // this is wrong
	// it should be:
	// 	\fade: (LiveText.floats*2).pseq(1)
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[81524.345238083] vasilis vasilis 250516_201030
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*60).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*80).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats)*2.pseq(1),
\dur: (LiveText.floats)*2.pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[81567.171329333] vasilis vasilis 250516_201113
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*60).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*80).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[81614.521440208] vasilis vasilis 250516_201201
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*200).prand(1),
\modfreq: (LiveText.ints*100).prand(1),
\ind: (LiveText.ints*2).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[81630.370345833] vasilis vasilis 250516_201216
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*150).pseq(1),
\vol: 0.4,
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats*2).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
//:[81642.254022542] vasilis vasilis 250516_201228
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*60).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*80).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats*2).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[81756.903475417] vasilis vasilis 250516_201423
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*1200).prand(1),
\modfreq: (LiveText.ints*600).prand(1),
\ind: (LiveText.ints*10).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[81833.317914333] vasilis vasilis 250516_201539
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*60).pseq(1),
freq2: (LiveText.ints*30).pseq(1),
\freq3: (LiveText.ints*80).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats*2).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[81905.459305167] vasilis vasilis 250516_201651
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*80).pseq(1),
\vol: 0.4,
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats*2).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
//:[81943.400970083] vasilis vasilis 250516_201729
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*30).pseq(1),
freq2: (LiveText.ints*30).pseq(1),
\freq3: (LiveText.ints*20).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats*2).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[82060.500515417] vasilis vasilis 250516_201927
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*1200).prand(1),
\modfreq: (LiveText.ints*60).prand(1),
\ind: (LiveText.ints*1000).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[82079.415789375] vasilis vasilis 250516_201945
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*10).pseq(1),
freq2: (LiveText.ints*15).pseq(1),
\freq3: (LiveText.ints*20).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats*2).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[82087.005633875] vasilis vasilis 250516_201953
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*30).pseq(1),
\vol: 0.4,
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats*2).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
//:[82183.304895208] vasilis vasilis 250516_202129
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*1200).prand(1),
\modfreq: (LiveText.ints*260).prand(1),
\ind: (LiveText.ints*10).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[82200.830435042] vasilis vasilis 250516_202147
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*60).pseq(1),
\vol: 0.4,
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats*2).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
//:[80247.942341792] vasilis vasilis 250516_194914
Tsr.undoOnVerse({\flute1 stop: (\instrument: \flute);}, \vasilisFlute);
//:[80251.83457975] vasilis vasilis 250516_194918
Tsr.undoOnVerse({\sines1 stop: (\instrument: \sines);}, \vasilisSines);
//:[80255.222506542] vasilis vasilis 250516_194921
Tsr.undoOnVerse({\grain1 stop: (\instrument: \grainAn);}, \vasilisGrains);
//:[80279.449284375] vasilis vasilis 250516_194945
\sines1 stop
//:[80310.492298167] vasilis vasilis 250516_195016
\sines1 stop: (\instrument: \sines);
//:[80393.980370792] vasilis vasilis 250516_195140
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*150).pseq(1),
\vol: 0.4,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
//:[80977.680319042] vasilis vasilis 250516_200124
"Ionian Academy"
//:[81359.640553708] vasilis vasilis 250516_200746
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*150).pseq(1),
\vol: 0.4,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
//:[81379.285163958] vasilis vasilis 250516_200805
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*60).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*80).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[81383.251908083] vasilis vasilis 250516_200809
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*300).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//Document for vasilis created at 250515_102734
//:[680.427010708] vasilis vasilis 250515_214306
~verse = "Alpha Beta";
//:[709.807764667] vasilis vasilis 250515_214336
~verse;
//:[774.962507375] vasilis vasilis 250515_214441
~verse;
//:[868.981619083] vasilis vasilis 250515_214615
LiveText.text(verse.postln);
//:[937.945314125] vasilis vasilis 250515_214724
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);

)
//:[958.594606958] vasilis vasilis 250515_214745
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);

)
//:[965.080031333] vasilis vasilis 250515_214751
~ints.postln
//:[967.340533] vasilis vasilis 250515_214753
~floats.postln
//:[1237.669854458] vasilis vasilis 250515_215224
1+1
//:[1240.650565083] vasilis vasilis 250515_215227
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);
)
//:[1279.012891042] vasilis vasilis 250515_215305
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);
)
//:[1676.375384958] vasilis vasilis 250515_215942
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);
)
//:[1947.332776708] vasilis vasilis 250515_220413
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);
)
//:[1966.473747042] vasilis vasilis 250515_220432
~ints.postln
//:[1975.653513875] vasilis vasilis 250515_220442
~ints.postln
//:[1977.639950375] vasilis vasilis 250515_220444
~floats.postln
//:[1980.64929125] vasilis vasilis 250515_220447
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);
)
//:[1999.17265375] vasilis vasilis 250515_220505
~ints.postln
//:[2013.059092875] vasilis vasilis 250515_220519
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(~verse);
}, \vasilis);
)
//:[2015.7384435] vasilis vasilis 250515_220522
~ints.postln
//:[2028.766376375] vasilis vasilis 250515_220535
\ints.postln
//:[2253.924209958] vasilis vasilis 250515_220920
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);
)
//:[2383.227478667] vasilis vasilis 250515_221129
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasilis);
)
//:[2431.067493042] vasilis vasilis 250515_221217
~texts.postln;
//:[2439.511791833] vasilis vasilis 250515_221226
~ints.postln
//:[2441.399681625] vasilis vasilis 250515_221227
~floats.postln
//:[2518.8970895] vasilis vasilis 250515_221345
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln;)
	LiveText.text(verse);
}, \vasilis);
)
//:[2546.915418042] vasilis vasilis 250515_221413
~verse;
//:[2606.653063042] vasilis vasilis 250515_221513
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	\verse !!! verse;
	LiveText.text(~verse);
}, \vasilis);
)
//:[2612.495097292] vasilis vasilis 250515_221519
~texts.postln;
//:[2616.827804333] vasilis vasilis 250515_221523
~verse;
//:[2634.209985667] vasilis vasilis 250515_221540
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	\verse !!! verse;
	LiveText.text(verse);
}, \vasilis);
)
//:[2649.344818583] vasilis vasilis 250515_221555
~ints.postln
//:[2652.512823625] vasilis vasilis 250515_221559
~texts.postln;
//:[2654.886582333] vasilis vasilis 250515_221601
~verse;
//:[2683.627135042] vasilis vasilis 250515_221630
	LiveText.text(~verse);
//:[2687.255512375] vasilis vasilis 250515_221633
~ints.postln
//:[2689.724446542] vasilis vasilis 250515_221636
~floats.postln
//:[2708.669037042] vasilis vasilis 250515_221655
~verse = "Hello World"
//:[2710.985346292] vasilis vasilis 250515_221657
LiveText.text(~verse);
//:[2716.679994458] vasilis vasilis 250515_221703
~ints.postln
//:[2719.902903917] vasilis vasilis 250515_221706
~floats.postln
//:[2723.165229542] vasilis vasilis 250515_221709
~texts.postln;
//:[2740.890054208] vasilis vasilis 250515_221727
~verse = "Hello World"
//:[2746.829447083] vasilis vasilis 250515_221733
LiveText.text(~verse);
//:[2749.690748708] vasilis vasilis 250515_221736
~texts.postln;
//:[2853.5637995] vasilis vasilis 250515_221920
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	\verse !!! verse;
	LiveText.text(verse);
}, \vasilis);
)
//:[2879.73902925] vasilis vasilis 250515_221946
~verse;
//:[2882.891080958] vasilis vasilis 250515_221949
LiveText.text(~verse);
//:[2885.063633708] vasilis vasilis 250515_221951
~texts.postln;
//:[2897.391835958] vasilis vasilis 250515_222003
~verse = "Hello";
//:[2899.756167417] vasilis vasilis 250515_222006
LiveText.text(~verse);
//:[2902.091128083] vasilis vasilis 250515_222008
~texts.postln;
//:[2937.677566875] vasilis vasilis 250515_222044
s.meters
//:[2945.864226625] vasilis vasilis 250515_222052
s.meter;
//:[3039.287733125] vasilis vasilis 250515_222225
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	\verse !!! verse;
	LiveText.text(verse);
}, \vasilis);
)
//:[3059.395673708] vasilis vasilis 250515_222245
~verse;
//:[3065.847409167] vasilis vasilis 250515_222252
LiveText.text(~verse);
//:[3068.747144625] vasilis vasilis 250515_222255
~texts.postln;
//:[3072.917207458] vasilis vasilis 250515_222259
~verse = "Hello";
//:[3079.622873292] vasilis vasilis 250515_222306
LiveText.text(~verse);
//:[3082.57142325] vasilis vasilis 250515_222309
~texts.postln;
//:[3188.080438458] vasilis vasilis 250515_222454
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[3227.919756083] vasilis vasilis 250515_222534
~verse;
//:[3292.863107125] vasilis vasilis 250515_222639
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	\verse !!! verse;
	LiveText.text(verse);
}, \vasilis);
)
//:[3407.993111417] vasilis vasilis 250515_222834
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	\verse !!! verse;
	LiveText.text(verse);
}, \vasilis);
)
//:[3424.729805333] vasilis vasilis 250515_222851
~verse.asString;
//:[3429.536017625] vasilis vasilis 250515_222856
LiveText.text(~verse);
//:[3444.658931958] vasilis vasilis 250515_222911
LiveText.text(~verse.asString);
//:[3447.919002167] vasilis vasilis 250515_222914
~texts.postln;
//:[3488.541984208] vasilis vasilis 250515_222955
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[3511.4576225] vasilis vasilis 250515_223017
~texts.postln;
//:[3541.916000458] vasilis vasilis 250515_223048
~texts.postln;
//:[3549.871163958] vasilis vasilis 250515_223056
\flute1 play: (\instrument: \flute);
//:[3561.327632333] vasilis vasilis 250515_223107
(
\flute1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[4181.738036875] vasilis vasilis 250515_224128
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[4292.913255917] vasilis vasilis 250515_224319
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[4322.780771167] vasilis vasilis 250515_224349
~texts.postln;
//:[4327.507548] vasilis vasilis 250515_224354
\flute1 play: (\instrument: \flute);
//:[4329.845615] vasilis vasilis 250515_224356
(
\flute1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[4406.4563105] vasilis vasilis 250515_224512
Rest(1)
//:[4540.808230542] vasilis vasilis 250515_224727
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[4561.057301125] vasilis vasilis 250515_224747
~texts.postln;
//:[4572.801195958] vasilis vasilis 250515_224759
~ints.postln
//:[4588.026559083] vasilis vasilis 250515_224814
~texts.postln;
//:[4678.75372225] vasilis vasilis 250515_224945
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[4695.900483833] vasilis vasilis 250515_225002
~texts.postln;
//:[4700.767425917] vasilis vasilis 250515_225007
\flute1 play: (\instrument: \flute);
//:[4705.509358875] vasilis vasilis 250515_225012
(
\flute1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[4933.47195475] vasilis vasilis 250515_225359
\flute1 play: (\instrument: \flute);
//:[4936.768434] vasilis vasilis 250515_225403
\flute1 play: (\instrument: \flute);
//:[5037.224030417] vasilis vasilis 250515_225543
\flute1 play: (\instrument: \flute);
//:[5041.634163417] vasilis vasilis 250515_225548
(
\flute1 set: (\freq: (1200..1600).prand,
\vol: 0.6,
\fade: 4,
\dur: 4,
\amp: (0.4..0.6).prand,
\pan: 0);
)
//:[5339.864422] vasilis vasilis 250515_230046
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[5428.048302792] vasilis vasilis 250515_230214
~texts.postln;
//:[5432.825721083] vasilis vasilis 250515_230219
\flute1 play: (\instrument: \flute);
//:[5435.955529292] vasilis vasilis 250515_230222
(
\flute1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[5796.383381583] vasilis vasilis 250515_230822
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5797.175356375] vasilis vasilis 250515_230823
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5797.597301] vasilis vasilis 250515_230824
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5797.833394667] vasilis vasilis 250515_230824
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5798.04051825] vasilis vasilis 250515_230824
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5798.201686458] vasilis vasilis 250515_230824
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5798.359413958] vasilis vasilis 250515_230824
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5798.506773917] vasilis vasilis 250515_230825
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5798.642634833] vasilis vasilis 250515_230825
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5798.738960042] vasilis vasilis 250515_230825
(
"
ERROR: Message 'minIndex' not understood.
RECEIVER:
   nil
ARGS:
CALL STACK:
	DoesNotUnderstandError:reportError
		arg this = <instance of DoesNotUnderstandError>
	Nil:handleError
		arg this = nil
		arg error = <instance of DoesNotUnderstandError>
	Thread:handleError
		arg this = <instance of Thread>
		arg error = <instance of DoesNotUnderstandError>
	Object:throw
		arg this = <instance of DoesNotUnderstandError>
	Object:doesNotUnderstand
		arg this = nil
		arg selector = 'minIndex'
		arg args = [*0]
	TypingListener:guessVerse
		arg this = <instance of TypingListener>
		var distances = nil
		var minDistance = nil
		var minIndex = nil
	Notification:update
		arg this = <instance of Notification>
		arg sender = <instance of Meta_OSC>
		arg argMessage = '/char'
		arg args = [*4]
	ArrayedCollection:do
		arg this = [*1]
		arg function = <instance of Function>
		var i = 0
	NotificationController:update
		arg this = <instance of NotificationController>
		arg theChanger = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	< FunctionDef in Method Set:do >
		arg item = <instance of NotificationController>
	ArrayedCollection:do
		arg this = [*4]
		arg function = <instance of Function>
		var i = 2
	Set:do
		arg this = <instance of IdentitySet>
		arg function = <instance of Function>
		var i = 0
	Object:changed
		arg this = <instance of Meta_OSC>
		arg what = '/char'
		arg moreArgs = [*4]
	Meta_OSC:respondTo
		arg this = <instance of Meta_OSC>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
	Main:recvOSCmessage
		arg this = <instance of Main>
		arg time = 126263.09866125
		arg replyAddr = <instance of NetAddr>
		arg recvPort = 22245
		arg msg = [*6]
^^ ERROR: Message 'minIndex' not understood.
RECEIVER: nil
")
//:[5888.237006292] vasilis vasilis 250515_230954
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[5916.746865667] vasilis vasilis 250515_231023
"Tsr.doOnVerse({ | index, verse, incipit |
	"FOR PENNY".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \penny);
"
//:[5932.012108083] vasilis vasilis 250515_231038
(
"
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR PENNY".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \penny);
"
)
//:[5939.637107458] vasilis vasilis 250515_231046
(
"
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR PENNY".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \penny);
";
)
//:[6098.082825208] vasilis vasilis 250515_231324
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[6338.924499875] vasilis vasilis 250515_231725
72
//:[6397.522761583] vasilis vasilis 250515_231824
1.rest
//:[6451.073097542] vasilis vasilis 250515_231917
s.meter;
//:[7524.311592667] vasilis vasilis 250515_233710
{WhiteNoise.ar(0.5)+BrownNoise.ar(0.5)}.play
//:[14893.532986458] vasilis vasilis 250516_014000
s.meter;
//:[15004.758631458] vasilis vasilis 250516_014151
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[15033.525552375] vasilis vasilis 250516_014220
\flute1 play: (\instrument: \flute);
//:[15037.1529345] vasilis vasilis 250516_014223
(
\flute1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[15388.346670167] vasilis vasilis 250516_014814
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[15463.686264042] vasilis vasilis 250516_014930
\flute1 play: (\instrument: \flute);
//:[15465.700985833] vasilis vasilis 250516_014932
(
\flute1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[15557.821428833] vasilis vasilis 250516_015104
~texts.postln;
//:[15603.70645725] vasilis vasilis 250516_015150
\flute1.stop
//:[15612.902946375] vasilis vasilis 250516_015159
\flute1 play: (\instrument: \flute);
//:[15631.494718833] vasilis vasilis 250516_015218
\sines1 play: (\instrument: \sines);
//:[15635.012007333] vasilis vasilis 250516_015221
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[15759.353921917] vasilis vasilis 250516_015425
"SynthDef(\sines, {|outbus = 0, buffoffset = 0, offsetval = 0, gate = 1, freq = 261.63, freq2 = 329.63, freq3 = 415.30, freqdust = 1, amp = 0.4, pan = 0, vol = 0.03, fadeinvl = 0, fadeoutvl = 0, fade = 4, char|

	var env, source;
			env = EnvGen.kr(Env([fadeinvl, 0.8, fadeoutvl], [fade, fade]), gate, doneAction: 2);
			//env = EnvGen.kr(Env.perc, doneAction:2);
			source = 	SinOsc.ar([freq, freq2, freq3], 0, amp*env)+Saw.ar([freq, freq2, freq3], 0, amp*env)+FSinOsc.ar([freq*2, freq2*2, freq3*2], 0.5, amp*env)+SinOsc.ar(SinOsc.ar(freq*4, freq2*4, freq3*4), 0, amp*env);
	Out.ar(outbus, Pan2.ar(source*env*vol, pan))!2
}).add;"
//:[16000.06122775] vasilis vasilis 250516_015826
\sines1 play: (\instrument: \sines);
//:[16002.12165025] vasilis vasilis 250516_015828
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[16090.103104083] vasilis vasilis 250516_015956
\sines1 play: (\instrument: \sines);
//:[16093.10359525] vasilis vasilis 250516_015959
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[16214.378209792] vasilis vasilis 250516_020200
\sines1 play: (\instrument: \sines);
//:[16216.487526458] vasilis vasilis 250516_020202
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[16289.732937333] vasilis vasilis 250516_020316
\sines1 play: (\instrument: \sines);
//:[16291.508305333] vasilis vasilis 250516_020318
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[16376.560518125] vasilis vasilis 250516_020443
\sines1 play: (\instrument: \sines);
//:[16378.536127292] vasilis vasilis 250516_020445
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[16842.12098375] vasilis vasilis 250516_021228
\sines1 play: (\instrument: \sines);
//:[16843.840186208] vasilis vasilis 250516_021230
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[16928.938292] vasilis vasilis 250516_021355
\sines1 play: (\instrument: \sines);
//:[16930.989880417] vasilis vasilis 250516_021357
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[16951.75637] vasilis vasilis 250516_021418
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[16959.048094792] vasilis vasilis 250516_021425
~texts.postln;
//:[17153.818911208] vasilis vasilis 250516_021740
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[17174.842205417] vasilis vasilis 250516_021801
\sines1 play: (\instrument: \sines);
//:[17177.383221625] vasilis vasilis 250516_021803
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[17508.249460208] vasilis vasilis 250516_022334
\sines1 play: (\instrument: \sines);
//:[17510.11854125] vasilis vasilis 250516_022336
(
\sines1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[17640.642425917] vasilis vasilis 250516_022547
\sines1 play: (\instrument: \sines);
//:[17644.137530542] vasilis vasilis 250516_022550
(
\sines1 set: (\freq: (LiveText.ints*600).pseq,
\vol: 0.2,
\fade: (LiveText.floats*4).pseq,
\dur: (LiveText.floats*4).pseq,
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[17697.044294542] vasilis vasilis 250516_022643
LiveText.ints
//:[17722.383597958] vasilis vasilis 250516_022708
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[17743.36509225] vasilis vasilis 250516_022729
LiveText.ints
//:[17747.824011] vasilis vasilis 250516_022734
\sines1 play: (\instrument: \sines);
//:[17750.162973042] vasilis vasilis 250516_022736
(
\sines1 set: (\freq: (LiveText.ints*600).pseq,
\vol: 0.2,
\fade: (LiveText.floats*4).pseq,
\dur: (LiveText.floats*4).pseq,
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[17882.038349708] vasilis vasilis 250516_022948
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
}, \vasilis);
)
//:[17925.475887167] vasilis vasilis 250516_023031
LiveText.ints
//:[17930.237385167] vasilis vasilis 250516_023036
\sines1 play: (\instrument: \sines);
//:[17936.15582275] vasilis vasilis 250516_023042
(
\sines1 set: (\freq: (LiveText.ints*600).pseq,
\vol: 0.2,
\fade: (LiveText.floats*4).pseq,
\dur: (LiveText.floats*4).pseq,
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);
)
//:[18051.439551375] vasilis vasilis 250516_023237
\sines1 play: (\instrument: \sines);
//:[18053.48179225] vasilis vasilis 250516_023239
(
\sines1 set: (\freq: (LiveText.ints*600).pseq(1),
\vol: 0.2,
	\fade: (LiveText.floats*4).pseq(1),
	\dur: (LiveText.floats*4).pseq(1),
	\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);
)
//:[18174.787491] vasilis vasilis 250516_023441
(
\sines1 set: (\freq: (LiveText.ints*600).pseq(1),
\vol: 0.2,
	\fade: (LiveText.floats).pseq(1),
	\dur: (LiveText.floats).pseq(1),
	\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);
)
//:[18183.007937125] vasilis vasilis 250516_023449
\sines1 play: (\instrument: \sines);
//:[18339.610658333] vasilis vasilis 250516_023726
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*600).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilis);
)
//:[18419.992664833] vasilis vasilis 250516_023846
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (~ints*600).pseq,
\vol: 0.2,
\fade: (~floats*4).pseq,
\dur: (~floats*4).pseq,
\amp: (~floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq,
\pan: 0);

}, \vasilis2);
)
//:[18463.562194708] vasilis vasilis 250516_023930
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*600).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);

}, \vasilis2);
)
//:[18630.766384208] vasilis vasilis 250516_024217
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*200).pseq(1),
\freq2: (LiveText.ints*400).pseq(1),
\freq3: (LiveText.ints*600).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);

}, \vasilis2);
)
//:[18662.124769] vasilis vasilis 250516_024248
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*600).pseq(1),
		freq2: (LiveText.ints*400).pseq(1),
\freq3: (LiveText.ints*600).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilis);
)
//:[18724.967143333] vasilis vasilis 250516_024351
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*200).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*150).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilis3);
)
//:[18741.454268333] vasilis vasilis 250516_024407
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*50).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*150).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilis3);
)
//:[19161.477166333] vasilis vasilis 250516_025107
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);

}, \vasilis);
)
//:[19228.248962958] vasilis vasilis 250516_025214
GrainEnvs.init;
//:[19276.337485917] vasilis vasilis 250516_025302
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);

}, \vasilis);
)
//:[19282.719569667] vasilis vasilis 250516_025309
GrainEnvs.init;
//:[19387.097273] vasilis vasilis 250516_025453
GrainEnvs();
//:[19449.361584333] vasilis vasilis 250516_025555
GrainEnvs.init;
//:[19500.997397208] vasilis vasilis 250516_025647
GrainEnvs();
//:[19526.033423958] vasilis vasilis 250516_025712
GrainEnvs.welch
//:[19617.487419917] vasilis vasilis 250516_025843
"GrainEnvs{

	classvar server;
	classvar <winEnvHn, <hanning, <winEnvHm, <hamming, <winEnvS, <sine, <winEnvR, <rect, <winEnvC, <cheby, <winEnvW, <welch;

	*new{
arg n;
var obj;


server = Server.local;
obj = super.new;

obj.init(n);


^obj;
}

init{
		fork{

winEnvHn = Signal.hanningWindow(1024);
		Server.default.sync;
hanning = Buffer.sendCollection(server, winEnvHn, 1);
				Server.default.sync;

winEnvHm = Signal.hammingWindow(1024);
				Server.default.sync;

hamming = Buffer.sendCollection(server, winEnvHm, 1);
				Server.default.sync;

winEnvS = Signal.sineFill(1000, 1.0/[1, 2, 3, 4, 5, 6]);
				Server.default.sync;

sine = Buffer.sendCollection(server, winEnvS, 1);
				Server.default.sync;

winEnvR = Signal.rectWindow(1024);
				Server.default.sync;

rect = Buffer.sendCollection(server, winEnvR, 1);
				Server.default.sync;

winEnvC = Signal.chebyFill(1000, [0.3, -0.8, 1.1]);
				Server.default.sync;

cheby = Buffer.sendCollection(server, winEnvC, 1);
				Server.default.sync;

winEnvW = Signal.welchWindow(1024);
				Server.default.sync;

welch = Buffer.sendCollection(server, winEnvW, 1);
				Server.default.sync;
		};

	}


}"
//:[19758.986414208] vasilis vasilis 250516_030105
"SynthDef(\grainAn, { |out = 0, gate = 1, carfreq = 1000, modfreq = 122, ind = 0.5, rate = 1, pos = 0.5, in = 1, bufnum,   trig = 1, dur = 1, amp = 1, fadein = 1, fadeout = 1, sndbuf, envbuf = 1, cutoff = 6000, pan = 0, vol = 0.5, sample, shape|
    var env,index, modulator, zoro, outbus, trigger;

		modulator= SinOsc.kr(modfreq,0,10*modfreq*ind, carfreq);
		env = EnvGen.kr(
        Env([0, 1, 0], [shape, shape]),
        gate,
		levelScale: amp, doneAction: 2);
		trigger = LPF.ar(GrainBuf.ar(2, Dust.kr(trig), dur, sndbuf, BufRateScale.kr(sndbuf)*(modulator/440)*rate, pos, 2, pan, envbuf), cutoff);

     outbus = Out.ar(out, trigger * env * vol)!2
		}).add;"
//:[20358.865366333] vasilis vasilis 250516_031105
~i1
//:[20385.469485333] vasilis vasilis 250516_031131
\grain1 play: (instrument: \grainAn, sndbuf: ~i1);
//:[20436.747757542] vasilis vasilis 250516_031223
(
\grain1 set: (
	\carfreq:  (LiveText.ints*600).prand(1),
	\modfreq: (LiveText.ints**10).prand(1),
	\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
	\pos: (LiveText.floats).prand(1),
\rate: 0.5,
	\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);
)
//:[20449.630557833] vasilis vasilis 250516_031236
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);

}, \vasilis);
)
//:[20635.099021083] vasilis vasilis 250516_031541
GrainEnvs();
//:[20639.216874667] vasilis vasilis 250516_031545
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1);

\grain1 set: (
	\carfreq:  (LiveText.ints*600).prand(1),
	\modfreq: (LiveText.ints**10).prand(1),
	\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
	\pos: (LiveText.floats).prand(1),
\rate: 0.5,
	\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[20819.19598375] vasilis vasilis 250516_031845
SynthDef(\grainAn, { |out = 0, gate = 1, carfreq = 1000, modfreq = 122, ind = 0.5, rate = 1, pos = 0.5, in = 1, bufnum,   trig = 1, dur = 1, amp = 1, fadein = 1, fadeout = 1, sndbuf=0, envbuf = 1, cutoff = 6000, pan = 0, vol = 0.5, sample, shape|
    var env,index, modulator, zoro, outbus, trigger;

		modulator= SinOsc.kr(modfreq,0,10*modfreq*ind, carfreq);
		env = EnvGen.kr(
        Env([0, 1, 0], [shape, shape]),
        gate,
		levelScale: amp, doneAction: 2);
		trigger = LPF.ar(GrainBuf.ar(2, Dust.kr(trig), dur, sndbuf, BufRateScale.kr(sndbuf)*(modulator/440)*rate, pos, 2, pan, envbuf), cutoff);

     outbus = Out.ar(out, trigger * env * vol)!2
		}).add;
//:[20832.883610583] vasilis vasilis 250516_031859
\grain1 play: (instrument: \grainAn, sndbuf: ~i1);
//:[20852.6655485] vasilis vasilis 250516_031919
~i1
//:[20861.391548708] vasilis vasilis 250516_031927
~i1.bufnum
//:[20886.13491975] vasilis vasilis 250516_031952
\grain1 play: (instrument: \grainAn, sndbuf: 25);
//:[20903.757025833] vasilis vasilis 250516_032010
\grain1 set: (
	\carfreq:  (LiveText.ints*600).prand(1),
	\modfreq: (LiveText.ints**10).prand(1),
	\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
	\pos: (LiveText.floats).prand(1),
\rate: 0.5,
	\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);
//:[20941.127770917] vasilis vasilis 250516_032047
~i1.bufnum
//:[20944.243327417] vasilis vasilis 250516_032050
GrainEnvs();
//:[20973.090665875] vasilis vasilis 250516_032119
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
	\carfreq:  (LiveText.ints*600).prand(1),
	\modfreq: (LiveText.ints**10).prand(1),
	\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
	\pos: (LiveText.floats).prand(1),
\rate: 0.5,
	\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21016.409841167] vasilis vasilis 250516_032202
GrainEnvs();
//:[21019.516464208] vasilis vasilis 250516_032206
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
	\carfreq:  (LiveText.ints*600).prand(1),
	\modfreq: (LiveText.ints**10).prand(1),
	\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
	\pos: (LiveText.floats).prand(1),
\rate: 0.5,
	\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21092.110319583] vasilis vasilis 250516_032318
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
	\carfreq:  (LiveText.ints*600).prand(1),
	\modfreq: (LiveText.ints**10).prand(1),
	\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
	\pos: (LiveText.floats).prand(1),
\rate: 0.5,
	\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21139.698726042] vasilis vasilis 250516_032406
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21255.274941958] vasilis vasilis 250516_032601
GrainEnvs();
//:[21257.505037167] vasilis vasilis 250516_032604
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21289.652027708] vasilis vasilis 250516_032636
GrainEnvs();
//:[21291.733568083] vasilis vasilis 250516_032638
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21319.55589125] vasilis vasilis 250516_032706
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
//:[78702.081675833] vasilis vasilis 250516_192328
(
Tsr.doOnVerse({ | index, verse, incipit //:[79797.330687917] vasilis vasilis 250516_194143
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*60).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*80).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[79874.074483708] vasilis vasilis 250516_194300
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*300).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[79897.913396708] vasilis vasilis 250516_194324
GrainEnvs();
//:[79909.677011208] vasilis vasilis 250516_194336
~i1.bufnum
//:[79992.393691875] vasilis vasilis 250516_194458
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\flute1 play: (\instrument: \flute);

\flute1 set: (\freq: (LiveText.ints*50).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
//:[79843.839771625] vasilis vasilis 250516_194230
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*50).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*150).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
|
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*50).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*150).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.9, 0.5, 0.8)).pseq(1),
\pan: 0);


}, \vasilisFlute);
)
//:[79528.344523042] vasilis vasilis 250516_193714
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*200).pseq(1),
freq2: (LiveText.ints*400).pseq(1),
\freq3: (LiveText.ints*600).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[79596.838513875] vasilis vasilis 250516_193823
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*100).pseq(1),
freq2: (LiveText.ints*300).pseq(1),
\freq3: (LiveText.ints*200).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
//:[79705.591451833] vasilis vasilis 250516_194012
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);


\sines1 play: (\instrument: \sines);

\sines1 set: (\freq: (LiveText.ints*100).pseq(1),
freq2: (LiveText.ints*150).pseq(1),
\freq3: (LiveText.ints*200).pseq(1),
\vol: 0.2,
\fade: (LiveText.floats).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);


}, \vasilisSines);
)
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21321.524225458] vasilis vasilis 250516_032708
GrainEnvs();
//:[21431.026554208] vasilis vasilis 250516_032857
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21437.143380125] vasilis vasilis 250516_032903
GrainEnvs();
//:[21508.450814875] vasilis vasilis 250516_033014
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21563.894939167] vasilis vasilis 250516_033110
~i1.bufnum
//:[21595.973635833] vasilis vasilis 250516_033142
~i1
//:[21602.990972625] vasilis vasilis 250516_033149
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21640.354459583] vasilis vasilis 250516_033226
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21766.53374925] vasilis vasilis 250516_033433
GrainEnvs();
//:[21780.4877265] vasilis vasilis 250516_033446
GrainEnvs.welch
//:[21783.396544458] vasilis vasilis 250516_033449

//:[21787.009070458] vasilis vasilis 250516_033453
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[21821.341080167] vasilis vasilis 250516_033527
LiveText.ints
//:[21837.538370917] vasilis vasilis 250516_033544
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);
//:[21857.936785833] vasilis vasilis 250516_033604
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum.postln);
//:[21921.192371625] vasilis vasilis 250516_033707
	SynthDef(\grainAn, { |out = 0, gate = 1, carfreq = 1000, modfreq = 122, ind = 0.5, rate = 1, pos = 0.5, in = 1, bufnum,   trig = 1, dur = 1, amp = 1, fadein = 1, fadeout = 1, sndbuf=0, envbuf = 1, cutoff = 6000, pan = 0, vol = 0.5, sample, shape|
    var env,index, modulator, zoro, outbus, trigger;

		modulator= SinOsc.kr(modfreq,0,10*modfreq*ind, carfreq);
		env = EnvGen.kr(
        Env([0, 1, 0], [shape, shape]),
        gate,
		levelScale: amp, doneAction: 2);
		trigger = LeakDC.ar(LPF.ar(GrainBuf.ar(2, Dust.kr(trig), dur, sndbuf, BufRateScale.kr(sndbuf)*(modulator/440)*rate, pos, 2, pan, envbuf), cutoff));

     outbus = Out.ar(out, trigger * env * vol)!2
		}).add;
//:[21962.402526958] vasilis vasilis 250516_033748
GrainEnvs.welch
//:[21965.778251958] vasilis vasilis 250516_033752
~i1
//:[22052.209275125] vasilis vasilis 250516_033918
GrainEnvs();
//:[22070.75047325] vasilis vasilis 250516_033937
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);
//:[22197.371706375] vasilis vasilis 250516_034143
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);
//:[22262.591385833] vasilis vasilis 250516_034249
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[22395.024841292] vasilis vasilis 250516_034501
LiveText.ints
//:[22411.893016667] vasilis vasilis 250516_034518
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[22476.726548875] vasilis vasilis 250516_034623
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[22510.0142] vasilis vasilis 250516_034656
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[22585.822673625] vasilis vasilis 250516_034812
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[22608.112534042] vasilis vasilis 250516_034834
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[22772.404863833] vasilis vasilis 250516_035118
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
//:[22848.343648958] vasilis vasilis 250516_035234
(
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse.postln);
	//\verse !!! verse;
	LiveText.text(verse.asString);
\grain1 play: (instrument: \grainAn, sndbuf: ~i1.bufnum);

\grain1 set: (
\carfreq:  (LiveText.ints*600).prand(1),
\modfreq: (LiveText.ints*10).prand(1),
\ind: (LiveText.ints).prand(1),
\trig: 30,
\dur: 4,
\shape: 4,
\pos: (LiveText.floats).prand(1),
\rate: 0.5,
\pan: (LiveText.floats).prand(1),
\cutoff: 1000,
\amp: 0.8,
\vol: 0.8,
\envbuf: GrainEnvs.welch);

}, \vasilisGrains);
)
