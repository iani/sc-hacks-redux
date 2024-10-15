/*  9 Feb 2021 23:07
Which methods update SynthDescs in SynthDescLib?
*/
//:create a new synthdef
SynthDef("test20", { | freq = 440, gate = 1 |
	Out.ar(0,
		SinOsc.ar(freq) *
		Env.adsr.kr(gate: gate, doneAction: 2)
	)
}).add; /* this also adds it to SynthDescLib.global !
even if the server is not booted
*/
//:
SynthDescLib.global.synthDescs[\test2];
//:test the new synthdef
a = Synth("test20", [freq: 2000]);
//:
a release: 1.5;
//:get the desc of the synthdef
SynthDescLib.at(\test1);
//:the controls
SynthDescLib.at(\test1).controls;
//:the control names
SynthDescLib.at(\test1).controlNames;
/*  1 Jun 2021 05:41
Does SynthDescLib update its contents when the 
default server boots?
*/
//:After recompile:
SynthDescLib.global.synthDescs[\default];
SynthDescLib.global.synthDescs[\test1];
//:
{ WhiteNoise.ar(0.1).dup }.play;
SynthDescLib.global.synthDescs['temp__0'];


