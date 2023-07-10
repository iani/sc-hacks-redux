/*  9 Jul 2023 00:09
Prototype.
See GlobalSensorEnvir.org
*/

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
