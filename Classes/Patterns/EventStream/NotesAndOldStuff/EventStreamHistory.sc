/* 23 Jan 2021 15:25
Obtain events to be played from an EventStream.

This version computes several events ahead of time.

Advantage: the present event has access to future events and can calculate
things like glissando or other phrases from them.

Disadvantage: Any changes to the event will only be reflected after the precomputed events have played.
Disadvantage: Changes made to the stream while it is playing take
effect only after the already pre-computed events have past. 

*/

EventStreamHistory {
	classvar >defaultParent;
	classvar <>horizon = 10; // number of future events to look up

	var <stream; // the stream that produces the events
	var <proto; // the prototype event producing the event source.
	var <parent; // parent event for the event source	

	var <event; // event with streams for producing the events to play
	var <past; // array holding all events played so far
	var <present; // the present event
	var <future; // array containing <horizon> events in the future

	*new { | stream, proto, parent |
		^this.newCopyArgs(stream,
			proto.copy,
			(parent ?? { this.defaultParent }).copy
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
		future = { this.getNextFromEvent } ! horizon;
	}

	getNextFromEvent {
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

	next {
		present = future[0];
		if (present.isNil) {
			"the end of the stream has been reached".postln;
		}{  // only record non-nil events
			future = future rotate: -1;
			future[future.size - 1] = this.getNextFromEvent;
			past = past add: present;
		};
		^present;
	}

	resetIfNeeded {
		if (this.atEnd) { this.reset }
	}

	atEnd {
		// true if the stream has already produced the last event.
		^this.eventsInFuture == 0;
	}

	eventsInFuture {
		// find how many events are left to the first nil event in the future
		^future indexOf: nil;		
	}

}