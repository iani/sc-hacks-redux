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
	// ============= Store state from user selection for simpler updates ==============
	var <selectedMinTime = 0, <selectedMaxTime = 0; // section selected
	var <selectedTimes, <selectedMessages;

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
			RangeSlider() // select a range of times
			.orientation_(\horizontal)
			.action_({ | me |
				this.selectTimeRange(
					selectedMinTime = me.lo * this.duration,
					selectedMaxTime = me.hi * this.duration
				);
			})
			.addNotifier(this, \selection, { | n |
				postln("rangeslider new update - selection")
			})
			.addNotifier(this, \timeselection, { | n ... selection |
				// resize when user selects time range in time list
				n.listener.setSpan(
					this.mapTime(selection.minItem),
					this.mapTime(selection.maxItem)
				);
			})
			.addNotifier(this, \timerange, { | n, lo = 0, hi = 1 |
				postln("setting range to lo" + lo + "hi" + hi);
				n.listener.lo = lo;
				n.listener.hi = hi;
			}),
			HLayout(
				StaticText().string_("beginning"),
				NumberBox()
				.decimals_(3)
				.action_({ | me |
					selectedMinTime = me.value;
					this.selectTimeRange(selectedMinTime, selectedMaxTime);
				})
				.addNotifier(this, \selection, { | n |
					postln("mintimebox new update - selection")
				})
				.addNotifier(this, \timeselection, { | n ... selection |
					n.listener.value = selectedMinTime = times[selection.minItem];
				})
				.addNotifier(this, \items, { | n ... selection |
					n.listener.value = selectedMinTime = times[selection].minItem;
				}),
				StaticText().string_("end"),
				NumberBox()
				.decimals_(3)
				.addNotifier(this, \timeselection, { | n ... selection |
					n.listener.value = selectedMaxTime = times[selection.maxItem];
				})
				.addNotifier(this, \selection, { | n |
					postln("maxtimebox new update - selection")
				})
				.addNotifier(this, \items, { | n ... selection |
					n.listener.value = selectedMaxTime = times[selection].maxItem;
				}),
				StaticText().string_("duration"),
				NumberBox()
				.decimals_(3)
				.addNotifier(this, \selection, { | n |
					postln("duration timebox new update - selection")
				})
				.addNotifier(this, \timeselection, { | n ... selection |
					n.listener.value = times[selection.maxItem] - times[selection.minItem];
				})
				.addNotifier(this, \items, { | n ... selection |
					n.listener.value = times[selection].maxItem - times[selection].minItem;
				}),
				StaticText().string_("total duration:" + this.duration),
				// NumberBox(),
				// StaticText().string_("end"),
			),
			HLayout(
				ListView() // times
				.maxWidth_(160)
				.palette_(QPalette.light
					.highlight_(Color(1.0, 0.9, 0.7))
					.highlightText_(Color(0.0, 0.0, 0.0))
				)
				.selectionMode_(\contiguous)
				.action_({ | me |
					// "running times list	MAIN action".postln;
					this.changed(\timeselection, me.selection)
				})
				.selectionAction_({ | me |
					// "running times list SELECTION action".postln;
					// postln("selection action selection:" + me.selection);
					// this.changed(\timeselection, me.selection)
				})
				.keyDownAction_({ | me ... args |
					// enable updates from cursor keys + Cmd-a (select all)
					me.defaultKeyDownAction(me, *args);
					{ this.changed(\timeselection, me.selection) }.defer(0.1);
				})
				.addNotifier(this, \selection, { | n |
					postln("times list new update - selection")
				})
				.addNotifier(this, \items, { | n ... selection |
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
				.addNotifier(this, \selection, { | n |
					postln("messages list new update - selection")
				})

				.addNotifier(this, \items, { | n ... selection |
					n.listener.items = messages[selection]
				})
				.addNotifier(this, \timeselection, { | n ... selection |
					n.listener.items = messages[selection]
				})

			)
		);
		{ this.selectAll; }.defer(0.1);
	}

	selectTimeRange { | lo = 0, hi = 1 |
		this.changed(\items, (this.findTimeIndex(lo)..this.findTimeIndex(hi)));
		//
	}

	findTimeIndex { | time |
		// var result;
		^times indexOf: (times detect: { | t | t >= time });
		// result.postln;
		// ^result;
	}

	mapTime { | index |
		// postln("Map time" + times[index] + "to duration" + this.duration + "result is" + (times[index] / this.duration));
		^times[index] / this.duration;
	}

	duration { ^times.last }

	selectAll {
		this.changed(\items, (0..times.size - 1));
		this.changed(\timerange, 0, 1)
	}

	makePlayer {

	}
}