//: ------- testing recording of typing !
Tsr.undoOnType(\iani1);
TypingListener(); // test gd verse matching
c = RecordTyping().activate;
b = TypeTsr(\iani).actions;
b.typeVerses;
//:
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
