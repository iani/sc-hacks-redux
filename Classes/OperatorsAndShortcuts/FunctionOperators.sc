/* 27 Feb 2022 09:52

*/

+ Function {
	amplify { | amp = 0.1 | // Thu 17 Aug 2023 11:47 experimental: provide amp bus control.
		^{ this.value * \amp.br(amp) }
	}
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

	playInEnvir { | player, envir, target, outbus = 0, addAction = \addToHead |
		// TODO: add arguments setting, bus mapping
		var synth;
		envir = envir ? player; // play in own envir, holding own busses
		// postln("Function playInEnvir. envir:" + envir);
		Mediator.wrap({
			var fadeTime;
			fadeTime = ~fadeTime ? 0.01; // allways make fade envelope: ensure the synth is releasable!
			if (Server.default.serverRunning) {
				// postln("playinEnvir envir:" + currentEnvironment);
				// postln("playInEnvir fadeTime:" + ~fadeTime);
				currentEnvironment.addSynth(player, synth = this.play(
					target, outbus, fadeTime,
					player: player, envir: envir,
					addAction: addAction
				));
			}{
				Server.default.waitForBoot({
					currentEnvironment.addSynth(player, synth = this.play(
						target, outbus, fadeTime,
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
