//:

//:
Tsr.doOnType({ | key |
	postln("type received key:" + key);
	(degree: key.wrap(-10, 30)).play;
}, \iani1);
//:
Tsr.doOnVerse({ | num, incipit, verse |
	postln("verse received verse:" + num + "incipit" + incipit);
	[~verses1, ~verses2, ~verses3, ~verses4].choose[num].play;
	~verses1[num].play;
}, \iani1);
//:
Tsr.verse(10);

Tsr.verse(30.rand) ;
//
//
Tsr undoOnType: \iani1;
Tsr undoOnVerse: \iani1;
currentEnvironment.parent.keys.asArray.sort;

~all1;

~verses1
~verses2
~verses3[6].play;
~verses4[6].play;

~incipits;
~verses[6]
