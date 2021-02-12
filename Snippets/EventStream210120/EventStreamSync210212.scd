/* 12 Feb 2021 10:08
Synchronizing the playing of EventStreams. 
Compare with Pdef examples from Nikos Kanelakis' coursework.
*/
//:N.Kanelakis excerpt
// using \default instrument for simplicity

~subkick =
Pdef(
	\subkick,
	Pbind(
		\instrument, \default,
		\degree, Pbrown(0, 5, 1),
		//		\dur, Pseq([1/16],inf),
		\dur, Pseq([1/4],inf), // slow down to hear synch clearly
		// \stretch, 60/128 * 4,
		// \rate, 1,
		\amp, Pseq([0.5, Pexprand(0.1,0.3,7)],inf),
	);
).quant_(1);

~rhythmclick =
Pdef(
	\rhythmclick,
	Pbind(
		\instrument, \default,
		// \dur, Pseq([1/16],inf),
		\dur, Pseq([1/4],inf), // slow down for clarity
		\degree, Pbrown(0, 5, 1) - [10, 13],
		// \stretch, 60/128 * 4,
		// \buf, Prand(d[\wood]++d[\plastic]++d[\ceramic], inf),
		// \rate, 11,
		\amp, Pseq([0.2, 0.1, 0.1], inf),
	);
).quant_(1);
//:Set default tempo before playing
TempoClock.default.tempo = 0.5;
//:
~subkick.play;
//:
~subkick.stop;
//:
~rhythmclick.play;
//:
~rhythmclick.stop;
//:
~subkick.play;
~rhythmclick.play;

//:Comparing with EventStream
//:Simple recoding
~esubkick = (
	instrument: \default,
	dur: Pseq([1/4],inf),
	degree: Pbrown(0, 5, 1),
	amp: Pseq([0.3, Pexprand(0.01,0.1,7)],inf),
).eventStream;
~erhythmclick = (
	instrument: \default,
	dur: Pseq([1/4],inf),
	degree: Pbrown(0, 5, 1) - [10, 13],
	amp: Pseq([0.05,0.1], inf),
).eventStream;
//:
~esubkick.play;
~erhythmclick.play;
//: cannot start these at once by evaluating following block
// Why? Debug!
~esubkick.play;
~erhythmclick.play;
//:================================================================
//:Simpler examples .... just examples
//:play immediately
~a = (degree: [3, 5]).splay;
~b = (degree: -5).splay;
//:+try stopping, then restarting and see if quant sync works
~a.stop;
~b.stop;
3.wait;
~a.play;
0.2.wait;
~b.play;

//:create, then play
~a1 = (degree: [3, 5]).eventStream;
~b1 = (degree: -5).eventStream;

//:Starting at once works.
~a1.play;
~b1.play;

//:+synchronization test
~a1.play;
0.2.wait;
~b1.play;
//:================================================================
//:Add the rest of the keys/streams
~a = (
	instrument: \default,
	dur: Pseq([1/4],inf),
	degree: Pbrown(0, 5, 1),
	amp: Pseq([0.3, Pexprand(0.01,0.1,7)],inf),
).eventStream;
~b = (
	instrument: \default,
	dur: Pseq([1/4],inf),
	degree: Pbrown(0, 5, 1) - [10, 13],
	amp: Pseq([0.05,0.1], inf),
).eventStream;
//:
~a.play;
~b.play;
//:================================================================
//:Name the variables
~esubkick = (
	instrument: \default,
	dur: Pseq([1/4],inf),
	degree: Pbrown(0, 5, 1),
	amp: Pseq([0.3, Pexprand(0.01,0.1,7)],inf),
).eventStream;
~erhythmclick = (
	instrument: \default,
	dur: Pseq([1/4],inf),
	degree: Pbrown(0, 5, 1) - [10, 13],
	amp: Pseq([0.05,0.1], inf),
).eventStream;
//:
~esubkick.play;
~erhythmclick.play;
//:modify these on the fly
~esubkick.put(\dur, 1/16);
~erhythmclick.put(\dur, 1/16);
//:
~esubkick.put(\dur, 1);
~erhythmclick.put(\dur, 1);
//:
~esubkick.put(\amp, Pseq([0.1, 0.02], inf));
//:
~esubkick.put(\dur, 1/3);
//:
~erhythmclick.put(\dur, 1);
//:
~erhythmclick.put(\dur, 1/2);
//:
~esubkick.put(\dur, 1/2);
~esubkick.put(\amp, Pseq([0.1, 0.02, 0.02], inf));
//:
~erhythmclick.put(\dur, 1/4);
//:
~erhythmclick.put(\amp, Pseq([0.1, 0.02, 0.02, 0.02], inf));
//:
~erhythmclick.put(\dur, 1);