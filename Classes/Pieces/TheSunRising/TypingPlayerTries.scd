
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
TypingRecorder(\ianiy);

//:
TypingRecorder(\ianiy).play({ | char |
	Synth(\sline2, [freq: char.wrap(40, 100).midicps, dur: 1]);
	{ Synth(\sline2, [freq: (char.wrap(40, 100) - 1).midicps]) }.defer(0.3);
}, from: 0, to: 10);
//:
TypingRecorder()


Platform.userAppSupportDir;

//:
OscMonitor.gui;
1 + 1;

Synth(\sline1, [dur: 1]);