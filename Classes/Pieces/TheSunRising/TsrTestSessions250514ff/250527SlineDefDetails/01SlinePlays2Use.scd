// 火 27  5 2025 08:15
//: -------- LIST (7 synthdefs)--------
// \sline1, \sline2, \sline3, \sline3b, \sline4, \sline4b, \sline5
//:======== sline def args =======
/*
\sline1, { | out = 0, freq = 400, amp = 0.1, dur = 0.1 |
\sline2, { | out = 0, freq = 400, amp = 0.1, dur = 0.1 |
\sline3, { | out = 0, ffreq=0.3, ascii = 50, dur = 1, amp = 0.1, mul = 1 |
\sline3b,out=0,ffreq=0.3,ascii=50,startfreq=200,endfreq=800,dur=1,amp=0.1,mul=1
\sline4, { | ascii = 50 |
\sline4b | ascii = 50, dur = 1 |
\sline5 out = 0, freq = 400, amp = 0.1, dur = 0.11
*/
//: From: 土 24  5 2025 18:14
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
Synth(\sline3, [dur: 10, mul: 2.0]);
Synth(\sline3, [dur: 10, mul: 5.0]);
Synth(\sline3, [dur: 10, mul: 0.01]);
Synth(\sline3, [dur: 10, mul: 0.1]);
Synth(\sline3, [dur: 10, mul: 0.1]);//:
//:
\s3 play: (instrument: \sline3, dur: 0.51, ascii: (0..127).prand);
//: sline3b
Synth(\sline3b, [dur: 10, mul: 2.0, startfreq: 800, endfreq: 1000]);
Synth(\sline3b, [dur: 10, mul: 2.0, startfreq: 1800, endfreq: 100]);
Synth(\sline3b, [dur: 10, mul: 20.0, startfreq: 1800, endfreq: 100]);
Synth(\sline3b, [dur: 20, mul: 20.0, startfreq: 1800, endfreq: 100]);
Synth(\sline3b, [dur: 1, mul: 2.0, startfreq: 1800, endfreq: 100]);
//: sline4
Synth(\sline4);
//:
fork {
	(1..127) do: { | ascii |
		Synth(\sline4, [ascii: ascii]);
		0.15.wait;
	}
}
//:
fork {
	(1..127) do: { | ascii |
		Synth(\sline4, [ascii: ascii]);
		0.05.wait;
	}
}
//: sline4b
Synth(\sline4b);
//:
fork {
	(1..127) do: { | ascii |
		Synth(\sline4b, [ascii: ascii]);
		0.15.wait;
	}
}

//: sline5
Synth(\sline5, [dur: 10]);
//:
fork {
	(1..127) do: { | ascii |
		Synth(\sline5, [dur: 3, freq: ascii.linlin(1, 127, 100, 4000)]);
		1.5.wait;
	}
}
//:
fork {
	(1..127).scramble do: { | ascii |
		Synth(\sline5, [dur: 3, freq: ascii.linlin(1, 127, 100, 4000)]);
		1.5.wait;
	}
}
//:
fork {
	(1..127).reverse do: { | ascii |
		Synth(\sline5, [dur: 3,
			freq: ascii.linlin(1, 127, 100, 4000),
			amp: 0.01
		]);
		0.15.wait;
	}
}
//:
\s5 play: (instrument: \sline5, dur: 0.51);