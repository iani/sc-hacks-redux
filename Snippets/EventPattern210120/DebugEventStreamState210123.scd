//: 23 Jan 2021 08:06
/* event stream state:

- when to reset stream
- consistent behavior when stopping with message or command-period.

*/
//:
a = EventStream((
	amp: 0.5,
	dur: Pseq([2, 1, 1], 1),
	degree: Pseq((0..2) + 14, inf)
));
//:
a.streamOperations.history.future do: _.postln;
//:
a.play;
//:
a.next.play;
//:
a.streamOperations.play;
//;
a.streamOperations;
//:
a.play;
//:
a.stop;