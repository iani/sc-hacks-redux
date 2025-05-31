//Sun 18 Feb 2024 10:42
//Adding PathName methods to String so that one can use
//String instead of PathName instances.

+ String {
	// new utility method: return filenamewithoutextension or folder name:
	asFolder { // add folder ending slash if needed.
		// Useful for entriesMatching ... methods
		^this +/+ "";
	}
	basicName {
		var p;
		p = PathName(this);
		if (p.pathOnly == p.fullPath) {
			^"[" ++ p.folderName ++ "]";
		}{
			^p.fileNameWithoutExtension;
		}
	}
	scroot { ^PathName.scroot }
	fileName { ^PathName(this).fileName }
	fileNameWithoutExtension { ^PathName(this).fileNameWithoutExtension }
	extension { ^PathName(this).extension }
	pathOnly { ^PathName(this).pathOnly }
	folder { ^this.pathOnly }
	lastColonIndex { ^PathName(this).lastColonIndex }
	up { ^this.superFolder }
	superFolder {
		var up, index;
		up = this.pathOnly;
		up = up.copyRange(0, up.lastColonIndex - 1).folder;
		if (up.size == 0) { ^"/" } { ^up }
	}

	isAbsolutePath { ^PathName(this).isAbsolutePath }
	isRelativePath { ^PathName(this).isRelativePath }
	// asRelativePath { | relativeTo | ^PathName(this).asRelativePath(relativeTo) }
	folderName { ^PathName(this).folderName }
	// !!!!! this causes segmentation fault!!!!!!!!!!
	// fullPath { ^PathName(this).fullPath } // WHY ????????
	entries {
		if (this.isFolder) {
			^PathName(this).entries
		}{
			^PathName(this.folder).entries
		}
	}
	// files { ^PathName(this).files }
	files {
		if (this.isFolder) {
			^PathName(this).files
		}{
			^PathName(this.folder).files
		}
	}

	// returns a list of all the files in the folder represented by this path.
	// whose extension is the same as the extension of the receiver.

	entriesMatchingScd { ^this entriesMatchingExtension: "scd" }
	entriesMatchingWav { ^this entriesMatchingExtension: "wav" }
	entriesMatchingWAV { ^this entriesMatchingExtension: "WAV" }

	entriesMatchingExtension { | extension = "scd" |
		^(this.pathOnly.asFolder +/+ "*." ++ extension).pathMatch;
	}

	// folders { ^PathName(this).folders }
	folders {
		if (this.isFolder) {
			^PathName(this).folders
		}{
			^PathName(this.folder).folders
		}
	}

	isFile { ^PathName(this).isFile }
	isFolder { ^PathName(this).isFolder }
	filesDo { | func |
		PathName(this).filesDo(func)
	}
	allFolders {
		^PathName(this).allFolders
	}
	diskName { ^PathName(this).diskName }
	endNumber { ^PathName(this).endNumber }
	// +/+ { | path | ^(PathName(this) +/+ path).fullPath } // already exists
	noEndNumbers { ^PathName(this).noEndNumbers }
	nextName { ^PathName(this).nextName }
	// absolutePath { ^PathName(this).absolutePath }
	deepFiles {
		^PathName(this).deepFiles

	}
	fileNameWithoutDoubleExtension { ^PathName(this).fileNameWithoutDoubleExtension }
	parentPath { ^PathName(this).parentPath }
}