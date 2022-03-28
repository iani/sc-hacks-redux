/*  1 Feb 2021 05:47
Experimental operator : trigSynth
See EventStream:trigSynth
*/

+ Symbol {
	// simple prototype. needs review and completion
	removeTr { | beatKey |
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

	// simple prototype
	// TODO: envir -> actionKey
	// TODO:  Use same syntax / semantics as Symbol >>>
	+>> { | player, key | this.addTr(player, key) }

	addTr { | player, key |
		key ?? { key = this };
		this.addAction({
			currentEnvironment[player].getNextEvent.play;
		}, key);
	}

	<<+ { | trigKey, envir | this.removeTr(trigKey, envir) }

	removeTr { | trigkey = \trigger, envir |
		// TODO: rewrite to add actionKey
		// Use same syntax / semantics as Symbol >>>
		this <<< this;
	}

	// OLDER STUFF - NEEDS REVISION
	<<! { | source, synthKey = \default |
		this.trigSynth(source, synthKey);
	}

	trigSynth { | source, synthKey = \default |
		OscTrig.fromLib(this).addSynth(source, synthKey);
	}
}