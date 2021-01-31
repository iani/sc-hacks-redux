/* 31 Jan 2021 11:02
First tests of OscTrig

FIXME: addSynth should replace previous synth at same key.
*/
//:
a = OscTrig();
a.addSynth({ | id = 0 | SendTrig.kr(Impulse.kr(1), id, LFNoise1.kr(pi))});
// a.watchChanges;
//:
\x.addNotifier(a, \trig, { | n, id, val |
	//	[id, val].postln;
	// (val * 1000 + 2000).postln;
	(freq: val * 1000 + 2000, dur: 0.01).play;
});
//:
a.addSynth({ | id = 0 | SendTrig.kr(Impulse.kr(0.5), id, SinOsc.kr(0.1))});

//:
a.addListener(\x, { | id, val | val.postln; });
//:
a.addListener(\x, { | id, val | val.postln; (freq: val * 1000 + 2000).play });
//a.addListener(\anything);
a.id;

(freq: 0 * 1000 + 2000).play