// 日 25  5 2025 15:54
// Selected from previous file  23VoicePlayingGrainSynthDefs250525.scd
//:2e trying to re-introduce triggering from outside, again
//: 2d   bssynthdef 2b --- introducing dur / trate, pan = 0
// target full args | out = 0, bufnum = 0, t_trig = 0, rate = 1, centerPos = 0, dur = 1, pan = 0, amp = 0.1 |
SynthDef(\tgrainsr2trig, {	| out = 0, bufnum = 0, trig = 0, rate = 1, centerPos = 0.5, dur = 1, pan = 0, amp = 0.1 |
    var src;
    src = TGrains.ar(2, trig, bufnum, rate,
		centerPos * BufDur.kr(bufnum),
		dur,
		pan, amp, 4
	);
	Out.ar(out, src.f);
}).add;
//:
a = Synth(\tgrainsr2trig, [bufnum: ~busy1.bufnum, dur: 0.1]);
//:
//
a.ctl(
	\trig, { Dust.kr(5) },
	\centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\rate, { LFNoise1.kr(0.3).range(0.5, 4) },
	\dur, { LFNoise1.kr(0.3).range(0.1, 1) }
);
//:
