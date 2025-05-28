// 日 25  5 2025 11:22
//TGrains.ar(numChannels, trigger: 0, bufnum: 0, rate: 1, centerPos: 0, dur: 0.1, pan: 0, amp: 0.1, interp: 4)
//
// numChannels // The number of output channels.
//
// trigger
// At each trigger, the following arguments are sampled and used as the arguments of a new grain. A trigger occurs when a signal changes from non-positive to a positive value.
// If the trigger is audio rate then the grains will start with sample accuracy.

// bufnum
// The index of the buffer to use. It must be a one channel (mono) buffer.

// rate
// 1.0 is normal, 2.0 is one octave up, 0.5 is one octave down -1.0 is backward normal rate… etc.

// centerPos
// The position in the buffer in seconds at which the grain envelope will reach maximum amplitude.

// dur
// Duration of the grain in seconds.

// pan
// determines where to pan the output.

// If numChannels = 1, the pan argument is ignored.
// If numChannels = 2, panning is similar to Pan2.
// If numChannels > 2, panning is the same as PanAz.
//
//: Synthdef draft 1 Most basic grainst
SynthDef(\tgrains1, { | out = 0, bufnum = 0, t_trig = 0, rate = 1, centerPos = 0, dur = 0.1, pan = 0, amp = 0.1 |
	var src;
//TGrains.ar(numChannels, trigger: 0, bufnum: 0, rate: 1, centerPos: 0, dur: 0.1, pan: 0, amp: 0.1, interp: 4)
	t_trig = Impulse.kr(10);
	src = TGrains(2, t_trig, bufnum, rate, centerPos * BufDur.kr(bufnum), dur, pan, amp, 4);
	Out.ar(out, src.f);
}).add;
//:test tgrains
a = Synth(\tgrains1, [bufnum: ~busy1.bufnum]);
a.set(\dur, 1);
a.ctl(\t_trig, { Impulse.kr(1) });
//: From TGrains help file
{
    var trate, dur, rate, b;
	b = ~busy1;
    trate = MouseY.kr(2,200,1);
    dur = 4 / trate;
    rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate,
		MouseX.kr(0,BufDur.kr(b)), dur,
		Dseq([-1, 1], inf), 0.1, 2);
}.scope(zoom: 4);
//: 1 De-re-constructing synthdef --- From TGrains help file
SynthDef(\tgrainsr1, {	| out = 0, bufnum = 0, amp = 0.1 |
    var trate, dur, rate, src;
    trate = MouseY.kr(2,200,1);
    dur = 4 / trate;
    rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    src = TGrains.ar(2, Impulse.ar(trate), bufnum, rate,
		MouseX.kr(0,BufDur.kr(bufnum)), dur,
		Dseq([-1, 1], inf), amp, 2
	);
	Out.ar(out, src.f);
}).add;
//:
a = Synth(\tgrainsr1, [bufnum: ~busy1.bufnum]);
a.set(\amp, 0.1);
//: 2 De-re-constructing synthdef 2 --- From TGrains help file
SynthDef(\tgrainsr2, {	| out = 0, bufnum = 0, rate = 1, amp = 0.1 |
    var trate, dur, src;
    trate = MouseY.kr(2,200,1);
    dur = 10 / trate;
    src = TGrains.ar(2, Impulse.ar(trate), bufnum, rate,
		MouseX.kr(0,BufDur.kr(bufnum)), dur,
		Dseq([-1, 1], inf), amp, 4
	);
	Out.ar(out, src.f);
}).add;
//:
a = Synth(\tgrainsr2, [bufnum: ~busy1.bufnum]);
a.ctl(\rate, { SinOsc.kr(5.reciprocal).range(0.1, 2) });
a.set(\rate, 1.1);
a.release;
//: 2b De-re-constructing synthdef 2b ---
// target full args | out = 0, bufnum = 0, t_trig = 0, rate = 1, centerPos = 0, dur = 0.1, pan = 0, amp = 0.1 |
SynthDef(\tgrainsr2b, {	| out = 0, bufnum = 0, trate = 100, rate = 1, amp = 0.1 |
    var dur, src;
    // trate = MouseY.kr(2,200,1);
    dur = 10 / trate;
    src = TGrains.ar(2, Impulse.ar(trate), bufnum, rate,
		MouseX.kr(0,BufDur.kr(bufnum)), dur,
		Dseq([-1, 1], inf), amp, 4
	);
	Out.ar(out, src.f);
}).add;
//:
a = Synth(\tgrainsr2b, [bufnum: ~busy1.bufnum]);
a.ctl(\rate, { SinOsc.kr(5.reciprocal).range(0.1, 2) });
a.set(\rate, 1.1);
a.release;
//: 2c De-re-constructing synthdef 2b --- Introducing pos
// target full args | out = 0, bufnum = 0, t_trig = 0, rate = 1, centerPos = 0, dur = 0.1, pan = 0, amp = 0.1 |
SynthDef(\tgrainsr2c, {	| out = 0, bufnum = 0, trate = 100, rate = 1, centerPos = 0.5, amp = 0.1 |
    var dur, src;
    // trate = MouseY.kr(2,200,1);
    dur = 10 / trate;
    src = TGrains.ar(2, Impulse.ar(trate), bufnum, rate,
		centerPos * BufDur.kr(bufnum),
		dur,
		Dseq([-1, 1], inf), amp, 4
	);
	Out.ar(out, src.f);
}).add;
//:
a = Synth(\tgrainsr2c, [bufnum: ~busy1.bufnum]);
//:
a.ctl(
	\rate, { LFNoise1.kr(0.3).range(0.5, 2) },
	\centerPos, { SinOsc.kr(0.1).fold(0, 1) }
);
//: 2d De-re-constructing synthdef 2b --- introducing dur / trate, pan = 0
// target full args | out = 0, bufnum = 0, t_trig = 0, rate = 1, centerPos = 0, dur = 1, pan = 0, amp = 0.1 |
SynthDef(\tgrainsr2c, {	| out = 0, bufnum = 0, trate = 100, rate = 1, centerPos = 0.5, pan = 0, amp = 0.1 |
    var dur, src;

    dur = 10 / trate;
    src = TGrains.ar(2, Impulse.ar(trate), bufnum, rate,
		centerPos * BufDur.kr(bufnum),
		dur,
		pan, amp, 4
	);
	Out.ar(out, src.f);
}).add;
//:
a = Synth(\tgrainsr2c, [bufnum: ~i3.bufnum, dur: 0.01]);
{
a.ctl(
	\rate, { LFNoise1.kr(0.3).range(0.5, 2) },
	\centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\trate, { LFNoise1.kr(0.3).range(10, 100) }
);
}.defer(0.1);
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
//
a.ctl(
	\trig, { Dust.kr(5) },
	\centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\rate, { LFNoise1.kr(0.3).range(0.5, 4) },
	\dur, { LFNoise1.kr(0.3).range(0.1, 1) }
);
//:
a = Synth(\tgrainsr2trig, [bufnum: ~busy1.bufnum]);
{
a.ctl(
	\trig, { Impulse.kr(0.5) },
	\dur, { SinOsc.ar(1).range(1, 2) },
	\centerPos, { SinOsc.kr(0.05).fold(0, 1) },
);
}.defer(0.1);
//:2 introducing a filter
SynthDef(\tgrainsr3, {	| out = 0, bufnum = 0, trate = 100, rate = 1, centerPos = 0.5, pan = 0, amp = 0.1, freq = 400, dt = 1 |
    var dur, src, filter;

    dur = 10 / trate;
    src = TGrains.ar(2, Impulse.ar(trate), bufnum, rate,
		centerPos * BufDur.kr(bufnum),
		dur,
		pan, amp / 10, 4
	);
	filter = Ringz.ar(src, freq, dt / 10);
	Out.ar(out, filter.f);
}).add;
//:
a = Synth(\tgrainsr3, [bufnum: ~busy1.bufnum, dur: 0.1, freq: 2000]);
{
a.ctl(
	\rate, { LFNoise1.kr(0.3).range(1.5, 2) },
	// \centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\centerPos, { LFSaw.kr(0.2).range(0, 1) },
	\trate, { LFNoise1.kr(0.3).range(10, 100) },
	\freq, { LFNoise1.kr(0.3).range(1500, 2000) }
);
}.defer(0.1);
//:
a = Synth(\tgrainsr3, [bufnum: ~busy1.bufnum, dur: 0.1]);
{
a.ctl(
	\rate, { LFNoise1.kr(0.3).range(1.5, 2) },
	// \centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\centerPos, { LFSaw.kr(0.2).range(0, 1) },
	\trate, { LFNoise1.kr(0.3).range(10, 100) },
	\freq, { LFNoise1.kr(0.3).range(100, 1000) }
);
}.defer(0.1);
//:
a = Synth(\tgrainsr3, [bufnum: ~busy1.bufnum, dur: 0.1]);
{
a.ctl(
	\rate, { LFNoise1.kr(0.3).range(1.5, 2) },
	// \centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\centerPos, { LFSaw.kr(0.2).range(0, 1) },
	\trate, { LFNoise1.kr(0.3).range(10, 100) },
	\freq, { Line.kr(1000, 100, 20, doneAction: 2) }
);
}.defer(0.1);
//:
a = Synth(\tgrainsr3, [bufnum: ~busy1.bufnum, dur: 0.1]);
//
a.ctl(
	\rate, { LFNoise1.kr(0.3).range(0.5, 0,8) },
	\centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\trate, { LFNoise1.kr(0.3).range(10, 100) },
	\freq, { LFNoise1.kr(0.3).range(100, 1000) }
);
//:+DynKlank resonant filter bank
SynthDef(\tgrainsr4, {	| out = 0, bufnum = 0, trate = 100, rate = 1, centerPos = 0.5, pan = 0, amp = 0.1, freq = 400, dt = 1 |
    var dur, src, filter;

    dur = 10 / trate;
    src = TGrains.ar(2, Impulse.ar(trate), bufnum, rate,
		centerPos * BufDur.kr(bufnum),
		dur,
		pan, amp / 10, 4
	);
	filter = Ringz.ar(src, freq, dt / 10);
	Out.ar(out, filter.f);
}).add;
//:
a = Synth(\tgrainsr3, [bufnum: ~busy1.bufnum, dur: 0.1, freq: 2000]);
{
a.ctl(
	\rate, { LFNoise1.kr(0.3).range(1.5, 2) },
	// \centerPos, { SinOsc.kr(0.05).fold(0, 1) },
	\centerPos, { LFSaw.kr(0.2).range(0, 1) },
	\trate, { LFNoise1.kr(0.3).range(10, 100) },
	\freq, { LFNoise1.kr(0.3).range(1500, 2000) }
);
}.defer(0.1);
//:
a.ctl(
	\rate, { LFNoise1.kr(0.3).range(1.5, 2) }
);
//:
a.set(\trate, 10);
//:
a.release;
//: - EXTRA: grainFroth
//: Model UGenFunc
GrainFroth_ : UGenFunc {
	*ar {
		var trate, dur, clk, pos, pan, b;
		b = (~buf ? \default).buf ?? { \default.buf };
		// trate = MouseY.kr(1,400,1);
		trate = \trate.br(~trate ? 0.5).linexp(0, 1, 1, 400);
		dur = 8 / trate;
		clk = Impulse.kr(trate);
		// pos = MouseX.kr(0,BufDur.kr(b));
		pos = Pos(b);
		pan = WhiteNoise.kr(0.8);
		^TGrains.ar(2, clk, b, 2 ** WhiteNoise.kr(2), pos, dur, pan, 0.1)
		* \vol.br(~vol ? 1);
	}
}
//: SynthDef grainFroth
SynthDef(\grainFroth1, { | out = 0, buf = 0, trate = 0.5, pos = 0, amp = 0.2 |
	var dur, clk, src, pan;
	trate = trate.linexp(0, 1, 1, 400);
	dur = 8 / trate;
	clk = Impulse.kr(trate);
	pos = pos * BufDur.kr(buf);
	pan = WhiteNoise.kr(0.8);
	src = TGrains.ar(2, clk, buf, 2 ** WhiteNoise.kr(2), pos, dur, pan, amp, 4);
	Out.ar(out, src * amp);
}).add;
//:
a = Synth(\grainFroth1, [buf: ~i2]);
a.ctl(\pos, { LFSaw.kr(0.01) });
a.ctl(\trate, { LFNoise1.kr(0.1).range(0.1, 0.9) });
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

//: adding DynKlank
SynthDef(\grainFroth3, { | out = 0, buf = 0, trate = 0.5, pos = 0, amp = 0.1,
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
//:
a = Synth(\grainFroth3, [buf: ~this2]);
//:
a.ctl(
	\f1, { LFNoise0.kr(0.2).range(100, 2000) },
	\f2, { LFNoise0.kr(0.3).range(100, 2000) },
	\f3, { LFNoise0.kr(0.4).range(100, 2000) },
	\f4, { LFNoise0.kr(2).range(100, 2000) },
);
//:
a release: 5;
//:
a.freeCtls;
//: 3 De-re-constructing synthdef 3 --- From TGrains help file
// target full arguments:
// | out = 0, bufnum = 0, t_trig = 0, rate = 1, centerPos = 0, dur = 0.1, pan = 0, amp = 0.1 |

SynthDef(\tgrainsr3, {	| out = 0, bufnum = 0,  trate = 100, rate = 1, centerPos = 0.5, dur = 0.1, pan = 0, amp = 0.1  |
    var trate, dur, src;
    trate = MouseY.kr(2,200,1);
    dur = 10 / trate;
    src = TGrains.ar(2, Impulse.ar(trate), bufnum, rate,
		MouseX.kr(0,BufDur.kr(bufnum)), dur,
		Dseq([-1, 1], inf), amp, 4
	);
	Out.ar(out, src.f);
}).add;
//:
a = Synth(\tgrainsr3, [bufnum: ~busy1.bufnum]);
a.ctl(\rate, { SinOsc.kr(5.reciprocal).range(0.1, 2) });
a.set(\rate, 1.1);
a.release;

//: Study 1
// variables / arguments:
// numChannels: (static, internal!!!): Number of output channels
// buf: the index of the buffer to be played
// trate: the rate of triggering of the grains. Controls Impulse.ar
// rate: the rate of playback of the buffer
// pos: the position to play in the buffer - in seconds.
// 		Note: Pos should be in 0-1 and scaled with * BufDur
// dur:
{ | buf, trate, dur,
    var buf; // the buffer to play
	var trate; // rate of triggering (samples per second)
	var dur; // duration of each sample
	var srate; // sample playback rate. 1 = playback as recorded
	var pos; //
	var numChannels; number
	var pan; // panning position.
	// , b;
	b = ~busy1;
    trate = MouseY.kr(2,200,1);
    dur = 4 / trate;
    rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    TGrains.ar(1, Impulse.ar(trate), b, rate,
		MouseX.kr(0,BufDur.kr(b)), dur,
}.scope(zoom: 4);
//: Pre-study - for numchannels
// Range of 0 to 1.5 goes evenly between channel 0 and channel 8!
{
    var trate, dur, rate, b;
	b = ~busy1;
    trate = MouseY.kr(2,200,1);
    dur = 4 / trate;
    rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    TGrains.ar(8, Impulse.ar(trate), b, rate,
		MouseX.kr(0,BufDur.kr(b)), dur,
		SinOsc.kr(0.1).range(0, 1.5);
		, 0.1, 2);
}.scope(zoom: 4)

//: Pre-study - comparing numchannels pos with PanAz.
// Range of 0 to 1.5 goes evenly between channel 0 and channel 8!
{
    var trate, dur, rate, b, src;
	b = ~busy1;
    trate = MouseY.kr(2,200,1);
    dur = 4 / trate;
    rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    src = TGrains.ar(1, Impulse.ar(trate), b, rate,
		MouseX.kr(0,BufDur.kr(b)), dur, 0, 0.1, 2
	);
	PanAz.ar(8, src, SinOsc.kr(0.1).range(0, 1.5))
}.scope(zoom: 4)

//: Study 2
