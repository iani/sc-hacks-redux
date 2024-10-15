//Fri 23 Feb 2024 09:31
//EventOperators
//FunctionOperators

+ Event {
	+@> { | key | Mediator.setEvent(this, key, \busses); }
}

+ Function {
	// set the target group of the player's environment and use it
	+>@ { | player, group = \default_group |
		player.envir[\target] = group;
		^this.pushPlayInEnvir(player, player);
	}
}