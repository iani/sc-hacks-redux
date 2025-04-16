/*
NOTE: 22 Jan 2021 21:50 Note: EventPattern is now obsolete. 
One can play EventStreams with events containing patterns directly, and no features are missing



EventPattern can be embedded in another Pattern, 
in similar way as Pdef can be embedded in a Pseq (see Pdef help entry).

IZ Mon, Apr 21 2014, 09:58 EEST

*/

EventPattern : Pattern {
	var <>event; // contains patterns

	*new { | event | ^this.newCopyArgs(event ?? { () }) }

	asStream { ^EventStream(event) }

	pattern { ^this }

	mergeEvent { | inEvent | // also accepts key-value pairs in Arrays
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


