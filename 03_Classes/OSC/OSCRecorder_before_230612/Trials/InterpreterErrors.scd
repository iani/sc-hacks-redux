/* 26 Oct 2022 23:22
PROTOTYPE
For OscReader convert read data.

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

NOTE: Fix interpreting read data code strings:
If the code saved contains a syntax error, then interpreting
the code will issue the same error.  This will cause the
interpretation loop to stop!
There seems to be no way to catch this error ("try" and "protect"
	do not work when doing "astring".interpret. ).
But one can repeat the loop if one does it inside a routine.
Use this as example, and build it in method "readData".

Alternatively, have readData call a separate method in a second
runthrough, which does the interpreting inside a forked routine,
and repeats it until all code snippets with syntax errors have been
ignored.

{
	5 do: {
[
	"1", "pi", "(1..3).sqrt", "xxx2"
] do: { | i | i.interpret.postln; };
		1.wait;
	}
}.fork

*/

~testscore = [
	[1, "1"],
	[2, "2"],
	[3, "x3"],
	[4, "4"],
	[5, "5"],
];

~workcopy = ~testscore.flop;
//:
~result1 = ~workcopy[1];
{
	~result2 = ~result1 collect: { | i |
		var r;
		r = i.interpret;
		if (r.isNil) { [\error, 0] } { r }
	};
}.fork
//:
~result2;
~result2.squared;
//:
~testscore = nil;
~workcopy = nil;
~result1 = nil;
~result2 = nil;

~testscore = [
	[1, "1"],
	[2, "2"],
	[3, "x3"],
	[4, "4"],
	[5, "5"],
];

~workcopy = ~testscore.flop;
~result1 = ~workcopy[1];
~result3 = ~result1 collect: { | i |
	var r;
	r = i.interpret;
	if (r.isNil) { [\error, 0] } { r }
};
//:
~result3;