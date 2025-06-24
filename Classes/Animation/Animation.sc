// 木  5  6 2025 11:13
// Load animations from osc recordings (see RokokoData2, OscData)
// Convert an Array of timestamped Osc Messages into an array of numbers
// Create and save an audiofile containing the data of the numeric array.
// The default folder for osc data (for animations or other stuff) is: "~/oscdata".
// Sets with oscdata are saved in subfolders of a single default folder.

Animation : NamedSingleton2 {
	classvar <>port = 22245; // default port for sending osc messages
	classvar >homeFolder;
	classvar >sessions; // all sessions read from homeFolder
	var <folder, <files;
	var converter, <cliplist;
	var player;

	*localListener {
		^NetAddr("127.0.0.1", port);
	}

	*homeFolder {
		homeFolder ?? { homeFolder = "~/oscdata".standardizePath; };
		^homeFolder;
	}

	*sessions {
		sessions ?? { this.makeSessions };
		^sessions;
	}

	*makeSessions {
		sessions !? { ^sessions }; // only remake sessions when needed
		// to force remake, set sessions to nil.
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
		this.readClipList;
	}

	converter {
		if (converter.isNil) { this.convertData };
		^converter;
	}

	play { // only one animation can play at any time.
		// Playing many in parallel to be implemented later, when needed.
		sessions do: _.stop;
		postln("Playing animation:" + this);
		this.player.play;
	}
	freeze { this.player.freeze }
	move { this.player.move }
	stopAll { sessions do: _.stop; }
	stop { player.stop }
	// use just default animation player
	// TODO: get different player class,
	// TODO: use different players per animation instance
	player { ^player ?? { player = AnimationPlayer(name, this); }; }

	convertData { converter = OscDataConverter convert: files; }

	readClipList { // read list of preset animation clips from file
		this.clipPath.postln;

	}

	clipPath { ^folder +/+ "clips.scd" }

	saveClipList {
	}

	*sessionFolders { ^this.sessions.keys.asArray.sort }

	*gui { AnimationGui.gui }
	gui { AnimationGui(this).gui }
	soundFilePath { ^this.converter.soundFilePath }

	setJoint { | bus, dim, value |
		player.setJoint(bus, dim, value);
	}
	stopCtls { player.stopCtls }
}