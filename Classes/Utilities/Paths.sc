//火 15  4 2025 00:00
//Redoing Path and related classes

Paths {

	*initClass {
		StartUp add: { this.makeBaseDirectory };
	}

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
			this.doAction(action, path)
		}{
			this.getPathFromUser(pathLocation, action);
		}
	}

	*getPathFromUser { | pathLocation, action |
		FileDialog({ | p |
			p = p.first;
			this.savePathAndDo(p, pathLocation, action);
			action.(p, this.pathsInFolder(p));
		});
	}

	*savePathAndDo { | path, pathLocation, action |
		File.use(pathLocation, "w", { | f |
			f.write(path.standardizePath)
		});
		this.doAction(action, path);
	}

	*makePathLocation { | argKey |
		^this.baseDirectory +/+ argKey.asString ++ ".scd";
	}

	*doAction { | action, path |
		action.(path, this.pathsInFolder(path));
	}

	*pathsInFolder { | path |
		var n;
		n = PathName(path);
		^(n.pathOnly +/+ "*." ++ n.extension).pathMatch;
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