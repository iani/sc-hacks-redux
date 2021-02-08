//  8 Feb 2021 12:35
// 2 Examples from Coursework submitted by Evi Giannakou.
// Uses examples from SCTweets and elsewhere.
//:
SynthDef(\bird,{
	var sig, freqenv, env, trig;
	trig = Dust.ar(2);
	env = Env.perc(0.05,0.4,0.8,-8).kr(trig, levelScale: \amp.kr(0.1), doneAction: 2);
	freqenv = EnvGen.ar(Env([Rand(1000,3000,4000), Rand(3000,4000,6000), Rand(1000,2000,3000), Rand(2000,5000,7000)],[0.1,0.01,0.1]);, trig);
	sig = Mix(FSinOsc.ar(freqenv * [1, 1.2], 0, env));
	sig = Pan2.ar(sig, Rand(-1.0,1.0,2.0,3.0,4.0));
	Out.ar(0, sig);
}).add;
//:
play{a=Impulse;tanh(a.kr(4).lag*Crackle.ar(LFSaw.kr(3).abs.lag*1.8)+GVerb.ar([a.kr(1)+a.kr(4,0.5)].lag*Blip.ar(4.9,7,0.4)!2,1,1)*3)}
