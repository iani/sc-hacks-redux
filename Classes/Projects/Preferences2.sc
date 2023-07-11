/* 11 Jul 2023 12:39

New version to replace or compare with older Preferences used by FilaNavigator.
Put paths (or other objects?) received from a class in a dictionary using the class as key.
Save the entire

*/

Preferences2 {
	classvar dict;

	*load { | object |
		^this.dict[object];
	}

	*save { | object, prefs |
		dict[object] = prefs;
		this.saveDict;
	}

	// *prefs { | object |
	// 	^this.dict.at(object)
	// }

	*clearPref { | object |
		object.postln;
		this.dict.postln;
		this.dict[object].postln;
		this.dict.put(object, nil);
		"after clearing:".postln;
		this.dict.postln;
		this.saveDict;
	}

	*clearAndSave {
		dict = IdentityDictionary();
		this.saveDict;
	}

	*clearDict { dict = IdentityDictionary(); }

	*dict {
		dict ?? { this.loadDict };
		^dict;
	}

	*loadDict {
		var path = this.path;
		// postln("preferences exist?" + File.exists(path));
		if (File.exists(path)) {
			dict = Object.readArchive(path);
		}{
			this.clearAndSave;
		}
	}

	*path {
		^PathName(Platform.userAppSupportDir +/+ "Preferences2.scd").fullPath;
	}

	*saveDict {
		postln("saving preferences to" + this.path);
		dict.writeArchive(this.path);
	}
}