/* 11 Jul 2023 13:10
Add functionality for saving preferences to any class.

Using Preferences2 which saves the entire preferences on a class (not instance) basis.
Individual subclasses may implement instance-basis preference saving schemes separately.

*/

+ Object {
	checkPrefs {
		^Preferences2.dict.at(this.asSymbol)
	}

	prefs {
		^Preferences2.load(this.asSymbol);
	}

	filePref {
		^Preferences2.load(this.asSymbol) ?? { this.defaultPath };
	}

	clearPrefs {
		Preferences2.clearPref(this.asSymbol);
	}

	withFile { | action |
		this.getPath(action, \File);
	}

	withFolder { | action |
		// "withFolder method called ".postln;
		// postln("action is" + action);
		this.getPath(action, \Folder);
	}

	getPath { | action, pathType = \File |
		var p;
		p = this.filePref;
		// postln("getPath path is" + p);
		// postln("getPath path exists?" + (File exists: p));
		if (File exists: p) {
			action.(p)
			^p;
		}{
			FileDialog.perform(
				("get" ++ pathType).asSymbol, { | argP |
					action.(argP);
					this.savePath(argP);
				}, ("Get preference" + pathType + "for" + this)
			)
		}
	}

	savePath { | argP |
		Preferences2.save(this.asSymbol, argP)
	}

}
