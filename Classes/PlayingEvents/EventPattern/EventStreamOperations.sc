//: 23 Jan 2021 09:07
/*
	Classes needed for EventStream to handle start, stop, reset,
	and other auxiliary stuff.

	To clarify code, perhaps delegate the following vars + functionalities
	to separate classes: 

	EventStreamCache (containing event, eventStreamParent, tempoClock)

	EventStreamHistory (containing past, present, future, horizon)

	EventStreamOperations (containing isRunning, streamHasFinished, userHasStopped)

	var <past; // array containing all events produced by playing this stream.
	var <present; // the present event;
	var <future; // array containing <horizon> events in the future
	var <horizon = 10; // number of future events to look up
	var <restartCache;

	var <isRunning = false; // play and stop change this
	var <streamHasFinished = true; // initEventStream sets this to false
	var <userHasStopped = false; // stop changes this

*/

EventStreamOperations {
	var <stream;
	var <tempoClock;
	var <userStopped = false;
	var <isRunning = false;

	var <prototype; // recreate event when reset is needed to start anew

	*new { | stream, event, parent, tempo |
		^this.newCopyArgs(stream, tempo ?? { TempoClock.default })
		.init(event, parent);
	}

	init { | event, parent |
		prototype = EventStreamPrototype(stream, event, parent);
		prototype.reset;
	}

	play {
		if (isRunning) { ^"Stream already playing".postln; };
		isRunning = true;
		stream.resetIfNeeded;
		tempoClock.sched(0, {
			var nextEvent;
			nextEvent = stream.next.postln.play;
			stream.changed(\played, nextEvent);
			if ( nextEvent.notNil ) { nextEvent.dur } {
				prototype.streamEnded;
			};
		})
	}
	
	parent { ^prototype.parent }

}