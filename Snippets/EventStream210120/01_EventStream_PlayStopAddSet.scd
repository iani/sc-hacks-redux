/* 25 Jan 2021 17:45
*/
//:Event:play plays just one event
().play; // plays just one event

//:================================================================
//:Event:splay plays the event as stream
a = (dur: Prand([0.5, 0.2], inf), degree: Pseq((0..14), inf)).splay;
//:test start and stop
a.stop; // Evaluate this to stop the stream:
// Evaluate this to restart the stream:
a.play; // the stream resumes from where it left off.
// NOTE: Cmd-period (stop) resets the stream to its beginning!
//:================================================================
//: Test in a loop: Start and stop an EventStream;
(
{
	var stream;
	stream = (degree: Pseq((0..14), inf), dur: 0.25).splay;
	loop {
		0.5.rrand(1.5).wait;
		stream.stop;
		0.5.rrand(1.5).wait;
		stream.play; // stream resumes from where it was stopped
	};
}.fork;
)
//:Modifying an EventStream while playing
// run the following one line at a time.
~stream = (dur: 0.1).splay; // just durations
~stream add: (degree: Pwhite(-10, 0) + Prand([0, [0, 2]], inf)); // add degree pattern
~stream add: (degree: Pbrown(0, 10, 2)); // replace degree pattern

//:put is alternative way for replacing one key in the Event:
~stream.put(\amp, Pbrown(0, 0.1, 0.01)); // replace amp pattern
~stream.put(\dur, Pbrown(0.01, 0.1, 0.01)); // replace dur pattern

~stream.stop; // test stopping and starting, (again)
~stream.start;

// Add the contents of an Event to the existing stream:
~stream add: (dur: Prand([0.5, 0.05], inf));
//: Replace the contents of an Event in the EventStream
~stream set: (dur: Prand([0.5, 0.05], inf));
//:
currentEnvironment;
~stream.proto;
//:================================================================
//:operators
(degree: Pbrown(0, 10, 1)) *> \stream;
(dur: Prand([0.1, 0.2, 0.4], inf)) +> \stream;
(amp: Pwhite(0.01, 0.2, inf)) +> \stream;
//:
~stream.stop;
~stream.play;
~stream.stop;
~stream.start;