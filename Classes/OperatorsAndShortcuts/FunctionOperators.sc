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
			currentEnvironment[player] = synth = this.play.notifyIdOnStart(player)
		}, envir);
		^synth;
	}
}