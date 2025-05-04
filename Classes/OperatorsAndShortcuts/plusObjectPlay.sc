//: 日  4  5 2025 06:38
// See also Symbol:play in SymbolOperators.sc

+ Object {
	play { | args | ^this.playArgs(args); }
	playArgs { | args | ^args[0].playOrMerge(args[1..]); }
	playOrMerge { | args | ^this } // remove this?
}

+ Symbol {
	play { | ... args |
		// Play something at key in currentEnvironment.
		// If key previously contains a Synth, release it.
		// If it contains an EventStream, merge it or replace it
		// Store the result in the new at currenEnvironment.
		// Mediator stops EventStream when it is replaced - which is ok
		// - But we handle EventStream merging here for simplicity.
		var stored;
		stored = currentEnvironment[this];
		if (stored isKindOf: EventStream) {
			if (args[0] isKindOf: Event) { stored mergeEvent: args[0]; };
			// Starts on quantized beat if ~clock and ~quant are set
			// in currentEnvironment:
			if (stored.isPlaying.not) { stored.start };
			// avoid stopping the EventStream that runs. Do not store!
			^this;
		};
		currentEnvironment[this] = currentEnvironment[this].playArgs(args);
	}
	play2 { | playFunc, event |
		// THIS IS OLD play METHOD OF V1, USED TO PLAY SynthTemplate
		// See Mediator play.
		// play playfunc in event envir of Mediator named by me
		// Use this	 only in v1 of this libary
		^Mediator.at(this).play(playFunc, event);
	}

	storeEventStream { | event | // stops previously playing synths or patterns
		// and stores a new EventStream without playing it.
		currentEnvironment[this] = EventStream(event);
	}

	set { | event |
		// set keys i.e. merge, but do not start
		// if object previously stored is not EventStrem, create an empty one.
		var receiver;
		receiver = currentEnvironment[this];
		if (receiver.isKindOf(EventStream).not) {
			receiver = EventStream(());
			currentEnvironment[this] = receiver;
		};
		receiver mergeEvent: event;
	}

	// ============= SHORTCUTS: =============
	instr { | instrument = \bf |
		this.set((instrument: instrument));
	}

	playBuf { | buf, instrument = \bf |
		buf ?? {
			buf = BaBufs.all.keys.asArray.first;
			postln("Setting buffer to" + buf);
		};
		this.instr set: (buf: BaBufs.all[buf]);
	}

	// ============= NOTE: =============
	// Symbol:stop is redefined in SystemOverwrites/plusSystemOverwrites.sc
	// stopp { currentEnvironment[this].free; } // EventStream.stop also locked
	// Clear EventStream: Remove all keys from its event
	// doclear { currentEnvironment[this].clear } // clear not overwriteable?
}

+ Nil {
	playOrMerge { | args | ^args[0] }
}

+ Synth {
	playArgs { | args |
		if (this.isPlaying) { this release: (~fadeTime ? 0.1); };
		^args[0].playArgs(args[1..]);
	}
	stop { this release: (~fadeTime ? 0.1) }
}

+ Event { // REVIEW: these methods can be scrapped ?
	playOrMerge { | args |
		// postln("Event playOrMerge. Receiver:" + this);
		// postln("Event playOrMerge. args:" + args);
		^EventStream(this).start;
	}
	playArgs { | args |
		// postln("Event playargs. Receiver:" + this);
		// postln("Event playArgs. args:" + args);
		^EventStream(this).start;
	}
}

+ Function {
	playArgs { | args | ^this.play(*args).register; }
	playOrMerge { | args | ^this.play(*args).register; } // remove this?
}