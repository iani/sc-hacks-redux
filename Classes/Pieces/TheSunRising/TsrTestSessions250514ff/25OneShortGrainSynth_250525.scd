// 日 25  5 2025 16:08
// Experimenting again with the idea of playing a single short grain
// of an audio buffer per synth.  To be used as a way to
// play single characters from type playback as short grains
// of a recorded verse buffer.
SynthDef(\grainSynth, { | out = 0, buf = 0, rate = 1, dur = 1, pos = 0, amp = 1 |
	var env, src, startPos;
	env = Env.perc(0.02, dur - 0.02 max: 0.02);
	startPos = pos.clip(0.0, 1.0) * BufFrames.kr(buf);
	rate = BufRateScale.kr(buf) * rate;
	src = PlayBuf.ar(1, buf, rate, 1, startPos, 1);
	Out.ar(out, Pan2.ar(src * env.kr(1, doneAction: 2), pos, amp));
}).add;
//:
Synth(\grainSynth, [buf: ~i2]);
//
Synth(\grainSynth, [buf: ~i2, dur: 10]);
//:pattern playing is easy:
//: but the single grains have max duration dur: There is no overlap!
\x play: (instrument: \grainSynth, buf: ~i2.bufnum, dur: 0.1);
\x play: (pos: (0..100).normalize.pseq(1));
//:
\x play: (instrument: \grainSynth, buf: ~i2.bufnum, dur: 0.1,
	pos: (0..100).normalize.pseq(1),
	rate: (0..100).normalize(1, 2).pseq(1)
);
//:
\x play: (instrument: \grainSynth, buf: ~i2.bufnum, dur: 0.1,
	pos: (0..100).normalize.pseq(1),
	rate: (0..100).normalize(2, 3).pseq(1),
	legato: 5
);
//:
\y play: (dur: 0.1, degree: [0, 10, 2].pbrown, legato: 2);
//:----------
//:To play overlapping synths, we should write our own redo of
//pattern playing.
fork {
	var pos, rate, dt, dur, p, r, buf;
	dt = 0.1;
	dur = 4;
	pos = (0..100).normalize.pseq(1).asStream;
	rate = (0..100).normalize(2, 3).pseq(1).asStream;
	buf = ~i3.bufnum;

	while {
		p = pos.next;
		r = rate.next;
		(p.notNil) and: { r.notNil }
	}{
		(instrument: \grainSynth, dur: dur, pos: p,
			rate: r, buf: buf
		).play;
		dt.wait;
	}
}