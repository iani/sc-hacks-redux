// testing multiple pairs of control args
// Temporary notes 水 21  5 2025 16:42

// Create a synth to control and store it in a
a = { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) }.play;

// map control \freq to a synth synth made from a func
a.ctl(\freq, { LFNoise0.kr(10).range(100, 300) });

// Shortcut for ctl: c
a.c(\freq, { LFNoise1.kr(5).range(800, 900) });

// Adding multiple controls with list of key-function pairs
a = { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) }.play;
//: with ctl:
a.ctl(\freq, { LFNoise1.kr(1).range(1080, 2090) },
	\amp, { LFSaw.kr(7).range(0, 0.1)}
);
//: with c
a.c(\freq, { LFNoise0.kr(4).range(1080, 2090) },
	\amp, { SinOsc.kr(1).range(0, 0.1)}
);
//: