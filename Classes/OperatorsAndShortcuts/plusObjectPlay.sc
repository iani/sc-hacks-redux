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
		{
			postln("Playing:" + currentEnvironment[this]);
			currentEnvironment[this].play;
		};
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

	set { | ... args |
		var player;
		player = currentEnvironment[this];
		case
		{ player isKindOf: Synth } { this.setSynth(player, args) }
		{ player isKindOf: EventStream } { this.setEventStream(player, args) }
		// { player.isNil } { "caught ja".postln; }
		{ player.isNil } {
			player = EventStream(());
			player mergeEvent: args.args2event;
			// this.setEventStream(player, args);
			currentEnvironment[this] = player;
		}
		{
			postln("symbol" + this + "can't set object of class" + player.class);
			"I will replace the object stored with the new arguments".postln;
			currentEnvironment[this].stop(currentEnvironment[\fadeTime] ? 0.1);
			if (args.size == 1) { args = args[0] };
			currentEnvironment[this] = args;
		}
	}

	setEventStream { | estream, args |
		var arg0, event;
		arg0 = args[0];
		case
		{ arg0 isKindOf: Event }{ event = arg0; }
		{ arg0 isKindOf: Array }{ event = arg0.asEvent; }
		{ arg0 isKindOf: Symbol }{ event = args.asEvent; }
		{
			postln(
				"I can;t deal with first argument of type" + arg0.class
			);
			event = ();
		};
		estream mergeEvent: event;
	}

	setSynth { | synth, args | synth.set(*args); }

	// NOTE: Symbol:clear and stop are defined in plusSystemOverWrites.sc
	// in folder SystemOverwrites.

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
	// ================= OLD STUFF ================
	play2 { | playFunc, event |
		// THIS IS OLD play METHOD OF V1, USED TO PLAY SynthTemplate
		// See Mediator play.
		// play playfunc in event envir of Mediator named by receiver
		// Use this	 only in v1 of this libary
		^Mediator.at(this).play(playFunc, event);
	}
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

+ Array {
	args2event {
		var arg0, event;
		arg0 = this[0];
		case
		{ arg0 isKindOf: Event }{ event = arg0; }
		{ arg0 isKindOf: Array }{ event = arg0.asEvent; }
		{ arg0 isKindOf: Symbol }{ event = this.asEvent; }
		{
			postln(
				"I can;t deal with first argument of type" + arg0.class;
				"Returning empty event".postln;
			);
			event = ();
		};
		^event;
	}
}