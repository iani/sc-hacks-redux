/* 27 Feb 2022 09:49

*/

+ Event {

	addEvent { | argEvent | // add all key-value pairs from argEvent to myself
		argEvent keysValuesDo: { | key, value | this[key] = value }
	}

	pp { // prettyprint
		^String.streamContents({ arg s;
			s << "(\n";
			this.keys.asArray.sort do: { | k |
				s << "'" << k << "': ";
				s << this[k].asCompileString;
				s << ",\n"
			};
			s << ")";
		})
	}

	splay { ^EventStream(this).start; } // obsolete?!

	getParent { if (this.parent.isNil) { ^defaultParentEvent } { ^this.parent }  }

	+> { | player, envir | ^this.pushPlayInEnvir(player, envir ? player, true) }

	playInEnvir { | player, envir, start = true |
		var atKey, new;
		Mediator.pushWrap({
			atKey = currentEnvironment[player];
			atKey.stop;
			new = EventStream(this);
			if (start) { new.start };
			currentEnvironment[player] = new;
		}, envir ? player);
		^new;
	}

	+>! { | player, envir | // do not start
		^this.playInEnvir(player, envir, false);
	}

	playbuf {
		^this filter: {
			var buf;
			buf = ~buf.next;
			~instrument = [nil, \playbuf1, \playbuf2][buf.numChannels];
			~bufnum = buf.bufnum;
		}
	}
	filter { | function |
		^EventStream(this, function);
	}

	+>> { | player, envir |
		// create, store and
		// make this respond to OSC trigger messages
		// Use coupled with {} +>> key
		var estream;
		estream = this.playInEnvir(player, envir ? player, false);
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
