/* 21 Feb 2022 13:14
Redo EventStream, removing all extras.

*/

EventStream {
	var <event, <>filter, <stream, <routine;
	// getNextEvent uses filter to modify stream if needed

	*new { | event, filter |
		^this.newCopyArgs(event, filter).init.reset;
	}

	// playing evenstream after adding a filter:

	+> { | player, envir | ^this.pushPlayInEnvir(player, envir ? player, true) }

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

	doOnCmdPeriod {
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

	free { this.stop }

	stop { // stop routine, but keep stream for continuing!
		routine.stop;
		routine = nil;
		this changed: \stopped;
		// this.objectClosed;
		// this.reset; // ?????
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
			// this.stop; // this is not the same
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
		// postln("debugging playAndNotify. inEvent" + inEvent.pp);
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
		// Make the stream event available to any functions running in it
		stream use: {
			stream keysValuesDo: { | key, value |
				nextValue = value.next(stream);
				if (nextValue.isNil) {
					// postf("% has ended\n", this);
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

	oscTrigger { | message | message >>> { this.playNext } }
	removeTrigger { | message |
		postln("removing trigger for" + message);
		message >>> nil }
}