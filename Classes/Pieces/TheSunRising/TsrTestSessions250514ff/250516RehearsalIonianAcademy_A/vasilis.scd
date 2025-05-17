//Document for vasilis created at 250516_194900
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
//:[81483.187111708] vasilis vasilis 250516_200949
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
