/*  2 Aug 2023 23:16
Latest redo for path management.
Keep paths for objects in Library.
*/

PathManager {
	classvar archivePath;
	classvar paths;

	*paths {
		paths ?? { paths = this.load };
		^paths;
	}

	*load {
		if (File exists: this.defaultPath ) {
			paths = Object.readArchive(this.defaultPath);
		};
		if (paths.isNil) {
			paths = MultiLevelIdentityDictionary();
			this.save;
		};
		^paths;
	}

	*save { paths.writeArchive(this.defaultPath); }

	*defaultPath { ^this.archivePath }

	*archivePath {
		archivePath ?? {
			archivePath = PathName(Platform.userAppSupportDir +/+ "paths.scd").fullPath
		};
		^archivePath;
	}

	*pathFor { | class, key = \default |
		var path;
		path = this.paths.at(class.asSymbol, key);
		if (path.isNil) {
			path = class.defaultPath;
			paths.put(class.asSymbol, key, paths);
			this.save;
		};
		^path;
	}

}