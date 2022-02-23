/* 21 Feb 2022 13:14
Redo EventStream, removing all extras.
*/

EventStream {
	var <event, <stream, <routine;

	*new { | event |
		^this.newCopyArgs(event).reset;
	}

	reset { this.makeStream }

	makeStream {
		stream = ().parent_(event.parent);
		event keysValuesDo: { | key, value |
			stream[key] = value.asStream;
		}
	}

	start {
		// postf("troubleshooting. isRunnign? %\n", this.isRunning);
		if (this.isRunning) { ^postf("% is running. will not restart it\n", this) };
		this.makeRoutine;
	}

	stop {
		routine.stop;
		routine = nil;
	}

	makeRoutine {
		var nextEvent;
		CmdPeriod add: this;
		routine = {
			this.changed(\started);
			while {
				(nextEvent = this.getNextEvent).notNil;
			}{
				nextEvent.play;
				this.changed(\played);
				nextEvent[\dur].wait;
			};
			this.changed(\stopped);
			routine = nil;
			this.reset;
		}.fork;
	}

	getNextEvent {
		var nextEvent, nextValue;
		nextEvent = ().parent_(event.parent);
		stream keysValuesDo: { | key, value |
			nextValue = value.next;
			if (nextValue.isNil) {
				postf("% has ended\n", this);
				^nil
			};
			nextEvent[key] = nextValue;

		};
		^nextEvent;
	}

	isRunning { ^routine.notNil }

	cmdPeriod { routine = nil; }

	addEvent { | inEvent |
		inEvent keysValuesDo: { | key, value |
			event[key] = value;
			stream[key] = value.asStream;
		}
	}

	addBeat { | beat |
		this.addNotifier(beat.beat, \beat, {
			this.getNextEvent.play;
		});
	}

	removeBeat { | beat | this.removeNotifier(beat.beat, \beat); }

	// This adds a listener to all trigger messages. To listen to just
	// one node, there should be a way to make OSC send a custom message
	// bound to one synth environment variable - even when the synth is replaced.
	// See method addSymbolTr below
	addTr {
		this.addNotifier(OSC, '/tr', { this.getNextEvent.play });
	}
	removeTr { this.removeNotifier(OSC, '/tr'); }

	// TODO: Test this and rename to addTr
	addSymbolTr { | symbol = \synth |
		// EXPERIMENTAL! Track updates from synths starting
		// in environment variable named by symbol
		this.addNotifier(symbol, \synth, { | n, msg |
			var id;
			id = msg; // msg[1];
			postf("% received 'synth' from %. new id is: %\n",
				this, symbol, id
			);
			this.addNotifier(OSC, '/tr', { | n, msg |
				postf("received /tr with msg %. id is: %, needs to match id %\n",
					msg, msg[1], id
				);
				if (msg[1] == id) {
					this.getNextEvent.play
				}
			});
		})
	}

}