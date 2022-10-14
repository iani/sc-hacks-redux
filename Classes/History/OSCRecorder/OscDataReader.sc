/* 31 Aug 2022 11:58
Read a file containing osc data as scripts in format:
//:<time.received>
<code>
<code>
//:<time.received>
<code>
... etd.

Create an array of entries like this:
[[time, code], [time, code], etc...]
*/

OscDataReader {
	var <path, <dataString, <data;

	*new { | path |
		^this.newCopyArgs(path).readData;
	}

	readData {
		var delimiters, entry;
		var timebeg, timeend, time;
		path.postln;
		dataString = File.readAllString(path);
		delimiters = dataString.findAll("\n//:--[");
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = dataString.copyRange(b, end)
			}{
				entry = dataString.copyRange(b, dataString.size - 1)
			};
			timebeg = entry.find(":--[");
			timeend = entry.find("]", 4);
			data = data add: [
				entry.copyRange(timebeg + 4, timeend - 1).interpret,
				entry.copyRange(timeend + 1, entry.size - 1)
			];
		}
	}

	*openDialog { | varname = \oscdata |
		"Opening file dialog".postln;
		FileDialog({ | argPath |
			currentEnvironment.put(varname, this.new(argPath.first))
		})
	}
}
