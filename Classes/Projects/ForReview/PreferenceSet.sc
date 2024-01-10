/* 23 Jan 2023 16:55
New scheme for saving preferences.
Save PreferenceSet subclass instances of the same class in one file, named after the name of the class.

	UNDER DEVELOPMENT
*/

PreferenceSet {
	var <key = \default;

	*all { ^(Registry.at(this) ?? { IdentityDictionary() }).values }
	*new { | key = \default |
		^Registry(this, key, { this.newCopyArgs(key) });
	}

	save { this.class.save }
	*save {
		Registry.at(this) writeArchive: this.archivePath;
	}

	*load { | key = \default |
		var all, new;
		all = Object readArchive: this.archivePath;
		new = all.at(key) ?? { this.new(key) };
		Registry.put(this, key, new);
		^new;
	}

	*archivePath {
		^Platform.userAppSupportDir +/+ this.asString ++ ".scd";
	}

}
