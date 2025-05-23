
a = Object.readArchive(Platform.userAppSupportDir +/+ "typingrecorder.scd");

a.size;
a[1];

b = TypingRecorder(\test);
b.lines = a;
b.lines;
b.play({ |...args | postln("TypingPlayer received:" + args) });
b.play({ | char | (dur: 0.1, midinote: char.wrap(20, 80)).play; });
