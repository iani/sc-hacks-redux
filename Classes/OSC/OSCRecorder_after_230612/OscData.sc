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
	var <unparsedEntries; // the entries as read from file. For export! // NOT USED?
	var <times, <messages; // times and messages obtained from parsedEntries
	// ============= Store state from user selection for simpler updates ==============
	var <timeline; // handle onsets and durations!
	var <oscgroupsAddr; // 11 used by sendItemAsOsc
	var <localAddr; // 10 used by sendItemAsOsc
	// TODO: Cleanup many of these, below - that are no longer used.
	var <selectedMinTime = 0; // 1
	var <selectedMaxTime = 0; // 2 section selected
	var <timesMessages; // 3,
	var <selectedTimes; // 4
	var <selectedMessages; // 5
	var <stream; // 6
	var <progressRoutine; // 7
	var <minIndex;  // 8
	var <maxIndex; // 9
	// var totalDuration; // 12 no longer used
	// var selectedDuration; // 13 no longer used
	// var totalOnsetsDuration; // 14 no longer used

	*currentDocumentGui {
		Document.current.path.postln;
	}

	cloneCode {
		^this.class.newCopyArgs(paths, sourceStrings,
			parsedEntries.copyRange(timeline.minIndex, timeline.maxIndex)
			.select({ | e | e[1].interpret[0] == '/code' }),
			unparsedEntries.copyRange(timeline.minIndex, timeline.maxIndex)
			.select({ | e | e.interpret[0] == '/code' })
		).convertTimesMessages.gui;
	}

	cloneMessages {
		^this.class.newCopyArgs(paths, sourceStrings,
			parsedEntries.copyRange(timeline.minIndex, timeline.maxIndex)
			.select({ | e | e[1].interpret[0] != '/code' }),
			unparsedEntries.copyRange(timeline.minIndex, timeline.maxIndex)
			.select({ | e | e.interpret[0] != '/code' })
		).convertTimesMessages.gui; // .gui;
	}

	*new { | paths |
		^this.newCopyArgs(paths).init;
	}

	init {
		this.readSource;
		this.makeMessages;
		localAddr = NetAddr.localAddr;
		OscGroups.enable(verbose: false);
		oscgroupsAddr = OscGroups.sendAddress;
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
		this.convertTimesMessages;
	}

	convertTimesMessages {
		if (parsedEntries.size == 0) {
			Error("Found no entries. cannot clone empty data.").throw;
		};
		#times, messages = parsedEntries.flop;
		this.makeTimeline(times);
		// times.postln;
		this.convertTimes;
		timesMessages = [times, messages].flop;
	}

	makeTimeline { | argTimes | timeline = Timeline.fromOnsets(argTimes); }
	convertTimes {
		times = times - times.first;
		// totalDuration = times.last;
		// selectedDuration = times.last;
		// totalOnsetsDuration = times.last;
	}

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
				// entry.copyRange(timeend + 2, if (end.notNil) { entry.size - 2 } { entry.size - 1 })
				entry.copyRange(timebeg - 2, if (end.notNil) { entry.size - 2 } { entry.size - 1 })
 			];
			unparsedEntries = unparsedEntries add: entry;
		};
		post(" . ");
	}

	checkFileType { | string, path |
		if ("//code" == string[..string.find("\n")-1]) {
			Error("File" + path + "is a code file. Use OscDataScore.").throw
		};
	}

	guiTested {
		"Will make gui later".postln;
		postln("onsets" + timeline.onsets);
		postln("durations" + timeline.durations);
		postln("timeline duration" + timeline.duration);
		postln("timeline maxIndex" + timeline.maxIndex);

	}

	gui { | paths |
		var window;
		window = this.br_(850, 500).vlayout(
			RangeSlider() // select a range of times
			.orientation_(\horizontal)
			.action_({ | me |
				this.changed(\timerange, me.lo, me.hi);
			})
			.mouseUpAction_({ | me |
				timeline.mapClipTime(me.lo, me.hi);
			})
			.addNotifier(timeline, \segment, { | n, argTimeline |
				n.listener.setSpan(*timeline.unmapTimeSpan)
			}),
			HLayout(
				StaticText().string_("1st onset"),
				NumberBox()
				.maxWidth_(100)
				.decimals_(3)
				.action_({ | me |
					me.value =  me.value.clip(0, timeline.totalDuration);
					timeline.clipMinTime(me.value);
				})
				.addNotifier(this, \timerange, { | n, lo, hi |
					n.listener.value = timeline.mapTime(lo);
				})
				.addNotifier(timeline, \segment, { | n, who |
					n.listener.value = timeline.minTime;
				}),
				StaticText().string_("last onset"), //.maxWidth_(50),
				NumberBox()
				.maxWidth_(100)
				.decimals_(3)
				.action_({ | me |
					me.value = me.value.clip(timeline.minTime, timeline.totalDuration);
					timeline.clipMaxTime(me.value);
				})
				.addNotifier(this, \timerange, { | n, lo, hi |
					n.listener.value = timeline.mapTime(hi);
				})
				.addNotifier(timeline, \segment, { | n, who |
					n.listener.value = timeline.maxTime;
				}),
				StaticText().string_("duration").maxWidth_(90),
				NumberBox()
				.maxWidth_(100)
				.decimals_(3)
				.action_({ | me |
					me.value_(me.value.clip(0, timeline.maxDuration));
					timeline.clipMaxTime(me.value);
				})
				.addNotifier(timeline, \segment, { | n, who |
					if (who != n.listener) {
						n.listener.value = timeline.duration;
					}
				}),
				StaticText().string_("total duration:" + timeline.totalDuration),
				StaticText().string_("size").maxWidth_(70),
				NumberBox()
				.maxWidth_(70)
				.addNotifier(timeline, \segment, { | n |
					n.listener.value = timeline.segmentSize;
				});
			),
			HLayout(
				ListView() // times
				.maxWidth_(230)
				.palette_(QPalette.light
					.highlight_(Color(1.0, 0.9, 0.7))
					.highlightText_(Color(0.0, 0.0, 0.0))
				)
				.font_(Font("Monaco", 12))
				.selectionMode_(\contiguous)
				.selectionAction_({ | me |
					this.changed(\item, me.value);
				})
				.keyDownAction_({ | me, key ... args |
					case
					{ key.ascii == 13 } {
						timeline.indexSegment(
							me.selection.minItem, me.selection.maxItem
						) }
					{ key == $r } {
						this.reread;
					}
					{ key == $a } {
						timeline.selectAll;
					}
					{ true } {
						me.defaultKeyDownAction(me, key, *args);
					}
				})
				.addNotifier(this, \item, { | n, index |
					n.listener.value = index;
				})
				.addNotifier(timeline, \segment, { | n, who |
					n.listener.items = times.copyRange(
						timeline.segmentMin, timeline.segmentMax
					);
				}),
				ListView() // messages
				.palette_(QPalette.light
					.highlight_(Color(0.7, 1.0, 0.9))
					.highlightText_(Color(0.0, 0.0, 0.0))
				)
				.font_(Font("Monaco", 12))
				.enterKeyAction_({ | me | this.sendItemAsOsc(me.item); })
				// .keyDownAction
				.addNotifier(timeline, \segment, { | n, who |
						n.listener.items = messages.copyRange(
						timeline.segmentMin, timeline.segmentMax
					);
				})
				.addNotifier(this, \item, { | n, index |
					// postln("messages set to index:" + index);
					n.listener.value = index;
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
				StaticText().string_("Clone:").maxWidth_(50),
				Button().states_([["code"]])
				.action_({ | me |
					this.cloneCode;
				}),
				Button().states_([["messages"]])
				.action_({ | me |
					this.cloneMessages;
				}),
				Button().states_([["Export"]])
				.action_({ | me |
					this.export;
				})
			),
			Slider().orientation_(\horizontal)
			.addNotifier(this, \progress, { | n, p |
				// postln("progress: " + p);
				n.listener.value = p;
			})
		);
		if (paths.size == 1) { window.name = paths[0]; };
		{ this.selectAll; }.defer(0.1);
	}

	selectedTimesItems { ^selectedTimes } // OscDataScore adds durations!
	/*
	selectIndexRange { | argMinIndex, argMaxIndex, view |
			minIndex = argMinIndex;
			maxIndex = argMaxIndex;
			#selectedTimes, selectedMessages = timesMessages.copyRange(
					minIndex, maxIndex;
			).flop;
			selectedMinTime = selectedTimes.minItem;
			selectedMaxTime = selectedTimes.maxItem;
			this.changed(\selection, view);
	}
	*/

	sendItemAsOsc { | string | // OscDataScore prepends '/code' here
		var msg;
		msg = string.interpret;
		localAddr.sendMsg(*msg);
		oscgroupsAddr.sendMsg(*msg);
	}
	findNextCode {
		var theSelectedMessages, found, index;
		theSelectedMessages = messages.copyRange(timeline.minIndex, timeline.maxIndex);
		found = theSelectedMessages detect: { | m | m.interpret.first === '/code'; };
		index = theSelectedMessages indexOf: found;
		this.changed(\item, index);
	}

	findTimeIndex { | time |
		time = time.clip(0, this.totalDuration);
		postln("findIndex. time:" + time + "times" + times);
		postln("detected" + (times detect: { | t | t >= time }));
		^times indexOf: (times detect: { | t | t >= time });

	}

	// mapTime { | index | ^times[index] / totalDuration; }

	selectAll { timeline.selectAll }

	/*
	selectTimesMessages { | who, selection |
		minIndex = selection.minItem;
		maxIndex = selection.maxItem;
		#selectedTimes, selectedMessages = timesMessages.copyRange(
			minIndex, maxIndex;
		).flop;
		selectedMinTime = selectedTimes.minItem;
		selectedMaxTime = selectedTimes.maxItem;
		this.changed(\selection, who);
	}
	*/

	/*
	updateTimesMessages { | who |
		minIndex = this.findTimeIndex(selectedMinTime);
		maxIndex = this.findTimeIndex(selectedMaxTime);
			postln("minIndex" + minIndex + "minIndex class" + minIndex.class +
		"maxIndex" + maxIndex + "maxIndex class" + maxIndex.class);
		this.updateSelectionTimes;
		this.changed(\selection, who);
	}
	*/

	/*
	updateSelectionTimes {
		#selectedTimes, selectedMessages = timesMessages.copyRange(
			minIndex, maxIndex
		).flop;
		selectedMinTime = selectedTimes.minItem;
		selectedMaxTime = selectedTimes.maxItem;
		this.updateSelectedDuration;
	}
	*/

	updateSelectedDuration {
		// selectedDuration = timeline.segmentDuration;
	}
	reread { this.init }

	resetStream {
		var restart = false;
		if (this.isPlaying) { restart = true; this.stop; };
		this.makeStream;
		if (restart) { this.start; }
	}
	makeStream {
		stream = ( // convert times to dt
			dur: timeline.segmentDurations.pseq,
			message: messages.copyRange(timeline.minIndex, timeline.maxIndex).pseq(1),
			play: this.makePlayFunc; // OscDataScore customizes this
		).asEventStream;
		this.addNotifier(stream, \stopped, { this.changed(\playing, false) });
		this.addNotifier(stream, \started, { this.changed(\playing, true) });
		this.makeProgressRoutine;
	}

	makePlayFunc { // OscDataScore customizes this
		var localaddr, oscgroupsaddr;
		localaddr = LocalAddr();
		OscGroups.enable(verbose: false); // enable silently
		oscgroupsaddr = OscGroups.sendAddress;
		^{
			var msg;
			msg = ~message.interpret;
			// postln("message class" + ~message.class + "message:" + ~message);
			localaddr.sendMsg(*msg);
			oscgroupsaddr.sendMsg(*msg);
		}
	}

	isPlaying { ^stream.isPlaying }

	start {
		if (this.isPlaying) { ^postln("Oscdata is already playing") };
		stream ?? { this.makeStream };
		stream.start;
		progressRoutine.start;
	}

	makeProgressRoutine {
		var streamduration, dt;
		progressRoutine.stop;
		streamduration = timeline.segmentTotalDur;
		// streams of duration < 1 sec will not display properly;
		dt = streamduration / 100 max: 0.01;
		progressRoutine = (
			dur: dt,
			progress: (1..100).normalize.pseq(1),
			play: { this.changed(\progress, ~progress) }
		).asEventStream;
		this.changed(\progress, 0);
	}

	stop { stream.stop; progressRoutine.stop; }

	export {
		// "WARNING: Cannot export as code yet. Exporting as messages".postln;
		// 	this.exportMessages;
		// "WARNING: Cannot export as code yet. Exported as messages".postln;
		if (this.hasMessages) {
			this.exportMessages;
		}{
			// "Cannot export as code yet. Exporting as messages".postln;
			this.exportCode;
			// "Cannot export as code yet. Exported as messages".postln;
		}
	}

	exportMessages {
		var exportPath;
		exportPath = this.messageExportPath;
		postln("exporting messages to" + exportPath);
		File.use(this.messageExportPath, "w", { | f |
			timesMessages do: { | tm |
				f.write(
					"\n//:--[" ++
					tm[0].asString ++
					"]\n" ++
					tm[1]
				)
			}
		});
		"Export done".postln;
	}

	exportCode {
		var exportPath, convertedTimesMessages, t, m;
		exportPath = this.codeExportPath;
		// #t, m = timesMessages.flop;
		// convertedTimesMessages = [t.differentiate, m].flop;
		postln("Exporting code to" + exportPath);
		File.use(this.codeExportPath, "w", { | f |
			f.write("//code\n");
			 [selectedTimes.rotate(-1).differentiate.max(0), selectedMessages].flop do: { | e |
				var time, message;
				#time, message = e;
				f.write(format("//:--[%] ", time));
				f.write(message.interpret[1]);
			}
			// unparsedEntries.size.postln;
			// unparsedEntries.class.postln;
			// unparsedEntries.first.postln;
		});
		// postln("now closing" + f);
		// f.close;
		// });
		"Export done".postln;
	}


	messageExportPath { ^this.exportPath("oscmessages") }

	codeExportPath { ^this.exportPath("code") }
	exportPath { | folder = "oscmessages" |
		^Platform.recordingsDir +/+ "exports" +/+ folder +/+
		(Date.getDate.stamp ++ ".scd");
	}

	hasMessages {
		^messages.detect({ | m |
			this.isCodeMessage(m).not;
		}).notNil
	}

	isCodeMessage { | s |
		^s[2..8] == "'/code'"
	}

	debug {
		parsedEntries.first[1].class.postln;
		parsedEntries.first[1].interpret[1].postln;
		messages.first.interpret[1].postln;
	}
}