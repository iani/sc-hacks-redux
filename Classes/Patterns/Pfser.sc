//: 19 Jan 2021 13:30
/*
A List Pattern that computes its index by applying a function on the previous index.

Note: Compare this to Pindex!

*/

Pfser : Pser {
	var <>func;
	*new { arg list, func, repeats=1, offset=0;
		^super.new(list, repeats, offset)
		.func_(func ?? {{ | index | index + 1 }});
	}

	embedInStream { arg inval;
		var item, index;
		index = offset.(inval);
		repeats.value(inval).do({ | count |
			item = list.wrapAt(index);
			inval = item.embedInStream(inval);
			index = func.(index, count, inval);
		});
		^inval;
	}
}