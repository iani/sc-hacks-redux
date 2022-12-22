/* 26 Feb 2022 07:45

String - PathName utilities

*/

+ PathName {
	up {
		^PathName(this.parentPath)
	}
	shortName {
		if (this.isFolder) {
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
	concatFolders { | ... folders |
		// TODO
	}
}