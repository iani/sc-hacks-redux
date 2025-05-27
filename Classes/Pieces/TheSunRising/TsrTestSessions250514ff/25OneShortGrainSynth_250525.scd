// 日 25  5 2025 16:08
// Experimenting again with the idea of playing a single short grain
// of an audio buffer per synth.  To be used as a way to
// play single characters from type playback as short grains
// of a recorded verse buffer.
SynthDef(\grainSynth, { | out = 0, buf = 0, rate = 1, dur = 1, pos = 0, amp = 1 |
	var env, src, startPos;
	env = Env([0, 1, 1, 0], [0.1, 1, 0.1].normalizeSum * dur);
	startPos = pos.clip(0.0, 1.0) * BufFrames.kr(buf);
	rate = BufRateScale.kr(buf) * rate;
	src = PlayBuf.ar(1, buf, rate, 1, startPos, 1);
	Out.ar(out, Pan2.ar(src * env.kr(1, doneAction: 2), pos, amp));
}).add;
//:
Synth(\grainSynth, [buf: ~i2]);
//
Synth(\grainSynth, [buf: ~i2, dur: 10]);
//: play just for the duration of the buffer (!)
Synth(\grainSynth, [buf: ~i2, dur: ~i2.dur]);
//: position the envelope to peak at midd5e of sample
Synth(\grainSynth, [buf: ~i2, dur: ~i2.dur, pos: 0.5]);
//: shorten the grain
Synth(\grainSynth, [buf: ~i2, dur: 1, pos: 0.75]);
//:
fork {
	10 do: { | i |
		Synth(\grainSynth, [buf: ~i2, dur: 1, pos: i / 10]);
		1.5.wait;
	};
}

//:
fork {
	10 do: { | i |
		Synth(\grainSynth, [buf: ~i2, dur: 1, pos: i / 10, rate: (i / 10 + 1)]);
		1.5.wait;
	};
}

//:
~i1.dur;
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
//:Reconfirming - redoing tests to check dur argument
Synth(\grainSynth, [buf: ~i1.bufnum, dur: 10]);
//:
fork {
	10 do: {
		Synth(\grainSynth,
			[buf: ~i1.bufnum, dur: 10, rate: 0.5.rrand(2), amp: 0.01.exprand(0.1)]);
		1.wait;
	}
}
//:
fork {
	10 do: {
		Synth(\grainSynth,
			[buf: ~i1.bufnum, dur: 3, rate: 1.5.rrand(2), amp: 0.01.exprand(0.1)]);
		1.wait;
	}
}

//:
fork {
	100 do: {
		Synth(\grainSynth,
			[buf: ~i1.bufnum, dur: 0.3, rate: 1.5.rrand(3), amp: 0.01.exprand(0.1)]);
		0.1.wait;
	}
}

//:
fork {
	100 do: {
		Synth(\grainSynth, [buf: ~i1.bufnum, dur: 0.7, rate: 1.5.rrand(3), amp: 0.01]);
		0.1.wait;
	}
}

//:
fork {
	100 do: { | i |
		Synth(\grainSynth, [buf: ~i1.bufnum, dur: 0.7,
			rate: (i / 100 + 1),
			pos: (i / 100),
			amp: 0.01
		]);
		0.1.wait;
	}
}
