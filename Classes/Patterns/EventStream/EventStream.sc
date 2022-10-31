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

	asEvenStream { ^this }

	restart { // to make Symbol:start also work with Synths.
		this start: nil; // being explicit ...
	}
	start { | quant |
		if (this.isRunning) { ^postf("% is running. will not restart it\n", this) };
		this.makeRoutine(quant);
	}

	free { this.stop }

	stop {
		routine.stop;
		routine = nil;
	}

	makeRoutine { | quant |
		var nextEvent;
		CmdPeriod add: this;
		routine = {
			this.changed(\started);
			while {
				(nextEvent = this.getNextEvent).notNil;
			}{
				this.playAndNotify(nextEvent);
				nextEvent[\dur].wait;
			};
			this.changed(\stopped);
			routine = nil;
			this.reset;
		}.fork( // synchronize start
			event[\clock] ? TempoClock.default,
			quant ?? { event[\quant] }
		);
	}

	playNext {
		this.playAndNotify(this.getNextEvent);
	}

	playAndNotify { | inEvent |
		inEvent.play;
		this.changed(\played, inEvent, this);
	}

	getNextEvent {
		var nextEvent, nextValue;
		nextEvent = ().parent_(event.parent);
		// Make the stream event available to any functions running in it
		stream use: {
			stream keysValuesDo: { | key, value |
				nextValue = value.next;
				if (nextValue.isNil) {
					// postf("% has ended\n", this);
					^nil
				};
				nextEvent[key] = nextValue;
			};
		};
		^nextEvent;
	}

	isRunning { ^routine.notNil }

	cmdPeriod { routine = nil; }

	setEvent { | inEvent |
		this mergeEvent: inEvent;
		if (this.isRunning.not) { this.start; };
	}

	// suggestion T.M: method name should be: mergeEvent?
	mergeEvent { | inEvent |
		inEvent keysValuesDo: { | key, value |
			event[key] = value;
			stream[key] = value.asStream;
		}
	}

	addSubEvent { | subEvent, key |
		if (event[\play].isKindOf(SubStream).not) { SubStream(this) };
		event[\play].addSubStream(subEvent, key);
	}

	set { | param, value | // compatibility with <+
		this.mergeEvent(().put(param, value))
	}

}