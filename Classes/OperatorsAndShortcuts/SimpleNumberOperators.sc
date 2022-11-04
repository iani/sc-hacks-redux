/* 27 Feb 2022 09:51

*/
+ SimpleNumber {

	// TODO: REVIEW OR REMOVE THIS METHOD:
	+> { | envir, param |
		param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		envir.push.put(param, this);
	}
}