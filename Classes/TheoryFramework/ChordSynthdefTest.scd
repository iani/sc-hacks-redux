/* 13 Sep 2023 13:48
Test SynthDefs loaded by ChordSynthdefLoader.
*/

//:
(freq: ["a", "b:dg", "G:d:f1"].pseq.chordcps, dur: 1.5) +> \x;
//:
(
freq: ["a", "b:dg", "G:d:f1"].pseq.chordcps,
instrument: ChordSynthdefLoader.defNames.pseq.pdup(3),
dur: 1.5
) +> \x;
//:
