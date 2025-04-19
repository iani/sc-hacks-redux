/* 25 Jan 2021 20:30
EnvironmentRedirect that handles state of objects before replacing them
in its keys. Setting the value of an environment variable in this environment will cause whatever was previously in that variable to stop. This is a simple way
to guarante that any patterns or synths will stop playing when the reference to them from an environment variable is lost.

See README.
*/

Mediator : EnvironmentRedirect {
	classvar default, global;
	var <name, busses;

	*allKeys { ^this.all.keys.asArray.sort }
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

	//: TODO: V2: Review this method and its usage.
	// It is used by Preset to play with SynthTemplates,
	// but it should be explained further for future use.
	play { | argEvent |
		// play within own event as environment, and setting the player.
		// used by SoundFileEvents, SoundFileEvent for playing
		// events + playfuncs loaded from scd file specs.
		var playfunc;
		// this.stopSynths;
		argEvent !? {// TODO: stop synths when replacing keys
			argEvent keysValuesDo: { | key, val |
				// envir[key].free;
				envir[key] = val.asArray.first; // get value from Preset dict
			};
		};
		playfunc = SynthTemplate.getFunc(argEvent[\playfunc]);
		// "======== debugging mediator play ===========".postln;
		// "playfunc is:".postln;
		// playfunc.postln;
		envir[\play] = { playfunc +> name }; // prepare event for playing
		envir[\mediator] = name;
		this.push;  // make self available to event during playing
		envir.play; // play a
		this.class.changed(\started, name);
	}

	synthReport { // for debugging
		postln("Synth report for" + envir[\mediator]);
		envir keysValuesDo: { | key, x |
			// [key, x].postln;
			if (x isKindOf: Synth) { postln("key" + key + "synth" + x) }
		};
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
			if (s isKindOf: Synth) {
				s.free;
				// s release: fadeTime
			};
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

	*initClass {
		StartUp add: {
			this.push;  // push default as currentEnvironment
			this.pushTopEnvir; // push default as topEnvironment
		}
	}

	*mergeEnvir {
		// merge currentEnvironment and then push
		// Put this in beginning of startup file if you are setting
		// environment variables from class startup code or from startup file
		if (currentEnvironment isKindOf: this) {
			// "will not merge".postln;
		}{
			var envir;
			envir = currentEnvironment;
			this.push;
			envir keysValuesDo: { | key, value | currentEnvironment.put(key, value);}
		}
	}

	*push { this.default.push; }
	*pushTopEnvir { topEnvironment = this.default }

	push { // get rid of warning
		if(currentEnvironment !== this) {
			// postln("Will call push on:" + this);
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
		this.changed(\key, key, obj);
	}

	set { | synthkey, param, value |
		var synth;
		synth = envir[synthkey];
		if (synth isKindOf: Synth) { synth.set(param, value) }
	}

	setBusses { | keysvalues |
		keysvalues keysValuesDo: { | key, value |
			envir[key] = value;
		};
	}

	*all { ^Library.at(this) }

	*at { | envirName |
		// if (envirName.isNil) { ^this.default; }{ ^this.fromLib(envirName); }
		if (envirName.isNil) { ^currentEnvironment; }{ ^this.fromLib(envirName); }
	}

	// UNDER REVIEW!!!!! does this push or just wrap without pushing?
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

	// TODO: Combine code of Symbol/Function:playInEnvir,
	// and adapt to work in Mediator.
	makeSynth { | funcOrSymbol, player, envir |
		// player + envir are passed on by +> operator.
		// all other values are inferred from the environment.
		var synth;
		synth = switch (funcOrSymbol.class,
			Function, { this.funcPlay(funcOrSymbol, envir)},
			Symbol, { this.symPlay(funcOrSymbol, envir); },
			{
				postln("attempt to play unplayable object" + funcOrSymbol);
			}
		);
		synth onStart: {
		// map any existing busses of the Mediator to controls
			envir keysValuesDo: { | key, val |
				if (val isKindOf: Bus) {
					synth.map(key, val);
					// postln("Mapped synth" + synth + "at key" + key + "to bus" + val);
				}
			}
		};
		synth.addNotifier(envir, \key, { | n, key, value |
			// postln("envir" + envir[\mediator] + "changed key:" + key);
			value.updateSynth(key, synth); // map or set control at key
		});
		// Emitted by Bus:HandleReplacement when bus is stored.
		// Unmap the bus that was removed.
		synth.addNotifier(envir, \busfree, { | n, key, bus |
			postln("envir" + envir[\mediator] + "freed bus at:" + key);
			// unmap by setting to latest values from the bus.
			bus.get({ | vals | synth.setn(key, vals) });
		});
		synth onEnd: {
			// postln("Synth ended:" + synth);
			if (envir[player] === synth) {
				envir.put(player, nil);
				Mediator.changed(\ended, player);
				synth changed: \ended;
			}
		};
		envir.put(player, synth);
		^synth;
	}

	// TODO: Do we need the envir argument if this is running inside the Mediator as envir?
	funcPlay { | function, envir |
		// create and return a synth by function.play,
		// obtain arguments to play from envir.
		^function.play(
			envir[\target] ? Server.default,
			envir[\outbus] ? 0,
			envir[\fadeTime] ? 0.02,
			envir[\addAction] ? \addToHead,
			envir.synthArgs
		);
	}

	// TODO: Do we need the envir argument if this is running inside the Mediator as envir?
	symPlay { | symbol, envir |
		// create and return a synth by Synth.new(sym),
		// obtain arguments from envir.
		^Synth(
			symbol,
			envir.synthArgs ++ [\fadeTime, envir[\fadeTime] ? 0.02],
			envir[\target] ? Server.default,
			envir[\addAction] ? \addToHead,
		)
	}

	isPlaying { | argPlayer |
		argPlayer ?? { argPlayer = this.name; };
		^envir[argPlayer].isPlaying;
	}

	////////////// V2 Extensions (250212ff) //////////////
	synthArgs {
		// create an arg array for making a new synth with values
		// set from values in your keys.  The only values that add
		// themselves are SimpleNumber, and later, the var value of ValueAdapter.
		var theArgs = [];
		this keysValuesDo: { | key, val |
			theArgs = theArgs ++ val.synthArgs(key);
		};
		^theArgs;
	}
}
