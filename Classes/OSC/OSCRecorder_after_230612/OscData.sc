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
	var <stream;

	*new { | paths |
		^this.newCopyArgs(paths).init;
	}

	init {
		this.readSource;
		this.makeMessages;
		// remake player stream when selection changes:
		this.addNotifier(this, \selection, {
			if (this.isPlaying.not) { { this.makeStream }.fork };
		});
		this.selectAll;
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
		this.convertTimes;
		timesMessages = [times, messages].flop;
		// timesMessages.postln;
		// this.changed(\selection);
	}

	convertTimes { times = times - times.first; }

	parseString { | stringAndPath |
		// parse a string read from a file, in the format of //:--[timestamp] message.
		// Add all parsedEntries found to parsedEntries.
		var delimiters, entry, string, path;
		var timebeg, timeend;
		#string, path = stringAndPath;
		this.checkFileType(string, path);
		delimiters = string.findAll("\n//:--[");
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = string.copyRange(b, end);
			}{
				entry = string.copyRange(b, string.size - 1)
			};
			timebeg = entry.find(":--[");
			timeend = entry.find("]", 4);
			parsedEntries = parsedEntries add: [
				entry.copyRange(timebeg + 4, timeend - 1).interpret,
				entry.copyRange(timeend + 2, if (end.notNil) { entry.size - 2 } { entry.size - 1 })
 			];
		};
		post(" . ");
	}

	checkFileType { | string, path |
		if ("//code" == string[..string.find("\n")-1]) {
			Error("File" + path + "is a code file. Use OscDataScore.").throw
		};
	}

	gui {
		this.br_(800, 500).vlayout(
			RangeSlider() // select a range of times
			.orientation_(\horizontal)
			.action_({ | me |
				selectedMinTime = me.lo * this.duration;
				selectedMaxTime = me.hi * this.duration;
				this.updateTimesMessages(me);
			})
			.addNotifier(this, \selection, { | n |
				// postln("rangeslider new update - selection");
				n.listener.setSpan(
					selectedMinTime / this.duration,
					selectedMaxTime / this.duration
				)
			}),
			HLayout(
				StaticText().string_("beginning"),
				NumberBox()
				.maxWidth_(100)
				.decimals_(3)
				.action_({ | me |
					selectedMinTime = me.value.clip(0, selectedMaxTime);
					me.value = selectedMinTime;
					this.updateTimesMessages(me);
				})
				.addNotifier(this, \selection, { | n, who |
					if (who != n.listener) { n.listener.value = selectedMinTime; }
				}),
				StaticText().string_("end").maxWidth_(50),
				NumberBox()
				.maxWidth_(100)
				.decimals_(3)
				.action_({ | me |
					selectedMaxTime = me.value.clip(selectedMinTime, this.duration);
					me.value = selectedMaxTime;
					this.updateTimesMessages(me);
				})
				.addNotifier(this, \selection, { | n, who |
					if (n.listener != who) { n.listener.value = selectedMaxTime; }
				}),
				StaticText().string_("duration"),
				NumberBox()
				.maxWidth_(100)
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
				StaticText().string_("size"),
				NumberBox()
				.maxWidth_(70)
				.addNotifier(this, \selection, { | n |
					n.listener.value = selectedTimes.size;
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
					if (who != n.listener) { n.listener.items = selectedTimes; };
				}),
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
			),
			HLayout(
				Button().states_([["x"]]).maxWidth_(20)
				.action_({ this.debug }),
				CheckBox().string_("Play")
				.maxWidth_(50)
				.action_({ | me |
					if (me.value) { this.start; } { this.stop; };
				})
				.addNotifier(this, \playing, { | n, status |
					n.listener.value = status;
				}),
				Button().states_([["->code"]])
				.action_({ this.findNextCode }),
				Button().states_([["Reset player"]])
				.action_({ this.resetStream }),
				Button().states_([["Reread files"]])
				.action_({ this.reread }),
				StaticText().string_("Export:").maxWidth_(50),
				Button().states_([["messages"]])
				.action_({ | me |
					"export not implemented".postln;
				}),
				Button().states_([["code"]])
				.action_({ | me |
					"export not implemented".postln;
				}),
				Button().states_([["both"]])
				.action_({ | me |
					"export not implemented".postln;
				})
			),
			Slider().orientation_(\horizontal)
		);
		{ this.selectAll; }.defer(0.1);
	}

	findNextCode {
		var found, index;
		found = selectedMessages detect: { | m |
			m.interpret.first === '/code';
		};
		index = selectedMessages indexOf: found;
		selectedMinTime = times[index];
		selectedMaxTime = selectedMinTime max: selectedMaxTime;
		this.updateTimesMessages(this);
	}

	findTimeIndex { | time | ^times indexOf: (times detect: { | t | t >= time }); }

	mapTime { | index | ^times[index] / this.duration; }

	duration { ^times.last }

	selectAll {
		selectedMinTime = 0;
		selectedMaxTime = this.duration;
		#selectedTimes, selectedMessages = timesMessages.flop;
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
		selectedMinTime = selectedTimes.minItem;
		selectedMaxTime = selectedTimes.maxItem;
		this.changed(\selection, who);
	}

	reread { this.init }

	resetStream {
		var restart = false;
		if (this.isPlaying) { restart = true; this.stop; };
		this.makeStream;
		if (restart) { this.start; }
	}
	makeStream { // Thu 22 Jun 2023 16:40 Test version
		stream = (
			dur: selectedTimes.differentiate.rotate(-1).putLast(0).pseq(1),
			message: selectedMessages.pseq(1),
			play: this.makePlayFunc; // OscDataScore customizes this
		).asEventStream;
		this.addNotifier(stream, \stopped, { this.changed(\playing, false) });
		this.addNotifier(stream, \started, { this.changed(\playing, true) });
	}

	makePlayFunc { // OscDataScore customizes this
		var localaddr, oscgroupsaddr;
		localaddr = LocalAddr();
		OscGroups.enable(verbose: false); // enable silently
		oscgroupsaddr = OscGroups.sendAddress;
		^{
			var msg;
			msg = ~message.interpret;
			localaddr.sendMsg(*msg);
			oscgroupsaddr.sendMsg(*msg);
		}
	}

	debug {
		var timeIndex, prevTime;
		selectedMinTime.postln;
		selectedTimes.differentiate.postln;
		"time index is: ".post;
		timeIndex = this.findTimeIndex(selectedTimes.first).postln;
		prevTime = times[timeIndex];
		postln("prevTime is:" + prevTime);
	}

	isPlaying { ^stream.isPlaying }

	start {
		if (this.isPlaying) { ^postln("Oscdata is already playing") };
		stream ?? { this.makeStream };
		stream.start;
	}

	stop { stream.stop; }
}