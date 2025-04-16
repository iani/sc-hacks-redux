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

	// New version, for sc-hacks v2 Thu 27 Feb 2025 07:48
	// todo: move to Mediator:makeSynth in order to share
	// code with Function:playInEnvir
	playInEnvir { | player, envir |
		// player + envir are passed on by +> operator.
		// all other values are inferred from the environment.
		var synth;
		envir = envir.envir;
		synth = this.play(
			envir[\target] ? Server.default,
			envir[\outbus] ? 0,
			envir[\fadeTime] ? 0.02,
			envir[\addAction] ? \addToHead,
			envir.synthArgs
		);
		synth onStart: {
		// map any existing busses of the Mediator to controls
			envir keysValuesDo: { | key, val |
				if (val isKindOf: Bus) {
					synth.map(key, val);
					// postln("Mapped synth" + synth + "at key" + key + "to bus" + val);
				}
			}
		};
		synth.addNotifier(envir, \key, { | n, key, value |
			// postln("envir" + envir[\mediator] + "changed key:" + key);
			value.updateSynth(key, synth); // map or set control at key
		});
		// Emitted by Bus:HandleReplacement when bus is stored.
		// Unmap the bus that was removed.
		synth.addNotifier(envir, \busfree, { | n, key, bus |
			postln("envir" + envir[\mediator] + "freed bus at:" + key);
			// unmap by setting to latest values from the bus.
			bus.get({ | vals | synth.setn(key, vals) });
		});
		synth onEnd: {
			// postln("Synth ended:" + synth);
			if (envir[player] === synth) {
				envir.put(player, nil);
				Mediator.changed(\ended, player);
				synth changed: \ended;
			}
		};
		envir.put(player, synth);
		^synth;
	}

	// This version is replaced in version 2 of the software.
	playInEnvirV1 { | player, envir, target, outbus = 0, addAction = \addToHead |
		// TODO: add arguments setting, bus mapping
		var synth;
		envir = envir ? player; // play in own envir, holding own busses
		// postln("Function playInEnvir. envir:" + envir);
		Mediator.wrap({
			var fadeTime;
			fadeTime = ~fadeTime ? 0.01; // allways make fade envelope: ensure the synth is releasable!
			if (Server.default.serverRunning) {
				currentEnvironment.addSynth(player, synth = this.play(
					target, outbus, fadeTime,
					player: player, envir: envir,
					addAction: addAction
				));
			}{
				Server.default.waitForBoot({
					currentEnvironment.addSynth(player, synth = this.play(
						target, outbus, fadeTime,
						player: player, envir: envir,
						addAction: addAction
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
