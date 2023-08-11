/* 27 Feb 2022 09:51

*/
+ SimpleNumber {

	+> { | param, envir |
		envir = envir ? param;
		envir.envir.put(param, this);
	}

	// Older version:
	// +> { | param, player |
	// 	player ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
	// 	// envir.push.put(param, this);
	// 	Mediator.setEvent(().put(param, this), player, player);
	// }
}