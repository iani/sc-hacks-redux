// 木 15  5 2025 10:40
// Incorporate OnChange
// Redo of TypingActions

TypingListener {
	classvar <>verbose = false; // for debugging;
	var onChange, <tmp, <cnt;
	// var <dist, <currIdx, <sumscores, <maxitem;

	*showTypingListener { this.new }

	*new { ^super.new.init; }

	init { // verseExraction -> extractVerse
		tmp = "";
		cnt = 0;
		onChange = OnChange({ | index |
			// postln("OnChange runs Tsr.verse with index" + index);
			Tsr.verse(index, TsrPoem.verses[index], TsrPoem.incipits[index]);
		});
		ShowTyping();
	}

	processKeyboardInput { | charNum, cocoaModifiers, unicode, keycode, key |
		var char;
		User.sendToAll(\char, charNum, cocoaModifiers, unicode);
		char = charNum.asAscii;
		this.processTypeInput(char, unicode ? 0);
		this.guessVerse;
	}

	processTypeInput { | char, unicode |
		// "processing type input".postln;
		(unicode == 13).if {
			Char.nl.asString.post;
			cnt = 0;
			tmp = "";
			User.sendToAll(\cret);
			^this;
		};
		(char.isAlpha or: { char.isPunct } or: { char.isSpace }).if {
			char.post;
			User.sendToAll(\tsrchar, char.ascii); // NEVER SEND CHAR OVER OSC!
			tmp = tmp ++ char.asString;
		}
	}

	guessVerse {
		var distances, minDistance, minIndex;
		distances = TsrPoem.verses collect: { | v | v editDistance: tmp };
		verbose.if { distances.postln; };
		minIndex = distances.minIndex;
		minDistance = distances[minIndex];
		(minDistance <= 10).if { onChange.check(minIndex); }
	}
}