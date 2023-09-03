/*  3 Sep 2023 00:03

1. c, d, e, f, g, a, b -> central octave starting with Piano middle c
2. c1...b1 -> one octave above (1.)
3. c2...b2 -> 2 octaves above (1.) ... etc until c5
4. C...C -> 1 octave below (1.) ... etc until c5
5. C1...B2 -> 2 octaves below (1.) ... etc until C5
6. alterations are appended to the above names. s = sharp, ss = double sharp, sss = triple sharp. b = flat, bb = double flat bbb = triple flat.

*/

Snote {
	var <name, <note, <mods, <octave, <alt, <midi, <freq;

	*new { | name |
		^this.newCopyArgs(name.asString).parseName;
	}

	parseName {
		var spec;
		note = name[0];
		mods = name[1..];
		spec = OctaveSpec(note, mods);
		// if (mods.size) ==
	}
}