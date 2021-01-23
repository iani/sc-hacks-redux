//: 23 Jan 2021 08:06
/* event stream state:

- when to reset stream
- consistent behavior when stopping with message or command-period.

*/
//:
a = EventStream((
	amp: 0.5,
	dur: Pseq([2, 1, 5], 1),
	degree: Pseq((0..2) + 14, inf)
));
//:
a.play;
//:
a
//:
a.next;
//:
a.next.play;
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
//:================================================================
// figuring out how many events are left in the future
//:count nils in an array
a = { [nil, \X].choose } ! 10;
a.postln;
a.collect({ | i | (i !== nil).binaryValue }).sum;
//:alternative
a = { [nil, \X].choose } ! 10;
a.postln;
a indexOf: nil