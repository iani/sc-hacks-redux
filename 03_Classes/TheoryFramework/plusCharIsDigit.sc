/*  3 Sep 2023 00:27
Helper for Snote

*/

+ Char {
	isDigit {
		var ascii;
		ascii = this.ascii;
		^ascii >= 48 and: { ascii <= 57};
	}
}

+ Nil {
	isDigit { ^false }
}