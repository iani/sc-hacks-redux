/* 24 May 2021 16:03

*/

+ Symbol {

	synthReport { this.envir.synthReport }
	stopSynths { this.envir.stopSynths }

	stop { | fadeTime = 1.0 |
		currentEnvironment[this].stop(fadeTime);
	}

	// start { currentEnvironment[this].start; }
	// 14 Oct 2022 23:17: enable Synth restart!
	// Mon  3 Jul 2023 18:14 : needs fixing:
	// should restart player argument in environment of receiver.
	// need to revisit restart also.
	start { | player |
		// Mediator.at(this)
		currentEnvironment[this].restart(currentEnvironment, player ? this);
	}
}

+ Synth {
	stop { | fadeTime = 1.0 |
		if (this.isPlaying) {
			this.release(fadeTime);
		}{
			this.onStart({ this.release(fadeTime) })
		}
	}

	restart { | envir, playerName |
		// Mon  3 Jul 2023 18:18 : TODO: Review this!
		PlayerHistory.at(envir.name, playerName).last[1].interpret;
	}
}

+ Nil {
	restart { | envir, playerName |
		"cannot restart:".postln;
		postln("There is no player named" + playerName + "in environment" + envir);
	}
}