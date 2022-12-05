/* 27 Feb 2022 09:52

*/

+ Object {
	pushPlayInEnvir { | player, envir |
		// "this is object pushPlayInEnvir".postln;
		(envir ? player).push;
		this.playInEnvir(player, envir);
	}
}

+ Function {
	+> { | player, envir |
		^this.pushPlayInEnvir(player, envir);
	}

	+>@ { | player, group = \root |
		player.envir[\target] = group;
		postln("+>0 made envir:" + player.envir);
		^this.pushPlayInEnvir(player, player);
	}
	playInEnvir { | player, envir |
		// TODO: add arguments setting, bus mapping
		var synth;
		envir = envir ? player; // play in own envir, holding own busses
		Mediator.wrap({
			// enable storing of source code:
			// Function.changed(\player, envir, player, Main.elapsedTime,
			// 	format("% +>.% %", this.def.sourceCode, envir, player.asCompileString)
			// );
			if (Server.default.serverRunning) {
					currentEnvironment.addSynth(player, synth = this.play(player: player, envir: envir));
			}{
				Server.default.waitForBoot({
					currentEnvironment.addSynth(player, synth = this.play(player: player, envir: envir));
				})
			}
		}, envir);
		// "DEBUGGING Just before leaving playInEnvir method".postln;
		// postln("envir is: " + envir);
		// postln("currentEnvironment is", currentEnvironment);
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
