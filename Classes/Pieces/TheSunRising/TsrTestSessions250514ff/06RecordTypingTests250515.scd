//: Debugging TypingListener
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener(); // Test whether this makes the Segmentation Fault:
//:
///////////////////////////////////////
//:This is ok
TypingListener.verbose = true;
TypingListener.verbose = false;
TypingListener()
Tsr.type($a);

Tsr.doOnType({ | char | char.postln; }, \iani1);
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;
// Test whether this makes the Segmentation Fault:
TypingListener();
//:
//: Record and replay of typed data.
b = TypeTsr(\iani).actions;
TypingListener.verbose = true;

TypingListener();
b.typeVerses;
//:
				Char.nl.asString.post;

//:
Tsr.doOnType({ | char | char.postln; }, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);
//:
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
//:: TypingListener(); // test gd verse matching
//: