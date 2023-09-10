/* 10 Sep 2023 21:01
Experimental.
Playing chords in various ways.
Here a basic first start.
*/

ChordPlayer {
	var funcspec, freqs, duration;
	*play { | playfunc, freqs, duration = 1 |
		^this.newCopyArgs(playfunc.chordPlayfunc, freqs.freqs, duration).play;
	}

	play {

	}
}