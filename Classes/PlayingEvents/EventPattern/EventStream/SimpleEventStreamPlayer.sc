//: 23 Jan 2021 09:07
// handle start, stop, reset, for EventStream

/* 
FIXME: using TempoClock.new(quant) issues a warning could not set 
scheduler priority and then issues an error 
if a cmdPeriod has been to interrupt playing previously. : 
In method *new : ...
	^this.newCopyArgs(stream, tempo ?? { TempoClock.new(quant) })

NOTE: The default value for quant is set to nil, which means there will be no synchronization in the start of players. One can synchronize by providing
a value for the quant argument in the new method.
*/

SimpleEventStreamPlayer {
	var <stream;
	var <>quant;
	var <tempoClock;
	var <getter; // get events from stream. reset stream if needed when starting
	var <routine;

	var <currentEvent;
	
	*new { | stream, event, parent, quant, tempo |
		^this.newCopyArgs(stream, quant, tempo ?? { TempoClock.default })
		.init(event, parent);
	}

	init { | event, parent | getter = EventGetter(stream, event, parent) }

	// ================================================================
	// playing

	play {
		if (this.isRunning) { ^"Stream already playing".postln; };
		getter.resetIfNotInterrupted;
		routine = {
			while {
				(currentEvent = getter.next.play).notNil;
			}{
				stream.changed(\played, currentEvent);
				currentEvent.dur.wait;
			};
			this.stopped;
		}.r.play(tempoClock, quant);
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

	next { ^getter.next }

	// ================================================================
	// access
	proto { ^getter.proto}
	source { ^getter.sourceEvent }
	parent { ^getter.parent }

	// Modify prototype event and its stream at any point (also while playing).
	// All of the below are delegated to EventGetter (q.v.).
	set { | inEvent | getter.set(inEvent); }
	add { | inEvent | getter.add(inEvent); }
	removeKey { | key | getter.removeKey(key); }
	addToParent { | key, value | getter.addToParent(key, value); }
}