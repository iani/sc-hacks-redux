/*  9 Nov 2023 18:16
Insect 3.

SinOsc (2x) + AM + Amp Env + Freq Env


Todo: redo using *triggered* freq + amp emvelopes.

*/
//:FINISHED PROTOTYPE : stereo + delay - 4 copies
{
	var srcfunc, dt = 0.08, reps = 4;
	dt = 0.0075 * reps * 2;
	srcfunc = {
		var basefreq = 5730, amp = 0.5, ampenv, freqenv, ampmod;
		var freqd;
		freqd = Rand(0, 100);
		basefreq = basefreq + freqd;
		// TODO: review env curves. ampenv should be [amp, 0]?
		ampenv = Env([0, amp, 0], [0.0075, 0.0075], 5);
		// TODO: test curve values -5, 0, 5;
		freqenv = Env([basefreq, basefreq - 1250], [0.015], 5);
		ampmod = SinOsc.ar(250).range(0.95, 1.05);
		SinOsc.ar(freqenv.kr, 0, amp * SinOsc.ar(250).range(0.95, 1.05))
		* ampenv.kr;
	};
	DelayC.ar(srcfunc ! reps, dt * reps, (1..reps).normalize(0, dt)).mix
	* Env(1.dup, [dt * reps]).kr(2);
} +> \insect3;
//:as synthdef:
SynthDef(\insect3, { | out = 0 |
	var srcfunc, dt = 0.08, reps = 4, src;
	dt = 0.0075 * reps * 2;
	srcfunc = {
		var basefreq = 5730, amp = 0.5, ampenv, freqenv, ampmod;
		var freqd;
		freqd = Rand(0, 100);
		basefreq = basefreq + freqd;
		// TODO: review env curves. ampenv should be [amp, 0]?
		ampenv = Env([0, amp, 0], [0.0075, 0.0075], 5);
		// TODO: test curve values -5, 0, 5;
		freqenv = Env([basefreq, basefreq - 1250], [0.015], 5);
		ampmod = SinOsc.ar(250).range(0.95, 1.05);
		SinOsc.ar(freqenv.kr, 0, amp * SinOsc.ar(250).range(0.95, 1.05))
		* ampenv.kr;
	};
	src = DelayC.ar(srcfunc ! reps, dt * reps, (1..reps).normalize(0, dt)).mix
	* Env(1.dup, [dt * reps]).kr(2);
	Out.ar(out, src);
}).add;
//:test synthdef
\insect3 +> \test;
// ================ Step-by-step drafts =================
//:Base
{
	var basefreq = 5730, amp = 0.1;
	SinOsc.ar(basefreq, 0, amp);
} +> \insect3
//: ampenv
{
	var basefreq = 5730, amp = 0.1, ampenv;
	ampenv = Env([0, amp, 0], [0.0075, 0.0075]);
	SinOsc.ar(basefreq, 0, amp) * ampenv.kr(2);
} +> \insect3
//:ampenv + ampmod
{
	var basefreq = 5730, amp = 0.5, ampenv, freqenv, ampmod;
	var freqd;
	freqd = Rand(0, 100);
	basefreq = basefreq + freqd;
	ampenv = Env([0, amp, 0], [0.0075, 0.0075], 5);
	freqenv = Env([basefreq, basefreq - 1250], [0.015]);
	ampmod = SinOsc.ar(250).range(0.95, 1.05);
		SinOsc.ar(freqenv.kr, 0, amp * SinOsc.ar(250).range(0.95, 1.05))
		* ampenv.kr(2);
} +> \insect3;
//:prepare delay
{
	var srcfunc;
	srcfunc = {
	var basefreq = 5730, amp = 0.5, ampenv, freqenv, ampmod;
	var freqd;
	freqd = Rand(0, 100);
	basefreq = basefreq + freqd;
	ampenv = Env([0, amp, 0], [0.0075, 0.0075], 5);
	freqenv = Env([basefreq, basefreq - 1250], [0.015]);
	ampmod = SinOsc.ar(250).range(0.95, 1.05);
		SinOsc.ar(freqenv.kr, 0, amp * SinOsc.ar(250).range(0.95, 1.05))
		* ampenv.kr(2);
	};
	srcfunc.value;
} +> \insect3;
//:prepare delay b : stereo
{
	var srcfunc;
	srcfunc = {
	var basefreq = 5730, amp = 0.5, ampenv, freqenv, ampmod;
	var freqd;
	freqd = Rand(0, 100);
	basefreq = basefreq + freqd;
	ampenv = Env([0, amp, 0], [0.0075, 0.0075], 5);
	freqenv = Env([basefreq, basefreq - 1250], [0.015]);
	ampmod = SinOsc.ar(250).range(0.95, 1.05);
		SinOsc.ar(freqenv.kr, 0, amp * SinOsc.ar(250).range(0.95, 1.05))
		* ampenv.kr(2);
	};
	srcfunc ! 2;
} +> \insect3;
//:prepare delay c : stereo + delay
{
	var srcfunc;
	srcfunc = {
	var basefreq = 5730, amp = 0.5, ampenv, freqenv, ampmod;
	var freqd;
	freqd = Rand(0, 100);
	basefreq = basefreq + freqd;
	ampenv = Env([0, amp, 0], [0.0075, 0.0075], 5);
	freqenv = Env([basefreq, basefreq - 1250], [0.015]);
	ampmod = SinOsc.ar(250).range(0.95, 1.05);
		SinOsc.ar(freqenv.kr, 0, amp * SinOsc.ar(250).range(0.95, 1.05))
		* ampenv.kr(0);
	};
	DelayC.ar(srcfunc ! 2, [0.1, 0.1], [0, 0.02]);
} +> \insect3;
//:prepare delay c : stereo + delay - 4 copies
{
	var srcfunc;
	srcfunc = {
	var basefreq = 5730, amp = 0.5, ampenv, freqenv, ampmod;
	var freqd;
	freqd = Rand(0, 100);
	basefreq = basefreq + freqd;
	ampenv = Env([0, amp, 0], [0.0075, 0.0075], 5);
	freqenv = Env([basefreq, basefreq - 1250], [0.015]);
	ampmod = SinOsc.ar(250).range(0.95, 1.05);
		SinOsc.ar(freqenv.kr, 0, amp * SinOsc.ar(250).range(0.95, 1.05))
		* ampenv.kr(0);
	};
	DelayC.ar(srcfunc ! 4, 0.08, (1..4).normalize(0, 0.08)).mix;
} +> \insect3;
//:FINISHED PROTOTYPE : stereo + delay - 4 copies
{
	var srcfunc, dt = 0.08, reps = 4;
	dt = 0.0075 * reps * 2;
	srcfunc = {
		var basefreq = 5730, amp = 0.5, ampenv, freqenv, ampmod;
		var freqd;
		freqd = Rand(0, 100);
		basefreq = basefreq + freqd;
		// Todo: review env curves. ampenv should be [amp, 0]?
		ampenv = Env([0, amp, 0], [0.0075, 0.0075], 5);
		freqenv = Env([basefreq, basefreq - 1250], [0.015], 5);
		ampmod = SinOsc.ar(250).range(0.95, 1.05);
		SinOsc.ar(freqenv.kr, 0, amp * SinOsc.ar(250).range(0.95, 1.05))
		* ampenv.kr(0);
	};
	DelayC.ar(srcfunc ! reps, dt * reps, (1..reps).normalize(0, dt)).mix
	* Env(1.dup, [dt * reps]).kr(2);
} +> \insect3;
//:TODO: Recode using localbuf to enable many repetitions
//:see freq env to check shape
var basefreq = 5730;
Env([basefreq, basefreq - 1250], [0.015], 5).plot;
//:see extra amplitude envelope
{ SinOsc.ar(250).range(0.95, 1.05) }.plot(0.015);
//:create durs array:
var dt = 0.08, durs, dur;
durs = dt.dup(4).integrate;
dur = durs.last;
durs = [0] ++ durs[..2];
postln("durs:" + durs + "total dur:" + dur);
//:create + test stopping envelope
var env;
env = Env([1, 1], [1], \step);
// env.plot;
{ SinOsc.ar(400, 0, 0.1).dup * Env([1, 1], [1], ).kr(2) } +>  \testenv;

//:
* Env([1, 0], [1], \step).kr } +> \testenv;
//:
Env.step(levels: [ 0, 1 ], times: [ 1, 1 ]).plot;
Env([1, 1], [1], \step).plot;
//:
var reps = 4, dt = 0.08;
(1..reps).normalize(0, dt);
//:
Env([0, 0.5, 0], [0.0075, 0.0075], 5).plot;
//:
\x +> \test;
0 +> \test;