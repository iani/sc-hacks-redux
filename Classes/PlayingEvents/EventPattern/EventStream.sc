//: 21 Jan 2021 21:29
/*
Reworking EventStream from sc-hacks.
Maybe this does not have to be a Stream.  We shall see later.
*/
EventStream /* : Stream */ { // TODO: review subclassing from Stream.
	var <streamPlayer;
	
	*new { | event, parent, tempoClock |
		^super.new.init(event, parent, tempoClock);
	}

	init { | inEvent, inParent, clock |
		streamPlayer = SimpleEventStreamPlayer(this, inEvent, inParent, clock);
		CmdPeriod add: { streamPlayer.cmdPeriod }
	}

	reset { this.resetStream }
	resetStream { streamPlayer.history.reset; }

	next { ^streamPlayer.next }

	start { this.play }
	play { streamPlayer.play; }

	stop { streamPlayer.stop }
}