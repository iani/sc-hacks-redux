/* 27 Feb 2022 09:52

*/
+ Function {
	+> { | player, envir |
		^this.playInEnvir(player, envir);
	}

	playInEnvir { | player, envir |
		// TODO: add arguments setting, bus mapping
		var synth;
		Mediator.wrap({
			// enable storing of source code:
			// currentEnvironment.changed(\playFunc, player, this);
			currentEnvironment[player] = synth = this.play.notify(player, envir)
		}, envir);
		^synth;
	}

	@> { | bus, player |
		// play as kr funcction in player name
		player = player ? bus;
		player.postln;
		{ Out.kr(bus.bus, this); Silent.kr.kadsr }.playInEnvir(player, player);
	}
}