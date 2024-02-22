/* 21 Feb 2022 13:14 (Redo EventStream, removing all extras.)

This plays like a pbind, except that its stream is an event containing streams.
That is:

Pbind uses as a source an array of key-value pairs, where each key is a symbol
and each value an object that can generate a stream (a constant, or a Pattern).

Pbind(\key1, value1, \key2, \value2 ...);

EventStream uses as source an event that contains key-value pairs, like those of Pbind:

(key1: value1, key2: value2 ...)

At each scheduled play-time, the EventStream creates and plays a new event, by getting
the values from the streams stored in the event in variable "stream".
The stream event is created by method "makeStream".

Variables:

1. event: An event containing the patterns that provide the values to play.
	It is given by the user.
2. filter: An optional function that can change the stream at both
(a) stream creation time (reset). (See method makeStream), and
(b) every time the stream-event is played. (See method getNextEvent).

3. stream: An event (!), created from the original event given by the user,
The stream event contains the streams obtained from the event's patterns or values.
This stream is used to generate the values that are played each time.
It is created by method makeStream, which is called by method reset, and
restarts the EventStream.

4. routine: A routine that plays the event according to the durations returned by the stream
stored under "dur" in the event.

*/

EventStream {
	var <event, <>filter, <stream, <routine;
	// getNextEvent uses filter to modify stream if needed

	*new { | event, filter |
		^this.newCopyArgs(event, filter).init.reset;
	}

	// playing evenstream after adding a filter:
	// see OperatorFix24022.sc
	// +> { | player, envir | ^this.pushPlayInEnvir(player, envir ? player, true) }

	// saving some time by copying this from Event.
	playInEnvir { | player, envir, start = true |
		var atKey, new;
		// "this is Event playInEnvir".postln;
		Mediator.pushWrap({
			atKey = currentEnvironment[player];
			atKey.stop;
			// "making event stream next".postln;
			// new = EventStream(this);
			new = this;
			// "JUST MADE AN event stream next".postln;
			if (start) { new.start };
			currentEnvironment[player] = new;
		}, envir ? player);
		^new;
	}

	init {
		CmdPeriod add: this;
	}

	free { this.stop }

	stop { // stop routine, but keep stream for continuing!
		routine.stop;
		this.stopped;
		// routine = nil;
		// this changed: \stopped;
	}

	cmdPeriod { this.stopped; }
	doOnCmdPeriod { this.stopped }

	stopped {
		routine = nil;
		this.changed(\stopped);
	}

	reset { this.makeStream }

	makeStream {
		stream = ().parent_(event.parent);
		event keysValuesDo: { | key, value |
			stream[key] = value.asStream(event, stream);
		};
		stream use: filter;
	}

	asEvenStream { ^this }

	restart { // to make Symbol:start also work with Synths.
		this start: nil; // being explicit ...
	}
	start { | quant |
		if (this.isRunning) { ^postf("% is running. will not restart it\n", this) };
		this.makeRoutine(quant);
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
		if (inEvent.isNil) {
			this.changed(\ended);
		}{
			this.changed(\played, inEvent, this);
		}
	}

	getNextEvent {
		var nextEvent, nextValue;
		nextEvent = ().parent_(event.parent);
		// Make the stream event available to any functions running in it:
		stream use: {
			stream keysValuesDo: { | key, value |
				nextValue = value.next(stream);
				if (nextValue.isNil) {
					^nil
				};
				nextEvent[key] = nextValue;
			};
			filter.(nextEvent);
		};
		^nextEvent;
	}

	isPlayer { ^true }
	isRunning { ^this.isPlaying }
	isPlaying { ^routine.notNil }


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

	oscTrigger { | message | message >>> { this.playNext } }
	removeTrigger { | message |
		postln("removing trigger for" + message);
		message >>> nil }
}