//: 23 Jan 2021 09:07
/*
	Classes needed for EventStream to handle start, stop, reset,
	and other auxiliary stuff.

	To clarify code, perhaps delegate the following vars + functionalities
	to separate classes 

	var <restartCache;
	var <isRunning = false; // play and stop change this
	var <streamHasFinished = true; // initEventStream sets this to false
	var <userHasStopped = false; // stop changes this

*/

SimpleEventStreamPlayer {
	var <stream;
	var <tempoClock;
	var <isRunning = false;

	var <getter; // get events from stream. reset stream if needed when starting

	*new { | stream, event, parent, tempo |
		^this.newCopyArgs(stream, tempo ?? { TempoClock.default })
		.init(event, parent);
	}

	init { | event, parent |
		getter = EventGetter(stream, event, parent);
		getter.reset;
	}

	play {
		if (isRunning) { ^"Stream already playing".postln; };
		isRunning = true;
		getter.resetIfNeeded;
		tempoClock.sched(0, {
			var nextEvent;
			if (isRunning) { // isRunning may be set by user 
				nextEvent = getter.next.postln.play;
				stream.changed(\played, nextEvent);
				if ( nextEvent.notNil ) { nextEvent.dur } {
					this.stopped;
				};
			}
		})
	}

	stop { this.stopped }
	stopped {
		isRunning = false;
		stream.changed(\stopped);
	}

	parent { ^getter.parent }
	next { ^getter.next }
}