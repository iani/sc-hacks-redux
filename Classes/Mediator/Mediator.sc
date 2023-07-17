/* 25 Jan 2021 20:30
EnvironmentRedirect that handles state of objects before replacing them
in its keys. Setting the value of an environment variable in this environment will cause whatever was previously in that variable to stop. This is a simple way
to guarante that any patterns or synths will stop playing when the reference to them from an environment variable is lost.

See README.
*/

Mediator : EnvironmentRedirect {
	classvar default, global;
	var <name, busses;

	*global {// experimental: Use Event instead of Environment
		^global ?? { global = Event() }
	}
	*putGlobal { | key, object | this.global.put(key, object) }

	*new { | name, envir |
		^this.newCopyArgs( // experimental: Use Event instead of Environment
				envir ?? { this.makeEnvir },
				nil, name
			).dispatch = MediatorHandler();
	}

	init { | ... args |
		// Make self known to your environment, to enable use of
		// Symbol:bin, br etc.
		// Store name instead of this to maintain postability
		envir[\mediator] = name;
	}

	pf { | playfunc |
		// test playing with custom funcs
		envir[\play] = { playfunc +> name };
		envir[\mediator] = name;
		this.push;
		envir.play; // return self! to be able to stop or do other stuff
	}

	play { | argEvent |
		// play within own event as environment, and setting the player.
		// used by SoundFileEvents, SoundFileEvent for playing
		// events + playfuncs loaded from scd file specs.
		var playfunc;
		argEvent !? {// TODO: stop synths when replacing keys
			// fix for non-releasable control rate funcs:
			argEvent keysValuesDo: { | key, val |
				envir[key].free;
				envir[key] = val;
			};
		};
		playfunc = SynthTemplate.getFunc(envir[\playfunc]);
		envir[\play] = { playfunc +> name };
		envir[\mediator] = name;
			this.push;
		envir.play; // return self! to be able to stop or do other stuff
		this.class.changed(\started, name);
	}

	clear {
		this.free;
		this.freeBusses;
		this.makeEnvir;
	}

	makeEnvir { ^envir = this.class.makeEnvir }
	*makeEnvir { ^Event.new(32, Event.new, this.global)  }
	*stopSynths {
		this.all do: _.stopSynths;
	}
	stopSynths { | fadeTime = 0.1 | // stop both ar and kr/br (/bus) synths
		envir do: { | s |
			if (s isKindOf: Synth) { s release: fadeTime };
		}
	}

	// free all synths
	free {
		envir do: { | s |
			if (s isKindOf: Synth) { s.free };
		}
	}

	fb { this.freeBusses }
	freeBusses {
		busses do: _.free;
		this.makeBusDict;
	}

	playPrototypeBroken { | argPlayFunc, argEvent |
		argPlayFunc !? { envir[\play] = argPlayFunc; };
		argEvent !? {
			argEvent keysValuesDo: { | key, val |  envir[key] = val; };
		};
		envir.play;
	}
	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << "<" << this.name << ">:[ " ;
		envir.printItemsOn(stream);
		stream << " ]" ;
	}

	*initClass { StartUp add: { this.push } }
	*push { this.default.push }
	push { // get rid of warning
		if(currentEnvironment !== this) {
			Environment.push(this)
		} // { "this environment is already current".warn }
	}
	*pop { this.default.pop }
	*default { ^default ?? { default = this.fromLib(\default) } }
	/* // this changed is now in Class:fromLib
	*fromLib { | key |
		this.changed(\envir, key);
		^super.fromLib(key);
	}
	*/
	parent { ^envir.parent }
	put { | key, obj |
		dispatch.value(key, obj);
		// this.changed(key, obj);
	}
	prPut { | key, obj |
		envir.put (key, obj);
		this.changed(key, obj);
	}

	*all { ^Library.at(this) }

	*at { | envirName |
		// if (envirName.isNil) { ^this.default; }{ ^this.fromLib(envirName); }
		if (envirName.isNil) { ^currentEnvironment; }{ ^this.fromLib(envirName); }
	}

	// UNDER REVIEW!!!!! this actually wraps
	*wrap { | func, envirName /*, push = true */ |
		// eval aMediator use: func
		// Where aMediator is obtained from envirName
		var envir;
		envir = this.at(envirName);
		// if (push) { "I am pushing the envir".postln; envir.push }
		// { "I am NOT pushing the envir".postln; };
		^envir use: func;
	}

	// pushWrap not needed?
	// UNDER REVIEW!!!!! this actually wraps
	*pushWrap { | func, envirName |
		// eval aMediator use: func
		// Where aMediator is obtained from envirName
		^this.wrap(func, envirName, true);// use: func;
	}

	busses { ^busses ?? { this.makeBusDict } }
	makeBusDict { ^busses = IdentityDictionary(); }

	playerGui { // TODO: IMPLEMENT THIS.
		/* List with all players.
			names of players that are playing are enclosed in *.
		*/
		"Mediator playerGui NOT YET IMPLEMENTED".postln;
	}
	players {
		^this.values select: _.isPlayer;
	}

	playerNames {
		var names = [];
		this.keysValuesDo({ | key, value |
			if (value.isPlayer) { names = names add: key };
		});
		^names;
	}

	*playerNames { | envir |
		^this.at(envir).playerNames;
	}

	*envirNames { ^this.all.keys.asArray }

	*setEvent { | event, player, envir |
		// Set all key-value pairs of the receiver to the object at key/envir
		// If object is EventStream: set keys of the Event.
		// Else if object is Synth, set all parameters corresponding to the keys
		this.wrap({
			var p;
			p = currentEnvironment[player];
			p ?? {
				p = EventStream(event);
				currentEnvironment.put(player, p);
			};
			// EventSream and Synth handle this differently:
			currentEnvironment[player].setEvent(event);
		}, envir ? player);
	}

	addSynth { | key, synth |
		// postln("Mediator add synth, playing?" + this[key]);
		this[key] = synth;
		synth.addNotifier(this, \target, { | n, target |
			// "my target has changed".postln;
			n.listener.moveToHead(target.asTarget);
		});
		synth.addNotifier(this, \outbus, { | n, outbus |
			// postln("testing how to treat outbus. input is:" + outbus);
			synth.set(\outbus, outbus);
			synth.set(\out, outbus);
		});
		synth.addNotifier(this, \out, { | n, outbus |
			// postln("testing how to treat outbus. input is:" + outbus);
			synth.set(\outbus, outbus);
			synth.set(\out, outbus);
		});
		synth onEnd: {
			if (this[key] === synth) { this[key] = nil };
			synth changed: \ended;
		};
		^synth;
	}
}
