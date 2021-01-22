//: 21 Jan 2021 21:29
/*
Reworking EventStream from sc-hacks.
Maybe this does not have to be a Stream.  We shall see later.
*/
EventStream /* : Stream */ { // TODO: review subclassing from Stream.
	var <>event, <>eventStreamParent;
	var <tempoClock;
	var <past; // array containing all events produced by playing this stream.
	var <present; // the present event;
	var <future; // array containing <horizon> events in the future
	var <horizon = 10; // number of future events to look up
	var <isRunning = false;
	var <restartCache;

	// Debugging state with many flags;
	var state = \waitingToStart;
	
	*new { | event, parent, tempoClock |
		^super.new.init(event, parent, tempoClock);
	}

	init { | inEvent, inParent, clock |
		this.restartCache_(inEvent, inParent, clock);
		this.initEventStream(inEvent, inParent, clock);
		CmdPeriod add: { this.resetStream }
	}

	restartCache_ { | inEvent, inParent, clock |
		restartCache = [inEvent, inParent, clock];
	}

	resetStream {
		this.initEventStream(*restartCache);
		isRunning = false;
		this.changed(\reset);
	}

	initEventStream { | inEvent, inParent, clock |
		tempoClock = clock ?? { TempoClock.default };
		eventStreamParent = (inParent ? Event.getDefaultParentEvent).copy;
		eventStreamParent[\stream] = this;
		event = ();
		event.parent = eventStreamParent;
		inEvent keysValuesDo: { | key, value | event[key] = value.asStream(this); };
	}

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

	// experimenting, trying to understand ... 
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

	stop {
		isRunning = false;
		this.changed(\stopped);
		state = \stopped;
		postf("stop received. present is: %\n", present);
		
	}

	// ================================================================
	// extentions, features etc.
	add { | inEvent |
		inEvent keysValuesDo: { | key, value |
			event[key] = value.asStream;
			if (key === \degree) {
				event[\freq] = nil;
				event[\note] = nil;
			};
			if (key === \note) {
				event[\freq] = nil;
			}
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
}