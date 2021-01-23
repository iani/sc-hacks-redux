//: 21 Jan 2021 21:29
/*
Reworking EventStream from sc-hacks.
Maybe this does not have to be a Stream.  We shall see later.
*/
EventStream /* : Stream */ { // TODO: review subclassing from Stream.
	var <streamOperations;
	
	*new { | event, parent, tempoClock |
		^super.new.init(event, parent, tempoClock);
	}

	init { | inEvent, inParent, clock |
		streamOperations = SimpleEventStreamPlayer(this, inEvent, inParent, clock);
		CmdPeriod add: { this.stop }
	}

	reset { this.resetStream }
	resetStream { streamOperations.history.reset; }

	next { ^streamOperations.next }

	start { this.play }
	play { streamOperations.play; }

	stop { streamOperations.stop }

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