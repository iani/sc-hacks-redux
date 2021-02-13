/* 13 Feb 2021 16:10
From: https://github.com/NikosKanelakis/microloop-project/blob/main/Supercollider_microloop.scd

To be reviewed, commented, and provided with examples.
*/


SynthDef.new(\sine, {
	arg freq=80, atk=0.05, rel=0.3, amp=1,pan=0;
	var sig, env;
	sig = SinOsc.ar(freq);
	env = EnvGen.kr(Env.new([0,1,0], [atk,rel], [1,-1]), doneAction:2);
	sig = Pan2.ar (sig, pan, amp);
	sig = sig * env;
	Out.ar (0, sig);
}).add;

SynthDef.new(\pulse, {
	arg freq=80, atk=0.05, rel=0.3, amp=1,pan=0;
	var sig, env;
	sig = Pulse.ar(freq);
	env = EnvGen.kr(Env.new([0,1,0], [atk,rel], [1,-1]), doneAction:2);
	sig = Pan2.ar (sig, pan, amp);
	sig = sig * env;
	Out.ar (0, sig);
}).add;

SynthDef.new(\sub, {
	arg freq=100, atk=0.05, rel=0.2, amp=1,pan=0;
	var sig, env;
	sig = SinOsc.ar(freq);
	env = EnvGen.kr(Env.new([0,1,0], [atk,rel], [1,-1]), doneAction:2);
	sig = Pan2.ar (sig, pan, amp);
	sig = sig * env;
	Out.ar (0, sig);
}).add;

SynthDef.new(\bufplay, {
	arg buf=0, rate=1, amp=1;
	var sig;
	sig = PlayBuf.ar(2, buf, BufRateScale.ir(buf) * rate, doneAction:2);
	sig = sig *amp;
	Out.ar(0, sig);
}).add;

SynthDef(\subkick, {arg out = 0, amp = 0.3, sinfreq = 80, glissf = 0.1, att = 0.01, rel = 0.20, pan = 0;
	var env, snd, ramp;
	env = Env.perc(att, rel, amp).kr(doneAction: 2);
	ramp = XLine.kr(
		start: sinfreq,
		end: sinfreq * glissf,
		dur: rel
	);
	snd = SinOsc.ar(freq: ramp, mul: env);
	snd = Pan2.ar(snd, pan);
	Out.ar(out, snd);
}).add;
