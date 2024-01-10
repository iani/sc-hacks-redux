/* 23 Jan 2023 23:35
Redoing Buffer.require - using PreferenceSet

See also FileLoader
*/

AudioFolders : PreferenceSet {
	var >rootPath, folders;
	var <>activep = false;

	*activate {
		ServerBoot.add(this);
		ServerQuit.add(this);
	}

	*doOnServerBoot { | server |
		// workaround for a bug: make sure the server is booted:
		server.doWhenBooted({ this.all do: _.loadFoldersIfActive;})
	}

	*doOnServerQuit { Library.put(Buffer, nil); }

	loadFoldersIfActive { if (activep) { this.loadFolders; } }
	loadFolders { this.folders do: this.loadFolder(_); }
	loadFolder { | f | this.loadAudioFiles(this.makeLoadPathname(f)); }
	makeLoadPathname { | f | ^this.rootPath +/+ f.asString; }

	loadAudioFiles { | pathName |
		pathName.matchingFiles("wav", "WAV", "aiff", "aif") do: _.loadAudiofile;
	}

	rootPath { ^rootPath ?? { rootPath = PathName("~/sc-audiofiles") } }
	folders { ^folders ?? { folders = Set() } }

	add { | folder |
		this.class.activate;
		this.folders add: folder.asSymbol;
		this.save;
		this.changed(\folders);
	}

	remove { | folder |
		this.folders remove: folder.asSymbol;
		this.save;
		this.changed(\folders);
	}

	*require { | folder, key = \default |
		this.new(key).require(folder);
	}

	require { | folder |
		folder = folder.asSymbol;
		if (this.folders includes: folder) {
			postln("The directory" + folder + "is already required");
		}{
			this.add(folder);
			activep = true;
			if (Server.default.serverRunning) {
				this loadFolder: folder
			} {
				Server.default.boot
			}
		}
	}
}