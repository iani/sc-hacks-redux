a = TsrVerse(\geodia).actions;
a.typeVerse;

b = TsrVerse(\iani).actions;
b.typeVerse;
b.typeVerses;
b.testVerse;
//:
Tsr.doOnType({ | char |
	// postln("Tsr received char" + char);

}, \iani1);
//:
Tsr.doOnVerse({ | verse, num |
	postln("Tsr received verse" + verse + "\nverse index is" + num);

});

{ OscMonitor.gui; }.defer;