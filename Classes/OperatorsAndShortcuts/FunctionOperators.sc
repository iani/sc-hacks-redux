/* 27 Feb 2022 09:52

*/

+ Object {
	pushPlayInEnvir { | player, envir, target, outbus = 0 |
		// "this is object pushPlayInEnvir".postln;
		(envir ? player).push;
		this.playInEnvir(player, envir, target, outbus);
	}
}

+ Function {
	+> { | player, envir |
		^this.pushPlayInEnvir(player, envir);
	}

	// set the target group of the player's environment and use it
	+>@ { | player, group = \default_group |
		player.envir[\target] = group;
		^this.pushPlayInEnvir(player, player);
	}

	// override (bypass) the target group of the player's environment
	// but do not set it.
	+><@> { | player, group = \root |
		^this.pushPlayInEnvir(player, player, group);
	}

	playInEnvir { | player, envir, target, outbus = 0 |
		// TODO: add arguments setting, bus mapping
		var synth;
		envir = envir ? player; // play in own envir, holding own busses
		Mediator.wrap({
			if (Server.default.serverRunning) {
				currentEnvironment.addSynth(player, synth = this.play(
					target, outbus,
					player: player, envir: envir
				));
			}{
				Server.default.waitForBoot({
					currentEnvironment.addSynth(player, synth = this.play(
						target, outbus,
						player: player, envir: envir
					));
				})
			}
		}, envir);
		^synth;
	}

	+>> { | cmdName, player |
		this.sendReply(cmdName, player)
	}

	sendReply { | cmdName, player, values = 1, replyID = 1 |
		// always save in environment \triggers (special envir)
		{
			SendReply.kr(
				this.value.kdsr, cmdName.asOscMessage,
				values.value, replyID.value
			)
		}.playInEnvir(player ? cmdName, \triggers)
	}

	**> { | player, envir | // infinite loop in envir
		{
			inf do: {
				var dur;
				dur = this.(player, envir);
				if (dur.isKindOf(SimpleNumber).not) { dur = 1 };
				dur.wait;
			}
		}.routineInEnvir(player, envir);
	}

	*> { | player, envir | // play as routine
		this.routineInEnvir(player, envir);
	}

	routineInEnvir { | player, envir |
		var routine;
		envir = envir ? player; // ? currentEnvironment.name;
		Mediator.wrap({
			currentEnvironment[player] = routine = this.fork;
		}, envir);
		^routine
	}
}
