/* 31 Oct 2022 12:06
Concatenate elements of me, with custom separator.

[\a, \b, \c].catList.postln;
[\a, \b, \c].catList("_").postln;
*/

+ Array {
	catList { arg separator = "";
		// concatenate this with a list as a string
		var string;
		string = this[0].asString.copy;
		this[1..].do({ arg item, i;
			string = string ++ separator ++ item;
		});
		^string
	}
}