/*  3 Sep 2023 10:12

Calculate midi note from input in the form of:

<Note>, "<Octave><Modifiers>

Where:

<Note> is one of:
"abcdefg".ascii;
or
"ABCDEFG".ascii;

<Octave> is one of:
nil or 1, 2, 3, 4 or 5.

<Modifiers> is one of:
nil, "b", "bb", "bbb", "h", "hh", "hhh"

Converting abcdef / ABCDEF to midi notes:
[60, 62, 64, 65, 67, 69, 71].rotate(2)[("abcdefg".ascii - 97)];
[60, 62, 64, 65, 67, 69, 71].rotate(2)[("ABCDEFG".ascii - 65)];
*/

OctaveSpec { // Only instantiate subclasses HiOctave and LoOctave!
	classvar <notesArray;
	var <noteName, <noteIndex, <mods, <midi;

	*initClass {
		StartUp add: { this.makeNotesArray }
	}

	*makeNotesArray {
		notesArray = [60, 62, 64, 65, 67, 69, 71].rotate(2);
	}

	*new { | note, mods |
		var ascii;
		ascii = note.ascii;
		if (ascii < 97) {
			^LoOctave.newCopyArgs(note, ascii - 65, mods).init;
		}{
			^HiOctave.newCopyArgs(note, ascii - 97, mods).init;
		}
	}

	init {
		midi = notesArray[noteIndex] + this.octaveTranspose(mods.first);
		mods[]
	}

	octaveTranspose { ^0 } // only use the subclass versions of this.
}

HiOctave : OctaveSpec { // octaves above middle c (letters a-g)
	octaveTranspose { | char |
		if (char.isNil) { ^0 } { ^char.ascii - 48 * 12 };
	}



}

LoOctave : OctaveSpec { // octaves below middle c (letters A-G)
	octaveTranspose { | char |
		if (char.isNil) {
			^-12
		} {
			^(char.ascii - 48 * 12 + 12).neg;
		};
	}
}
