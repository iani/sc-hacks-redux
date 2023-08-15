/* 14 Aug 2023 22:16

Can be part of a PresetList, just as a Preset.
Loads an OscData code from a snippet list, and plays
it either with a routine or by stepping through its snippets
triggered by an osc message.

a = ScorePlayer(nil, nil, "testscore");

ScorePlayer(nil, nil, "testscore-negative");
*/

ScorePlayer {
	var <list, index = 0, <name, <path, <score;

	index { ^index ?? { index = 0 } }

	*new { | list, index = 0, name |
		^this.newCopyArgs(list, index, name).init;
	}

	init { this.readScore }

	readScore {
		path = this.makePath;
		postln("ScorePlayer:readScore. name:" + name);
		postln("Path:" + path);
		postln("File exists?" + (File exists: path));
		if (File.exists(path).not) {
			postln("Could not find path:\n" ++ path);
			"Using default score path instead".postln;
			path = this.defaultPath;
		};
		// postln("DOUBLECHECK PATH\n" ++ path);
		// postln("DOUBLECHECK File exists?" + (File exists: path))
		score = OscData.fromPath(path);
	}

	makePath { | argName |
		^PathName(
			PathName(this.class.filenameSymbol.asString).parentPath
			+/+ "Scores" +/+ (argName ? name).asString ++ ".scd"
		).fullPath;
	}

	defaultPath { ^this.makePath("default") }

}