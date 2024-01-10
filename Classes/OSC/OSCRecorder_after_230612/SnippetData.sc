/*  6 Jul 2023 12:27
Variant of OscData that hnndles snippets without timestamps.

Enables handling of snippet files as osc-data files sending code.

*/

SnippetData : OscDataScore {

	checkFileType {}

	parseString { | stringAndPath |
		// parse a string read from a file, in the format of //:\nmessage.
		// Add all parsedEntries found to parsedEntries.
		// Set durations of all entries to 1.
		var delimiters, entry, string, path;
		// var timebeg, timeend;
		#string, path = stringAndPath;
		// this.checkFileType(string, path);
		delimiters = string.findAll("\n//:");
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = string.copyRange(b + 1, end - 1);
			}{
				entry = string.copyRange(b + 1, string.size - 1)
			};
			// timebeg = entry.find(":--[");
			// timeend = entry.find("]", 4);
			parsedEntries = parsedEntries add: [
				1,
				// entry.copyRange(timeend + 2, if (end.notNil) { entry.size - 2 } { entry.size - 1 })
				entry
 			];
			unparsedEntries = unparsedEntries add: entry;
		};
		post(".");
	}
	formatTimeIndex { | t, i |
		^(t.asString + messages[timeline.segmentMin + i].split(Char.nl)[0][3..])
	}

	windowName { ^PathName(paths.first).fileNameWithoutExtension }
}