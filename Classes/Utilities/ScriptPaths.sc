//: Like Paths, but instead of returning the string returned
//in a path, it loads (evaluates) the file (or files) and returns the result.

ScriptPaths : Paths {
	classvar <scriptFolder = "Scripts", startupFolder = "startup";
	// optional custom paths from folder (NOT YET IMPLEMENTED):
	classvar <scriptPath, <startupPath; // only used if set
	*initClass {
		StartUp add: {
			this.init;
			this.startupFolder.postln;
		};
	}

	*init {
		super.init;
		this.runStartupFiles;
	}

	*baseDirectory { ^Platform.userAppSupportDir +/+ scriptFolder; }

	*load { | key | ^this.loadFile.key }

	*loadFile { | key |
		var path;
		path = this.baseDirectory +/+ key ++ ".scd";
		if (File.exists(path)) {
			^path.load;
		};
		postln("File" + key ++ ".scd" + "not found in Script folder");
		^nil;
	}

	*loadFolder { | key |
		var path;
		path = this.baseDirectory +/+ key;
		if (File.exists(path)) {
			var files, result;
			files = (path +/+ "*.scd").pathMatch;
			files do: { | f |
				postln("Loading file:" + f.fileName);
				result = f.load;
			}
			^result;
		};
		postln("Folder" + key + "not found in Script folder");
		^nil;
	}

	// Utilities
	*runStartupFiles {
		this.startupFolder.entriesMatchingScd do: { | p |
			postln(">>>>>>>>>> ScriptPaths loading startup file:" + p.fileName + ">>>>>>>>>>");
			p.load;
			postln("<<<<<<<<<< ScriptPaths loaded startup file:" + p.fileName + "<<<<<<<<<<");
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

	*startupFolder {
		^Platform.userConfigDir +/+ startupFolder
	}

	*setScriptFolder {

	}

	*setStartupFolder {

	}
}
