/* 10 Sep 2023 21:04
Convert stuff into a Chord.
Experimental.

Testing:

"c:c^5/4:c^3/2".asChord;
"c:c^5/4:c^3/2".asChord.freqs;
*/

// each element should be a note spec: ...
+ Array { asChord { ^Chord(*this) } }

// format; $: separates notes. Examples:
// "c:g:e", "c:gf:e1", "C1:g:e"
// "c:c^5/4:c^3/2"
+ String {
	asChord {
		^Chord(*this.split($:))
	}

	/> { | duration, playfunc = \default |
		// playfunc is obtained from template. see folder ChordPlayers
		playfunc.playChord(this.asChord, duration);
	}
}

+ Symbol {
	playChord { | freqs, duration |
		// playfunc is obtained from template. see folder ChordPlayers
		ChordPlayer.play(this, freqs, duration);
	}
}