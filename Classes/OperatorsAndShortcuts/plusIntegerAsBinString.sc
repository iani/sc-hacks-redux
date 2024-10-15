/*  2 Mar 2022 12:43
Translate to string representing my binary form.
*/

+ Integer {
	asBinString { | length = 32 |
		var str, chars;
		str = "".copy;
		chars = [$0, $1];
		this.asBinaryDigits(length).do { | d |
			str = str add: chars[d];
		}
		^str;
	}
}