/* 21 Feb 2022 13:14
Redo EventStream, removing all extras.
*/

EventStream2 {
	var <event, <stream, <routine;

	*new { | event |
		^this.newCopyArgs(event).reset;
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
		var nextEvent, nextValue, seenNil;
		CmdPeriod add: this;
		routine = {
			this.changed(\started);
			while {
				nextEvent = ().parent_(event.parent);
				seenNil = false;
				stream keysValuesDo: { | key, value |
					nextValue = value.next;
					seenNil = nextValue.isNil;
					if (seenNil.not) {
						nextEvent[key] = nextValue;
					}
				};
				if (seenNil.not) {
					nextEvent.play;
					this.changed(\played);
				};
				seenNil.not;
			}{
				nextEvent[\dur].wait;
			};
			this.changed(\stopped);
			routine = nil;
			this.reset;
		}.fork;
	}

	reset { this.makeStream }

	makeStream {
		stream = ().parent_(event.parent);
		event keysValuesDo: { | key, value |
			stream[key] = value.asStream;
		}
	}

	isRunning { ^routine.notNil }

	cmdPeriod { routine = nil; }

	addEvent { | inEvent |
		inEvent keysValuesDo: { | key, value |
			event[key] = value;
			stream[key] = value.asStream;
		}
	}
}