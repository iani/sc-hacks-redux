/* 24 Feb 2022 18:14

*/

+ String {
	sepcatList { arg separator = "", list;
		// concatenate this with a list as a string
		var string = this.copy;
		list.do({ arg item, i;
			string = string ++ separator ++ item;
		});
		^string
	}
	absPath {
		^PathName(this).asAbsolutePath;
	}
}