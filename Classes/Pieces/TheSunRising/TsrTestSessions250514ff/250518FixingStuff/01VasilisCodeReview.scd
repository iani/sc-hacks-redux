//:日 18  5 2025 11:16
//Trying code from Vasili's transcript window 250516

//:Testing LiveText
//LiveText.text(verse.asString)

LiveText.text("Busy old fool, unruly sun.");
LiveText.ints;
(LiveText.ints*60).pseq(1);

//Testing the sines pattern
\sines1 play: (\instrument: \sines);
\sines1 set: (\freq: (LiveText.ints*60).pseq(1),
freq2: (LiveText.ints*100).pseq(1),
\freq3: (LiveText.ints*80).pseq(1),
\vol: 0.2,
	// mistake:
	// \fade: (LiveText.floats)*2).pseq(1)
	// Correction:
\fade: (LiveText.floats*2).pseq(1),
\dur: (LiveText.floats).pseq(1),
\amp: (LiveText.floats.linlin(0.5, 0.3, 0.4, 0.5)).pseq(1),
\pan: 0);
//: Redoing LiveText to permit following syntax:
LiveText.text(10, "Busy old fool, unruly sun.");
//:
Tsr.doOnVerse({ | num, verse, incipit |
	LiveText2.text(verse);
	\sines1 play: (\instrument: \sines);
	\sines1 set: (
		freq: LiveText2.ints(2)*60,
		fade: LiveText2.floats(2)*2,
		freq2: LiveText2.ints(2)*100,
		freq3: LiveText2.ints(2)*80,
		dur: LiveText2.floats(2),
		vol: 0.2
	);
}, \vastest);
//:

Tsr.doOnTrig({ | num, verse, incipit |
	\sines1.stop;
}, \vastest);


//:

//:
// etc.
\sines1.stop;

currentEnvironment;
