//: 22 Jan 2021 03:09
/*
very simple approach to scheduling
does not require routine

Tries
*/
//:schedule something repeatedly
AppClock.sched(1, { "hello".postln; 1; });
//:
a = EventStream((dur: 1));
a.next;
//:
a = EventStream((dur: 1));
AppClock.sched(1, {
	var next;
	next = a.next;
	next.postln;
	next.dur;
});

//:
a = EventStream((dur: Pn(1, 3), play: Pseries(1, 1, inf)));
AppClock.sched(0, {
	var next;
	next = a.next;
	next.play.postln;
	if (next.notNil) { next.dur } {};
});

//:
a = EventStream((dur: 0.2, play: Pseries(1, 1, 10)));
AppClock.sched(0, {
	var next;
	next = a.next;
	a.changed(\newEvent); // previous voices stop in response to this
	next.play.postln;     // new voices start here
	if (next.notNil) { next.dur } {};
});
//: