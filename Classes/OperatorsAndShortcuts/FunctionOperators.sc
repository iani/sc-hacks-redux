/* 27 Feb 2022 09:52

*/

+ Function {
	pan { | pan = 0 |
		^{ Pan2.ar(this.value, \pan.br(~pan ? pan)) }
	}
	amplify { | amp = 1 | // Thu 17 Aug 2023 11:47 experimental: provide amp bus control.
		^{ this.value * \amp.br(~amp ? amp) }
	}

	amplifyl { | amp = 1, lim = 0.5 | // Thu 17 Aug 2023 11:47 experimental: provide amp bus control.
		^{ this.value * (\amp.br(~amp ? amp) min: lim) }
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

	/* // older version
	+>> { | cmdName, player |
		this.sendReply(cmdName, player)
	}
	*/

	/*
	sendReply { | cmdName, player, values = 1, replyID = 1 |
		// always save in environment \triggers (special envir)
		{
			SendReply.kr(
				this.value.kdsr, cmdName.asOscMessage,
				values.value, replyID.value
			)
		}.playInEnvir(player ? cmdName, \triggers)
	}
	*/

	routineInEnvir { | player, envir |
		var routine;
		envir = envir ? player; // ? currentEnvironment.name;
		Mediator.wrap({
			currentEnvironment[player] = routine = this.fork;
		}, envir);
		^routine
	}
}
