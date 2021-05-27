//:
SynthDef.new(\lfpar, {|freq = 440, amp = 0.8, pan = 0, atk = 2, rel = 2, cfmin = 500, cfmax = 2000, cfhzmin = 0.1, cfhzmax = 0.3, rqmin = 0.1, rqmax = 0.2|
	var sig, env;
	sig = LFPar.ar(Vibrato.kr(freq));
	env = EnvGen.ar(Env.new([0, 1, 0], [atk, rel], \wel, ),doneAction:2);
	sig = Pan2.ar(sig, pan, amp);
	sig = LPF.ar(sig, {LFNoise1.kr(
		LFNoise1.kr(4).exprange(cfhzmin, cfhzmax)
	).exprange(cfmin, cfmax)}!2, {LFNoise1.kr(0.1).exprange(rqmin, rqmax)}!2);
	sig = FreeVerb.ar(sig, 0.45, 0.8);
	sig = sig * env * amp;
	Out.ar(0, sig);
}).add;


//:
Pdef (
	\lfpar0,
    Pbind(
		\instrument, \lfpar,
		\dur, Pwhite(2, 4, inf),
		\midinote, Pxrand([
			[23, 35, 54, 63, 64],
			[45, 52, 54, 59, 61, 64],
			[28, 40, 47, 56, 59, 63],
			[42, 52, 57, 61, 63],
		], inf),
		\cfmin, 100,
		\cfmax, 1500,
		\rqmin, Pexprand(0.01, 0.15, inf),
		\atk, Pwhite(2.0, 2.5, inf),
		\rel, Pwhite(6.5, 10.0, inf),
		\amp, 0.45,
	);
);
//:

SynthDef.new(\dust, { |freq = 440, atk = 1, rel = 1, amp = 0.6, pan = 0, den = 2|
	var sig, env;
	sig = Dust.ar(den);
	env = EnvGen.kr(Env.new([0, 1, 0], [atk, rel], [1, -1]), doneAction:2);
	sig = Pan2.ar(sig, ClipNoise.kr(), amp);
	sig = sig * env * 2;
	sig = FreeVerb.ar(sig, 0.8, 0.6, 0.4, 2);
	sig = BBandPass.ar(sig, {LFNoise1.kr(
		LFNoise1.kr(4).exprange(0.4, 0.5)
	).exprange(800, 1900)}!2, {LFNoise1.kr(0.1).exprange(0.2, 0.3)}!2);
	Out.ar(0, sig);
}).add;
//:
Pdef(
	\dust,
    Pbind(
		\instrument, \dust,
		\dur, Pseq([0.4, 0.3, 0.4], inf),
		\midinote, Pseq([66, 70, 73], inf),
		\atk, Pwhite(0.2, 0.5, inf),
		\rel, Pwhite(0.2, 0.3, inf),
		\amp, Pexprand( 0.4, 0.6, inf),
	);
);

//:

SynthDef.new(\saw, {|freq = 500, pan = 0, amp = 0.05, atk = 2, sus = 0, rel = 3, cf = 1200|
	var sig, env, type;
	sig = 0;
	env = EnvGen.kr(Env([0, 1, 1, 0], [atk, sus, rel], [1, 0, -1]), doneAction:2);
	10.do {
		type = VarSaw.ar(freq);
		sig = sig + type;
	};
	sig = BPF.ar(sig, cf, 0.3);
	sig = sig * env * amp;
	sig = Pan2.ar(sig, pan, amp);
	Out.ar(0, sig);
}).add;

//:

Pdef(
	\saw,
    Pbind(
		\instrument, \saw,
		\freq, (Scale.major.degrees+30).midicps.choose,
		\amp, 0.16,
		\atk, 6,
		\sus, 1,
		\rel, 3,
	);
);

//:

SynthDef.new(\sine, {|count, amp = 0.3, pan = 0, freq = 440, atk = 1, sus = 1, rel = 1|
	var sig, env;
	sig = SinOsc.ar(freq);
	env = EnvGen.kr(Env([0, 1, 1, 0], [atk, sus, rel], [1, 0, -1]), doneAction:2);
	sig = FreeVerb.ar(sig, 0.5, 0.9, 0.7);
	sig = sig * env * amp;
	sig = Pan2.ar(sig, pan, amp);
	Out.ar(0, sig);
}).add;

//:
Pdef (
	\sine,
	Pbind(
		\instrument, \sine,
		\dur, Pseq([1, 1, 1], inf),
		\midinote, Pseq([64, 68, 71], inf),
		\amp, 0.20,
	);
);
//:\sine pdef coded as EventPattern
(
instrument: \sine,
\dur: Pseq([1, 1, 1], inf),
\midinote: Pseq([64, 68, 71], inf),
\amp: 0.20,
) +> \sine;
//:
\sine.stop;
//:
\sine.play;

