/* 18 Jul 2023 09:51
Save and choose paths for the class of any object.
Works with Load, Save.

To be implemeneted using parts of code from Preferences2
*/

Files {
	classvar dict;

	*dict {
		dict ?? { this.loadPrefs };
		^dict;
	}

	*loadPrefs {
		if (File exists: this.prefsPath) {
			dict = Object readArchive:   this.prefsPath;
		}{
			dict = MultiLevelIdentityDictionary();
			this.savePrefs;
		};
	}

	*prefsFor { | object, key = \default |
		^this.dict.at(this symbolFor: object, key);
	}

	*doWithFolder { | object, key = \default, path, action |

	}

	*savePrefs { dict.writeArchive(this.prefsPath) }

	*prefsPath {
		^PathName(Platform.userAppSupportDir +/+ "Files.scd").fullPath;
	}

	*symbolFor { | anObject |
		if (anObject isKindOf: Class)
		{ ^anObject.asSymbol }
		{ ^anObject.class.asSymbol }
	}
}