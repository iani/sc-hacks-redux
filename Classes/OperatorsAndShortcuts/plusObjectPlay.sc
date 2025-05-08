//: 日  4  5 2025 06:38
// See also Symbol:play in SymbolOperators.sc

+ Symbol {
	// arguments as expected from all types of Templates
	ndef { | source, args, target, addAction = \addToHead, outbus, fadeTime = 0.02 |
		// postln("debugging ndef");
		switch (source.class,
			Symbol, {
				var new, old, oldIsPlaying;
				// "this is ndef symbol".postln;
				old = currentEnvironment[this];
				new = NodeTemplate(this, source, old, args, target, addAction);
				oldIsPlaying = old.isPlaying;
				// postln("OLd is" + old + "old is playing is" + oldIsPlaying);
				(old === new).not.if {
					// "This is new and I should stop old and start new".postln;
					// This is now done internally at init method:
					// new getParametersFrom: old;
					old.stop;
					currentEnvironment[this] = new;
					// "I am checking if old is playing".postln;
					// postln("old is playing is" + oldIsPlaying);
					if (oldIsPlaying) {
						// "now I run new.play".postln;
						// postln("new is" + new);
						// postln("new source is" + new.source);
						// postln("new play args are" + new.getArgs);
						new.play;
					}{
						// "I did not run new.play".postln;
					};
				};
				^new;
			},
			Function, {
				var new, old, oldIsPlaying;
				old = currentEnvironment[this];
				new = FunctionNodeTemplate(
					this, source, old, args, target, addAction, outbus, fadeTime
				);
				oldIsPlaying = old.isPlaying;
				// "\n========= CHECKING OLD VS NEW ORDER ============".postln;
				// postln("old is" + old);
				// postln("new is" + new);
				// postln("old params are" + old.getArgs);
				// postln("new params are" + new.getArgs);

				(old === new).if {
					old.updateProcessControls;
				} {
					// This is now done internally at init method:
					// new getParametersFrom: old;
					old.stop;
					currentEnvironment[this] = new;
					if (oldIsPlaying) { new.play };
				};
				^new;
			},
			Nil, {
				var old, new;
				old = currentEnvironment[this];
				if (old isKindOf: PlayerTemplate) { ^old };
				new = this.ndef({ Silent.ar });
				currentEnvironment[this] = new;
				^new;
			},
			{ // TODO: finish and debug this
				var new;
				postln("WARNING: Creating generic PlayerTemplate for a" + source.class);
				new = PlayerTemplate(this, source);
				currentEnvironment[this] = new;
				^new;
			}
		)
	}

	pdef { | source |
		// postln("experimental: storee a pattern player in" + this);
		var new, old, oldIsPlaying;
		new = PatternTemplate(this).mergeEnvir(source ?? { () });
		old = currentEnvironment[this];
		oldIsPlaying = old.isPlaying;
		(old === new).not.if {
			old.stop;
			currentEnvironment[this] = new;
			if (oldIsPlaying) { new.play };
		}
		^new;
	}

	// accept args to pass to ndef
	play { | source, args, target, addAction = \addToHead, outbus = 0, fadeTime = 0.02 |
		// Play something at key in currentEnvironment.
		// If key previously contains a Synth, release it.
		// If it contains an EventStream, merge it or replace it
		// Store the result in the new at currenEnvironment.
		case
		{ source isKindOf: Nil } { ^this.ndef.play; }
		{ source isKindOf: Function } {
			var old, new;
			old = currentEnvironment[this];
			new = this.ndef(source, args, target, addAction, outbus, fadeTime);
			(old === new).not.if { old.stop };
			if (new.isPlaying.not) { { new.play }.defer(0.1); }
			// this causes duplicates. could not determine cause:
			// if (new.isPlaying.not) { new.play; } // this causes duplicates!
		}
		{ source isKindOf: Symbol }{
			var old, new;
			old = currentEnvironment[this];
			new = this.ndef(source, old, *args);
			(old === new).not.if { old.stop };
			if (new.isPlaying.not) { { new.play }.defer(0.1); }
			// this causes duplicates. could not determine cause:
			// if (whatever.isPlaying.not) { whatever.play; }
		}
		{ source isKindOf: Event } {
			this.pdef(source, args).play
		}
		{
			// this breaks things in most cases.
			// Unpredictable stuff is stored in environment
			postln("Playing:" + currentEnvironment[this]);
			source = currentEnvironment[this].play; // PlayerTemplate!
			currentEnvironment[this] = source;
		};
		// TODO: maybe different types should store differently?
		// currentEnvironment.storeAction(this, \play, player, args);
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

	playTemplate { | player, args |

	}

	set { | ... args |
		var player;
		player = currentEnvironment[this];
		case
		{ player isKindOf: PlayerTemplate } { player.set(*args) }
		{ player isKindOf: Synth } { this.setSynth(player, args) }
		{ player isKindOf: EventStream } { this.setEventStream(player, args) }
		{ player.isNil } { // choose template type from args types
			if (args.size = 1) {
				currentEnvironment[this] = args.first;
			}{
				currentEnvironment[this] = args;
			}
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
				"I cannot deal with first argument of type" + arg0.class;
			);
			"Merging empty event to current stream".postln;
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

+ Nil { getArgs { ^[] } }

/* // DISCARDED 火  6  5 2025 06:28
+ Object {
	play { | args | ^this.playArgs(args); }
	playArgs { | args | ^args[0].playOrMerge(args[1..]); }
	playOrMerge { | args | ^this } // remove this?
}
*/