/* 27 Feb 2022 09:53

*/

+ Synth {
	addEvent { | event, key | // used??????
		event +> key
	}
	notify { | playerName, envirName |
		this.onStart({ | synth |
			// track state and broadcast id for use with tr
			Mediator.wrap({
				currentEnvironment.changed(\started, playerName, synth);
			}, envirName)
		});
		this.onEnd({ | synth |
			// track state and broadcast id for use with tr
			Mediator.wrap({
				currentEnvironment.changed(\stopped, playerName, synth);
			}, envirName)
		})
	}
}