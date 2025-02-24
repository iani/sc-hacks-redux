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
	// dur = ~dur / (size max: 6);
	dur = ~dur / size;
	(freq: freq, dur: dur).splay;
	// (freq: freq, dur: ~dur / 6).splay;
});
(type: \chord0, chord: ["b:a:c", "C:e:g", "c:e:g:b1", "c:e:g:b1f"].chords.pseq,
dur: Pdup(12, [0.2, 0.4, 0.8, 1.6].pseq)
) +> \test;
//:alternative: get chords of pattern.  This recurses inside nested patterns!
(type: \chord0, chord: ["b:a:c", "C:e:g", "c:e:g:b1", "c:e:g:b1f"].pseq.chords,
dur: Pdup(12, [0.2, 0.4, 0.8, 1.6].pseq)
) +> \test;

//:Nested pattern 1
(type: \chord0, chord:
["C", "C", "C", "Cs", "Cs:g", Pn("Cs:fs", 4), "Cs", "b:a:g:c", "b:a:gf:c", "bf:af:gf:cs",
	"Cs:cs:e:g", "C:c:e:g", "c:e:g:b1", "c:e:g:b1f"
].pseq.chords,
dur: Pdup(12, [0.2, 0.4, 0.8, 1.6].pseq)
) +> \test;

//:Nested pattern 2
(type: \chord0, chord:
[Pn("C", 3), Pn("b:a:g:c", 3), Pn("b:a:g:c", 3), "bf:af:gf:cs",
	"Cs:cs:e:g", "C:c:e:g", "c:e:g:b1", "c:e:g:b1f"
].pseq.chords,
dur: Pdup(12, [0.2, 0.4, 0.8, 1.6].pseq)
) +> \test;
//:Nested patterns - variation 3
(type: \chord0, chord:
Pdup(3, ["C", "c", "b", "bf", "b:a:g:c", "b:af:g:c", "bf:af:gf:cs",
	"Cs:cs:e:g", "C:c:e:g", "c:e:g:b1", "c:e:g:b1f"
].pseq).chords,
dur: Pdup(24, [0.2, 0.4, 0.8, 1.6].pseq)
) +> \test;
//: