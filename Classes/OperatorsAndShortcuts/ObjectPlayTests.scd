
~x = { SinOsc.ar(100, 0, 0.1).dup }.play;
\x play: [1, 2, 2]
\x play: { "blah.postln".postln }

~x.isRunning;
~x.isPlaying;
~x.release;
\x play: { SinOsc.ar(100 rrand: 1000, 0, 0.05).dup };
\x.stop;
\x.stopp;
\x play: 1;
\x play: [1, 2, 3];
\x play: nil;
~x;

\x play: ();

a = EventStream(()).start;

a.free;

\x.stop;
\x.free;

//: ================ Testing Event, EventStream ==============
\x play: (dur: 0.1, degree: 2);
\x play: (dur: 1.1, degree: 1);
\x play: (dur: 0.1, degree: [2, 5]);
\x play: { SinOsc.ar(100 rrand: 1000, 0, 0.05).dup };
~x;

//:
\x play: (dur: 0.1, degree: 2.pn(2));

//: ======= TempoClock and quant =======
t = TempoClock(2).permanent_(true);
a = EventStream((clock: t, quant: 1));
a.start;
t.tempo = 0.5;
b = EventStream((clock: t, quant: 1, degree: 2));
b.start;
t.tempo = 2;
//: ======= TempoClock and quant from currentEnvironment =======
~clock = TempoClock(0.5).permanent_(true);
~quant = 1;
a = EventStream(());
a.start;
~clock.tempo = 0.5;
b = EventStream((degree: 2));
b.start;
//:
TempoClock.default.tempo = 0.25;