// 木 15  5 2025 15:42
// Record and replay typing actions, with timestamps

RecordTyping : NamedSingleton2 {
	classvar <>charMsg = \ascii;
	var <lines, <currentLine, <routine;

	init {
		lines ?? { this.newLine }; // start with a new line
		this.activate;
		CmdPeriod add: this;
	}
	doOnCmdPeriod { routine = nil }

	activate {
		Tsr.doOnType({ | char |
			currentLine = currentLine add: [Main.elapsedTime, char];
		}, name);
		OSC.add(\cret, { this.newLine }, name);
	}

	newLine {
		currentLine = List();
		lines = lines add: currentLine;
	}

	deactivate {
		Tsr.undoOnType(name);
		OSC.remove(\cret, name);
	}

	replay { | from = 0, to |
		var timesChars;
		(lines.size < 1).if { ^"There are no lines to replay".postln; };
		to ?? { to = lines.size - 1 };
		to = to.clip(from, lines.size - 1);
		lines[(from..to)] do: { | tc |
			timesChars = timesChars add: tc.array;
		};
		this.play(timesChars.flatten2);
	}

	replay1 { | n = 0 |
		((lines@n).size == 0).if { ^postln("Cannot play an emptly line") };
		this.play(lines[n].array);
	}

	play { | timesChars |
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