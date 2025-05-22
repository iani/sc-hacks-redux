Tsr.verse(2);
//:
Tsr.doOnVerse2({ | index |
LiveText2.text(~verses[0]);
	\fm1 play: (\instrument: \fms);
	\fm1 set: (
		freq: LiveText2.ints(1).pseq*600,
		shape: LiveText2.floats(1).pseq*4,
		modfreq: LiveText2.ints(1).pseq*1000,
		ind: LiveText2.ints(1).pseq*280,
		dur: LiveText2.floats(1).pseq*4,
		amp: LiveText2.floats(1).pseq,
		pan: LiveText2.floats(1).pseq.linlin(0.1, 0.9, -1.0, 1.0),
		vol:0.4
	);
}, \ianisfm);
//:
Tsr.undoOnVerse(\ianisfm);