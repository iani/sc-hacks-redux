//: 日  4  5 2025 06:38
// See also Symbol:play in SymbolOperators.sc

+ Object {
	play { | args | ^this.playArgs(args); }
	playArgs { | args | ^args[0].playOrMerge(args[1..]); }
	playOrMerge { | args | ^this } // remove this?
}

+ Symbol {
	play { | player ... args |
		// Play something at key in currentEnvironment.
		// If key previously contains a Synth, release it.
		// If it contains an EventStream, merge it or replace it
		// Store the result in the new at currenEnvironment.
		case
		{ player isKindOf: Function } { this.playFunction(player, args) }
		{ player isKindOf: Event } { this.playEvent(player, args) }
		{ player isKindOf: Symbol } { this.playSymbol(player, args) }
		{ postln("symbol" + this + "can't play object of class" + player.class) };
		// currentEnvironment[this] = currentEnvironment[this].playArgs(args);
	}

	playFunction { | player, args |
		currentEnvironment[this].stop(currentEnvironment[\fadeTime] ? 0.1);
		currentEnvironment[this] = player.play(*args);
	}

	playEvent { | event, args |
		var prev;
		prev = currentEnvironment[this];
		if (prev isKindOf: EventStream) {
			prev mergeEvent: event;
			if (prev.isPlaying.not) { prev.start; };
		}{
			prev.stop(currentEnvironment[\fadeTime] ? 0.1);
			prev = EventStream(event);
			currentEnvironment[this] = prev;
			prev.start;
		};
	}

	playSymbol { | player, args |
		var target, addAction;
		#args, target, addAction = args;
		currentEnvironment[this].stop(currentEnvironment[\fadeTime] ? 0.1);
		currentEnvironment[this] = Synth(
			player, args, target, addAction ? \addToHead
		).register;
	}

	play2 { | playFunc, event |
		// THIS IS OLD play METHOD OF V1, USED TO PLAY SynthTemplate
		// See Mediator play.
		// play playfunc in event envir of Mediator named by receiver
		// Use this	 only in v1 of this libary
		^Mediator.at(this).play(playFunc, event);
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
	// Symbol:stop, clear are redefined in SystemOverwrites/plusSystemOverwrites.sc
}

+ Synth {
	playArgs { | args |
		if (this.isPlaying) { this release: (~fadeTime ? 0.1); };
		^args[0].playArgs(args[1..]);
	}
	stop {
		if (this.isPlaying) { this release: (~fadeTime ? 0.1) }
	}
}
