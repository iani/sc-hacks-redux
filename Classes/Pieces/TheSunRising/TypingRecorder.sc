// 木 15  5 2025 15:42
// Record and replay typing actions, with timestamps

TypingRecorder : NamedSingleton2 {
	classvar <>charMsg = \ascii;
	classvar <>custom;
	var <>lines, <currentLine;
	var players; // dict with multiple player tasks
	//  // obsolete! Replaced by task. Kept for backward compatibility:
	var <routine;

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
		^allch flatten: 2
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
}