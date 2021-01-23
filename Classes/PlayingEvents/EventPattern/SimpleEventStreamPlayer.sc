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
	var <getter; // get events from stream. reset stream if needed when starting
	var <routine;

	var <currentEvent;
	
	*new { | stream, event, parent, tempo |
		^this.newCopyArgs(stream, tempo ?? { TempoClock.default })
		.init(event, parent);
	}

	init { | event, parent | getter = EventGetter(stream, event, parent) }
	
	play {
		if (this.isRunning) { ^"Stream already playing".postln; };
		routine = {
			while {
				(currentEvent = getter.next.play).notNil;
			}{
				stream.changed(\played, currentEvent);
				currentEvent.dur.wait;
			};
			this.stopped;
		}.play(tempoClock);
	}

	isRunning { ^routine.notNil }

	stop { // For control by user while running
		routine !? {
			routine.stop;
			getter.userStopped;
		};
		this.stopped;
	}
	
	stopped {
		routine = nil;
		stream.changed(\stopped);
	}

	cmdPeriod { this.stopped; }

	parent { ^getter.parent }
	next { ^getter.next }
}