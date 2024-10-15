/* 10 Sep 2023 21:04
Convert stuff into a Chord.
Experimental.

Testing:

"c:c^5/4:c^3/2".asChord;
"c:c^5/4:c^3/2".asChord.midi; // midi ignores partial overtone specs
"c:c^5/4:c^3/2".asChord.freqs;
['c', 'e', 'g'].asChord.midi;

["c:g:b", "d:f:a"].pseq.asChord;
a = ["c:g:b", "d:f:a"].pseq.asChord;
b = a.asStream;
b.next.freqs;
*/

// each element should be a note spec: ...
+ Array { asChord { ^Chord(*this) } }

// format; $: separates notes. Examples:
// "c:g:e", "c:gf:e1", "C1:g:e"
// "c:c^5/4:c^3/2"
+ String {
	chord { ^this.asChord }
	freqs { ^this.chordfreqs }
	chordfreqs { ^this.asChord.freqs }
	asChord {
		^Chord(*this.split($:))
	}

	/> { | duration, playfunc = \default |
		// playfunc is obtained from template. see folder ChordPlayers
		playfunc.playChord(this.asChord, duration);
	}
}

+ Pattern {
	chords { ^this.asChord(this) }
	asChord { ^Pcollect({ | c | c.asChord }, this);}
	freqs { ^Pcollect({ | c | c.asChord.freqs }, this);}
	// ???: untested:
	midi { ^Pcollect({ | c | c.asChord.midi }, this);}
}

+ Pcollect {
	asChord { ^this }
}
//: ====================

// ???????? Maybe obsolete?
+ Symbol {
	playChord { | freqs, duration |
		// playfunc is obtained from template. see folder ChordPlayers
		ChordPlayer.play(this, freqs, duration);
	}
}
