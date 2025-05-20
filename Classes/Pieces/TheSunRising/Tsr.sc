//: Encapsulate sending / reacting to typing + verses
Tsr {
	classvar charActions, verseActions;
	classvar <>verbose = false;

	*initClass {
		ServerBoot add: {
			SynthDef(\pinch, { |freq=330, ffreq=110, pos=0.0, amp=1.0|
				var signal, env, conv, f1;
				f1 = LFSaw.kr(ffreq * 0.001).range(0,1);
				env = EnvGen.ar(Env.perc(0.01,0.1), doneAction: 2);
				signal = SinOsc.ar(freq+SinOsc.ar(ffreq,0,freq)).fold(f1.neg,f1) * env;
				conv = Limiter.ar(Convolution.ar(signal, Decay2.ar(Dust.ar(ffreq),0.01,f1/3)), 0.95);
				Out.ar(0, Pan2.ar(signal, pos, amp))
			}).add;
		}
	}

	*charActions {
		^charActions ?? { charActions = IdentityDictionary() }
	}

	*verseActions {
		^verseActions ?? { verseActions = IdentityDictionary() }
	}

	*type { | char, cocoaModifiers, unicode, keycode, key |
		User.sendToAll(\char, char.ascii, cocoaModifiers, unicode, keycode);
	}

	*verse { | index, verse, incipit | // Send a verse.
		// debugging double send on 250520
		// User.sendToAll(\verse, index, verse, incipit);
		User.forwardMessage(\verse, index, verse, incipit, User.localId);
	}

	*trig { | index, verse, incipit | // Send a verse.
		User.sendToAll(\trig, index, verse, incipit);
	}
	*voice { | voice, verseNums |
		// send a selected voice + verse number + verse
		// For use by Iannis - and maybe also others.
		// \tsr.changed(\voice, voice, verseNums);
		User.sendToAll(\voice, voice, verseNums);
		// User.sendToSelf(\voice, voice, verseNums);
		// User.forwardMessage(\voice, voice, verseNums);
	}

	*doOnType { | action, key = \default |
		OSC.addArgs(\char, action, key);
	}

	*doOnType2 { | action, key = \default |
		var envir;
		envir = currentEnvironment; // store environment of user at doOnType2
		// at each notification, run user's action inside user's environment
		OSC.addArgs(\char, { | ... args |
			currentEnvironment.use({ action.(*args) }) },
			key
		);
	}

	*undoOnType { | key = \default |
		OSC.remove(\char, key);
		// this.removeNotifier(\tsr, \char);
	}

	*doOnVerse { | action, key = \default |
		OSC.addArgs(\verse, action, key);
	}

	*doOnVerse2 { | action, key = \default |
//		OSC.addArgs(\verse, action, key);
		var envir;
		envir = currentEnvironment; // store environment of user at doOnType2
		//postln("The user of this doOnVerse is:" + envir[\user].id);
		// at each notification, run user's action inside user's environment
		OSC.addArgs(\verse, { | ... args |
			// postln("this runs in the environment of user:" +
			// 	envir[\user].id;
			// );
			envir.use({ action.(*args) }) },
			key
		);
	}
	*doOnTrig { | action, key = \default |
		OSC.addArgs(\trig, action, key);
	}

	*undoOnVerse { | key = \default |
		OSC.remove(\verse, key);
		// this.removeNotifier(\tsr, \verse);
	}

	*doOnVoice { | action, key = \default |
		OSC.addArgs(\voice, action, key);
		// this.addNotifier(\tsr, \voice, action);
	}

	*undoOnVoice { | key = \default |
		OSC.remove(\voice, key);
		// this.removeNotifier(\tsr, \voice);
	}


	*addCharActions { | ... charActionPairs |
		charActionPairs keysValuesDo: { | char, action |
			this.charActions[char] = action;
		};
		this.doOnType({ | n, char |
			this.charActions[char].value;
		})
	}
}