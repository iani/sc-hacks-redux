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
		\tsr.changed(\char, char);
		User.sendCode(
			format("\\tsr.changed(\\char, %)", char.asCompileString)
		);
	}

	*verse { | verse |
		\tsr.changed(\verse, verse);
		User.sendCode(
			format("\\tsr.changed(\\verse %)", verse.asCompileString)
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

	*addCharActions { | ... charActionPairs |
		charActionPairs keysValuesDo: { | char, action |
			this.charActions[char] = action;
		};
		this.doOnType({ | n, char |
			this.charActions[char].value;
		})
	}
}