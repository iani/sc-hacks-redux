
//

TypingRecorder(\ianix);
//:

TypingRecorder(\ianix).save;
//:
// TypingRecorder(\ianix).loadLast;
//: Sound check
// TypingRecorder(\ianix).play({ | char |
// 	(dur: 0.1, midinote: char.wrap(20, 80)).play;
// });
//: sline1 - default duration, Default play duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline1, [freq: char.wrap(20, 80).midicps])
});
//: sline5 - default duration, Default play duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline2, [freq: char.wrap(20, 80).midicps, dur: 4])
});
//: sline3 - default duration, Default play duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline3, [ascii: char.first, dur: 4, amp: 0.01])
});
//: sline3 - default duration, Default play duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline3, [ascii: char.first, dur: 0.1.exprand(5), amp: 0.1])
});
//: sline1, dur: 5 Default play duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline1, [freq: char.wrap(20, 80).midicps, dur: 5])
});
//: sline1, multiplay dur: 5   Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		10 do: { | i |
			var f;
			f = i.linlin(0, 10, 1.0, 2.0);
			Synth(\sline1, [freq: char.wrap(20, 80).midicps * i, dur: 5, amp: 0.01]);
			0.1.wait;
		};
	};
});
//: sline1, multiplay dur: 0.1   Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		10 do: { | i |
			var f;
			f = i.linlin(0, 10, 1.0, 2.0);
			Synth(\sline1, [freq: char.wrap(20, 80).midicps * i, dur: 0.1, amp: 0.01]);
			0.1.wait;
		};
	};
});
//: sline1, multiplay dur: 0.1   Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		var dur;
		dur = [0.05, 0.1, 0.15, 0.2].choose;
		50 do: { | i |
			var f;
			f = i.linlin(0, 50, 1.0, 2.0);
			Synth(\sline1, [freq: char.wrap(20, 80).midicps * i, dur: dur, amp: 0.01]);
			dur.wait;
		};
	};
});
//: sline1, multiplay dur: 0.1   Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		var dur;
		dur = [0.05, 0.1, 0.15, 0.2].choose;
		50 do: { | i |
			var f;
			f = i.linlin(0, 50, 1.0, 2.0);
			Synth(\sline2, [freq: char.wrap(20, 80).midicps * i, dur: dur, amp: 0.01]);
			dur.wait;
		};
	};
});
//: sline1, multiplay dur: 0.1   Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		var dur;
		dur = [0.05, 0.1, 0.15, 0.2].choose;
		(50..1) do: { | i |
			var f;
			f = i.linlin(1, 50, 1.0, 2.0);
			Synth(\sline2, [freq: char.wrap(20, 80).midicps * i, dur: dur, amp: 0.01]);
			dur.wait;
		};
	};
});
//: sline1, multiplay dur: 0.1   Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		var dur;
		dur = [0.05, 0.1, 0.15, 0.2].choose;
		(50..1) do: { | i |
			var f;
			f = i.linlin(1, 50, 1.0, 2.0);
			Synth(\sline2, [freq: char.wrap(20, 80).midicps * i, dur: dur, amp: 0.01]);
			dur.wait;
		};
	};
});
//: sline1, multiplay dur: 0.1   Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		var dur;
		dur = [0.05, 0.1, 0.15, 0.2].choose;
		(50..1) do: { | i |
			var f;
			f = i.linlin(1, 50, 1.0, 2.0);
			Synth(\sline3, [ascii: char.first, dur: dur, amp: 0.01]);
			dur.wait;
		};
	};
});
//: sline1, multiplay dur: 0.1   Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		var dur;
		dur = [0.05, 0.1, 0.15, 0.2].choose;
		(50..1) do: { | i |
			var f;
			f = i.linlin(1, 50, 1.0, 2.0);
			Synth(\sline3, [ascii: char.first, dur: dur * 5, amp: 0.1]);
			dur.wait;
		};
	};
});
//: sline1, multiplay - lower    Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		10 do: { | i |
			var f;
			f = i.linlin(0, 10, 0.5, 1.0);
			Synth(\sline1, [freq: char.wrap(20, 80).midicps * i, dur: 5, amp: 0.01]);
			0.1.wait;
		};
	};
});
//: sline1, multiplay - downwards    Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		(10..1) do: { | i |
			var f;
			f = i.linlin(1, 10, 0.5, 1.0);
			Synth(\sline1, [freq: char.wrap(20, 80).midicps * i, dur: 5, amp: 0.01]);
			0.1.wait;
		};
	};
});
//: sline1, multiplay - downwards - short    Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		(10..1) do: { | i |
			var f;
			f = i.linlin(1, 10, 0.5, 1.0);
			Synth(\sline1, [freq: char.wrap(20, 80).midicps * i, dur: 0.5, amp: 0.01]);
			0.1.wait;
		};
	};
});
//: sline2, multiplay - downwards - short    Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		(10..1) do: { | i |
			var f;
			f = i.linlin(1, 10, 0.25, 0.5);
			Synth(\sline2, [freq: char.wrap(20, 80).midicps * i, dur: 0.5, amp: 0.01]);
			0.1.exprand(1.0).wait;
		};
	};
});
//: sline2, multiplay - downwards - short    Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	fork {
		(10..1) do: { | i |
			var f;
			f = i.linlin(1, 10, 0.25, 0.5);
			Synth(\sline2, [freq: char.wrap(20, 80).midicps * i, dur: 1.5, amp: 0.01]);
			0.1.exprand(1.0).wait;
		};
	};
});
//: Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline2, [freq: char.wrap(20, 80).midicps])
});
//: sline3 --- Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline3, [ascii: char.first])
});
//: sline3 --- long notes!!! Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	char = char.first;
	Synth(\sline3, [ascii: char, dur: 1.1.exprand(5.2)]);
	{ Synth(\sline3, [ascii: char + 10]) }.defer(3.1 exprand: 7.2)
});
//: sline3 --- Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	char = char.first;
	Synth(\sline3, [ascii: char, dur: 0.1.exprand(1.2)]);
	{ Synth(\sline3, [ascii: char + 10]) }.defer(0.1 exprand: 1.2)
});
//: sline3 --- Default duration: 10 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline3, [freq: char.wrap(20, 80).midicps])
});
//: custom duration: 30 seconds
TypingRecorder(\ianix).play({ | char |
	Synth(\sline1, [freq: char.wrap(20, 80).midicps])
}, dur: 30);
//:
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