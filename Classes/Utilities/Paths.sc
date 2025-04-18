//火 15  4 2025 00:00
//Redoing Path and related classes

Paths {

	*initClass {
		StartUp add: {
			this.makeBaseDirectory;
			this.runStartupFiles;
		};
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

	*getPathFromKey { | key, default = "" |
		var path, returnValue;
		path = this.makePathLocation(key);
		if (File.exists(path)) {
			returnValue = File.readAllString(path);
		}{
			returnValue = default;
			this.savePath(returnValue, path);
		};
		^returnValue;
	}


	*savePathAndDo { | path, pathLocation, action |
		this.savePath(path, pathLocation);
		this.doAction(action, path);
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
	// Utilities

	*runStartupFiles {
		(this.startupFolder +/+ "blah.scd").entriesMatchingExtension do: { | p |
			postln(">>>>>>>>>> Loading startup file:" + p.fileName + ">>>>>>>>>>");
			p.load;
			postln("<<<<<<<<<< Loaded startup file:" + p.fileName + "<<<<<<<<<<");
		}
	}
	*addStartupFileAtDate { | string, date |
		var startupFolder;
		startupFolder = this.startupFolder;
		date ?? { date = Date.getDate.dayStamp };
		if (File.exists(startupFolder).not) {
			File mkdir: startupFolder;
			{
				File.use(
					startupFolder +/+ date + ".scd"
					"w",
					{ | f | f write: string }
				)
			}.defer(1)
		}
	}

	*startupFolder { ^Platform.userConfigDir +/+ "startup" }
}