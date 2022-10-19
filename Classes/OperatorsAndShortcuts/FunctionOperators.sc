/* 27 Feb 2022 09:52

*/
+ Function {
	+> { | player, envir |
		^this.playInEnvir(player, envir);
	}

	playInEnvir { | player, envir |
		// TODO: add arguments setting, bus mapping
		var synth;
		envir = envir ? \default;
		Mediator.wrap({
			// enable storing of source code:
			Function.changed(\player, envir, player, Main.elapsedTime,
				format("% +>.% %", this.def.sourceCode, envir, player.asCompileString);
			);
			if (Server.default.serverRunning) {
				currentEnvironment[player] = synth = this.play.notify(player, envir)
			}{
				Server.default.waitForBoot({
					currentEnvironment[player] = synth = this.play.notify(player, envir)
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
}
