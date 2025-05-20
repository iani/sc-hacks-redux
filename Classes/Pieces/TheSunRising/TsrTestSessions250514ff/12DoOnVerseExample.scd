//:
Tsr.doOnVerse2({ | index, verse, incipit, id |
	postln("iani do on verse" + [index, verse, incipit, id]);
	~x = { PinkNoise.ar(0.1).dup }.play;
},\ianinoisedemo);
//:
Tsr.doOnVerse2({
	\y play: (dur: 0.1, amp: 0.01);
},\ianipatterndemo)
//:
~x.free;
