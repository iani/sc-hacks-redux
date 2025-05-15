// 木 15  5 2025 10:40
// Incorporate OnChange
// Redo of TypingActions

TypingListener {
	var onChange, <tmp, <cnt;
	// var <dist, <currIdx, <sumscores, <maxitem;

	*new { ^super.new.onTyping; }

	onTyping { // verseExraction -> extractVerse
		tmp = "";
		cnt = 0;
		onChange = OnChange({ | index |
			// postln(~verses[index]);
			Tsr.verse(index, ~verses[index], ~incipits[index]);
		});

		Tsr.doOnType({ |ascii, cocoaModifiers, unicode, keycode, key|
			var char;
			char = ascii.asAscii;
			this.processTypeInput(char, unicode);
			this.guessVerse;
			this.playSound(char);
		}, key: \gdmusic);
	}

	processTypeInput { | char, unicode |
			case
			{ unicode == 13 }{ // ascii code for Char.ret
				Char.nl.asString.post;
				cnt = 0;
				tmp = "";
				User.sendToAll(\cret);
			}
			{ (char.isAlpha || char.isPunct || char.isSpace) }{
				char.post;
				tmp = tmp ++ char.asString.last;
			};
	}

	playSound { | char |
			(instrument: \pinch, freq: char.ascii*2, ffreq: char.ascii / 2).play;
			(instrument: \pinch, freq: char.ascii*4, ffreq: char.ascii / 4).play;
	}
	// disabled to fix error
	guessVerse {
		var distances, minDistance, minIndex;
		distances = ~verses collect: { | v | v editDistance: tmp };
		minIndex = distances.minIndex;
		minDistance = distances[minIndex];
		(minDistance <= 10).if { onChange.check(minIndex); }
	}
}