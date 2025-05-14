a = TsrVerse(\geodia).actions;
a.typeVerse;

b = TsrVerse(\iani).actions;
b.typeVerse;
b.typeVerses;
b.testVerse;
//:
Tsr.doOnType({ | char |
	postln("Tsr received char" + char);
});
//:
Tsr.doOnVerse({ | verse, num |
	postln("Tsr received verse" + verse + "\nverse index is" + num);

});
