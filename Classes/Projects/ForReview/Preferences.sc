/* 19 Dec 2022 17:30
Save and retrieve preferencees for diverse classes.

Replaced by new class of same name on: Tue 11 Jul 2023 13:12
*/

Preferences {
	classvar preferences, savePath;
	*initPreferences {
		// WARNING: This erases existing preferences.
		MultiLevelIdentityDictionary().writeArchive(this.savePath);
	}
	*save {
		preferences.put(this.name.asSymbol, this.myPreferences);
		// preferences.writeArchive(this.savePath);
		this.writeToArchive;
	}

	*writeToArchive {
		preferences.writeArchive(this.savePath);
	}

	*clearAndSave {
		this.initPreferences;
		this.writeToArchive;
	}

	*myPreferences {
		var myprefs;
		myprefs = ();
		this.settableVarNames do: { | vn | myprefs[vn] = this.perform(vn); };
		^myprefs;
	}

	*settableVarNames {
		^this.class.methods.collect(_.name)
		.select({ | n | n.asString.last == $_})
		.collect({ | n | n.asString.butLast.asSymbol });
	}

	*load {
		var myPreferences;
		preferences = Object.readArchive(this.savePath);
		preferences ?? {
			preferences = MultiLevelIdentityDictionary();
			this.save;
		};
		myPreferences = preferences.at(this.name.asSymbol) ?? {
			this.makeDefaults;
		};
		preferences.put(this.name.asSymbol, myPreferences);
		this.settableVarNames do: { | vn |
			this.perform(this.setterName(vn), myPreferences[vn]);
		};
		^preferences;
	}

	*makeDefaults {
		^(
			savePath: (PathName(Platform.userAppSupportDir) +/+ "Preferences.scd").fullPath;
		)
	}

	*setterName { | vn | ^(vn.asString ++ "_").asSymbol }

	*savePath {
		^savePath ?? {
			savePath =
			(PathName(Platform.userAppSupportDir) +/+ "Preferences.scd").fullPath;
		}
	}

	*savePath_ { | argPath |
		savePath = argPath;
		this.save;
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