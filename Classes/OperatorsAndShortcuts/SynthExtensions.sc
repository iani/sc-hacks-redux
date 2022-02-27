/* 27 Feb 2022 09:53

*/

+ Synth {
	addEvent { | event, key |
		event +> key
	}
	notifyIdOnStart { | notifier = \synth |
		this.onStart({ | synth |
			// track state and broadcast id for use with tr
			notifier.changed(\synth, synth.nodeID)
		})
	}
}