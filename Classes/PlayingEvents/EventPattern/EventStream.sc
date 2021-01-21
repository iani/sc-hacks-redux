//: 21 Jan 2021 21:29
/*
Reworking EventStream from sc-hacks.
Maybe this does not have to be a Stream.  We shall see later.
*/
EventStream /* : Stream */ { // TODO: review subclassing from Stream.
	var <>event, <>eventStreamParent;
	*new { | event, parent |
		^super.new.initEventStream(event, parent);
	}

	initEventStream { | inEvent, inParent |
		eventStreamParent = (inParent ? Event.getDefaultParentEvent).copy;
		event = ();
		event.parent = eventStreamParent;
		inEvent keysValuesDo: { | key, value | event[key] = value.asStream(this); };
		// event.initParent(eventStreamParent);
		// "================================================================".postln;
		// event.postln;
		// event.parent.postln;
	}

	setParentKey { | key, value |
		eventStreamParent[key] = value;
	}
	
	// 21 Jan 2021 21:16 simplified version. See below for quoted earlier version
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
	
	// 21 Jan 2021 21:15 substituted simpler version above
	/*
	next { | inEvent |
		// If inEvent is provided, add its contents,
		// filtering them through own values.
		var outEvent, outValue;

		if (inEvent.isNil) {
			"not using inEvent".postln;
			outEvent = ();
			event keysValuesDo: { | key, value |
				outValue = value.next(this);
				if (outValue.isNil) { ^nil };
				outEvent[key] = outValue;
			}
		}{
			// Use inEvent as main event to play,
			// and then filter any of its values through the present event
			"USING INEVENT".postln;
			postf("my inevent is: %\n", inEvent);
			postf("its parent is: %\n", inEvent.parent);
			outEvent = inEvent.copy;
			outEvent use: { //  evaluate using outEvent as environment
				// makes outEvent values available as environmentVariables
				event keysValuesDo: { | key value |
					outValue = value.(this);
					if (outValue.isNil) { ^nil };
					outEvent [key] = outValue;
				}
			}
		};
		postf("The outevent produced is: %\n", outEvent);
		^outEvent;
	}
	*/
	
	addEvent { | inEvent |
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

	/* Needed to embed an EventPattern in a Stream as in: 
		Pseq([EventPattern((degree: (1..8).pseq(2)))]).play;
	*/
	embedInStream { arg inval;
		var outval;
		// this.changed(\started); // Put this in when the need arises.
		while {
			outval = this.next;
			outval.notNil;
		}{
			outval.yield;
		};
		// this.changed(\stopped); // Put this in when the need arises.
		nil;
	}

	// experimenting, trying to understand ... 
	play2 {
		var myevent;
		postf("% starting to build playing mechanism from scratch\n", this);
		postf("this is what happens when I call next to self: %\n",
			myevent = this.next);
		^myevent;
		/* 
			per default, myevent has no parent.
			myevent.play however provides defaultParentEvent as parent.
		*/
		
	}
}