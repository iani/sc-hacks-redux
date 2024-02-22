/* 27 Feb 2022 09:51
Redoing on Fri  1 Sep 2023 07:50
*/
+ SimpleNumber {
	// See OperatorFix240222.sc
	// Set a key in the environment to me
	// +> { | param, envir |
	// 	param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
	// 	envir = envir ? \default;
	// 	envir.envir.put(param, this);
	// }

	// Set the parameter of a synth of a player in the environment to me.
	+>! { | playerParam, envir |
		var param, player, synth;
		envir ?? { ^"SimpleNumber +> requires an envir adverb".warn };
		#envir, player, param = envir.playerParam(playerParam);
		synth = envir.envir[player];
		if (synth isKindOf: Synth) { synth.set(param, this) }
		// envir.envir.put(param, this);
	}

	>>! { | sensor | // emulate minibee
		sensor.sensorbus.set(this)
	}


	// create a pair lo-hi at equal ratio distances from 1
	/*
		0.2.bi.postln collect: _.ratio;  // minor thirds
		0.25.bi.postln collect: _.ratio; // major thirds
		(4 / 3 - 1).postln.bi.postln collect: _.ratio; // fourths
		0.5.bi.postln collect: _.ratio;  // fifths
		0.2.bi(10).postln collect: _.ratio; // not implemented
	*/
	bi { // | center = 1 |
		^[1 / (1 + this), 1 * (1 + this)]
	}

	// Older version:
	// +> { | param, player |
	// 	player ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
	// 	// envir.push.put(param, this);
	// 	Mediator.setEvent(().put(param, this), player, player);
	// }
}
