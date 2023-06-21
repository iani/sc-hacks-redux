/* 20 Jun 2023 14:29
Redo of OscDataReader
*/

OscData {
	var <paths;
	var <sourceStrings;
	var <parsedEntries; // list of messages sorted by timestamp, in the form:
	// [timestamp, message] where:
	// float: timestamp
	// array: The message and its arguments. Obtained by interpreting
	// the source string of the message.
	var <times, <messages; // times and messages obtained from parsedEntries
	var <duration;

	*new { | paths |
		^this.newCopyArgs(paths).init;
	}

	init {
		this.readSource;
		this.makeMessages;
	}

	readSource {
		sourceStrings = paths collect: File.readAllString(_);
	}

	makeMessages {
		// split sourceStrings to message components by regexp
		// Collect interpreted time and message from each message.
		parsedEntries = [];
		[sourceStrings, paths].flop do: this.parseString(_);
		postln("Parsed " + parsedEntries.size + "entries");
		// sort messages in ascending timestamp order first!????
		#times, messages = parsedEntries.flop;
		times = times - times.first;
		duration = times.sum;
		this.changed(\inited);
	}

	parseString { | stringAndPath |
		// parse a string read from a file, in the format of //:--[timestamp] message.
		// Add all parsedEntries found to parsedEntries.
		var delimiters, entry, string, path;
		var timebeg, timeend;
		#string, path = stringAndPath;
		if ("//code" == string[..string.find("\n")-1]) {
			Error("File" + path + "is a code file. Use OscDataScore.").throw
		};
		delimiters = string.findAll("\n//:--[");
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = string.copyRange(b, end)
			}{
				entry = string.copyRange(b, string.size - 1)
			};
			timebeg = entry.find(":--[");
			timeend = entry.find("]", 4);
			parsedEntries = parsedEntries add: [
				entry.copyRange(timebeg + 4, timeend - 1).interpret,
				entry.copyRange(timeend + 1, entry.size - 1)
			];
		};
		post(" . ");
	}

	gui {
		this.br_(800, 300).vlayout(
			HLayout(
				RangeSlider() // select a range of times
				.orientation_(\vertical)
				.action_({ | me |
					var lo, hi;
					#hi, lo = 1 - [me.lo, me.hi];
					postln("original: " + me.lo + me.hi + "inverted" +
					lo + hi);
				})
				.addNotifier(this, \range, { | n, lo = 0, hi = 1 |
					postln("setting range to lo" + lo + "hi" + hi);
					n.listener.lo = lo;
					n.listener.hi = hi;
				}),
				ListView() // times
				.maxWidth_(160)
				.palette_(QPalette.light
					.highlight_(Color(1.0, 0.9, 0.7))
					.highlightText_(Color(0.0, 0.0, 0.0))
				)
				.selectionMode_(\contiguous)
				.action_({ | me |
					this.changed(\selection, me.selection)
				})
				.addNotifier(this, \selection, { | n ... selection |
					n.listener.items = times[selection]
				})
				.addNotifier(this, \inited, { | n |
					// n.listener.items =
				}),
				// .items_(times),
				ListView() // messages
				.palette_(QPalette.light
					.highlight_(Color(0.7, 1.0, 0.9))
					.highlightText_(Color(0.0, 0.0, 0.0))
				)
				.addNotifier(this, \selection, { | n ... selection |
					n.listener.items = messages[selection]
				})
			)
		);
		{ this.selectAll; }.defer(0.1);
	}

	selectAll {
		this.changed(\selection, (0..times.size - 1));
		this.changed(\range, 0, 1)
	}

	makePlayer {

	}
}