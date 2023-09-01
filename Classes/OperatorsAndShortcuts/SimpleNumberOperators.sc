/* 27 Feb 2022 09:51
Redoing on Fri  1 Sep 2023 07:50
*/
+ SimpleNumber {

	// Set a key in the environment to me
	+> { | param, envir |
		param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		envir = envir ? \default;
		envir.envir.put(param, this);
	}

	// Set the parameter of a synth of a player in the environment to me.
	+>! { | playerParam, envir |
		var param, player, synth;
		envir ?? { ^"SimpleNumber +> requires an envir adverb".warn };
		#envir, player, param = envir.playerParam(playerParam);
		synth = envir.envir[player];
		if (synth isKindOf: Synth) { synth.set(param, this) }
		// envir.envir.put(param, this);
	}


	// Older version:
	// +> { | param, player |
	// 	player ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
	// 	// envir.push.put(param, this);
	// 	Mediator.setEvent(().put(param, this), player, player);
	// }
}