/*  7 Sep 2023 01:06
Very simple prototype for playing chords with sines.
Notes are specified with an array of specs for NoteSpecs
*/

Chord {
	var <noteNames, <notes;
	*new { | ... notes |
		^this.newCopyArgs(notes).init
	}

	init {
		notes = noteNames collect: NoteSpec(_);
	}

	transpose { | semitones = 1 |
		^this.copy.transposeNotes(semitones);
	}

	transposeNotes { | semitones |
		notes = notes.collect({ | n | n.transpose(semitones)});
	}

	freqs { ^notes collect: _.freq }

	// simple play
	play { | dur = 1, amp = 1 |
		{
			var freq;
			freq = this.freqs;
			DynKlang.ar(`[
				freq,
				amp / freq.size,
				pi
			]) * Env.perc(0.01, dur - 0.01, 0.1).kr(doneAction: 2)
		}.play
	}

	// play with user-provided envelope
	eplay { | env |
		{
			var freq;
			freq = this.freqs;
			DynKlang.ar(`[
				freq,
				freq.size.reciprocal,
				pi
			]) * env.kr(doneAction: 2)
		}.play
	}
}