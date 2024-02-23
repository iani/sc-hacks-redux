(
SynthDef("addsynth",{arg freq = 40, gate=1;
var modulator,carrier,env;

modulator=
	SinOsc.ar(100)+SinOsc.ar(200);

carrier=
	SinOsc.ar(freq, 0, 0.1)   +
    SinOsc.ar(freq+10.567, 0, 0.1) +
    SinOsc.ar(freq+40.489, 0, 0.1) +
    SinOsc.ar(freq+60.9174, 0, 0.1) +
	SinOsc.ar(freq+70.1245, 0, 0.1) +
	SinOsc.ar(freq+80.2345, 0, 0.1) !2;
env=
	Env.pairs([[0,0],[10,0.8],[14,0.01],[16,1.0],[20,1.0],[35,0]],-2).kr(2,gate);

	Out.ar(44,modulator*carrier*env);

}).add;


SystemClock.sched(0.0,{h=Synth("addsynth",[\freq,10000],~modsources);rrand(20,50)});
)


h.set(\freq,20);