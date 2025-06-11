// 木  5  6 2025 11:13
// Load animations from osc recordings (see RokokoData2, OscData)
// Convert an Array of timestamped Osc Messages into an array of numbers
// Create and save an audiofile containing the data of the numeric array.
// The default folder for osc data (for animations or other stuff) is: "~/oscdata".
// Sets with oscdata are saved in subfolders of a single default folder.

Animation : NamedSingleton2 {
	classvar >homeFolder;
	classvar sessions; // all sessions read from homeFolder
	var <folder, <files;
	var <oscdata, <numdata;

	*homeFolder {
		homeFolder ?? { homeFolder = "~/oscdata".standardizePath; };
		^homeFolder;
	}

	*sessions {
		sessions ?? { this.makeSessions };
		^sessions;
	}

	*makeSessions {
		sessions = IdentityDictionary();
		this.homeFolder.entries do: { | e |
			var basename, subfolder, sessionname;
			basename = e.folderName;
			e.entries do: { | f |
				subfolder = f.folderName;
				sessionname = format("%:%", basename, subfolder).asSymbol;
				sessions[sessionname] = this.new(sessionname).setFolder(f);
			};
		};
		^sessions;
	}

	setFolder { | argFolder |
		folder = argFolder;
		files = (folder.fullPath +/+ "*.scd").pathMatch;
	}

	readData {
		oscdata = OscData(files);
		numdata = OscDataConverter convert: oscdata;

	}

	type { ^oscdata.type }

	*gui {
	// list loaded Animations.
	// Add new animation by reading data.
	// Start/stop a selected animation from the list.
		var list;
		this.vlayout(
			list = ListView().items_(this.sessionFolders);
		);
	}

	*sessionFolders { ^this.sessions.keys.asArray.sort }

	*fromUser {
		FileDialog({ | path |
			path = path[0];
			postln("Fullpath" + path);
			postln("foldername" + path.folderName);
			postln("folder" + path.folder);
		}, fileMode: 2);
	}

	*fromFolder { | folder |
		var name;
		name = folder.folderName.asSymbol;

	}
	// *new { | folder |
	// 	*this.newCopyArgs(folder).init;
	// }
}