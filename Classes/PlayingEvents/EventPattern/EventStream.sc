//: 21 Jan 2021 21:29
/*
Reworking EventStream from sc-hacks.
Maybe this does not have to be a Stream.  We shall see later.
*/
EventStream /* : Stream */ { // TODO: review subclassing from Stream.
	var <>event;
	var <>eventStreamParent; // 
	// var <tempoClock; moved to streamOperations
	var <isRunning = false;

	var <streamOperations;
	
	// Debugging state with many flags;
	var state = \waitingToStart;
	
	*new { | event, parent, tempoClock |
		^super.new.init(event, parent, tempoClock);
	}

	init { | inEvent, inParent, clock |
		streamOperations = EventStreamOperations(this, inEvent, inParent, clock);
		CmdPeriod add: { /* this.resetStream */ }
	}

	resetStream {

	}

	initEventStream { | inEvent, inParent, clock |
		eventStreamParent = (inParent ? Event.getDefaultParentEvent).copy;
		eventStreamParent[\stream] = this;
		event = ();
		event.parent = eventStreamParent;
		inEvent keysValuesDo: { | key, value | event[key] = value.asStream(this); };
	}

	/*
	next {
		var outEvent, outValue;
		outEvent = ();
		outEvent.parent = eventStreamParent;
		event keysValuesDo: { | key, value |
			outValue = value.next(this);
			if (outValue.isNil) { ^nil };
			outEvent[key] = outValue;
		}
		^outEvent;
	}
	*/

	embedInStream { arg inval;
		var outval;
		while {
			outval = this.next;
			outval.notNil;
		}{
			outval.yield;
		};
		nil;
	}

	play {
		streamOperations.play;
	}

	resetIfNeeded {
		"I will check if I need to reset.".postln;
	}

	// experimenting, trying to understand ... 
	/*
	play {
		if (isRunning) { ^postf("% is already running\n", this) };
		isRunning = true;
		this.changed(\started);
		tempoClock.sched(0, {
			if (isRunning) {
				present = this.next.play;
				// maybe move this to 'stopped'?
				if (present.isNil) {
					state = \streamHasFinished;
					this.resetStream
				};
			} {
				present = nil
			};
			this.changed(\played, present);
			if ( present.notNil ) { present.dur } {
				this.stop;
			};
		});
	}
	*/
	stop {
		//		isRunning = false;
		this.changed(\stopped);
		state = \stopped;
		// postf("stop received. present is: %\n", present);
		
	}

	// ================================================================
	// extensions, features etc.
	/*
	add { | inEvent |
		inEvent keysValuesDo: { | key, value |
			event[key] = value.asStream;
		}
	}

	set { | inEvent |
		// inherit parent
		inEvent.parent = eventStreamParent;
		event = inEvent;
	}
	setParentKey { | key, value |
		eventStreamParent[key] = value;
	}
	*/
}