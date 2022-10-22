/* 27 Feb 2022 09:49

*/

+ Event {
	// obsolete?!
	splay { ^EventStream(this).start; }

	+> { | player, envir | ^this.playInEnvir(player, envir ? currentEnvironment.name, true) }

	playInEnvir { | player, envir, start = true |
		var atKey, new;
		Mediator.wrap({
			atKey = currentEnvironment[player];
			atKey.stop;
			new = EventStream(this);
			if (start) { new.start };
			currentEnvironment[player] = new;
		}, envir ? currentEnvironment.name);
		^new;
	}

	+>! { | player, envir | // do not start
		^this.playInEnvir(player, envir, false);
	}

	+>> { | player, envir |
		// create, store and
		// make this respond to OSC trigger messages
		// Use coupled with {} +>> key
		var estream;
		estream = this.playInEnvir(player, envir ? currentEnvironment.name, false);
		player >>> { estream.playNext };
		^estream;
	}

	+@> { | key |
		Mediator.setEvent(this, key, \busses);
	}

	++> { | key, envir |
		// Set all key-value pairs of the receiver to the object at key/envir
		// If object is EventStream: set keys of the Event.
		// Else if object is Synth, set all parameters corresponding to the keys
		Mediator.setEvent(this, key, envir);
		// var p;
		// Mediator.wrap({
		// 	p = currentEnvironment[key];
		// 	p ?? {
		// 		p = EventStream(this);
		// 		currentEnvironment.put(key, p);
		// 	};
		// 	// EventSream and Synth handle this differently:
		// 	currentEnvironment[key].setEvent(this);
		// }, envir);
	}
}
