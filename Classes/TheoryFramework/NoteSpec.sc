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

NoteSpec { // Only instantiate subclasses HiOctave and LoOctave!
	classvar <notesArray;
	var <spec, <noteName, <noteIndex, <degreeClass, <octChar;
	var <octTranspose, <alts, <midi, <freq;

	*initClass {
		StartUp add: { this.makeNotesArray }
	}

	*makeNotesArray {
		notesArray = [60, 62, 64, 65, 67, 69, 71].rotate(2);
	}

	*new { | spec |
		var ascii, note, mods;
		note = spec.first;
		ascii = note.ascii;
		mods = spec[1..];
		if (ascii < 97) {
			^LoOctave.newCopyArgs(spec, note, ascii - 65).init(mods);
		}{
			^HiOctave.newCopyArgs(spec, note, ascii - 97).init(mods);
		}
	}

	init { | mods |
		degreeClass = notesArray@noteIndex;
		this.parseMods(mods);
		// midi = notesArray[noteIndex] + this.octaveTranspose(mods.first);
	}

	parseMods { | mods |
		var bs, hs;
		octChar = mods.findRegexp1("^\\d").first;
		octTranspose = this.octaveTranspose(octChar);
		bs = mods.findRegexp1("[f].*").size.neg;
		hs = mods.findRegexp1("[s].*").size;
		alts = bs + hs;
		midi = degreeClass + octTranspose + alts;
		freq = midi.midicps;
	}

	octaveTranspose { ^0 } // only use the subclass versions of this.
	report {
		postln("OctaveSpec: noteName" + noteName
			+ "noteIndex" + noteIndex
			+ "degreeClass" + degreeClass
			+ "octChar" + octChar
			+ "octTranspose" + octTranspose
			+ "alts" + alts
			+ "midi" + midi
		);
		(midinote: midi).play;
	}
}

HiOctave : NoteSpec { // octaves above middle c (letters a-g)
	octaveTranspose { | char |
		if (char.isNil) { ^0 } { ^char.ascii - 48 * 12 };
	}
}

LoOctave : NoteSpec { // octaves below middle c (letters A-G)
	octaveTranspose { | char |
		if (char.isNil) {
			^-12
		} {
			^(char.ascii - 48 * 12 + 12).neg;
		};
	}
}
