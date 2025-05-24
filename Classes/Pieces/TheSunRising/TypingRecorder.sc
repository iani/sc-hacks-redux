// 木 15  5 2025 15:42
// Record and replay typing actions, with timestamps

TypingRecorder : NamedSingleton2 {
	classvar <>charMsg = \ascii;
	classvar <>custom;
	var <>lines, <currentLine;
	var players; // dict with multiple player tasks
	//  // obsolete! Replaced by task. Kept for backward compatibility:
	var <routine;

	*initClass {
		StartUp add: { this.init; };
	}

	*init { this.makeBaseDirectory; }

	*makeBaseDirectory {
		if (File.exists(this.baseDirectory).not) {
			File.mkdir(this.baseDirectory)
		}
	}

	*baseDirectory { ^Platform.userAppSupportDir +/+ "TypingRecorder"; }

	init {
		lines ?? { this.newLine }; // start with a new line
		this.activate;
		CmdPeriod add: this;
		custom ?? { | ascii | ascii };
	}
	doOnCmdPeriod { routine = nil }

	activate {
		Tsr.doOnType({ | char |
			currentLine = currentLine add: [Clock.seconds, char];
		}, name);
		OSC.add(\cret, { this.newLine }, name);
	}

	newLine {
		currentLine = List();
		lines = lines add: currentLine;
		this.changed(\newLine);
	}

	deactivate {
		Tsr.undoOnType(name);
		OSC.remove(\cret, name);
	}

	//============================================================
	// PLAYING
	// Play an entire verse.
	playVerse { | index = 0, actionFunc, key = \default, filterFunc, repeats = 1, numChars |

	}
	// play characters from-to from the entire typing.
	playChars { | from = 0, to, actionFunc, key = \default, filterFunc |

	}

	play { | playFunc, dur = 10, key = \default, filterFunc, from = 0, to, numChars |
		var charList, player;
		charList = this.allChars;
		from = from.clip(0, charList.size - 1);
		numChars ?? { numChars = charList.size - 1 };
		numChars = numChars.clip(from + 1, charList.size - 1);
		player = this.getPlayer(key);
		player.data = charList;
		player.playFunc = playFunc;
		player.filterFunc = filterFunc ?? {{ | ... args | args }};
		player.dur = dur;
		player.start;
	}

	charCount { | verseNum |
		^this.allChars.size;
	}

	getPlayer { | key = \default |
		var player;
		player = this.players[key];
		player ?? {
			player = TypingPlayer(this, key);
			players[key] = player;
		};
		^player
	}

	players { ^players ?? { players = IdentityDictionary() } }

	allChars {
		var allch;
		lines do: { | l | allch = allch add: l.array };
		^allch.flatten
	}
	//============================================================
	// replay, replay1, simplePlay is replaced by new scheme:
	// play, using TypingPlayer on 22  5 2025 11:43
	replay { | from = 0, to |
		var timesChars;
		(lines.size < 1).if { ^"There are no lines to replay".postln; };
		to ?? { to = lines.size - 1 };
		to = to.clip(from, lines.size - 1);
		lines[(from..to)] do: { | tc |
			timesChars = timesChars add: tc.array;
		};
		this.simplePlay(timesChars.flatten2);
	}

	replay1 { | n = 0 |
		((lines@n).size == 0).if { ^postln("Cannot play an emptly line") };
		this.simplePlay(lines[n].array);
	}

	simplePlay { | timesChars |
		var times, chars, dtimes;
		timesChars ?? { ^"RecordTyping refuses to play an empty array" };
		#times, chars = timesChars.flop;
		dtimes = times.differentiate;
		dtimes[0] = 0;
		this.makeRoutine(dtimes, chars);
	}

	makeRoutine { | dtimes, chars |
		routine ?? { routine.stop; };
		routine = fork {
			dtimes do: { | dt, i |
				dt.wait;
				// postln("playing" + chars[i]);
				this.changed(charMsg, chars[i]);
				/*

				this.changed(charMsg, *custom.(chars[i]).asArray);
				*/
				this.changed(charMsg, chars[i]);
			}
		}
	}
	// actions interface:
	addAction { | action, key = \default |
		key.addNotifier(this, charMsg, action);
	}

	removeAction { | key = \default |
		key.removeNotifier(this, charMsg);
	}

	post {
		this.addAction({ | n, char |
			postln("recorder received" + char);
		}, \post)
	}

	undoPost { this.removeAction(\post); }

	gdplay {
		this.addAction({ | n, char |
			// postln("debugging gdplay. char is:" + char);
			(instrument: \pinch, freq: char*3, ffreq: char / 3).play;
			(instrument: \pinch, freq: char*5, ffreq: char / 5).play;
		}, \gdplay);

	}
	undoGdplay { this.removeAction(\gdplay); }

	// --------- Saving and loading lists --------
	// Save current list in file named afer name + key + timestamp
	save { | key |
		var filename;
		key ?? { key = name };
		filename = format("%_%_%.scd", name, key, Date.localtime.stamp);
		postln("Saving lines of" + this + "to" + filename);
		lines.writeArchive(this.baseDirectory +/+ filename);
		filename.writeArchive(this.lastSavedPathLocation);
	}

	baseDirectory { ^this.class.baseDirectory }

	loadLast { | key |
		var filename, newlines;
		filename = this.lastSavedPath;
		if (filename.isNil or: {
			File.exists(filename = this.baseDirectory +/+ filename).not }) {
			"Could not find file at".postln;
			filename.postln;
			"Trying load from GUI".postln;
			this.loadFromGui;
		}{
			// filename = this.baseDirectory +/+ filename;
			postln("Reading last saved file:");
			filename.postln;
			newlines = Object.readArchive(filename);
			if (newlines.size == 0) {
				"Refusing to receive 0 lines.".postln;
			}{
				lines = newlines;
				this.postLoadStats;
			}
		};
	}

	lastSavedPath {
		var lspl, lastSavedPath;
		lspl = this.lastSavedPathLocation;
		if (lspl.isNil or: { File.exists(lspl).not })
		{
			"TypingRecorder could not locate last saved file path".postln;
			"No file found at:".postln;
			lspl.postln;
			^nil;
		}{
			lastSavedPath = Object.readArchive(lspl);
			postln("Last saved path is" + lastSavedPath);
			^lastSavedPath;
		}
	}

	lastSavedPathLocation { ^this.baseDirectory +/+ "lastsaved.scd" }

	pathsKey { | key = \default | ^(name ++ "_" ++ key).asSymbol }

	loadFromGui {
		var window, listview, paths;
		paths = (TypingRecorder.baseDirectory +/+ "").entriesMatchingScd;
		window = \typingRecorder.hlayout(
			listview = ListView();
		).bounds_(Rect(600, 200, 400, 400));
		window.name = "Select a list file";
		listview.items = paths collect: _.fileName;
		listview.hiliteColor = Color(0.5, 0.9, 0.8);
		listview.enterKeyAction = { | me |
			postln("Loading" + me.item);
			lines = Object readArchive: paths[me.value];
			this.postLoadStats;
		}
	}

	postLoadStats {
		postln("read" + lines.size + "lines" + "containing a total of"
			+ this.allChars.size + "chars. Duration is:"
			+ this.duration.minsec + "minutes:seconds"
		);
	}

	duration { ^this.times.sum; }
	times { ^this.allChars.flop[0].differentiate[0] = 0; }
}