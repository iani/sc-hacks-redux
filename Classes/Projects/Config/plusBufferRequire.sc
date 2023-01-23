/* 17 Dec 2022 14:33
Add subfolders of buffers contained in ~/sc-audiofiles/
to be loaded when the buffer is booted.
*/

+ Buffer {
	*require { | ... directories |
		this.addNotifier(Server.default, \serverRunning, { | n |
			if (n.notifier.serverRunning) {
				// { this.required.postln; } ! 10;
				this.required do: this.loadRequiredIfExists(_)
			}{
				Library.put(Buffer, nil);
			}
		}
		);
		directories do: this.require1(_);
	}

	*require1 { | directory |
		var required;
		required = this.required;
		directory = directory.asSymbol;
		if (required includes: directory) {
			postln("The directory" + directory + "is already required");
		}{
			required add: directory;
			this.loadRequiredIfExists(directory);
		}
	}

	*required { ^Registry(\buffers, \required, { Set() }); }
	// New alternative, using separate class:
	// *required { ^Registry(\buffers, \required, { RequiredAudioFolders() }); }

	*loadRequiredIfExists { | required |
		var thePath;
		thePath = this.makeLoadPathname(required);
		if (File.exists(thePath.fullPath)) {
			this.loadRequired(required);
		}{
			postln("Directory not found:" + thePath.fullPath);
		}
	}

	*loadRequired { | required |
		Server.default.waitForBoot({
			this.loadAudioFiles(this.makeLoadPathname(required));
		})
	}

	*loadAudioFiles { | pathName |
		pathName.matchingFiles("wav", "WAV", "aiff", "aif") do: _.loadAudiofile;
	}

	*makeLoadPathname { | required |
		// ^PathName("~/sc-audiofiles") +/+ required.asString;
		^this.audioFilesRoot +/+ required.asString;
	}

	*clearRequired {
		// TODO: free buffers???
		Registry.remove(\buffers, \required);
	}

	*audioFilesRoot { | key = \default |
		^Preferences.get(\audioFilesRoot, key) ?? { PathName("~/sc-audiofiles") }
	}

	*audioFolders { | key = \default |
		^Preferences.get(\audioFolders, key) ?? { Set() }
	}

	*saveAudioFolders { | key = \default |
		^Preferences.put(\audioFolders, key, this.required);
	}

	*setAudioFilesRootDialog { | key = \default |
		FileDialog({ | path |
			this.setAudioFilesRoot(PathName(path[0]), key);
		}, fileMode: 2)
	}

	*setAudioFilesRoot { | path, key = \default |
		Preferences.put(\audioFilesRoot, key, path);
	}

	*projectFolders { // list subfolders of audioFilesRoot
		^this.audioFilesRoot.folders;
	}

	*listProjectFolders {
		this.projectFolders do: { | f | f.shortName.postln; }
	}
}