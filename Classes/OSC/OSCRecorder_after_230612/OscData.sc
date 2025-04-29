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
	// var <selectedMessages; // 5 is method!
	var <stream; // 6
	var <progressRoutine; // 7
	var <minIndex;  // 8
	var <maxIndex; // 9
	// var totalDuration; // 12 no longer used
	// var selectedDuration; // 13 no longer used
	// var totalOnsetsDuration; // 14 no longer used
	var <>header = ""; // Displayed in PresetList gui score view

	comments { ^header ? "" } // unused
	comments_ { | s | header = s } // unused

	*fromPathDialog {
		{ | p |
			this.fromPath(p.first).gui
		}.getFilePath("Click OK to select an osc data file")
	}

	*fromPathGui { | p |
		^this.fromPath(p).gui;
	}

	*multiPlay { | paths |
		var scores;
		"--------------".postln;
		"Playing scores for following paths at once:".postln;
		paths do: { | x | postln("***<" + x.name + ">***") };
		"--------------".postln;
		scores = paths collect: { | p | this.fromPathGui(p) };
		scores do: { | s |
			postln("Playing" + s + "of class" + s.class);
			s.play;
		};
	}
	*fromPath { | p | // choose class depending on file contents
		case
		{ p.isCode }{ ^OscDataScore([p]).postln }
		{ p.isOnsetCode }{ ^OscDataOnsetScore([p]).postln }
		{ p.hasTimestamps }{ ^OscData([p]).postln; }
		{ true }{ ^SnippetScore([p]).postln };
	}

	*new { | paths |
		^this.newCopyArgs(paths).init;
	}
	cloneCode {
		^this.class.newCopyArgs(paths, sourceStrings,
			parsedEntries.copyRange(timeline.minIndex, timeline.maxIndex)
			.select({ | e | e[1].interpret[0] == '/code' }),
			unparsedEntries.copyRange(timeline.minIndex, timeline.maxIndex)
			.select({ | e | e.interpret[0] == '/code' })
		).convertTimesMessages;
	}

	cloneMessages {
		^this.class.newCopyArgs(paths, sourceStrings,
			parsedEntries.copyRange(timeline.minIndex, timeline.maxIndex)
			.select({ | e | e[1].interpret[0] != '/code' }),
			unparsedEntries.copyRange(timeline.minIndex, timeline.maxIndex)
			.select({ | e | e.interpret[0] != '/code' })
		).convertTimesMessages; // .gui;
	}

	init {
		header = ""; // initialize header to reread from files.
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
		this.changed(\reread);
	}

	readSource {
		sourceStrings = paths collect: File.readAllString(_);
	}

	makeMessages {
		// split sourceStrings to message components by regexp
		// Collect interpreted time and message from each message.
		// 'MAKING MESSAGEA'.postln;
		parsedEntries = [];
		[sourceStrings, paths].flop do: this.parseString(_);
		postln("\n" ++ paths.first.name + "(" ++ paths.size ++ ")" + "parsed " + parsedEntries.size + "entries");
		this.convertTimesMessages;
		this.notifyLoaded;
	}
	notifyLoaded { this.changed(\scoreLoaded); }
	convertTimesMessages {
		if (parsedEntries.size == 0) {
			parsedEntries = sourceStrings;
			times = 1.dup(sourceStrings.size);
			messages = sourceStrings;
		}{
			#times, messages = parsedEntries.flop;
		};
		this.makeTimeline(times);
		this.convertTimes;
		timesMessages = [times, messages].flop;
	}

	makeTimeline { | argTimes | timeline =
		Timeline(this).setOnsets(argTimes);
	}
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
		header = header ++ string[..delimiters.first] ++ "\n";
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
		post(".");
	}

	checkFileType { | string, path |
		if ("//code" == string[..string.find("\n")-1]) {
			Error("File" + path + "is a code file. Use OscDataScore.").throw
		};
	}

	gui { | argName |
		var window;
		if (parsedEntries.size == 0) {
			"Cannot open a gui for OscData without entries".ok;
			^this;
		};
		window = this.br_(850, 500).vlayout(
			this.headerView,
			RangeSlider() // select a range of times
			.orientation_(\horizontal)
			.action_({ | me |
				this.changed(\timerange, me.lo, me.hi);
			})
			.mouseUpAction_({ | me |
				timeline.mapClipTime(me.lo, me.hi);
			})
			.addNotifier(this, \segment, { | n, argTimeline |
				n.listener.setSpan(*timeline.unmapTimeSpan);
				// postln("Range slider action not implemented: setSpan");
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
				.addNotifier(this, \segment, { | n, who |
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
				.addNotifier(this, \segment, { | n, who |
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
				.addNotifier(this, \segment, { | n, who |
					if (who != n.listener) {
						n.listener.value = timeline.duration;
					}
				}),
				StaticText().string_("total duration:" + timeline.totalDuration.formatTime),
				StaticText().string_("size").maxWidth_(70),
				NumberBox()
				.maxWidth_(70)
				.addNotifier(this, \segment, { | n |
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
					this.changed(\scrollItem, me.value);
				})
				// .enterKeyAction_({ | me |
				// 	"enterKeyAction for indexList".postln;
				// 	postln("indexlist value is:" + me.value);
				// 	postln("messages are" + messages[me.value]);
				// 	postln("SKIPPING ACTION!");
				// 	// this.shareSnippet(messages[me.value]);
				// })
				.keyDownAction_({ | me, key ... args |
					case
					{ key.ascii == 13 } {
						if (me.selection.size > 1) {
							timeline.indexSegment(me.selection.minItem, me.selection.maxItem);
							this.changed(\segment)
						}{
							this.changed(\sendtoself, me.value);
						}
					}
					{ key == $r } {
						this.reread;
					}
					{ key == $a } {
						timeline.selectAll;
					}
					{ key == $. } {
						Mediator.stopSynths;
					}
					{ key == $j } {
						this.changed(\item, me.value + 1 min: (me.items.size - 1));
					}
					{ key == $k } {
						this.changed(\item, me.value - 1 max: 0);
					}
					{ key == $l } {
						this.changed(\localrun, me.value);
					}
					{ key == $s } {
						this.changed(\sendtoself, me.value);
					}
					{ true } {
						me.defaultKeyDownAction(me, key, *args);
					}
				})
				.addNotifier(this, \item, { | n, index |
					// postln("OscData gui received item. index:" + index);
					n.listener.value = index;
				})
				.addNotifier(this, \segment, { | n, who |
					n.listener.items = times.copyRange(
						timeline.segmentMin, timeline.segmentMax
					).collect({ | t, i | this.formatTimeIndex(t, i) });
				}),
				ListView() // messages
				.palette_(QPalette.light
					.highlight_(Color(0.7, 1.0, 0.9))
					.highlightText_(Color(0.0, 0.0, 0.0))
				)
				.font_(Font("Monaco", 12))
				.enterKeyAction_({ | me | this.sendItemAsOsc(me.item); })
				.keyDownAction_({ | me, key ... args |
					case
					{ key == $. } {
						Mediator.stopSynths;
					}
					{ true } {
						me.defaultKeyDownAction(me, key, *args);
					}
				})
				.addNotifier(this, \segment, { | n, who |
					n.listener.items = messages.copyRange(
						timeline.segmentMin, timeline.segmentMax
					);
				})
				.addNotifier(this, \item, { | n, index |
					// postln("messages set to index:" + index);
					n.listener.value = index;
				})
				.addNotifier(this, \scrollItem, { | n, index |
					n.listener.value = index;
				})
				.addNotifier(this, \localrun, { | n, index |
					// postln("messages set to index:" + index);
					n.listener.value = index;
					"Local run not implemented. Try send to self".postln;
					// n.listener.item.postln;
					// n.listener.item.class.postln;
				})
				.addNotifier(this, \sendtoself, { | n, index |
					// postln("messages set to index:" + index);
					n.listener.value = index;
					LocalAddr().sendMsg(*n.listener.item.interpret);
				})
			),
			HLayout(
				Button().states_([["x"]]).maxWidth_(10)
				.action_({ CmdPeriod.run }),
				Button().states_([["x", Color.white, Color.red]]).maxWidth_(10)
				.action_({ "CmdPeriod.run".share }),
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
				Button().states_([["+trigger"]])
				.action_({ this.addTriggerFromUser }),
				StaticText().string_("Clone:").maxWidth_(50),
				Button().states_([["code"]])
				.action_({ | me |
					this.cloneCode;
				}),
				Button().states_([["messages"]])
				.action_({ | me |
					this.cloneMessages;
				}),
				Button().states_([["Edit"]]).action_({ this.edit }),
				Slider().orientation_(\horizontal)
				.addNotifier(this, \progress, { | n, p |
					// postln("progress: " + p);
					n.listener.value = p;
				})
			)
		);
		window.name = this.windowName(argName);
		{ this.selectAll; }.defer(0.1);
	}

	headerView {
		^TextView().maxHeight_(80).string_(header ?? { "(This score has no header)" })
		.addNotifier(this, \reread, { | n |
			// postln("Received reread from" + this + "and will refresh");
			n.listener.string = header; });
	}
	edit { paths do: Document.open(_); }
	shareSnippet { | argSnippet |
		var themessage;
		themessage = argSnippet.interpret;
		LocalAddr().sendMsg(*themessage);
		OscGroups.broadcast(themessage[0], *themessage[1..]);
	}

	name { ^this.windowName }
	windowName { | argName |
		//^argName ? this.class.name
		^PathName(paths.first).fileNameWithoutExtension;
	}

	formatTimeIndex { | t, i |
		^t.asString;
		// m = messages[timeline.segmentMin + i];
		// ^(t.asString + m.copyRange(m.indexOf($]) + 1, m.indexOf(Char.nl) - 1))
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

	selectAll { timeline.selectAll; }

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

	addTriggerFromUser {
		{ | msg |
			postln("Activating score stream trigger:" + msg.asCompileString);
			this.addTrigger(msg);
		}.inputText("test", "This osc message plays next item in list:")
	}

	addTrigger { | message |
		this.makeStreamEvent;
		stream.oscTrigger(message.asSymbol);
	}

	removeTrigger { | message | stream.removeTrigger(message) }

	makeStream {
		this.makeStreamEvent;
		// track progress both from onsets and progress routine
		// If onsets are too far apart, then progressRoutine is useful
		this.makeProgressRoutine;
	}

	stream_ { | argStream |
		stream !? {
			this.removeNotifier(stream, \started);
			this.removeNotifier(stream, \played);
			this.removeNotifier(stream, \stopped);
		};
		stream = argStream;
		this.addNotifier(stream, \started, { this.changed(\playing, true) });
		this.addNotifier(stream, \played, { | notifier, event |
			this.changed(\item, event[\index])
		});
		this.addNotifier(stream, \stopped, { this.changed(\playing, false) });

	}
	makeStreamEvent {
		postln("Making stream for" + this.name);
		this.stream = ( // convert times to dt
			index: timeline.indexPattern,
			onsets: timeline.segmentOnsets.pseq,
			dur: timeline.segmentDurations.pseq,
			message: messages.copyRange(timeline.segmentMin, timeline.segmentMax).pseq(1),
			play: this.makePlayFunc; // OscDataScore customizes this
		).asEventStream;
		^stream;
	}

	makePlayFunc { // OscDataScore customizes this
		var localaddr, oscgroupsaddr;
		localaddr = LocalAddr();
		OscGroups.enable(verbose: false); // enable silently
		oscgroupsaddr = OscGroups.sendAddress;
		^{
			var msg;
			msg = ~message.interpret;
			// postln("debugging. message:" + msg);
			// postln("message class" + ~message.class + "message:" + ~message);
			localaddr.sendMsg(*msg);
			oscgroupsaddr.sendMsg(*msg);
			this.changed(\progress, ~onsets / timeline.segmentTotalDur);
		}
	}

	isPlaying { ^stream.isPlaying }

	play { this.start }
	start {
		postln("STARTING" + this);
		// { "!!!!!!1 -------------- !!!!!!!!!!".postln; } ! 10;
		if (this.isPlaying) { ^postln("Oscdata is already playing") };
		this.makeStream; // update stream to current selection
		//: TODO: advance progress to next message when restarting!
		stream.start;
		progressRoutine.start;
	}

	makeProgressRoutine {
		var streamduration, dt, numRepeats;
		progressRoutine.stop;
		streamduration = timeline.segmentTotalDur;
		dt = streamduration / 1000 max: 0.01;
		numRepeats = (streamduration / dt);
		progressRoutine = (
			dur: dt,
			progress: (1..numRepeats).normalize.pseq(1),
			play: { this.changed(\progress, ~progress) }
		).asEventStream;
		this.changed(\progress, 0);
	}

	stop { stream.stop; progressRoutine.stop; }

	export {
		if (this.hasMessages) {
			this.exportMessages;
		}{
			this.exportCode;
		}
	}

	exportMessages {
		var exportPath;
		if (parsedEntries.size == 0) {
			"Found no entries. cannot export empty data.".ok;
			"Found no entries. cannot export empty data.".postln;
			^this;
		};
		exportPath = this.messageExportPath;
		// ("exporting messages to" + exportPath).ok;
		postln("exporting messages to" + exportPath);
		File.use(this.messageExportPath, "w", { | f |
			[timeline.segmentOnsets, this.selectedMessages].flop do: { | tm |
				var check;
				check = tm[1].removeFirstLine;
				check.postln;
				f.write(
					"\n//:--[" ++
					tm[0].asString ++
					"]" ++
					tm[1].removeFirstLine
				)
			}
		});
		"Export done".postln;
	}

	exportCode {
		var exportPath, convertedTimesMessages, t, m;
		if (parsedEntries.size == 0) {
			"Found no entries. cannot export empty data.".ok;
			"Found no entries. cannot export empty data.".postln;
			^this;
		};
		exportPath = this.codeExportPath;
		postln("Exporting code to" + exportPath);
		File.use(this.codeExportPath, "w", { | f |
			f.write("//code\n");
			f.write("Source:" + (paths collect: _.name) + "\n");
			[timeline.segmentOnsets.rotate(-1).differentiate.max(0),
				this.selectedMessages].flop do: { | e |
					var time, message;
					#time, message = e;
					f.write(format("//:--[%] ", time));
					f.write(message.interpret[1]);
				}
		});
		"Export done".postln;
	}

	selectedMessages {
		^messages.copyRange(timeline.segmentMin, timeline.segmentMax);
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
		^s.interpret[0] == '/code'
	}

	debug {
		this.selectedMessages.postln;
	}
}