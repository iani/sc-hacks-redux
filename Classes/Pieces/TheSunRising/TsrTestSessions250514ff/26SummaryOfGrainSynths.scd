// 月 26  5 2025 14:30
// Summary of TGrain and Playbuf based synths and first selection
// for use in The Sun Rising
// ========= 1. TGrain based synths ===========
//: 1a single grain triggered by external control
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
TsrPoem.incipits;
 [
	 ~busy1, ~whyDost1, ~through1, ~must1, ~saucy1, ~late1, ~go1, ~call1,
	 ~love1, ~nor1, ~thy1, ~whyShouldst1, ~i1, ~but1, ~if1, ~look1, ~whether1,
	 ~be1, ~ask1, ~and1, ~shes1,
	 ~nothing, ~princes, ~all, ~thou, ~in, ~thine, ~to, ~shine, ~this1
 ]
//:
a = Synth(\tgrainsr2trig, [bufnum: ~in2.bufnum, dur: 0.1]);
a.ctl(
	\trig, { Dust.kr(5) },
	\centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\rate, { LFNoise1.kr(0.3).range(0.5, 4) },
	\dur, { LFNoise1.kr(0.3).range(0.1, 1) }
);
//:
a release: 10;
a.freeCtls;
//:

//: 1b
// ========= 2. PlayBuf based synths ==========
// \grainSynth args:  out = 0, buf = 0, rate = 1, dur = 1, pos = 0, amp = 1 |
// Short single grain synth with PlayBuf:
// Experimenting again with the idea of playing a single short grain
// of an audio buffer per synth.  To be used as a way to
// play single characters from type playback as short grains
// of a recorded verse buffer.
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
[
	 ~busy1, ~whyDost1, ~through1, ~must1, ~saucy1, ~late1, ~go1, ~call1,
	 ~love1, ~nor1, ~thy1, ~whyShouldst1, ~i1, ~but1, ~if1, ~look1, ~whether1,
	 ~be1, ~ask1, ~and1, ~shes1,
	 ~nothing, ~princes, ~all, ~thou, ~in, ~thine, ~to, ~shine, ~this1
 ]
//:
a.resease;
//:single grain, duration 1 sec
a = Synth(\grainSynth, [buf: ~nothing2]);
//:single grain, duraion 2 sec
a = Synth(\grainSynth, [buf: ~nothing2, dur: 10]);
//:single grain, duraion 2 sec, map rising rate control
a = Synth(\grainSynth, [buf: ~nothing4, dur: 10, amp: 0.1]);
a.ctl(\rate, { Line.kr(1, 2, 10, doneAction: 2) });
//:single grain, duraion 2 sec, map LFNoise rate control
a = Synth(\grainSynth, [buf: ~i2, dur: 10, amp: 0.1]);
a.ctl(\rate, { LFNoise2.kr(0.1).range(0.2, 0.8) * Line.kr(1, 1.00001, 10, doneAction: 2) });
//: play just for the duration of the buffer (!)
a = Synth(\grainSynth, [buf: ~i2, dur: ~i2.dur]);
//: position the envelope to peak at midd5e of sample
a = Synth(\grainSynth, [buf: ~i2, dur: ~i2.dur, pos: 0.5]);
//: shorten the grain
a = Synth(\grainSynth, [buf: ~i2, dur: 1, pos: 0.75]);
//:Routines
//:Routine 1
fork {
	100 do: { | i |
		Synth(\grainSynth, [buf: ~must2, dur: 0.3,
			pos: 0.0.rrand(1.0), rate:
			[0.2.rrand(2.0), -2.0.rrand(-0.2)].choose, amp: 0.01.exprand(0.25)]);
		0.15.wait;
	};
}

//:Routine 2
fork {
	10 do: { | i |
		Synth(\grainSynth, [buf: ~thine2, dur: 1, pos: i / 10]);
		1.5.wait;
	};
}

//:Routine 3
fork {
	10 do: { | i |
		Synth(\grainSynth, [buf: ~i2, dur: 1, pos: i / 10, rate: (i / 10 + 1)]);
		1.5.wait;
	};
}
//:Several more forks
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

