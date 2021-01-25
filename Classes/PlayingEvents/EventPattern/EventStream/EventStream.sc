//: 21 Jan 2021 21:29
/*
Reworking EventStream from sc-hacks.
Maybe this does not have to be a Stream.  We shall see later.
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

	next { ^streamPlayer.next } // access for playing (in various ways)

	start { this.play }
	play { streamPlayer.play; }
	stop { streamPlayer.stop }

	// access
	event { ^streamPlayer.event } 	// source event
	proto { ^streamPlayer.proto }   // source event's prototype
	atProto { | key | ^this.proto.at(key) }
	// putProto { | key, value | this.proto.}
	currentEvent { ^streamPlayer.currentEvent } // currently played event
	parent { ^streamPlayer.parent }

	// Modify prototype event and its stream at any point (also while playing).
	// All of the below are delegated to EventGetter (q.v.).
	set { | inEvent | streamPlayer.set(inEvent); }
	put { | key, value | this add: ().put(key, value); }
	add { | inEvent | streamPlayer.add(inEvent); }
	removeKey { | key | streamPlayer.removeKey(key); }
	addToParent { | argEvent | streamPlayer addToParent: argEvent; }
	setParentKey { | key, value | streamPlayer.setParentKey(key, value); }
}