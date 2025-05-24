//: 土 24  5 2025 18:14
// Testing TypingSynthdefs
//: sline1
Synth(\sline1, [dur: 10]);
//:
\s1 play: (instrument: \sline1, dur: 0.51);
//: sline2
Synth(\sline2, [dur: 10, amp: 0.1]);
//:
\s2 play: (instrument: \sline2, dur: 0.51);
//: sline3
Synth(\sline3, [dur: 10]);
//:
\s3 play: (instrument: \sline3, dur: 0.51, ascii: (0..127).prand);
//:sline4
Synth(\sline4);

//: sline5
Synth(\sline5, [dur: 10]);
//:
\s2 play: (instrument: \sline5, dur: 0.51);