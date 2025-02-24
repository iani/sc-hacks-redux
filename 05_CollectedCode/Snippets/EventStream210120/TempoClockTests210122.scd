/* 22 Jan 2021 22:00
Refresh tempo clock knowledge
*/
//:
a = TempoClock.default;

a.sched(1, { "alpha".postln; 1; });
//:
a.tempo = 0.4;
// ================================================================
//:Playing routines with tempo clock
t = TempoClock();
t = TempoClock(1);
t = TempoClock(1, 1);
//:
t = TempoClock();
{
	10 do: { | i |
		i.postln;
		1.wait;
	}
}.fork(t);
//:From http://doc.sccode.org/Classes/Quant.html
// Routine({ ... }).play(quant: 4.0);
// .r returns Routine() ...:
//:+Synchronize 2 routines to beats with quant
{
	10 do:  | i |
	i.postln;
	(dur: 0.1, degree: 20, amp: 0.5).play;
	2.wait;
}.r.play; // (quant: 4);
0.25.wait;
{
	10 do: { | i |
		i.postln;
		(dur: 0.1, degree: 30, amp: 0.5).play;
		2.wait;
	}	
}.r.play; // (quant: 4)
//:build a function to run synchronization tests
~testRoutine = { | degree = 10, dur = 1 |
	{
		10 do: { | i |
			postf("count: %, degree: %, dur: %\n", i, degree, dur);
			(dur: dur, degree: degree.postln, amp: 0.5).play;
			dur.wait;
		}	
	}.r;
};
//:
//:test testroutine
~testRoutine.().play;
//:+no synchronization if quant is nil
~testRoutine.(10).play(quant: nil);
0.1.wait;
~testRoutine.(11).play(quant: nil);
//:+synchronization with quant
TempoClock.default.tempo = 0.5;
~testRoutine.(10).play(quant: 0.5);
0.1.wait;
~testRoutine.(12).play(quant: 0.5);
3.wait; // test tempo change
TempoClock.default.tempo = 3;
//:
