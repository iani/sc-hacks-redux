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
// 12 Nov 2022 12:26 : also calculate relative times.
// This saves time to re-calculate times when playing with OscDataPlayer.
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
	classvar <times, <messages;  // relative times calculated at load time
	// classvar <scoreData; // for debugging purposes
	*openDialog { | key = \oscdata |
		"Opening file dialog".postln;
		Dialog.openPanel({ | argPaths |
			// put data from all files in one dictionary
			// each file is put under a new numeric index.
			argPaths writeArchive: this.pathsArchivePath;
			this.readAndMergePaths(argPaths, key);
			// argPaths do: { | path, index |
			// 	Library.put(this, key, index, this.new(path));
			// 	this.merge(key); // Merge all files under this key into one data array
			// 	this.processMerged;
			// };
		}, multipleSelection: true);
	}

	*pathsArchivePath {
		^(PathName(Platform.userHomeDir) +/+ "OscDataReader_LastRead.scd").fullPath;
	}

	*reRead {  | key = \oscdata | // reread last read paths
		if (this.hasSavedPaths) {
			this.readAndMergePaths(this.pathsLastRead, key);
		}{
			"There is no osc data path to read.".postln;
			"Running openDialog to select files to read.".postln;
			this.openDialog;
		}
	}

	*hasData { ^allData.notNil }

	*hasSavedPaths {
		^File exists: this.pathsArchivePath;
	}

	*pathsLastRead {
		^Object.readArchive(this.pathsArchivePath);
	}

	*readAndMergePaths { | argPaths, key = \oscdata |
		allData = []; messages = []; times = [];
		argPaths do: { | path, index |
			if (File exists: path) {
				Library.put(this, key, index, this.new(path));
			}{
				postln("File not found:", path);
			}
		};
		this.merge(key);
		this.processMerged;
	}

	*readLastSaved { | key = \oscdata |
		var archivePath, paths;
		archivePath = this.pathsArchivePath;
		if (File exists: archivePath) {
			paths = Object.readArchive(archivePath);
			this.readAndMergePaths(paths, key);
		}{
			postln("File not found:" + archivePath);
			"Cannot find location of last paths archive path".postln;
		};
		//		postln(Object readArchive: this.pathsArchivePath );
	}

	*new { | path |
		^this.newCopyArgs(path).readData;
	}
	readData {
		// postln("Reading data from" + path + "...");
		this.parseDataOSC(File.readAllString(path));
	}

	parseDataOSC { | argString |
		var delimiters, entry;
		var timebeg, timeend, time;
		dataString = argString;
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
		// postln("... read " + data.size + "entries.")
		post(" . ");
	}

	*merge { | key |
		var dict;
		dict = Library.at(this, key);
		allData = []; // Start with data and then add all data from instances in key.
		dict.keys.asArray.sort do: { | i |
			var newData;
			newData = dict[i].data;
			// post("Read" + newData.size + "entries. ");
			allData = allData ++ newData;
		};
		postln("\nallData has" + allData.size + "entries.")
	}

	*calculateTimes {
		#times, messages = allData.flop;
		times = times.differentiate;
		times[0] = times[1];
		times = times rotate: -1;
		postf("data duration is:" + this.duration);
	}

	*duration { times !? {^times.sum.formatTime} }

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
		// sort data by *ENTRY TIME* in ascending order
		// use sortMerget method instead of this line: ?
		allData = converted.sort({| a, b | a[0] < b[0] });
		postln("... Done. Collected" + allData.size + "messages.");
		this.calculateTimes;
	}

	*sortMerged { // sor§t data by *ENTRY TIME* in ascending order
		allData = allData.sort({| a, b | a[0] < b[0] })
	}

	// experimental
	*part { | start = 0, length |
		^allData.copyRange(start, (start + length ?? { allData.size - 1 }));
	}

	*makePlayer {  | start = 0, length |
		// Make player from pre-calculated times, messages.
		// Save time to re-calculate times.
		var end;
		length ?? { length = allData.size - 1 };
		end = start + length;
		^OscDataPlayer.newCopyArgs(
			allData.copyRange(start, end),
			LocalAddr(),
			times.copyRange(start, end),
			messages.copyRange(start, end)
		);
	}

	*play {  | start = 0, length, repeats = 1, player = \oscdata |
		^this.makePlayer(start, length).play;
	}

	*info {

	}
	// These are done by OscDataPlayer with playSelect, playReject
	// *select {}
	// *reject {}
}

