/* 24 Sep 2023 10:22
Prototype for providing defaults when timestamp is wrong.
*/

["[123]", "alpha"] collect: { |m|
	var x;
	protect {
	x = m.interpret;
	} {
		if (x.isNil) {
			"x is nil. providing default: 12345".postln;
			x = 12345;
		} { postln("x is" + x); };
		x;
	};
	x;
}