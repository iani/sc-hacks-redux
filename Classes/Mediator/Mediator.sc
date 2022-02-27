/* 25 Jan 2021 20:30
EnvironmentRedirect that handles state of objects before replacing them
in its keys. Setting the value of an environment variable in this environment will cause whatever was previously in that variable to stop. This is a simple way
to guarante that any patterns or synths will stop playing when the reference to them from an environment variable is lost.

See README.
*/

Mediator : EnvironmentRedirect {
	classvar default;
	var <name;

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
	*pop { this.default.pop }
	*default { ^default ?? { default = this.fromLib(\default) } }
	put { | key, obj |
		dispatch.value(key, obj);
		// this.changed(key, obj);
	}
	prPut { | key, obj |
		envir.put (key, obj);
	}

	*all { ^Library.at(this) }

	*at { | envirName |
		if (envirName.isNil) { ^this.default; }{ ^this.fromLib(envirName); }
	}

	*wrap { | func, envirName |
		// eval aMediator use: func
		// Where aMediator is obtained from envirName
		^this.at(envirName) use: func;
	}

	playerGui { // TODO: IMPLEMENT THIS.
		/* List with all players.
			names of players that are playing are enclosed in *.
		*/
		"Mediator playerGui NOT YET IMPLEMENTED".postln;
	}
}
