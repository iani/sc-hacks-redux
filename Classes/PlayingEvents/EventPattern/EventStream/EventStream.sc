//: 21 Jan 2021 21:29
/*
Play an event, converting its values to streams.
Features user methods: Start, stop, reset
Also methods to modify the stream:
set, put, add, removeKey, addToParent, setParentKey
These work independently of whether the EventStream is playing or not.
Changes take effect immediately.
*/
EventStream /* : Stream */ { // TODO: review subclassing from Stream.
	classvar >defaultParent;

	var <clock, <quant;
	var <streamPlayer;
	
	*new { | event, parent, tempoClock, quant |
		^super.newCopyArgs(tempoClock, quant).init(event, parent);
	}

	*defaultParent {
		^defaultParent ?? { defaultParent = Event.default.parent.copy };
	}

	init { | argEvent, argParent |
		streamPlayer = SimpleEventStreamPlayer(
			this, argEvent, argParent, clock, quant
		);
		CmdPeriod add: { streamPlayer.cmdPeriod }
	}

	// playing
	reset { this.resetStream }
	resetStream { streamPlayer.history.reset; }

	next { ^streamPlayer.next } // access for playing (in various ways!)

	start { this.play }
	play { streamPlayer.play; }
	// play using a tr synth for timing (instead of own dur stream).
	addTrig { 
		
		
	}
	stop { streamPlayer.stop }

	// access
	event { ^streamPlayer.event } 	// source event
	proto { ^streamPlayer.proto }   // source event's prototype
	atProto { | key | ^this.proto.at(key) } // prototype's value at key
	currentEvent { ^streamPlayer.currentEvent } // currently played event
	parent { ^streamPlayer.parent } // parent

	// Modify prototype event and its stream at any point (also while playing).
	// All of the below are delegated to EventGetter (q.v.).
	set { | inEvent | streamPlayer.set(inEvent); }
	put { | key, value | this add: ().put(key, value); }
	add { | inEvent | streamPlayer.add(inEvent); }
	removeKey { | key | streamPlayer.removeKey(key); }
	addToParent { | argEvent | streamPlayer addToParent: argEvent; }
	setParentKey { | key, value | streamPlayer.setParentKey(key, value); }
}