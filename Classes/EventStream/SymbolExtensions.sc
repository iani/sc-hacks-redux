/*  1 Feb 2021 05:47
Experimental operator : trigSynth
See EventStream:trigSynth
*/

+ Symbol {
	removeBeat { | beatKey |
		// remove EventStream from dependants of BeatCounter
		var eventStream;
		eventStream = currentEnvironment[this];
		if (eventStream isKindOf: EventStream) {
			beatKey.beat.removeDependant(eventStream)
		}{
			postf("Key % contains % instead of an EventStream. Ignoring this.\n",
				eventStream;
			);
		}
		^eventStream;
	}

	// OLDER STUFF - NEEDS REVISION
	<<! { | source, synthKey = \default |
		this.trigSynth(source, synthKey);
	}

	trigSynth { | source, synthKey = \default |
		OscTrig.fromLib(this).addSynth(source, synthKey);
	}
}