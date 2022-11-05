/* 25 Jan 2021 20:30
EnvironmentRedirect that handles state of objects before replacing them
in its keys. Setting the value of an environment variable in this environment will cause whatever was previously in that variable to stop. This is a simple way
to guarante that any patterns or synths will stop playing when the reference to them from an environment variable is lost.

See README.
*/

Mediator : EnvironmentRedirect {
	classvar default;
	var <name, busses;

	*new { | name, envir |
			^this.newCopyArgs(
				envir ?? { Environment.new(32, Environment.new) },
				nil, name
			).dispatch = MediatorHandler();
	}

	init { | ... args |
		// postf("my iit args are: %\n", args);
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << "<" << this.name << ">:[ " ;
		envir.printItemsOn(stream);
		stream << " ]" ;
	}

	*initClass {
		StartUp add: { this.push }
	}

	*push { this.default.push }
	push { // get rid of annoying warning
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
	put { | key, obj |
		dispatch.value(key, obj);
		// this.changed(key, obj);
	}
	prPut { | key, obj |
		envir.put (key, obj);
		// this.changed(key, obj);
	}

	*all { ^Library.at(this) }

	*at { | envirName |
		// if (envirName.isNil) { ^this.default; }{ ^this.fromLib(envirName); }
		if (envirName.isNil) { ^currentEnvironment; }{ ^this.fromLib(envirName); }
	}

	*wrap { | func, envirName /*, push = true */ |
		// eval aMediator use: func
		// Where aMediator is obtained from envirName
		var envir;
		envir = this.at(envirName);
		// if (push) { "I am pushing the envir".postln; envir.push }
		// { "I am NOT pushing the envir".postln; };
		^envir use: func;
	}

	// UNDER REVIEW!!!!!
	*pushWrap { | func, envirName |
		// eval aMediator use: func
		// Where aMediator is obtained from envirName
		^this.wrap(func, envirName, true) use: func;
	}

	busses { ^busses ?? { busses = IdentityDictionary() } }

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
}
