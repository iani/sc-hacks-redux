/*  8 Sep 2023 20:21
Simple concept tried out during rehearsal.
*/
//:
Chord(\c, \e, \g).play;
//:
Chord(\c, \e, \g).play(5, 5);
//:
Chord(\c, \e, \g).transpose(5).play(5, 5);
//:
Chord(\c, \e, \g).transpose(-5).play(5, 5);
//:
Chord(\c, \e, \g, \b).transpose(-5).play(5, 5);
//:
Chord(\c, \e, \g, \b).transpose(5).play(5, 5);
//:
Chord(\c, \e, \g, \bf).transpose(5).play(5, 5);
//:
Chord(\c, \e, \g, \bf, \d2).play(5, 5);
//:
Chord(\c, \e, \g, \bf, \d2, \e2b, \f2s).play(5, 5);
//:
{
	var chords;
	chords = [
		[\c, \e, \g],
		[\c, \ef, \g],
		[\cs, \e, \g],
		[\c, \e, \g, \b],
		[\c, \e, \g, \bf],
		[\c, \e, \g, \bf, \d2],
		[\c, \e, \g, \b],
		[\c, \e, \g, \bf, \d2, \e2b, \f2s]
	];
	loop {
		Chord(*chords.choose).transpose(-30 rrand: 10)
		.play(0.1 exprand: 5, 1.01 exprand: 7);
		([0.1, 0.2, 0.4, 0.8, 1.6] * 0.2).choose.wait;
	};
}.fork;
//:
Chord(\c, \e, \g).play;
Chord(\c, \e, \g).play(5, 5);
Chord(\c, \e, \g).transpose(5).play(5, 5);
Chord(\c, \e, \g).transpose(-5).play(5, 5);
Chord(\c, \e, \g, \b).transpose(-5).play(5, 5);
Chord(\c, \e, \g, \b).transpose(5).play(5, 5);
Chord(\c, \e, \g, \bf).transpose(5).play(5, 5);
Chord(\c, \e, \g, \bf, \d2).play(5, 5);
Chord(\c, \e, \g, \bf, \d2, \e2b, \f2s).play(5, 5);