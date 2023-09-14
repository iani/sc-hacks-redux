
//:
(instrument: \piano_mda) +> \test;
//:
(instrument: \piano_mda, freq: "a:b:c".chordfreqs) +> \test;
//:
(instrument: \piano_mda, freq:
["a:b:c", "C", "C:E:G"].pseq.freqs
) +> \test;
//:
(dur: 1) ++> \test;
//:
(hard: 1) ++> \test;
//:
(hard: 0.1) ++> \test;
//:
(hard: (1..10).normalize(0.1, 2).pseq.pdup(10), dur: 0.2) ++> \test;
//:
(hard: 0.5, dur: 0.2, muffle: 0.20) ++> \test;
(hard: 0.5, dur: 0.2, muffle: 0.20, velmuff: 0.3, stretch: 3.5) ++> \test;
//:
"a:b:c".asChord