/* 25 Jan 2021 17:45
*/
//:Event:play plays just one event
().play; // plays just one event

//:================================================================
//:Event:splay plays the event as stream
a = (dur: Prand([0.5, 0.2], inf), degree: Pseq((0..14), inf)).splay;
// Execute this to stop the stream:
a.stop;
// Execute this to restart the stream:
a.play; // the stream resumes from where it left off.
// NOTE: Cmd-stop resets the stream to its beginning!
//:Start and stop an EventStream;
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
~stream = (dur: 0.1).splay;
~stream add: (degree: Pwhite(-10, 0));
~stream add: (degree: Pbrown(0, 10, 2));
~stream.put(\amp, Pbrown(0, 0.1, 0.01));
~stream.put(\dur, Pbrown(0.01, 0.1, 0.01));
~stream.stop;
~stream.start;
~stream add: (dur: Prand([0.5, 0.05], inf));
~stream set: (dur: Prand([0.5, 0.05], inf));
