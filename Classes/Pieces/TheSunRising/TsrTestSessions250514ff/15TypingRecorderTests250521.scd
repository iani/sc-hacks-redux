

a = TypingRecorder(\iani2);
b = TypingRecorder(\iani);
c = TypingRecorder(\giorgos);

a === b

a === c;
a;
a.inspect;

a.lines;
a.currentLine;
//:
\checkRecording.addNotifier(TypingRecorder(\iani),
	\ascii, { | ... args |
		postln("I received from recorder args:" + args);
	},
	\test1
);
//:
TypingRecorder(\iani2).replay;
//:
\checkRecording.addNotifier(TypingRecorder(\iani2),
	\ascii, { | ... args |
		postln("I received from recorder args:" + args);
		User.sendToAll(
			User.codeMessage,
			format(
				"Synth('lsine3', [ascii: %])",
				args[1]
			);
		);
	},
	\plainEvent
);
//:
TypingRecord f
er(\iani2).lines.writeArchive(
Platform.userAppSupportDir +/+ "typingrecorder.scd"
);
//:
Object.readArchive(Platform.userAppSupportDir +/+ "typingrecorder.scd");
//
(note: 100).play;
//:
SynthDef(\lsine1, {
	var src, env, freq, dur, amp;
	dur = Rand(0.1, 0.8);
	amp = Rand(0.01, 0.1);
	freq = Line.kr(Rand(100, 1000), Rand(100, 1000), dur,
		doneAction: 2
	);
	env = Env.perc(0.01, dur - 0.01, amp);
	src = SinOsc.ar(freq).dup * env.kr(1, doneAction: 2);
	Out.ar(0, src);
}).add;
//:
SynthDef(\lsine2, { | ascii = 50 |
	var src, env, freq, dur, amp;
	ascii = ascii.wrap(1, 127).linlin(1, 127, 1.01, 2);
	dur = Rand(0.1, 0.8);
	amp = Rand(0.01, 0.1);
	freq = Line.kr(Rand(100, 1000), Rand(100, 1000), dur,
		doneAction: 2
	);
	env = Env.perc(0.01, dur - 0.01, amp);
	src = SinOsc.ar(freq * [1, ascii]) * env.kr(1, doneAction: 2);
	Out.ar(0, src);
}).add;
//:
SynthDef(\lsine3, { | ascii = 50 |
	var src, env, freq, dur, amp, noise, decay;
	noise = WhiteNoise.ar(1);
	ascii = ascii.wrap(1, 127).linlin(1, 127, 1.01, 2);
	decay = ascii.wrap(1, 127).linlin(1, 127, 0.1, 0.9);
	dur = Rand(0.2, 1.8);
	amp = Rand(0.01, 0.1);
	freq = Line.kr(Rand(100, 1000), Rand(100, 1000), dur,
		doneAction: 2
	);
	env = Env.perc(0.1, dur - 0.1, amp);
	src = Ringz.ar(
		noise,
		freq * [1, ascii],
		decay
	) * env.kr(1, doneAction: 2);
	Out.ar(0, src);
}).add;

//:
a.play;
//:
Rand()

Synth(\lsine3);
//============================================================
// Testing new playback mechanism:
// Play multiple playback tasks in parallel, accessed by name
// customize playback function and data filter for each task, by name
//: Testing Task for state tracking (Routine isPlaying does not work)
a = Task({ loop { "PLAYING".postln; 1.0.wait;} } );
a.play;
a.stop;
a.isPlaying;