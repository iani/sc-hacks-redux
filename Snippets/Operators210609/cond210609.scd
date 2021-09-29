/*  9 Jun 2021 10:53
Condition.
No comment.
*/

//:
a = {
	{
		"waiting".postln;
		nil.yield
	}.value;
	"went".postln;
}.fork;
//:
a.next;
//:
//:
a = {
	var synth;
	{
		synth = {WhiteNoise.ar(0.1).dup}.play;
		nil.yield
	}.value;
	synth.release;
}.fork;
//:
a.next;
//:
~cond = { | before, after |
	var routine, handover;
	routine = {
		handover = before.value;
		nil.yield;
		after.(handover);
	}.fork;
	routine;
};
//:
~waiter = ~cond.(
	{  { WhiteNoise.ar(0.1).dup }.play; },
	{ | s | s.postln; s release: 0.7 }
);
//:
~waiter.next;