//:

fork{
	Pdef(\sine).play;
	20.wait;
	Pdef(\saw).play;
	30.wait;
	Pdef(\lfpar0).play;
	40.wait;
	Pdef(\dust).play;
	25.wait;
	Pdef(
		\dust,
		Pbind(
			\instrument, \dust,
			\amp, 1,
			\den, Pseq([1000], inf),
		);
	).play;
	8.wait;
	Pdef(
		\dust,
		Pbind(
			\instrument, \dust,
			\amp, 0.6,
			\den, Pseq([8], inf),
		);
	).play;
	60.wait;
	Pdef (
		\lfpar0,
		Pbind(
			\instrument, \lfpar,
			\dur, Pwhite(1, 2, inf),
			\midinote, Pxrand([
				[45, 61, 63],
			], inf),
			\amp, 0.25,
			\rel, 4,
		);
	).play;
	15.wait;
	Pdef(
		\dust,
		Pbind(
			\instrument, \dust,
			\amp, 0.5,
			\den, 3,
		);
	).play;
	12.wait;
	Pdef (
		\sine,
		Pbind(
			\instrument, \sine,
			\dur, Pseq([1.5], inf),
			\midinote, Pseq([64, 68, 71], inf),
			\amp, 0.19,
			\rel, Pseq([2], inf),
		);
	).play;
	9.wait;
	Pdef (
		\sine,
		Pbind(
			\instrument, \sine,
			\dur, Pseq([2], inf),
			\midinote, Pseq([64, 68, 71], inf),
			\amp, 0.17,
			\rel, Pseq([3], inf),
		);
	).play;
	25.wait;
	Pdef (
		\lfpar0,
		Pbind(
			\instrument, \lfpar,
			\dur, Pwhite(4, 6, inf),
			\midinote, [23, 35, 54, 63, 64],
			\cfmin, 200,
			\cfmax, 500,
			\cfhzmin, 0.2,
			\cfhzmax, 0.8,
			\rqmin, Pexprand(0.01, 0.15, inf),
			\atk, Pwhite(2.0, 2.5, inf),
			\rel, Pwhite(6.5, 10.0, inf),
			\amp, 0.50,
		);
	).play;
	Pdef (
		\sine,
		Pbind(
			\instrument, \sine,
			\dur, Pseq([2], inf),
			\midinote, Pseq([64, 68, 71], inf),
			\amp, 0.15,
			\rel, Pseq([3], inf),
		);
	).play;
	25.wait;
	Pdef(
		\saw,
		Pbind(
			\instrument, \saw,
			\dur, Pwhite(1, 2, inf),
			\freq, (Scale.minor.degrees+30).midicps.choose,
			\amp, 0.10,
			\atk, 3,
			\sus, 2,
			\rel, 3,
			\cf, Pexprand(300, 900, inf),
		);
	).play;
	40.wait;
	Pdef(
		\dust,
		Pbind(
			\instrument, \dust,
			\dur, Pseq([5, 8, 6],1),
			\den, Pseq([40, 100, 30], 1),
			\amp, Pseq ([0.8, 1, 0.8], 1),
			\atk, Pseq ([3, 2, 3], 1),
			\rel, Pseq ([3, 7, 4], 1),
		);
	).play;
	20.wait;
	Pdef(
		\dust,
		Pbind(
			\instrument, \dust,
			\den, Pseq([30], inf),
			\amp, Pseq ([0.8], inf),
		);
	).play;
	10.wait;
	Pdef(
		\chordsSaw,
		Pbind(
			\instrument, \saw,
			\dur, 6,
			\midinote, Pxrand([[40, 68, 40, 71],
				[76, 56, 76, 59]], inf),
			\amp, 0.10,
			\atk, 2,
			\rel, 4,
		);
	).play;
	20.wait;
	Pdef (
		\sine,
		Pbind(
			\instrument, \sine,
			\dur, Pseq([3, 1, 2], inf),
			\midinote, Pseq([51, 56, 59], inf),
			\amp, 0.25,
			\atk, 1.5,
			\rel, 2,
		);
	).play;
	20.wait;
	Pdef(
		\dust,
		Pbind(
			\instrument, \dust,
			\amp, 0.5,
			\den, 7,
		);
	).play;
	30.wait;
	Pdef(
		\saw,
		Pbind(
			\instrument, \saw,
			\freq, (Scale.minor.degrees+30).midicps.choose,
			\amp, 0.13,
			\atk, 6,
			\sus, 1,
			\rel, 3,
		);
	).play;
	15.wait;
	Pdef(\chordsSaw).stop;
	15.wait;
	Pdef (\lfpar0).stop;
	20.wait;
	Pdef(\sine).stop;
	30.wait;
	Pdef(\saw).stop;
	30.wait;
	Pdef(\dust).stop;
};




