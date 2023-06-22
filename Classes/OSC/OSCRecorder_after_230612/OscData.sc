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
	var <timesMessages, <selectedTimes, <selectedMessages;

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
		timesMessages = [times, messages].flop;
		// timesMessages.postln;
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
				selectedMinTime = me.lo * this.duration;
				selectedMaxTime = me.hi * this.duration;
				this.updateTimesMessages(me);
			})
			.addNotifier(this, \selection, { | n |
				postln("rangeslider new update - selection");
				n.listener.setSpan(
					selectedMinTime / this.duration,
					selectedMaxTime / this.duration
				)
			}),
			HLayout(
				StaticText().string_("beginning"),
				NumberBox()
				.decimals_(3)
				.action_({ | me |
					selectedMinTime = me.value.clip(0, selectedMaxTime);
					me.value = selectedMinTime;
					this.updateTimesMessages(me);
				})
				.addNotifier(this, \selection, { | n, who |
					if (who != n.listener) { n.listener.value = selectedMinTime; }
				}),
				StaticText().string_("end"),
				NumberBox()
				.decimals_(3)
				.action_({ | me |
					selectedMaxTime = me.value.clip(selectedMinTime, this.duration);
					me.value = selectedMinTime;
					this.updateTimesMessages(me);
				})
				.addNotifier(this, \selection, { | n, who |
					if (n.listener != who) { n.listener.value = selectedMaxTime; }
				}),
				StaticText().string_("duration"),
				NumberBox()
				.decimals_(3)
				.action_({ | me |
					var clippedVal, clippedDuration;
					clippedVal = me.value.max(0);
					clippedDuration = me.value.clip(0, this.duration.min(clippedVal - selectedMinTime));
					me.value_(clippedDuration);
					selectedMaxTime = selectedMinTime + clippedDuration;
					this.updateTimesMessages(me);
				})
				.addNotifier(this, \selection, { | n, who |
					if (who != n.listener) {
						n.listener.value = selectedMaxTime - selectedMinTime;
					}
				}),
				StaticText().string_("total duration:" + this.duration)
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
					#selectedTimes, selectedMessages = timesMessages.copyRange(
						me.selection.minItem, me.selection.maxItem;
					).flop;
					selectedMinTime = selectedTimes.minItem;
					selectedMaxTime = selectedTimes.maxItem;
					this.changed(\selection, me);
				})
				.selectionAction_({ | me |
					// do not use selection action because it blocks update
					// use keyDownAction instead.
				})
				.keyDownAction_({ | me ... args |
					// enable updates from cursor keys + Cmd-a (select all)
					me.defaultKeyDownAction(me, *args);
					{ this.selectTimesMessages(me, me.selection) }.defer(0.1);
				})
				.addNotifier(this, \selection, { | n, who |
					postln("updating selection" + n.listener + who);
					if (who === n.listener) {
						// do not update
					}{
						n.listener.items = selectedTimes;
					};
					// postln("times list new update - selection")
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
				.addNotifier(this, \selection, { | n, who |
					if (who != n.listener) {
						n.listener.items = selectedMessages;
					}
				})
			)
		);
		{ this.selectAll; }.defer(0.1);
	}

	findTimeIndex { | time | ^times indexOf: (times detect: { | t | t >= time }); }

	mapTime { | index | ^times[index] / this.duration; }

	duration { ^times.last }

	selectAll {
		// this.changed(\items, (0..times.size - 1));
		// this.changed(\timerange, 0, 1);
			// var <selectedMinTime = 0, <selectedMaxTime = 0; // section selected
	// var <timesMessages, <selectedTimes, <selectedMessages;
		selectedMinTime = 0;
		selectedMaxTime = this.duration;
		#selectedTimes, selectedMessages = timesMessages.flop;
		selectedTimes.postln;
		selectedMessages.postln;
		this.changed(\selection);
	}

	selectTimesMessages { | who, selection |
		#selectedTimes, selectedMessages = timesMessages.copyRange(
			selection.minItem, selection.maxItem;
		).flop;
		selectedMinTime = selectedTimes.minItem;
		selectedMaxTime = selectedTimes.maxItem;
		this.changed(\selection, who);
	}

	updateTimesMessages { | who |
		#selectedTimes, selectedMessages = timesMessages.copyRange(
			this.findTimeIndex(selectedMinTime),
			this.findTimeIndex(selectedMaxTime)
		).flop;
		this.changed(\selection, who);
	}

	makePlayer {

	}
}