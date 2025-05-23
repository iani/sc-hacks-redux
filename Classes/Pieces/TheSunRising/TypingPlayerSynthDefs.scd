// 金 23  5 2025 20:28

SynthDef(\sline1, { | out = 0, freq = 400, amp = 0.2, dur = 0.1 |
	var env, line, src;
	env = Env.perc(0.02, dur - 0.02 max: 0.01);
	line = Line.kr(freq, freq * Rand(0.5, 2), dur, doneAction: 2);
	src = SinOsc.ar(line);
	Out.ar(out, src * env.kr(1, doneAction: 2));
}).add;
//:
Synth(\sline1, [freq: 1000, dur: 0.2]);