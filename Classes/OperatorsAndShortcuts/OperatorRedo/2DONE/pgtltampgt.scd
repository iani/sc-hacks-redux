//Fri 23 Feb 2024 11:45
//FunctionOperators

+ Function {
	// override (bypass) the target group of the player's environment
	// but do not set it.
	+><@> { | player, group = \root |
		^this.pushPlayInEnvir(player, player, group);
	}
}