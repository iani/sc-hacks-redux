// 木 15  5 2025 10:40
// Incorporate OnChange
// Redo of TypingActions

TypingListener {
	classvar <>verbose = false; // for debugging;
	var onChange, <tmp, <cnt;
	// var <dist, <currIdx, <sumscores, <maxitem;

	*new { ^super.new.onTyping; }

	onTyping { // verseExraction -> extractVerse
		tmp = "";
		cnt = 0;
		onChange = OnChange({ | index |
			Tsr.verse(index, ~verses[index], ~incipits[index]);
		});

		Tsr.doOnType({ |ascii, cocoaModifiers, unicode, keycode, key|
			var char;
			char = ascii.asAscii;
			this.processTypeInput(char, unicode ? 0);
			this.playSound(char);
			this.guessVerse;
		}, key: \gdmusic);
		ShowTyping();
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

	playSound { | char |
			(instrument: \pinch, freq: char.ascii*2, ffreq: char.ascii / 2).play;
			(instrument: \pinch, freq: char.ascii*4, ffreq: char.ascii / 4).play;
	}

	guessVerse {
		var distances, minDistance, minIndex;
		distances = ~verses collect: { | v | v editDistance: tmp };
		minIndex = distances.minIndex;
		minDistance = distances[minIndex];
		(minDistance <= 10).if { onChange.check(minIndex); }
	}
}