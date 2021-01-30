//: 21 Jan 2021 21:29
/*
Play an event, converting its values to streams.
Play with methods: start, stop, reset
Modify the stream before or during playing with methods:
set, put, add, removeKey, addToParent, setParentKey.
Changes take effect immediately.
*/
EventStream { // intentionally not a subclass of Stream
	classvar >defaultParent; // private copy for non-interference with lib default
	var <streamPlayer, <triggers;

	*new { | event, parent, tempoClock, quant |
		^super.new().init(event, parent, tempoClock, quant);
	}

	*defaultParent {
		^defaultParent ?? { defaultParent = Event.default.parent.copy };
	}

	init { | argEvent, argParent, argClock, argQuant |
		triggers = IdentityDictionary();
		streamPlayer = SimpleEventStreamPlayer(
			this, argEvent, argParent, argClock, argQuant
		);
		CmdPeriod add: { streamPlayer.cmdPeriod }
	}

	// ================ playing
	reset { this.resetStream }
	resetStream { streamPlayer.history.reset; }
	playNext { this.next.play } // avoid this while playing with routine
	next { ^streamPlayer.next } // get next event (for playing)
	start { this.play }
	play { streamPlayer.play; }
	stop { streamPlayer.stop }
	// play using a tr synth for timing (instead of own dur stream).
	addTrig { | key = \default |
		 triggers[key] = OscTrig.fromLib(key);
		
	}
	removeTrig { | trigKey |

	}
	addTrigSynth { | playFunc, trigKey, synthKey |

	}

	// ================ access
	event { ^streamPlayer.event } 	// source event
	proto { ^streamPlayer.proto }   // source event's prototype
	atProto { | key | ^this.proto.at(key) } // prototype's value at key
	currentEvent { ^streamPlayer.currentEvent } // currently played event
	parent { ^streamPlayer.parent } // parent

	// ================ modifying
	set { | inEvent | streamPlayer.set(inEvent); }
	put { | key, value | this add: ().put(key, value); }
	add { | inEvent | streamPlayer.add(inEvent); }
	removeKey { | key | streamPlayer.removeKey(key); }
	addToParent { | argEvent | streamPlayer addToParent: argEvent; }
	setParentKey { | key, value | streamPlayer.setParentKey(key, value); }
}