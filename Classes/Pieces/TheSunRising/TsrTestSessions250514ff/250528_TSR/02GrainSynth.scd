//: TsrPoem.incipits;
 [
	 ~busy1, ~whyDost1, ~through1, ~must1, ~saucy1, ~late1, ~go1, ~call1,
	 ~love1, ~nor1,

	 ~thy1, ~whyShouldst1, ~i1, ~but1, ~if1, ~look1, ~whether1,
	 ~be1, ~ask1, ~and1,

	 ~shes1, ~nothing1, ~princes1,
	 ~all1, ~thou1, ~in1, ~thine1, ~to1, ~shine1, ~this1
 ];
//:
SynthDef(\grainSynth, { | out = 0, buf = 0, rate = 1, dur = 1,
	pos = 0, pan = 0, amp = 0.1 |
	var env, src, startPos;
	env = Env([0, 1, 1, 0], [0.1, 1, 0.1].normalizeSum * dur);
	startPos = pos.clip(0.0, 1.0) * BufFrames.kr(buf);
	rate = BufRateScale.kr(buf) * rate;
	src = PlayBuf.ar(1, buf, rate, 1, startPos, 1);
	Out.ar(out, Pan2.ar(src * env.kr(1, doneAction: 2), pan, amp));
}).add;
//:
a = Synth(\grainSynth, [buf: ~v1]);
//:
a = Synth(\grainSynth, [buf: ~v2, dur: 3, amp: 0.1]);
a.ctl(\rate, { Line.kr(1, 2, 3, doneAction: 2) });
//:
a = Synth(\grainSynth, [buf: ~v3, dur: 3, amp: 0.1]);
a.ctl(\rate, { Line.kr(2, 0.2, 3, doneAction: 2) });
//:
a = Synth(\grainSynth, [buf: ~v4, dur: 10, amp: 0.1]);
a.ctl(\rate, { LFNoise2.kr(0.1).range(0.2, 0.8) * Line.kr(1, 1.00001, 10, doneAction: 2) });
a.ctl(\amp, { LFNoise1.kr(1).range(0.1, 1) / 10 * Line.kr(1, 1.00001, 10, doneAction: 2) });
//:
a = Synth(\grainSynth, [buf: ~v1, dur: 10, amp: 0.1]);
a.ctl(\rate, { LFNoise0.kr(10.1).range(1.2, 1.8) * Line.kr(1, 1.00001, 10, doneAction: 2) });
//: play just for the duration of the buffer (!)
a = Synth(\grainSynth, [buf: ~v2, dur: ~i2.dur]);
//: position the envelope to peak at middlee of sample
a = Synth(\grainSynth, [buf: ~v2, dur: ~thine2.dur, pos: 0.5]);
//: shorten the grain
a = Synth(\grainSynth, [buf: ~v2, dur: 1, pos: 0.75]);
//:Routines
//:Routine 1
fork {
	100 do: { | i |
		Synth(\grainSynth, [buf: ~v4, dur: 0.3,
			pos: 0.0.rrand(1.0), rate:
			[0.2.rrand(2.0), -2.0.rrand(-0.2)].choose, amp: 0.01.exprand(0.25)]);
		0.15.wait;
	};
};
//:
a.free;
//:Alvin
fork {
	10 do: { | i |
		Synth(\grainSynth, [buf: ~v3, dur: 1, pos: i / 10]);
		1.5.wait;
	};
}

//:Alvin rising
fork {
	10 do: { | i |
		Synth(\grainSynth, [buf: ~v1, dur: 1, pos: i / 10, rate: (i / 10 + 1)]);
		1.5.wait;
	};
}
//:Alvin falling
fork {
	10 do: { | i |
		Synth(\grainSynth, [buf: ~v2, dur: 1, pos: i / 10,
			rate: (10 - i).linlin(0, 10, 0.2, 2)]);
		1.5.wait;
	};
}

//:Patterns
//:Pattern 1 progress through entire buf
//:
\q play: (instrument: \grainSynth, buf: ~v2.bufnum, dur: 0.1);
\q play: (pos: (0..100).normalize.pseq(1));
//:
\y play: (instrument: \grainSynth, buf: ~v3.bufnum, dur: 0.1);
\y play: (pos: (0..100).normalize.prand(100));
//:Pattern 2 progress + increase playback rate
\z play: (instrument: \grainSynth, buf: ~v4.bufnum, dur: 0.1,
	pos: (0..100).normalize.pseq(1),
	rate: (0..100).normalize(1, 2).pseq(1)
);
//:Patern 3: Higher rate
\w play: (instrument: \grainSynth, buf: ~v1.bufnum, dur: 0.1,
	pos: (0..100).normalize.pseq(1),
	rate: (0..100).normalize(2, 3).pseq(1),
);
//: TsrPoem.incipits
[
	 ~busy1, ~whyDost1, ~through1, ~must1, ~saucy1, ~late1, ~go1, ~call1,
	 ~love1, ~nor1,

	 ~thy1, ~whyShouldst1, ~i1, ~but1, ~if1, ~look1, ~whether1,
	 ~be1, ~ask1, ~and1,

	 ~shes1, ~nothing1, ~princes1,
	 ~all1, ~thou1, ~in1, ~thine1, ~to1, ~shine1, ~this1
 ]

a.release