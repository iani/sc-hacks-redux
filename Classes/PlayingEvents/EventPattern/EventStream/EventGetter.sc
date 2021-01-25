/* 23 Jan 2021 15:25
Obtain events to be played from an EventStream.
*/

EventGetter {
	classvar >defaultParent;
	classvar <>horizon = 10; // number of future events to look up

	var <stream; // the stream that produces the events
	var <proto; // the prototype event producing the event source.
	var <parent; // parent event for the event source	

	var <sourceEvent; // event with streams for producing the events to play
	var <currentEvent; // event last accessed from sourceEvent through 'next'

	// if stopped, then only run reset if the stream has already 
	//	reached its end.
	var <wasInterrupted = false; // if true, restart without resetting
	var <nextEvent; // lookahead needed to reset when interrupted at last event

	*new { | stream, proto, parent |
		^this.newCopyArgs(stream,
			proto.copy,
			(parent ?? { this.defaultParent }).copy
		);
	}

	*defaultParent {
		^defaultParent ?? { defaultParent = Event.default.parent.copy };
	}


	reset {
		if (wasInterrupted) {
		}{
			sourceEvent = ().parent = parent;
			proto keysValuesDo: { | key, value |
				sourceEvent[key] = value.asStream;
			};
		};
		wasInterrupted = false; // !!!
	}

	next {
		var next;
		if (nextEvent.notNil) {
			next = nextEvent;
			nextEvent = nil;
			^next;
		};
		next = this.prNext;
		if (wasInterrupted) {
			next ?? {
				this.reset;
				next = this.prNext;
			};
			wasInterrupted = false;
		}
		^next;
	}

	prNext {
		var outValue;		
		currentEvent = ();
		currentEvent.parent = parent;
		sourceEvent keysValuesDo: { | key, value |
			outValue = value.next(this);
			if (outValue.isNil) { ^currentEvent = nil };
			currentEvent[key] = outValue;
		};
		^currentEvent;		
	}
	
	userStopped {
		wasInterrupted = true;
		 // ensure reset if at end of stream:
		nextEvent = this.prNext;
		if (nextEvent.isNil) {
			this.reset;
			nextEvent = this.prNext;
		}
	}

	atEnd { ^currentEvent.isNil; }
}
