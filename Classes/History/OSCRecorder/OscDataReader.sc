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
	classvar <allData;
	var <path, <dataString, <data;

	*new { | path |
		^this.newCopyArgs(path).readData;
	}

	readData {
		var delimiters, entry;
		var timebeg, timeend, time;
		postln("Reading data from" + path + "...");
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
		};
		postln("... read " + data.size + "entries.")
	}

	*openDialog { | key = \oscdata |
		"Opening file dialog".postln;
		Dialog.openPanel({ | argPaths |
			// currentEnvironment.put(key, this.new(argPath.first))
			argPaths do: { | path, index |
				Library.put(this, key, index, this.new(path))
			}
		}, multipleSelection: true);
		this.merge(key);
		this.processMerged;
	}

	*merge { | key |
		var dict;
		dict = Library.at(this, key);
		allData = [];
		dict.keys.asArray.sort do: { | i |
			var newData;
			newData = dict[i].data;
			postln("Adding" + newData.size + "entries to allData ...");
			allData = allData ++ newData;
		};
		postln("DONE. allData has" + allData.size + "entries.")
	}

	*processMerged { | filter = true |
		var exclude, converted;
		if (filter) { exclude = OSCRecorder3.excludedMessages } { exclude = [] };
		postln("Processing " + allData.size + "entries...");
		allData do: { | data |
			var message;
			message = data[1].interpret;
			if (exclude includes: message[0]) {} {
				converted = converted add: [data[0], message];
			};
		};
		"Sorting...".post;
		allData = converted.sort({| a, b | a[0] < b[0] });
		postln("... Done. Collected" + allData.size + "messages.");
	}

	*sortMerged {
		allData = allData.sort({| a, b | a[0] < b[0] })
	}
}
