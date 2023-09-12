/* 12 Sep 2023 15:21

*/
//:==== trill, up+down arpeggios
Event.addEventType(\trill, { (degree: (~degree + [0, 1]).pseq(3), dur: ~dur / 6).splay; });
Event.addEventType(\arpeggio, { (degree: (~degree + [0, 2, 4, 7]).pseq(1), dur: ~dur / 6).splay; });
Event.addEventType(\darpeggio, { (degree: (~degree - [0, 2, 4, 7]).pseq(1), dur: ~dur / 6).splay; });
(type: [\trill, \arpeggio, \darpeggio].pseq, degree: (1..10).pseq, dur: [0.5, 3, 2, 1].pseq) +> \test;
//:==== Playing Chords / notation-based
Event.addEventType(\chord0, {
	var freq, size, dur;
	freq = ~chord.freqs;
	size = freq.size;
	freq = freq.pseq(1);
	dur = ~dur / (size max: 6);
	(freq: freq, dur: dur).splay;
	// (freq: freq, dur: ~dur / 6).splay;
});
(type: \chord0, chord: ["b:a:c", "C:e:g", "c:e:g:b1", "c:e:g:b1f"].chords.pseq, dur: 0.4) +> \test;
