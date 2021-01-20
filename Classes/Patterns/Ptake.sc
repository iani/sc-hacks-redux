//: 21 Jan 2021 07:15
/*
Return the first n elements from a stream, and nil after that.

This implementation is based on Pn and Pseq

Ptake(Pseries(1, 1, 6), 2).asStream.nextN(5)

*/

Ptake : Pattern {
	var <>source, <>repeats=1;

	*new { | source, repeats = 1 |
		^this.newCopyArgs(source, repeats);
	}

	embedInStream {
		var previous, outval, sourceStream, count = 0;
		sourceStream = source.asStream;
		previous = sourceStream.next;
		while {
			(outval = sourceStream.next).notNil and: {				
				count < repeats;
			}
		}{
			count = count + 1;
			previous.yield;
			previous = outval;
		};
	}
	storeArgs { ^[ source, repeats ] }
}

/* // DOES NOT WORK. 

Ptake : FilterPattern {
	var <>repeats, <>key;
	*new { arg pattern, repeats=inf, key;
		^super.newCopyArgs(pattern, repeats, key )
	}
	storeArgs { ^[pattern,repeats, key] }
	embedInStream { | event |
		var source, result;
		source = pattern.embedInStream(event);
		repeats.value(event).postln.do {
			result = source.next;
		};
		^result;
	}
}
*/