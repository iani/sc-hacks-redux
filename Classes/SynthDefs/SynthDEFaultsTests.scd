/* 13 Sep 2023 13:48
Test SynthDefs loaded by ChordSynthdefLoader.
*/
//:
SynthDEFaults.defcat(\pitched)
//:
(freq: [
"a", "b:dg", "G:d:f1", "c:ef:g",
"C:e:g:bf:e1"
].pseq.chordcps, dur: 0.15) +> \x;
//:
(
freq: [
"a", "b:dg", "G:d:f1", "c:ef:g",
"C:e:g:bf:e1"
].pseq.chordcps,
instrument: SynthDEFaults.defcat(\pitched).pseq(1).pdup(3),
dur: 0.15
) +> \x;
//:post the instrument
//:
(
freq: [
"a", "b:dg", "G:d:f1", "c:ef:g",
"C:e:g:bf:e1"
].pseq.chordcps,
instrument: SynthDEFaults.defcat(\pitched).pseq(1).pdup(3),
dur: 0.15
) +> \x;
\y.addNotifier(~x, \played, { | n, event |
	event[\instrument].postln;
});
