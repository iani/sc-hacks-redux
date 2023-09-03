/*  3 Sep 2023 12:16
Testing the note-to-midi framework.
*/


(midinote: OctaveSpec($c, "").midi).play;
(midinote: OctaveSpec($c, "1").midi).play;
(midinote: OctaveSpec($c, "2").midi).play;
(midinote: OctaveSpec($c, "3").midi).play;
(midinote: OctaveSpec($c, "4").midi).play;

(midinote: OctaveSpec($C, "").midi).play;
(midinote: OctaveSpec($C, "1").midi).play;
(midinote: OctaveSpec($C, "2").midi).play;
(midinote: OctaveSpec($C, "3").midi).play;
(midinote: OctaveSpec($C, "4").midi).play;

//: Parsing octave + accidentals. Using findRegexp
// test by examples:
f = { | mods |
	var oct, alt, bs, hs;
	oct = mods.findRegexp1("^\\d").interpret;
	alt = mods.findRegexp1("[bh].*");
	bs = mods.findRegexp1("[b].*").size.neg;
	hs = mods.findRegexp1("[h].*").size;
	postln("mods:" + mods.asCompileString
		+ "oct:" + oct + "alt:" + alt.asCompileString
		+ "bs:" + bs + "hs" + hs
	);
};
//:
f.("c"[1..]);
//:
f.("");
f.("1");
f.("5");
f.("b");
f.("bb");
f.("bbb");
f.("h");
f.("hh");
f.("hhh");
f.("1b");
f.("2bb");
f.("3bbb");
f.("4h");
f.("5hh");
f.("1hhh");

//:===================== PART 2 =====================
OctaveSpec("c").report;
//:
f = { | spec |
	var os;
	os = NoteSpec(spec);
	os.report;
};
//:
f.("c");
//:*
f.("c"); 0.25.wait;
f.("g"); 0.25.wait;
f.("gs"); 0.25.wait;
f.("gss"); 0.25.wait;
f.("gsss"); 0.25.wait;
f.("gf"); 0.25.wait;
f.("gff"); 0.25.wait;
f.("gfff"); 0.25.wait;
f.("g1f"); 0.25.wait;
f.("g2ff"); 0.25.wait;
f.("g3fff"); 0.25.wait;
f.("Gfff"); 0.25.wait;
f.("G1f"); 0.25.wait;
f.("G2ff"); 0.25.wait;
f.("G3fff"); 0.25.wait;
//:
f.("Gfff");
f.("Bfff");
f.("Dfff");
f.("ffff");
//:
f.("gfff");
f.("bfff");
f.("dfff");
f.("f1fff");
//:
//:preliminary tries:

o = "5".findRegexp("^\\d");

a = "bb".findRegexp("[bh]*");
"b".findRegexp("[bh].*");
"h".findRegexp("[bh].*");
"4h".findRegexp("[bh].*");
"hh".findRegexp("[bh].*");
"1hh".findRegexp("[bh].*");