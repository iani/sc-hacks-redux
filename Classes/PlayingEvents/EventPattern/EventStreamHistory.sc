/* 23 Jan 2021 15:25
Obtain events to be played from an EventStream.

*/

EventStreamHistory {
	classvar >defaultParent;

	var <stream; // the stream that produces the events
	var <proto; // the prototype event producing the event source.
	var <parent; // parent event for the event source	
	var <horizon = 10; // number of future events to look up

	var <event; // event with streams for producing the events to play
	var <past; // array holding all events played so far
	var <present; // the present event
	var <future; // array containing <horizon> events in the future

	*new { | stream, proto, parent, horizon = 10 |
		^this.newCopyArgs(stream,
			proto.copy,
			(parent ?? { this.defaultParent }).copy,
			horizon
		).reset;
	}

	*defaultParent {
		^defaultParent ?? { defaultParent = Event.default.parent.copy };
	}
	
	reset {
		// "next the reset method will be developed".postln;
		event = ();
		event.parent = parent;
		proto keysValuesDo: { | key, value |
			event[key] = value.asStream;
		};
		future = { this.next } ! horizon;
	}

	next {
		var outEvent, outValue;
		outEvent = ();
		outEvent.parent = parent;
		event keysValuesDo: { | key, value |
			outValue = value.next(this);
			if (outValue.isNil) { ^nil };
			outEvent[key] = outValue;
		};
		^outEvent;
	}

	resetIfNeeded {
		if (this.atEnd) { this.reset }
	}

	atEnd {
		// true if the stream has already produced the last event.
	}
}