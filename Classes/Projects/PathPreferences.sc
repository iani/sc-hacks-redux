/*  4 Jul 2023 22:39
Save a path for a class.
Load it when needed.
If path does not exist, then prompt the user to select another path.
Ensure that the action that uses that path only runs when the path
exists.
*/

PathPreferences {
	classvar <paths;

	*path { // my own path!
		^Platform.userAppSupportDir +/+ "PathPreferences.scd";
	}

	*loadAll {
		if (File.exists(this.path)) {
			paths = Object.readArchive(this.path);
		}{
			paths = IdentityDictionary();
			this.save;
		}
	}

	*doWithPathFor { | class, action |
		var thePath;
		this.loadAll;
		thePath = paths[class.asSymbol];
		if (thePath.isNil) {
			thePath = class.defaultPath;
			this.savePathFor(class, thePath);
		};
		thePath.postln;
		File.exists(thePath).postln;
		if (File exists: thePath) {
			action.(thePath)
		}{
			{ | newPath |
				newPath = newPath.first;
				action.(newPath);
				this.savePathFor(class, newPath);
			}.confirm(
				"Select a path for" + class + "to continue",
				"Select path"
			)
		};
	}

	*savePathFor { | argClass, argPath |
		paths[argClass.asSymbol] = argPath;
		this.save;
	}

	*save { paths writeArchive: this.path; }
}