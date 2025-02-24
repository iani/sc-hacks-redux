/* 31 Jan 2021 11:02
First tests of OscTrig

*/
//:
a = OscTrig();
a.addSynth({ Impulse.kr(0.5) });
// a.watchChanges;
//:
\x.addNotifier(a, \trig, { | n, id, val |
	(freq: val * 1000 + 2000, dur: 0.01).play;
});
//:
a.addSynth({ Impulse.kr(5) });
//:
a.addSynth({ Impulse.kr(25) });
//:
a.addSynth({ Impulse.kr(3) });
//;
a.addSynth([{ Impulse.kr(25) }, { SinOsc.ar(0.5) }]);
//:
a.addSynth([{ Impulse.kr(5) }, { SinOsc.ar(0.1) }]);
//:
a.addSynth([{ Impulse.kr(2) }, { LFSaw.ar(0.2) - 0.5 }], \synth2);
