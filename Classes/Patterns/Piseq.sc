/* 25 Oct 2022 10:33

Cycle over sub-section of an array.

*/

Piseq : Pseq {
	var <>length, <>step = 1;
	*new { arg list, repeats=1, offset=0, length, step = 1;
		^super.new(list, repeats, offset)
		.length_(length)
		.step_(step);
	}

	embedInStream { arg inval;
		var item, index;
		index = offset.(inval);
		repeats.value(inval).do({
			item = list.wrapAt(index);
			inval = item.embedInStream(inval);
			index = index + step.(inval);
		});
		^inval;
	}

	reverse {
		offset = list.size - 1;
		step = -1;
	}
}

