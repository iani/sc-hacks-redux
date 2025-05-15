//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TypeTsr(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingListener(); // test gd verse matching
//: