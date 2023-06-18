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
		postln("Reading data from" + path);
		// "Will now call parseDataScript".postln;
		this.parseDataScript(File.readAllString(path));
	}

	parseDataScript { | argString |
		var delimiters, entry;
		var timebeg, timeend, time;
		dataString = argString;
		delimiters = dataString.findAll("\n//:[");
		// delimiters.postln;
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = dataString.copyRange(b, end)
			}{
				entry = dataString.copyRange(b, dataString.size - 1)
			};
			timebeg = entry.find(":[");
			timeend = entry.find("]", 2);
			data = data add: [
				entry.copyRange(timebeg + 2, timeend - 1).interpret,
				entry
			];
		};
		postln("... read " + data.size + "entries.")
	}

	play { | player, envir, rate = 1, repeats = 1, osc = true |
		var event, playFunc;
		player ?? { player = PathName(path).fileNameWithoutExtension.asSymbol };
		envir = envir.envir;
		// postln("scriptreader play osc is:" + osc);
		if (osc.not) {
			playFunc = {
				~score[1].postln.interpretIn(envir);
				~dur = ~score[0] * rate
			}
		}{
			OscGroups.enableCodeReception;
			playFunc = {
				~score[1].sim;
				~dur = ~score[0] * rate
			}
		};
		event = (
			score: Pindex(data, Pseries(0, 1, data.size * repeats)),
			play: playFunc
		);
		event +> player;
	}
}