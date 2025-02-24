//Fri 23 Feb 2024 09:34
//EventOperators
//FunctionOperators

+ Event {
	+>> { | player, envir |
		// create, store and
		// make this respond to OSC trigger messages
		// Use coupled with {} +>> key
		var estream;
		estream = this.playInEnvir(player, envir ? player, false);
		player >>> { estream.playNext };
		^estream;
	}
}

+ Function {
		+>> { | cmdName, player = \osctrigger |
		{
			cmdName ?? { cmdName = player };
			this.value.sendReply(cmdName.asOscMessage);
		} +> player;
	}
}