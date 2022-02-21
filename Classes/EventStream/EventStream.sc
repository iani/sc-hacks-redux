//: 21 Jan 2021 21:29
/*
Play an event, converting its values to streams.
Play with methods: start, stop, reset
Modify the stream before or during playing with methods:
set, put, add, removeKey, addToParent, setParentKey.
Changes take effect immediately.
*/
EventStream { // intentionally not a subclass of Stream
	classvar >defaultParent; // avoid modifying default from SCClassLibrary
	var <streamPlayer, <triggers;
	/*
	clone {
		// not yet tested
		// TODO : clone currentEvent of my SimpleEventStreamPlayer?
		^this.class.new(this.event, this.parent, this.quant, this.tempoClock);
	}
	*/
	
	*new { | event, parent, quant = 1, tempoClock |
		^super.new().init(event, parent, quant, tempoClock);
	}

	*defaultParent { // avoid modifying default from SCClassLibrary!
		^defaultParent ?? { defaultParent = Event.default.parent.copy };
	}

	init { | argEvent, argParent, argQuant, argClock |
		triggers = IdentityDictionary();
		streamPlayer = SimpleEventStreamPlayer(
			this, argEvent, argParent, argQuant ? 1, argClock
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
	// ==== play using a tr synth for timing (instead of own dur stream).
	addTrig { | key = \default, action |
		var trig;
		trig = OscTrig.fromLib(key);
		trig.addListener(this, action ?? {{ this.playNext }});
		triggers[key] = trig;
		^trig;
	}
	removeTrig { | key = \default |
		var trig;
		if ((trig = triggers[key]).notNil) {
			this.removeNotifier(trig);
			triggers[key] = nil;
		}
	}
	trigSynth { | source, trigKey = \default, synthKey = \default |
		// NOTE: Perhaps better use a method like:
		// \trigKey.trigSynth(source, synthKey)!
		this.addTrig(trigKey).addSynth(source, synthKey);
	}
	remSynth { | trigKey = \default, synthKey = \default |
		// TODO: Implement this
		// remove synth - best done perhaps via OscTrig directly?
		"remSynth not yet implemented".postln;
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
	quant_ { | quant = 1 | streamPlayer.quant = quant }
	isPlaying { ^streamPlayer.isRunning }
}
