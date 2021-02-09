/*  9 Feb 2021 23:07
Which methods update SynthDescs in SynthDescLib?
*/
//:create a new synthdef
SynthDef("test1", { | freq = 440, gate = 1 |
	Out.ar(0,
		SinOsc.ar(freq) *
		Env.adsr.kr(gate: gate, doneAction: 2)

	)

}).add;
//:test the new synthdef
a = Synth("test1", [freq: 2000]);
//:
a release: 1.5;
//:get the desc of the synthdef
SynthDescLib.at(\test1);
//:the controls
SynthDescLib.at(\test1).controls;
//:the control names
SynthDescLib.at(\test1).controlNames;