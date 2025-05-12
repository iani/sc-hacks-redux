//火 15  4 2025 00:00
//Redoing Path and related classes

Paths {

	*initClass {
		StartUp add: { this.init; };
	}

	*init { this.makeBaseDirectory; }

	*makeBaseDirectory {
		if (File.exists(this.baseDirectory).not) {
			File.mkdir(this.baseDirectory)
		}
	}

	*baseDirectory { ^Platform.userAppSupportDir +/+ "Paths"; }

	*doGetPath { | action, argKey = \default |
		// get the path and then do the action with it
		var pathLocation, path;
		pathLocation = this.makePathLocation(argKey);
		if (File.exists(pathLocation)) {
			path = File.readAllString(pathLocation);
			this.doAction(action, path);
		}{
			this.getPathFromUser(pathLocation, action, argKey);
		}
	}

	*getPathFromUser { | pathLocation, action, argKey |
		postln("Please select a path from the FileDialog for" + argKey);
		FileDialog({ | p |
			p = p.first;
			this.savePathAndDo(p, pathLocation, action);
			action.(p, this.pathsInFolder(p));
		});
	}

	*getPathFromKey { | key, default = "" |
		var path, returnValue;
		path = this.makePathLocation(key);
		postln("Looking for file" + path.fileName);
		if (File.exists(path) and: {
			(returnValue = File.readAllString(path)).size > 0;
		}) {
			"File was found and read.  Skipping writing".postln;
		}{
			"File was not found. saving default to path.".postln;
			File.use(path, "w", { | f | f write: default.asString });
		};
 		^returnValue;
	}

	*savePathAndDo { | path, pathLocation, action |
		this.savePath(path, pathLocation);
		this.doAction(action, path);
	}

	*saveAtKey { | key, string = "" |
		File.use(this.makePathLocation(key), "w", { | f | f write: string.asString });
	}

	*savePath { | path, pathLocation |
		File.use(pathLocation, "w", { | f |
			f.write(path.standardizePath)
		});
	}

	*makePathLocation { | argKey |
		^this.baseDirectory +/+ argKey.asString ++ ".scd";
	}

	*doAction { | action, path |
		action.(path, this.pathsInFolder(path));
	}

	*pathsInFolder { | path |
	// return paths in folder whose file extensions match extension of path.
		// var n;
		// n = PathName(path);
		// ^(n.pathOnly +/+ "*." ++ n.extension).pathMatch;
		^path.entriesMatchingExtension;
	}

	*resetPath { | argKey = \default |
		this.getPathFromUser(this.makePathLocation(argKey));
	}

	*allPathFiles {
		^(this.baseDirectory +/+ "*.scd").pathMatch;
	}

	*allPaths {
		var dict;
		dict = ();
		this.allPathFiles do: { | p |
			var key, path;
			key = PathName(p).fileNameWithoutExtension.asSymbol;
			path = File.readAllString(p);
			dict[key] = path;
		};
		^dict;
	}
}