//Document for geodia created at 250516_194900
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
