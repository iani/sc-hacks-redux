//: 20 Jan 2021 22:38
/*
** 20 Jan 2021 22:32 define new kind of pattern that continues outputting the last value produced by the pattern that precedes it, for n number of times. 

Say the pattern is called Pcontinue.


	// For example: 
Pcontinue(Pseries(1, 1, 3), 3).asStream.all; // nextN(8) 
	// should produce: 
	// [1, 2, 3, 3, 3, 3, nil, nil]
	// More tests:
Pcontinue(Pn(Pseries(5, 3, 3), 3), 6).asStream.all;
Pcontinue(Pwhite(-10, 5, 4), 8).asStream.all;
Pcontinue(Pbrown(-5, 5, 3, 5), 8).asStream.all;

*/

Pcontinue : Pattern {
	var <>before, <>repeats=1;

	*new { | before, repeats = 1 |
		^this.newCopyArgs(before, repeats);
	}

	embedInStream {
		var previous, outval, beforeStream;
		beforeStream = before.asStream;
		previous = beforeStream.next;
		while {
			(outval = beforeStream.next).notNil
		}{
			previous.yield;
			previous = outval;
		};
		previous.yield;
		repeats do: {
			previous.yield;
		}
	}
	storeArgs { ^[ before, repeats ] }
}
