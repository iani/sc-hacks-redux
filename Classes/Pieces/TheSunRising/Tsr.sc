//: Encapsulate sending / reacting to typing + verses
Tsr {
	classvar charActions, verseActions;
	classvar <>verbose = false;

	*charActions {
		^charActions ?? { charActions = IdentityDictionary() }
	}

	*verseActions {
		^verseActions ?? { verseActions = IdentityDictionary() }
	}

	*type { | char |
		// Type a character. For use by Yorgos.
		\typing.changed(\click, char);
		\tsr.changed(\char, char);
		User.sendCode(
			format("\\tsr.changed(\\char, %)", char.asCompileString)
		);
	}

	*verse { | verse |
		// Send a verse. For use by Yorgos.
		\tsr.changed(\verse, verse);
		User.sendCode(
			format("\\tsr.changed(\\verse %)", verse.asCompileString)
		);
	}

	*voice { | voice, verseNums |
		// send a selected voice + verse number + verse
		// For use by Iannis - and maybe also others.
		\tsr.changed(\voice, voice, verseNums);
		User.sendCode(
			format("\\tsr.changed(\\voice %, %, %)",
				voice,
				verseNums,
			)
		);
	}

	*doOnType { | action |
		this.addNotifier(\tsr, \char, action);
	}

	*undoOnType {
		this.removeNotifier(\tsr, \char);
	}

	*doOnVerse { | action |
		this.addNotifier(\tsr, \verse, action);
	}

	*undoOnVerse {
		this.removeNotifier(\tsr, \verse);
	}

	*doOnVoice { | action |
		this.addNotifier(\tsr, \voice, action);
	}

	*undoOnVoice {
		this.removeNotifier(\tsr, \voice);
	}


	*addCharActions { | ... charActionPairs |
		charActionPairs keysValuesDo: { | char, action |
			this.charActions[char] = action;
		};
		this.doOnType({ | n, char |
			this.charActions[char].value;
		})
	}

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
}