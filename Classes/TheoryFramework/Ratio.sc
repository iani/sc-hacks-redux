//: 14 Dec 2020 08:47 Arithmetic on rational numbers
/* draft 

1 -- 2;

15 -- 10;


(1 -- 2) == (2 -- 4);
(1 -- 2) == (2 -- 5);

(1 -- 2) < (2 -- 5);

(1 -- 3) < (2 -- 5);

(1--2)*(1--2);
(1--2)+(1--2);
(1--2)+(1--4);
(1--2)-(1--4);
(1--2)*(2--4);

*/

Ratio {
	var <numerator, <denominator;

	*new { | numerator, denominator |
		^this.newCopyArgs(numerator, denominator).init;
	}

	init {
		//? canonical form
		var gcd;
		gcd = numerator gcd: denominator;
		numerator = (numerator / gcd).asInteger;
		denominator = (denominator / gcd).asInteger;
		// canonical form: only numerator may be negative
		if (denominator < 0) {
			denominator = denominator * -1;
			numerator = numerator * -1;
		}
	}

	printOn { arg stream;
		if (stream.atLimit, { ^this });
		stream << "(" ;
		stream << numerator.asString;
		stream << "/";
		stream << denominator.asString;
		stream << ")" ;
	}

	ratio { ^this }

	asFloat { ^numerator / denominator }

	+ { | ratio |
		var c, d;
		ratio = ratio.ratio;
		c = ratio.numerator;
		d = ratio.denominator;
		^Ratio(
			(numerator * d) + (denominator * c),
			denominator * d
		)
	}

	- { | ratio |
		var c, d;
		ratio = ratio.ratio;
		c = ratio.numerator;
		d = ratio.denominator;
		^Ratio(
			(numerator * d) - (denominator * c),
			denominator * d
		)
	}

	* { | ratio |
		var c, d;
		ratio = ratio.ratio;
		c = ratio.numerator;
		d = ratio.denominator;
		^Ratio(
			(numerator * c),
			denominator * d
		)
	}

	/ { | ratio |
		var c, d;
		ratio = ratio.ratio;
		c = ratio.numerator;
		d = ratio.denominator;
		^Ratio(
			numerator * d,
			denominator * c
		)
	}

	== { | ratio |
		ratio = ratio.ratio;
		^(numerator * ratio.denominator) == (denominator * ratio.numerator)
	}

	< { | ratio |
		var c, d;
		ratio = ratio.ratio;
		c = ratio.numerator;
		d = ratio.denominator;
		^(numerator * d) < (denominator * c);
	}

	inverse { ^this.reciprocal }

	reciprocal {
		^Rational(denominator, numerator);
	}
}


+ Integer { // turn integers into ratio = shorthand notation for Rationals
	-- { | denominator |
		^Ratio(this, denominator);
	}
}

+ SimpleNumber {
	ratio { | denominator = 1000 |
		^Ratio(*this.asFraction(denominator));	
	}
}