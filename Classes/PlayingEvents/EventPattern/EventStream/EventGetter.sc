/* 23 Jan 2021 15:25
Obtain events to be played from an EventStream.
*/

EventGetter {
	// this should go to class EventHistory:
	// classvar <>horizon = 10; // number of future events to look up

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
			parent.asParent
		).reset;
	}

	reset { sourceEvent = proto.makeStream(parent) }

	resetIfNotInterrupted { // do not reset if resuming from user stop
		if (wasInterrupted) {}{ this.reset };
		wasInterrupted = false; // !!!
	}

	clear { 
		proto = ().parent = parent.asParent;
		this.reset;
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
		^currentEvent = sourceEvent.makeNext(parent);
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

	// ================================================================
	// modifying contents while running

	// ================
	// 1: Modify prototype. Also add the streams of all new keys sourceEvent
	// modified keys 
	set { | argEvent |
		// set proto to argEvent and update sourceEvent
		proto = argEvent.copy.parent = parent;
		this.reset;
	}

	addEventStreams { | argEvent |
		sourceEvent !? { sourceEvent.addStreams(argEvent) };
	}
	
	add { | argEvent |
		// add all keys-values of argEvent to proto.
		// set proto to argEvent and update sourceEvent
		proto = argEvent.copy.parent = parent;
		this addEventStreams: proto;
	}

	removeKey { | key |
		proto.put(key, nil);
		sourceEvent.put(key, nil);
	}
	// ================
	// 2: Modify parent.
	// Events having this parent automatically inherit new contents.

	addToParent { | argEvent | // add all keys-values of argEvent to parent.
		parent addEvent: argEvent;
	}

	setParentKey { | key, value | parent[key] = value; }
}
