//: 19 Jan 2021 12:58
/*
Devise a Pattern that plays like a Pseq but creates a new array for the Pseq at each new repetition, using a function.  The function should take the initial array, the previous array, and the number of repetitions as argument.
*/

/*
//:
Pfseq((1..3), { | l, i | l.reverse.rotate(i) }, 5).asStream.nextN(12);
//:
EventPattern((dur: 0.15, degree: Pfseq((1..5) * 2, { | l, i | l.reverse.rotate(i) }, 200))).play;
//:
*/

Pfseq : Pseq {
	var <>func, <>initialList;

		*new { arg list, func, repeats=1, offset=0;
			^super.new(list, repeats, offset)
			.initialList_(list).func_(func ?? { { | l | l } });
	}
	
	embedInStream {  arg inval;
		var item, offsetValue;
		offsetValue = offset.value(inval);
		repeats.value(inval).do({ | count |
			list.size.do({ arg i;
				item = list.wrapAt(i + offsetValue);
				inval = item.embedInStream(inval);
			});
			list = func.(list, count, initialList)
			});
		^inval;
	}
}


