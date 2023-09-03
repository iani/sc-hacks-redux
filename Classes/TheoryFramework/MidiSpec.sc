/*  3 Sep 2023 09:15
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


*/

MidiSpec {
	var <spec, <note, <mods, <midi, <converter;
	*new { | spec |
		^this.newCopyArgs(spec.asString).init;
	}

	init {
		note = spec[0];
		mods = spec[1..];
		if (note.ascii < 97) {
			converter = LoOctave(note, mods);
		}{
			converter = HiOctave(note, mods);
		}
	}
}

