//:soundcheck
(
freq: ([
"a", "A1:A:a:e:c", "b:dg", "G:d:f1", "c:ef:g",
"C:e:g:bf:e1", "C:c:c1:g1:e2"
].prand(inf).chordcps),
instrument: SynthDEFaults.defcat(\pitched).prand(3).pdup(3),
dur: [0.2, 0.15, 0.1, 0.04].choose
) +> \x;
\y.addNotifier(~x, \played, { | n, event |
	event[\instrument].postln;
});
//:---- post trigger only -----
{ \x5.gt2; } +>.jun \trig1;
\x5 >>> { | n, vals |
	if (vals[3] > 0) { "ON".postln } { "OFF".postln; };
};
//:check on
0.9 @>.sensors \x5;
//:check off
0.01 @>.sensors \x5;
//:---- trigger sound - different sound sets ----
[\strings, \marimba, \prophet5strings] <!> \sounds;
[\strings] <!> \sounds;
[\xylophone] <!> \sounds;
[\prophet5strings] <!> \sounds;
{ \x5.gt2; } +>.jun \trig1;
\x5 >>> { | n, vals |
	if (vals[3] > 0) {
(
freq: ([
"a", "A1:A:a:e:c", "b:dg", "G:d:f1", "c:ef:g",
"C:e:g:bf:e1", "C:c:c1:g1:e2"
].prand(inf).chordcps),
instrument: ~sounds.prand(3).pdup(3),
// SynthDEFaults.defcat(\pitched).prand(3).pdup(3),
dur: [0.2, 0.15, 0.1, 0.4].choose
) +> \x;
\y.addNotifier(~x, \played, { | n, event |
	event[\instrument].postln;
});
	} {

	};
};
//:
(
freq: ([
"a", "A1:A:a:e:c", "b:dg", "G:d:f1", "c:ef:g",
"C:e:g:bf:e1", "C:c:c1:g1:e2"
].prand(inf).chordcps),
instrument: SynthDEFaults.defcat(\pitched).prand(3).pdup(3),
dur: [0.2, 0.15, 0.1, 0.04].choose
) +> \x;
\y.addNotifier(~x, \played, { | n, event |
	event[\instrument].postln;
});
//:
(
freq: ([
"a", "A1:A:a:e:c", "b:dg", "G:d:f1", "c:ef:g",
"C:e:g:bf:e1", "C:c:c1:g1:e2"
].prand(inf).chordcps),
instrument: SynthDEFaults.defcat(\pitched).prand(3).pdup(3),
dur: [0.2, 0.15, 0.1, 0.04].choose
) +> \x;
\y.addNotifier(~x, \played, { | n, event |
	event[\instrument].postln;
});
//:
(
freq: ([
"a", "b:dg", "G:d:f1", "c:ef:g",
"C:e:g:bf:e1"
].prand(inf).chordcps),
instrument: SynthDEFaults.defcat(\pitched).prand(3).pdup(3),
dur: 0.15
) +> \x;
\y.addNotifier(~x, \played, { | n, event |
	event[\instrument].postln;
});