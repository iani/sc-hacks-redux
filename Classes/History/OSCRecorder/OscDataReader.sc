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

/*

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

NOTE: Fix interpreting read data code strings:
If the code saved contains a syntax error, then interpreting
the code will issue the same error.  This will cause the
interpretation loop to stop!
There seems to be no way to catch this error ("try" and "protect"
	do not work when doing "astring".interpret. ).
But one can repeat the loop if one does it inside a routine.
Use this as example, and build it in method "readData".

Alternatively, have readData call a separate method in a second
runthrough, which does the interpreting inside a forked routine,
and repeats it until all code snippets with syntax errors have been
ignored.

//:
{
	5 do: {
[
	"1", "pi", "(1..3).sqrt", "xxx2"
] do: { | i | i.interpret.postln; };
		1.wait;
	}
}.fork;

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
				Library.put(this, key, index, this.new(path));
				this.merge(key);
				this.processMerged;
			};
		}, multipleSelection: true);
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
			if (message.isNil) { message = [\error, data[1]]; };
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