//:Patterns
//:Pattern 1 progress through entire buf
//:
\x play: (instrument: \grainSynth, buf: ~i2.bufnum, dur: 0.1);
\x play: (pos: (0..100).normalize.pseq(1));
//:Pattern 2 progress + increase playback rate
\x play: (instrument: \grainSynth, buf: ~i2.bufnum, dur: 0.1,
	pos: (0..100).normalize.pseq(1),
	rate: (0..100).normalize(1, 2).pseq(1)
);
//:Patern 3: Higher rate
\x play: (instrument: \grainSynth, buf: ~i2.bufnum, dur: 0.1,
	pos: (0..100).normalize.pseq(1),
	rate: (0..100).normalize(2, 3).pseq(1),
	legato: 5
);


//  ========= 3. TGrain based synths with Noise rate "GrainFroth" ===========

//: 3a GrainFroth1 TGrain without resonance
//: SynthDef grainFroth
SynthDef(\grainFroth1, { | out = 0, buf = 0, trate = 0.5, pos = 0, amp = 0.2 |
	var dur, clk, src, pan;
	trate = trate.linexp(0, 1, 1, 400);
	dur = 8 / trate;
	clk = Impulse.kr(trate);
	pos = pos * BufDur.kr(buf);
	pan = WhiteNoise.kr(0.8);
	src = TGrains.ar(2, clk, buf, 2 ** WhiteNoise.kr(2), pos, dur, pan, amp, 4);
	Out.ar(out, src * amp.f);
}).add;
//:!!!!!!!!!!!!
a = Synth(\grainFroth1, [buf: ~i2]);
a.ctl(\pos, { LFSaw.kr(0.01) });
a.ctl(\trate, { LFNoise1.kr(0.1).range(0.1, 0.9) });
a release: 10;
//: 3b GrainFroth2 TGrain with FIXED resonance
//: adding DynKlank
SynthDef(\grainFroth2, { | out = 0, buf = 0, trate = 0.5, pos = 0, amp = 0.05 |
	var dur, clk, src, ksrc, freqs, amps, rings, pan;
	trate = trate.linexp(0, 1, 1, 400);
	dur = 8 / trate;
	clk = Impulse.kr(trate);
	pos = pos * BufDur.kr(buf);
	pan = WhiteNoise.kr(0.8);
	src = TGrains.ar(2, clk, buf, 2 ** WhiteNoise.kr(2), pos, dur, pan, amp, 4);
	freqs = #[100, 200, 300, 400] * 1;
    amps = #[1, 0.3, 0.2, 0.05];
    rings = #[1, 1, 1, 2];
	ksrc = DynKlank.ar(`[freqs, amps, rings], src * amp);
	Out.ar(out, ksrc.f);
}).add;
//:
a = Synth(\grainFroth2, [buf: ~i2]);
a.release;
a.free;
//: 3c GrainFroth3 TGrain with resonance with 4 controled frequencies
//: adding controls f1, f2, f3, f4
SynthDef(\grainFroth3, { | out = 0, buf = 0, trate = 0.5, pos = 0, amp = 0.05,
	f1 = 100, f2 = 200, f3 = 300, f4 = 400 |
	var dur, clk, src, ksrc, freqs, amps, rings, pan;
	trate = trate.linexp(0, 1, 1, 400);
	dur = 8 / trate;
	clk = Impulse.kr(trate);
	pos = pos * BufDur.kr(buf);
	pan = WhiteNoise.kr(0.8);
	src = TGrains.ar(2, clk, buf, 2 ** WhiteNoise.kr(2), pos, dur, pan, amp, 4);
	freqs = [f1, f2, f3, f4];
    amps = #[1, 0.3, 0.2, 0.05];
    rings = #[0.05, 0.05, 0.05, 0.05];
	ksrc = DynKlank.ar(`[freqs, amps, rings], src * amp);
	Out.ar(out, ksrc.f);
}).add;
//://:
a = Synth(\grainFroth3, [buf: ~i2, amp: 0.05]);
a.ctl(
	\f1, { LFNoise0.kr(0.2).range(100, 2000) },
	\f2, { LFNoise0.kr(0.3).range(100, 2000) },
	\f3, { LFNoise0.kr(0.4).range(100, 2000) },
	\f4, { LFNoise0.kr(2).range(100, 2000) },
);
//:
a release: 0.3;
//:!!!!!!!!
a.ctl(
	\f1, { LFNoise1.kr(1.2).range(100, 2000) },
	\f2, { LFNoise1.kr(2.3).range(100, 2000) },
	\f3, { LFNoise1.kr(1.4).range(100, 2000) },
	\f4, { LFNoise1.kr(2).range(100, 2000) },
);
//:
a release: 20;
