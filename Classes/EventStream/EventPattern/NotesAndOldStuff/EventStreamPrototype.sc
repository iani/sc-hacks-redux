//: 23 Jan 2021 10:21
/*
Recreate an EventStream's event in order to reset it.
This makes it possible to start a stream again after it has ended.
*/

EventStreamPrototype {
	var <>stream, <>proto, <>parent;
	var <>streamHasEnded = false;

	*new { | stream, proto, parent |
		^this.newCopyArgs(stream, proto.copy, parent);
	}

	reset {
		var event;
		event = ();
		event.parent = (parent ?? { Event.default.parent }).copy;
		event[\stream] = stream;
		proto keysValuesDo: { | key, value | event[key] = value.asStream(this); };
		stream.event = event;
		streamHasEnded = false;
	}
	
	streamEnded {
		"noting that my stream ended to reset next time".postln;
		streamHasEnded = true;
	}
	
}