/*
EventPattern can be embedded in another Pattern, 
in similar way as Pdef can be embedded in a Pseq (see Pdef help entry).

IZ Mon, Apr 21 2014, 09:58 EEST

*/

EventPattern : Pattern {
	var <>event; // contains patterns

	*new { | event | ^this.newCopyArgs(event ?? { () }) }

	asStream { ^EventStream(event) }

	pattern { ^this }

	addEvent { | inEvent | // also accepts key-value pairs in Arrays
		// Add event's keys/values and also update the event
		// of the currently playing stream.
		inEvent keysValuesDo: { | key value |
			event[key] = value;
			if (key === \degree) {
				event[\freq] = nil;
				event[\note] = nil;
			};
			if (key === \note) {
				event[\freq] = nil;
			}
		}
	}
	
	put { | key, value |
		event[key] = value
	}
}


