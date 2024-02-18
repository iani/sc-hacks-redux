//Sun 18 Feb 2024 10:42
//Adding PathName methods to String so that one can use
//String instead of PathName instances.

+ String {
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
    asRelativePath { | relativeTo | ^PathName(this).asRelativePath(relativeTo) }
    folderName { ^PathName(this).folderName }
	// !!!!! this causes seggmentation faul!!!!!!!!!!
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
	absolutePath { ^PathName(this).absolutePath }
	deepFiles {
		^PathName(this).deepFiles

	}
	fileNameWithoutDoubleExtension { ^PathName(this).fileNameWithoutDoubleExtension }
	parentPath { ^PathName(this).parentPath }
}