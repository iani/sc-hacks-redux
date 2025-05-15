//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TypeTsr(\iani).actions;
b.typeVerses;
//:
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);
//:
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
OSC.remove(\cret);
TypingListener(); // test gd verse matching
//:
//
(0 + 100).asString[1..]
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR KOSMA".postln;
	postln("index" + index + "convereted index string" + (100 + index).asString[1..].asCompileString);
}, \kosmaverse1);
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasiliverse1);
//:
Tsr.undoOnVerse(\kosmavers1);
