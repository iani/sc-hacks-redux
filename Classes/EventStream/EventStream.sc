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

	removeBeat { | beat |
		this.removeNotifier(beat.beat, \beat);
	}
}