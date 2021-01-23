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

	// if userStopped, then only run reset if the stream has already 
	//	reached its end.
	var <wasInterrupted = false;

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
		"Was told to reset but will check if interrupted to decide".postln;
		if (wasInterrupted) {
			"WAS NOT INTERRUPTED AND WILL THEREFORE NOT RESET".postln;
		}{
			"was not interrupted and  will therefore reset!".postln;
			sourceEvent = ().parent = parent;
			proto keysValuesDo: { | key, value |
				sourceEvent[key] = value.asStream;
			};
		};
	}

	next {
		var next;
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
	
	userStopped { wasInterrupted = true }

	atEnd { ^currentEvent.isNil; }
}
