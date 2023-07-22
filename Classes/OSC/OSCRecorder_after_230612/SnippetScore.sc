/* 22 Jul 2023 13:24

Parse snippets without timestamps.
Provide default duration of 1 second for each snippet.
*/

SnippetScore : OscDataScore {
	parseString { | stringAndPath |
		// parse a string read from a file, in the format of //:--[timestamp] message.
		// Add all parsedEntries found to parsedEntries.
		var delimiters, entry, string, path;
		var timebeg, timeend;
		#string, path = stringAndPath;
		// this.checkFileType(string, path);
		delimiters = string.findAll("\n//:");
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = string.copyRange(b, end);
			}{
				entry = string.copyRange(b, string.size - 1)
			};
			parsedEntries = parsedEntries add: [
				1,
				entry = entry.copyRange(1, if (end.notNil) { entry.size - 2 } { entry.size - 1 })
 			];
			unparsedEntries = unparsedEntries add: entry;
		};
		post(".");
	}
	formatTimeIndex { | t, i | // include header comments
		var m;
		m = messages[timeline.segmentMin + i];
		^(t.asString + m.copyRange(3, (m.indexOf(Char.nl) ?? { m.size }) - 1))
	}
}