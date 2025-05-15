//: This only works on George Diapoulis' machine
a = TypeTsr(\geodia).actions;
a.typeVerse;
//: This only works on Iannis Zannos' machine
b = TypeTsr(\iani).actions;
b.typeVerse;
//:type all verses
b.typeVerses;
//:????
b.testVerse;
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TypeTsr(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingListener(); // test gd verse matching
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TypeTsr(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingListener(); // test gd verse matching
//:
Tsr.undoOnType(\gdmusic);
b = TypeTsr(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);
//:
b = TypeTsr(\iani).actions;
b.typeVerse;

//:
Tsr.doOnVerse({ | verse, num |
	postln("Tsr received verse" + verse + "\nverse index is" + num);

});

{ OscMonitor.gui; }.defer;