SnippetParser {
	var <string;
	*new { | string | ^this.newCopyArgs(string) }
	parse { | separator = "\n//:" |
		var delimiters, data, entry, positions;
		// string.findRegexp("^//:").postln;
		// delimiters = string findAll: separator;string.findRegexp("^//:")
		delimiters = string.findRegexp("^//:").collect({ | d | d[0] });
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = string.copyRange(b, end - 1)
			}{
				entry = string.copyRange(b, string.size - 1)
			};
			data = data add: entry;
			positions = positions add: [delimiters[i], entry.size];
		};
		if (data.size == 0) {
			data = [string];
			positions = [[0, string.size-1]];
		};
		postln("... read " + data.size + "entries.");
		^[data, positions];
	}
}