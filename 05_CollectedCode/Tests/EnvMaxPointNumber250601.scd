// 日  1  6 2025 08:04
// an envelope with 65537 levels and 65536 time intervals
// To load it, the Server real time memory must be increased.
//: Run this first:
Server.default.options.memSize = 2**18;
Server.default.reboot;
//:
SynthDef(\bigenv, {
	var n, times, valenv, fadenv, src, levels;
	n = 2.pow(16).asInteger;
	times = Array.fill(n, { 0.3 rrand: 0.4 });
	levels = Array.fill(n + 1, { 0.1 });
	levels[0] = 0;
	levels[n] = 0;
	valenv = Env(
		Array.fill(n + 1, { 400 rrand: 4000 }),
		times,
		\lin);
	fadenv = Env(levels, times);
	src = SinOsc.ar(valenv.kr(1, doneAction: 2), 0, 0.1).dup;
	Out.ar(0, src * fadenv.kr(1, doneAction: 2).f);
}).load;
//:
a = Synth(\bigenv);
a.release;
//:
fork {
	10 do: {
		var a, d;
		d = 0.25.exprand(2);
		a = Synth(\bigenv);
		{ a.release; } defer: d * 1.5;
		d.wait;
	}
};

//============================================================
//:Rewrite using Dindex demand ugen with buffer
// TDuty.kr(dur: 1.0, reset: 0.0, level: 1.0, doneAction: 0, gapFirst: 0)
//: from the help file:
b = Buffer.alloc(s, 24, 1);
//:
b.setn(0, { exprand(200, 500) } ! b.numFrames);
b.getn(0, b.numFrames, {|x| x.postln })
b.setn(0, 1000 ! 10);
//:
{
	var indexPattern;
    indexPattern = Dseq([Dseq([0, 3, 5, 0, 3, 7, 0, 5, 9], 3), Dbrown(0, 23, 1, 5)], inf);
    SinOsc.ar(
        Demand.kr(Dust.kr(10), 0, Dbufrd(b, indexPattern))
    ) * 0.1
}.play;
//:NOT THIS
{
	var indexPattern;
    indexPattern = Dseries(1, 1, inf);
    SinOsc.ar(
        TDuty.kr(1, 0, Dbufrd(b, indexPattern))
    ) * 0.1
}.play;
//:BUT THIS
(
{ var indexPattern;
    indexPattern = Dseries(1, 1);
    SinOsc.ar(
        Demand.kr(Impulse.kr(20), 0, Dbufrd(b, indexPattern))
    ) * 0.1
}.play;
)