/*  9 Jul 2023 00:09
Prototype.
See GlobalSensorEnvir.org
*/

+ Function {
	gt { | sensor, thresh = 0.5, lagtime = 0.2 |
		^{ this.value * (sensor.sr > thresh).lag(lagtime) }
	}
	lt { | sensor, thresh = 0.5, lagtime = 0.2 |
		^{ this.value * (sensor.sr < thresh).lag(lagtime) }
	}

	gtp { | sensor, player, thresh = 0.5, lagtime = 0.2, envir |
		^{ this.value * (sensor.sr > thresh).lag(lagtime) }
		.perform('+>', envir ? player, player)
	}
	ltp { | sensor, player, thresh = 0.5, lagtime = 0.2, envir |
		^{ this.value * (sensor.sr < thresh).lag(lagtime) }
		.perform('+>', envir ? player, player)
	}
}

/*
	// Older prototype and tests
+ Function {
	** { | rightarg, adverb |
		postln("right arg:" + rightarg + "adverb:" + adverb);
	}

	>** { | mainarg, adverb |
		[mainarg, adverb].postln;
	}

	<** { | mainarg, adverb |
		[mainarg, adverb].postln;
	}
}
*/