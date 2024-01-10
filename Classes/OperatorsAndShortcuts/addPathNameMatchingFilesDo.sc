/* 26 Feb 2022 07:45

String - PathName utilities

*/

+ PathName {
	name { ^this.shortName }
	loadAudiofile { this.fullPath.loadAudiofile }
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

	matchingFiles { | ... types |
		var match;
		types = types collect: _.asSymbol;
		postln("Looking for files in" + this.fullPath + "...");
		this.deepFiles do: { | p |
			if (types includes: p.extension.asSymbol) {
				match = match add: p;
			}{
				postln("Skipping non-matching file type:" + p.fileName);
			}
		};
		^match
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