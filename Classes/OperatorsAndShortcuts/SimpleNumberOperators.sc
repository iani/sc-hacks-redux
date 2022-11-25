/* 27 Feb 2022 09:51

*/
+ SimpleNumber {

	// TODO: REVIEW OR REMOVE THIS METHOD:
	+> { | param, player |
		player ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		// envir.push.put(param, this);
		Mediator.setEvent(().put(param, this), player, player);
	}
}