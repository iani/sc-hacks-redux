/* 27 Feb 2022 09:53

*/

+ Synth {
	setEvent { | event | // see Event ++>
		var params;
		event keysValuesDo: { | key, value |
			params = params add: key;
			params = params add: value.next;
		};
		this.set(*params);
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