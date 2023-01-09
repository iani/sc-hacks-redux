/* 26 Feb 2022 07:45

String - PathName utilities

*/

+ PathName {
	asDir { ^this +/+ "" }
	up {
		^PathName(this.parentPath)
	}
	shortName {
		if (fullPath.last.isPathSeparator) {
			// ad hoc format. For Project.gui
			^format("[%]", this.folderName);
		}{
			^this.fileNameWithoutExtension;
		}
	}

	matchingFilesDo { | action, template |
		this.deepFiles.select({ | pn |
			template.matchRegexp(pn.fileName)
		}) do: action.(_);
	}

	matchingFilesCollect { | action, template |
		^this.deepFiles.select({ | pn |
			template.matchRegexp(pn.fileName)
		}) collect: action.(_);
	}
}

+ String {
	up { ^PathName(this).up.fullPath; }
	concatFolders { | ... folders |
		var result;
		result = PathName(this);
		folders do: { | f |
			result = result +/+ f;
		};
		^result.fullPath;
	}
}