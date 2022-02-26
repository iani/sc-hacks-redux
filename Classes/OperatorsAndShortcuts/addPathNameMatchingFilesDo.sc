/* 26 Feb 2022 07:45

*/

+ PathName {
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