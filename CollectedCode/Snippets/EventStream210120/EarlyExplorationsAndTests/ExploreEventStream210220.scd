//: 20 Jan 2021 19:26
/* explore how eventstream plays.

*/
//:TEMPORARILY DISABLED
/* 
EventPattern((amp: 0.5, freq: 1000, dur: 0.25)).play;

EventPattern((amp: 0.5, degree: Pbrown(10, 20, 2), dur: 0.05)).play;
*/
//:
a = EventStream((amp: 0.5, degree: Pseries(20, 1, 10)));
a;
a.event.dur;
a.setParentKey(\dur, 0.1);

a.parent;
a.event.parent;

a.event;

a.next;
a.event.dur;
a.next.dur;
a.next.parent;

a.event.play;

a


a.dur; // before playing, is nil
a.play; // provides defaultParentEvent and plays
a.dur; // after playing, is 1
//:Try a different approach - calling next many times:
a = EventStream((amp: 0.5, degree: Pseries(21, 1, 5)));
10 do: {
	a.next.postln;
};
//:
/*
What we learn from the above:

As soon as any stream in any key of the event returns nil, the EventStream returns nil. This is a result of this statement found in the next method of EventStream: 

				if (outValue.isNil) { ^nil };

(This method was copied from Stream:prNext - I think. 
Must be from an older version of the library).

*/