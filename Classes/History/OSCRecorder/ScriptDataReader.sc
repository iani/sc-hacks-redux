/* 27 Oct 2022 16:30
Like OSCDataReader but works with files written in scd script format for Project class.

The snippet separator is
//:[1234.1234134]

where [1234.1234134] is the duration (not the absolute time).
*/

ScriptDataReader : OscDataReader {

		*openDialog { | key = \scriptdata |
		"Opening file dialog".postln;
			Dialog.openPanel({ | argPath |
			Library.put(this, key, this.new(argPath));
			// argPaths do: { | path, index |
			// 	Library.put(this, key, index, this.new(path));
			// 	this.merge(key);
			// 	this.processMerged;
			// };
		}, multipleSelection: false);
	}

	readData {
		postln("Reading data from" + path + "...");
		"Will now call parseDataScript".postln;
		this.parseDataScript(File.readAllString(path));
	}

	parseDataScript { | argString |
		var delimiters, entry;
		var timebeg, timeend, time;
		dataString = argString;
		delimiters = dataString.findAll("\n//:[");
		delimiters.postln;
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = dataString.copyRange(b, end)
			}{
				entry = dataString.copyRange(b, dataString.size - 1)
			};
			// entry.postln;
			timebeg = entry.find(":[");
			timeend = entry.find("]", 2);
			data = data add: [
				entry.copyRange(timebeg + 2, timeend - 1).interpret,
				entry
			];
			// [timebeg, timeend].postln;
			// entry.copyRange(timebeg + 2, timeend - 1).postln;
			// data = data add: [
			// 	entry.copyRange(timebeg + 2, timeend - 1).interpret,
			// 	entry.copyRange(timeend + 1, entry.size - 1)
			// ];
		};
		postln("... read " + data.size + "entries.")
	}

	play { | player, envir, rate = 1, repeats = 1 |
		var event;
		player ?? { player = PathName(path).fileNameWithoutExtension.asSymbol };
		envir = envir.envir;
		// envir.postln;
		// "===============================================================".postln;
		event = (
			score: Pindex(data, Pseries(0, 1, data.size * repeats)),
			play: {
				~score[1].postln.interpretIn(envir);
				~dur = ~score[0] * rate
			}
		);
		// event.postln;
		// postln("I am going to send " + event + "to " + player);
		event +> player;
		/*
		(
			score: Pindex(data, Pseries(0, 1, data.size - 1)),
			play: {
				~score[1].interpretIn(envir);
				~dur = ~score[0] * rate
			}
		) +> player;
		*/
	}
}