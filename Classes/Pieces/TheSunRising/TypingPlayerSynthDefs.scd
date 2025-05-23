// 金 23  5 2025 20:28
//:
SynthDef(\sline1, { | out = 0, freq = 400, amp = 0.02, dur = 0.1 |
	var env, line, src;
	env = Env.perc(0.02, dur - 0.02 max: 0.01);
	line = Line.kr(freq, { freq * Rand(0.5, 2) } ! 2, dur, doneAction: 2);
	src = SinOsc.ar(line);
	Out.ar(out, src * amp * env.kr(1, doneAction: 2));
}).add;
//:
SynthDef(\sline2, { | out = 0, freq = 400, amp = 0.02, dur = 0.1 |
	var env, line, src;
	env = Env.perc(0.02, dur - 0.02 max: 0.01);
	line = Line.kr(freq, { freq * Rand(0.5, 2) } ! 2, dur, doneAction: 2);
	src = Mix(SinOsc.ar(line));
	Out.ar(out, Pan2.ar(src, Rand(-1.0, 1.0), amp) * env.kr(1, doneAction: 2));
}).add;
//:
Synth(\sline1, [freq: 700, dur: 10.2]);
(dur: 5).play;
//:
SynthDef(\sline3, { | ffreq=0.3, ascii = 50 |
	var src, env, freq, lfo, dur, amp, noise, decay;
	lfo = SinOsc.kr(ffreq).exprange(0.3,0.9);
	noise = WhiteNoise.ar(1);
	ascii = ascii.wrap(1, 127).linlin(1, 127, 1.01, 2);
	decay = ascii.wrap(1, 127).linlin(1, 127, 0.1, 0.9);
	dur = Rand(0.2, 1.8);
	amp = Rand(0.01, 0.1);
	freq = Line.kr(Rand(100, 1000), Rand(100, 1000), dur,
		doneAction: 2
	);
	env = Env.perc(0.1, dur - 0.1, amp);
	src = Ringz.ar(
		noise,
		freq * [1, ascii],
		decay
	) * env.kr(1, doneAction: 2);
	Out.ar(0, Lag.ar(src.fold(lfo.neg,lfo), amp/30));
}).add;

1 + 1;

(
SynthDef(\sline4, { | ascii = 50 |
	var src, env, freq, dur, amp;
	ascii = ascii.wrap(1, 127).linlin(1, 127, 1.01, 2);
	dur = Rand(0.1, 0.8);
	amp = Rand(0.01, 0.1);
	freq = Line.kr(Rand(100, 1000), Rand(100, 1000), dur,
		doneAction: 2
	);
	env = Env.perc(0.01, dur - 0.01, amp);
	src = SinOsc.ar(freq * [1, ascii]) * env.kr(1, doneAction: 2);
	Out.ar(0, src);
}).add;
)
