/* 21 Feb 2022 13:14
Redo EventStream, removing all extras.
*/

EventStream {
	var <event, <stream, <routine;
	var <>beatFilter = true; // select beats from BeatCounter to play;

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

	// adding self as dependant to BeatCounter is a safe simple
	// way to add / remove.
	update { | counter, message, count |
		if (message === \beat and: { beatFilter.(count) }) {
			this.getNextEvent.play;
		}
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