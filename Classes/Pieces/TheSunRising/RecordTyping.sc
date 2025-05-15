// 木 15  5 2025 15:42
// Record and replay typing actions, with timestamps

RecordTyping {
	classvar tsrKey = \recordTyping;
	var <lines, <currentLine, <routine;

	*new { ^super.new.init }

	init {
		this.newLine; // start with a new line
		CmdPeriod add: this;
	}
	doOnCmdPeriod { routine = nil }

	activate {
		Tsr.doOnType({ | char |
			currentLine = currentLine add: [Main.elapsedTime, char];
		}, tsrKey);
		OSC.add(\cret, { this.newLine });
	}

	newLine {
		lines = lines add: currentLine;
		currentLine = [];
	}

	deactivate {
		Tsr.undoOnType(tsrKey);
		OSC.remove(\cret);
	}

	replay { | from = 0, to |
		to ?? { to = lines.size - 1 };
		to = to.clip(from, lines.size - 1);
	}

	replay1 { | n = 0 |
		this.play(lines[[]])
	}

	play { | timesChars |
		var times, chars, dtimes;
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
				this.changed(\ascii, chars[i]);
			}
		}
	}
}