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
		sourceEvent = ();
		sourceEvent.parent = parent;
		proto keysValuesDo: { | key, value | sourceEvent[key] = value.asStream; };
	}

	next {
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

	resetIfNeeded { if (this.atEnd) { this.reset } }

	atEnd { ^currentEvent.isNil; }
}
