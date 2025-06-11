// \sline1, \sline2, \sline3, \sline3b, \sline4, \sline4b, \sline5
//: sline1
Synth(\sline1, [dur: 10]);
//:
\s1 play: (instrument: \sline1, dur: [0.2, 0.1].prand);
\s1 play: (amp: (100..1).normalize(0.1, 0.001).pseq(1));
\s1 play: (freq: (40..100).midicps.pseq(1));
\s1 play: (freq: (40..100).palindrome.midicps.pseq(1));
//:
\s1.stop;
//: sline2
Synth(\sline2, [dur: 10, amp: 0.1]);
//:
\s2 play: (instrument: \sline2, dur: 0.51);
\s2.stop;
//:
\s3 play: (instrument: \sline2, dur: [0.2, 0.1].prand);
\s3 play: (amp: (100..1).normalize(0.1, 0.001).pseq(1));
\s3 play: (freq: (40..100).midicps.pseq(1));
\s3 play: (freq: (40..100).palindrome.midicps.pseq(1));
\s3 play: (amp: (100..1).normalize(0.1, 0.001).prand);
\s3.stop;

//: sline3
Synth(\sline3, [dur: 10]);
Synth(\sline3, [dur: 10, mul: 2.0]);
Synth(\sline3, [dur: 10, mul: 5.0]);
Synth(\sline3, [dur: 10, mul: 0.01]);
Synth(\sline3, [dur: 10, mul: 0.1]);
//:
\s3 play: (instrument: \sline3, dur: 0.51, ascii: (0..127).prand);
\s3.stop;
//: sline3b
Synth(\sline3b, [dur: 10, mul: 2.0, startfreq: 800, endfreq: 1000]);
Synth(\sline3b, [dur: 10, mul: 2.0, startfreq: 1800, endfreq: 100]);
Synth(\sline3b, [dur: 20, mul: 20.0, startfreq: 1800, endfreq: 100]);
//: sline4
fork {
	(1..127) do: { | ascii |
		Synth(\sline4, [ascii: ascii]);
		0.15.wait;
	}
}
//: sline4
fork {
	(1..127) do: { | ascii |
		Synth(\sline4, [ascii: ascii]);
		[0.15, 0.3].choose.wait;
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
//: !!!!!!!!
fork {
	(1..127).reverse do: { | ascii |
		Synth(\sline5, [dur: 3,
			freq: ascii.linlin(1, 127, 100, 4000),
			amp: 0.01
		]);
		0.15.wait;
	}
}

//:============================================================
Synth(\sline5, [dur: 10]);
//: !!!!!!!! [not?]
r = fork {
	(1..127) do: { | ascii |
		Synth(\sline5, [dur: 3,
			freq: ascii.linlin(1, 127, 100, 4000),
			amp: 0.01
		]);
		0.05.wait;
	}
};
//:
r.stop;
//:
r = fork {
	(1..127).scramble do: { | ascii |
		Synth(\sline5, [dur: 3,
			amp: 0.01,
			freq: ascii.linlin(1, 127, 100, 4000)]);
		0.25.wait;
	}
};
//:
r = fork {
	(1..127).reverse do: { | ascii |
		Synth(\sline5, [dur: 3,
			freq: ascii.linlin(1, 127, 100, 4000),
			amp: 0.01
		]);
		0.15.wait;
	}
};
//:
\s5 play: (instrument: \sline5, dur: [0.1, 0.2].prand(20));
\s5.stop;

a.free;