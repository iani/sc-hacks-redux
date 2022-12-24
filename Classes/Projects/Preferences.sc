/* 19 Dec 2022 17:30
Save and retrieve preferencees for diverse classes.
*/

Preferences {
	classvar preferences, >savePath;
	*save { preferences.writeArchive(this.savePath); }
	*load { ^preferences = Object.readArchive(this.savePath); }

	*savePath {
		^savePath ?? {
			savePath =
			(PathName(Platform.userAppSupportDir) +/+ "Preferences.scd").fullPath;
		}
	}

	*preferences  {
		^preferences ?? { preferences = MultiLevelIdentityDictionary() };
	}

	*put { | ... path |
		this.preferences.put(*path);
		this.save;
	}

	*get { | ... path |
		this.load;
		^this.preferences.at(*path);
	}

	*list { | key |
		this.preferences.at(key) keysValuesDo: { | key, value |
			postln("key:" + key + "value:" + value);
		}
	}
}