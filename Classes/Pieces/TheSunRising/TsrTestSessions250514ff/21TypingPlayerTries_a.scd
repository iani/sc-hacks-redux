
TypingRecorder(\ianix);
//:
TypingRecorder(\ianix).play({ | char | (dur: 0.1, midinote: char.wrap(20, 80)).play; });

TypingRecorder(\ianix).play({ | char | Synth(\sline1, [freq: char.wrap(20, 80).midicps]) });
TypingRecorder(\ianix).play({ | char | Synth(\sline1, [freq: char.wrap(20, 80).midicps]) });

//:

b.play({ | char | Synth(\sline1, [freq: char.wrap(20, 80).midicps]) });

b.play({ |...args | postln("TypingPlayer received:" + args) });
b.play({ | char | (dur: 0.1, midinote: char.wrap(20, 80)).play; });
b.play({ | char | Synth(\sline1, [freq: char.wrap(20, 80).midicps]) });
//:
TypingRecorder(\ianix).play({ | char |
	Synth(\sline1, [freq: char.wrap(40, 100).midicps, dur: 1]);
	{ Synth(\sline1, [freq: (char.wrap(40, 100) - 1).midicps]) }.defer(0.3);
});
//:
TypingRecorder(\ianix).play({ | char |
	Synth(\sline1, [freq: char.wrap(40, 100).midicps, dur: 1]);
	{ Synth(\sline1, [freq: (char.wrap(40, 100) - 1).midicps]) }.defer(0.3);
});
//:
TypingRecorder(\ianiy).loadLast;

//: ---------- sline2 ------------
TypingRecorder(\ianiy).play({ | char |
	Synth(\sline2, [freq: char.wrap(40, 100).midicps, dur: 1]);
	{ Synth(\sline2, [freq: (char.wrap(40, 100) - 1).midicps, dur: 5]) }.defer(3);
}, from: 0, to: 10);
//: ---------- sline5 ------------
TypingRecorder(\ianiy).play({ | char |
	postln("char" + char + "char size" + char.size + "char first" + char.first);
	Synth(\sline5, [freq: char.wrap(40, 100).midicps, dur: 10]);
	{ Synth(\sline5, [freq: (char.wrap(40, 100) - 1).midicps, dur: 5]) }.defer(3);
}, from: 0, to: 10);
//: ---------- sline3 a ------------
TypingRecorder(\ianiy).play({ | char |
	// postln("char" + char + "char size" + char.size + "char first" + char.first);
	Synth(\sline3, [ascii: char.first, dur: 0.10]);
	{ Synth(\sline3, [ascii: char.first, dur: 0.5]) }.defer(3);
}, from: 0, to: 10);
//: ---------- sline3 b ------------
TypingRecorder(\ianiy).play({ | char |
	// postln("char" + char + "char size" + char.size + "char first" + char.first);
	Synth(\sline3, [ascii: char.first, dur: 1.10]);
	{ Synth(\sline3, [ascii: char.first + 10, dur: 2.5]) }.defer(3);
}, from: 0, to: 10);
//:
TypingRecorder(\ianiy).play({ | char |
	Synth(\sline2, [freq: char.wrap(20, 30).midicps, dur: 10, amp: 0.1]);
	{ Synth(\sline2, [freq: (char.wrap(40, 50) - 1).midicps, dur: 5, amp: 0.2]) }.defer(3);
}, from: 0, to: 10);
//:
TypingRecorder(\ianiy).play({ | char |
	Synth(\sline2, [freq: char.wrap(20, 90).midicps, dur: 10, amp: 0.1]);
	{ Synth(\sline2, [freq: (char.wrap(40, 90) - 1).midicps, dur: 5, amp: 0.2]) }.defer(3);
}, from: 0, to: 10);
//:
TypingRecorder(\ianiy).play({ | char |
	Synth(\sline3, [freq: (char.wrap(40, 100) - 20).midicps, dur: 1]);
	{ Synth(\sline3, [freq: (char.wrap(40, 100) - 1).midicps], dur: 3) }.defer(0.3 rrand: 1);
}, from: 0, to: 10);
//: KEEP THIS ONE!!!!!!
TypingRecorder(\ianiy).play({ | char |
	Synth(\sline3, [freq: (char.wrap(40, 100) - 20).midicps, dur: 10, amp: 0.1]);
	{ Synth(\sline3, [freq: (char.wrap(40, 100) - 1).midicps, dur: 3, amp: 0.1]) }.defer(0.3 rrand: 1);
}, from: 0, to: 10);
//: KEEP THIS ONE!!!!!!
TypingRecorder(\ianiy).play({ | char |
	Synth(\sline3, [freq: (char.wrap(40, 100) - 20).midicps, dur: 10, amp: 0.1]);
	{ Synth(\sline3, [freq: (char.wrap(40, 100) - 1).midicps, dur: 3, amp: 0.1]) }.defer(0.3 rrand: 1);
	{ Synth(\sline3, [freq: (char.wrap(40, 100) - 1).midicps, dur: 3, amp: 0.1]) }
	.defer(0.25);
}, from: 0, to: 10);
//:
TypingRecorder()
letter_frequency
User.all[\geodia].envirs[\geodia].use({ ~letter_frequency });

Platform.userAppSupportDir;

//:
OscMonitor.gui;
1 + 1;

Synth(\sline1, [dur: 1]);