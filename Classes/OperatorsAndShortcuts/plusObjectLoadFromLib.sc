/* 21 Jul 2023 17:27
Load an scd file located in the same folder as your class definition.
*/

+ Object {

	loadFromLib { | filename |
		var fullPath;
		fullPath = PathName(this.class.filenameSymbol.asString).pathOnly ++ filename ++ ".scd";
		if (File exists: fullPath) {
			^fullPath.load;
		}{
			postln("File not found" + fullPath);
			^nil
		};
	}
}