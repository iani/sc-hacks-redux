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
				this.playAndNotify(nextEvent);
				// nextEvent.play;
				// this.changed(\played);
				nextEvent[\dur].wait;
			};
			this.changed(\stopped);
			routine = nil;
			this.reset;
		}.fork;
	}

	playAndNotify { | inEvent |
		inEvent.play;
		this.changed(\played, inEvent, this);
	}

	getNextEvent {
		var nextEvent, nextValue;
		nextEvent = ().parent_(event.parent);
		// run this within the stream environment,
		// to make the stream event available to any functions running in it?
		stream use: {
			stream keysValuesDo: { | key, value |
				nextValue = value.next;
				if (nextValue.isNil) {
					postf("% has ended\n", this);
					^nil
				};
				nextEvent[key] = nextValue;

			};
		} // stream use: EXPERIMENTAL
		^nextEvent;
	}

	isRunning { ^routine.notNil }

	cmdPeriod { routine = nil; }

	setEvent { | inEvent |
		this addEvent: inEvent;
		this.start;
	}

	addEvent { | inEvent |
		inEvent keysValuesDo: { | key, value |
			event[key] = value;
			stream[key] = value.asStream;
		}
	}

	set { | param, value | // compatibility with <+
		this.addEvent(().put(param, value))
	}

	addBeat { | beat |
		this.addNotifier(beat.beat, \beat, {
			this.playAndNotify(this.getNextEvent);
		});
	}

	removeBeat { | beat | this.removeNotifier(beat.beat, \beat); }

	// Trigger the next event when receiving message from OSC./
	// Use with Symbol:makeTrig.
	// THIS VERSION IGNORES SUBSEQUENT STREAM MODIFICATIONS
	// USE Symbol.addTr instead ...
	/*
	addTr { | message = \trigger |
		this.addNotifier(OSC, message.asOscMessage, {
			// TODO: DEBUG WHY STREAM MODIFICATIONS DO NOT WORK HERE
			// Add extra debug statements to follow the contents
			// of event and stream before and after getNextEvent
			this.getNextEvent.play;
		});
	}
	removeTr { | message = \trigger |
		this.removeNotifier(OSC, message.asOscMessage);
	}
	*/
}