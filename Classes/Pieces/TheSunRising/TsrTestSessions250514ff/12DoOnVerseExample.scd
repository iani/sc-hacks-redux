//:
Tsr.doOnVerse2({ | index, verse, incipit, id |
	postln("iani do on verse" + [index, verse, incipit, id]);
	// playing synths directly is dangerous
	// ~x = { PinkNoise.ar(0.1).dup }.play;
	\y play: (dur: 0.5, amp: 0.05, degree: 10.rand);
	// \x play: { SinOsc.ar(400 rrand: 1000, 0, 0.1).dup };

},\ianinoisedemo);
//:
Tsr.doOnVerse2({
	\y play: (dur: 0.1, amp: 0.01);
},\ianipatterndemo)
//:
~x.free;
~y.stop;

\x play: { SinOsc.ar(400 rrand: 1000, 0, 0.1).dup };

~x = "private by iani";

User.prUse({ ~x.postln }, \iani, \iani);
User.prUse({ ~allo.postln }, \penny, \penny);
User.prUse({ ~kati.postln }, \geodia, \geodia);

User.all[\geodia].envir;
User.all[\geodia].envirs[\geodia];

ShowTyping();