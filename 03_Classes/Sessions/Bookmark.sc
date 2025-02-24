//Sun 18 Feb 2024 17:12

Bookmark {
	classvar all; // array of strings. duplicate paths not allowed!
	var <path, <name;

	*save {
		postln("Saving bookmarks in:" + this.archivePath);
		all.writeArchive(this.archivePath);
	}
	*load {
		postln("Loading bookmarks from:" + this.archivePath);
		if (File.exists(this.archivePath)) {
			all = Object.readArchive(this.archivePath);
			postln("Loaded" + all.size + "bookmarks from:");
			this.archivePath.postln;
			{ this.changed(\bookmarks) }.defer(0.1);
		}{
			"Bookmark file not found: ".postln;
			this.archivePath.postln;
		}
	}

	*archivePath {
		^Platform.userAppSupportDir +/+ "Bookmarks.scd";
	}

	*new { | path |
		^this.newCopyArgs(PathName(path).fullPath).init;
	}

	init {
		this.makeName;
		this.add;
	}

	makeName { name = path.basicName }

	add {
		if (all.detect({| p | p.path == path}).isNil) {
			all = all add: this;
			postln("Added bookmark for" + path);
			this.class.save;
			this.class.changed(\bookmarks);
		}{
			postln("Bookmark for\n" + path + "\n already exists.");
			"Duplicate bookmark for this path was not added.".postln;
		}
	}

	*fileDialog {
		FileDialog.new({ | p |
			this.new(p.first);
		},{ | p |
			postln("Bookmark add canceled");
		}, fileMode: 1)
	}

	*folderDialog {
		FileDialog.new({ | p |
			this.new(p.first +/+ ""); // convert to directory path!
		},{ | p |
			postln("Bookmark add canceled");
		}, fileMode: 2)
	}

	*gui {
		this.vlayout(
			HLayout(
				Button()
				.states_([["+file"]])
				.action_({ this.fileDialog }),
				Button()
				.states_([["+folder"]])
				.action_({ this.folderDialog })
			),
			ListView()
			.action_({ | me |
				me.item.postln;
			})
			.addNotifier(this, \bookmarks, { | n |
				n.listener.postln;
				n.listener.items = all collect: _.name;
			})
		);
		this.load;
	}
}