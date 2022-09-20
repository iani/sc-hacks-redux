/* 27 Feb 2022 09:49

*/

+ Event {
	+> { | key, envir | ^this.playInEnvir(key, envir) }

	playInEnvir { | key, envir |
		var atKey, new;
		Mediator.wrap({
			atKey = currentEnvironment[key];
			atKey.stop;
			new = this.splay(key);
			currentEnvironment[key] = new;
		}, envir);
		^new;
	}

	splay { | key | ^EventStream(this).start; }

	+>! { | key |
		var new;
		new = EventStream(this);
		currentEnvironment.put(key, new);
		^new;
	}

	++> { | key, envir |
		// Set all key-value pairs of the receiver to the object at key/envir
		// If object is EventStream: set keys of the Event.
		// Else if object is Synth, set all parameters corresponding to the keys
		var p;
		Mediator.wrap({
			p = currentEnvironment[key];
			p ?? {
				p = EventStream(this);
				currentEnvironment.put(key, p);
			};
			// EventSream and Synth handle this differently:
			currentEnvironment[key].setEvent(this);
		}, envir);
	}

	@> { | beatKey |
		beatKey.beat.addDependant(EventStream(this));
	}

	addBeat { | beatKey |
		this @> (beatKey ? this);
	}
}
