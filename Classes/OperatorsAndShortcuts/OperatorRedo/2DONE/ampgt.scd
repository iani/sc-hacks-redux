//Fri 23 Feb 2024 11:38
//Array

// ====== Array -> Buffer
+ Array {
	@> { | bufname |
		// send contents to buffer
		// Create new buffer and store it in bufname, under Library at Arrray (!)
		// If previous buffer exists under that name, free that buffer. (!?)
		var buf;
		buf = Library.at(Buffer, bufname); // Array.bufname? We'd need to remove on Server quit!
		if (buf.isNil) {
				buf = Buffer.sendCollection(Server.default, this);
		} {
			if ( buf.size == this.size ) {
				Buffer.sendCollection(Server.default, this);
			} {
				buf.free;
				buf = Buffer.sendCollection(Server.default, this);
			}
		};
		Library.put(Buffer, bufname, buf);
	}
}

// !!!!!!!!!!!!!!!! cleanup needed. which @> will we use for Array
+ Array { // generate bus names from base name + index of array element
	@> { | bus, playerEnvir |
		this do: { | el, in |
			el.perform('@>', format("%%", bus, in).asSymbol, playerEnvir);
		}
	}
}

// ====== Setting busses:

+ Symbol {
	// <+ { | value, envir | envir.envir.put(this, value); }
	@> { | targetBus, player |
		^().put(
			this.busify,
			targetBus.bus(nil, player ? currentEnvironment.name).index
		);
	}
}

+ Function {
	@> { | bus, player | // play as kr function in bus (or player) name
		player ?? { player = currentEnvironment.name };
		// postln("debugging function @>. bus is:" + bus + "player is" + player);
		// postln("prev synth is:" + player.envir[bus]);
		{
			Out.kr(bus.bus(nil, player), this.value.kdsr);
		}.playInEnvir(bus, player); // do not push envir !!!
	}
}

+ Event {
	@> { | bus, player |
		var busNames, busStreams, stream;
		player = player ? bus;
		busNames = this[\busNames] ?? { [bus] };
		busStreams = busNames collect: { | busName |
			stream = this[busName];
			stream ?? { stream = defaultParentEvent[busName] };
			if (stream.isNil) {
				postln("Missing value for bus: " + busName + "in Event: " + this);
				[busName.bus, 0]
			}{
				[busName.bus, stream.asStream]
			};
		};
		this[\busStreams] = busStreams;
		this[\play] = {
			var bus, val;
			~busStreams do: { | busStreamPair |
				#bus, val = busStreamPair;
				val = val.value;
				if (val.isNil) { // the next line actually never gets executed:
					postln("Stream for bus" + bus + "ended.");
				}{  // \_ is a pause. Do not set the value.
					if (val !== \_) { // only set if not pause \_
						bus.set(val);
					}
				};
			};
			val;
		};
		this.playInEnvir(player, \busses);
	}
}

+ SimpleNumber {
	@> { | bus, playerEnvir | // set bus value
		// works with new AND already existing busses.
		// Stop processes playing in this bus before setting a new value:
		playerEnvir ?? { playerEnvir = currentEnvironment.name };
		playerEnvir.envir[bus].free;
		bus.bus(nil, playerEnvir).set(this);
	}
}

+ Point {
	// create XLine from current value to x in y seconds
	@> { | bus, playerEnvir | // glide with xline
		var b;
		b = bus.bus(nil, playerEnvir ? currentEnvironment.name);
		b.get({ | v |
			// Hack: permit XLine ... with 0 to positive values :
			v = v max: 0.00000001;
			{ XLine.kr(v, x, y) }.perform('@>', bus, playerEnvir);
		})
	}
}

+ Nil {
	@> { | busPlayer, envir | // Stop player for bus
		// stop player with same name as bus in an environment
		busPlayer.stopPlayer(envir, 0);
	}
}