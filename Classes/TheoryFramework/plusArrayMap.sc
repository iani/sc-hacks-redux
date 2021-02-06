/*  6 Feb 2021 11:36
[normalize +] Map all values of the receiver.
Useful for creating rhythms with pseq etc.
However, distorts the proportions of the values!  Example:

(1..10).map([0.2, 0.4]);

To keep the proportions the same, use adjustMin (see below)

(1..10).adjustMin(2);

(1..10).adjustMin(0.2);

*/
+ Array {
	map { | spec |
		spec = spec.asSpec;
		^this.normalize collect: spec.map(_); // { | i | spec.map(i) };
	}

	adjustMin { | min = 1 |
		var mul;
		mul = min / this.minItem;
		^this * mul;
	}
}