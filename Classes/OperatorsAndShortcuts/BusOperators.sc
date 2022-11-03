/* 17 Sep 2022 19:40

Simplenumber @> \symbol // set bus to number
(event) @> \symbol // play EventSream that sets the bus at symbol to the value of symbol in the stream.
	// the symbol is also the name of the player where the stream plays!
{ function } @> \symbol // play function as synth and set the symbol's bus to its control ugen output


*/

+ Bus {
	// ensure that bus.set runs after the bus has been created on the server
	syncSet { | val |
		this.addNotifierOneShot(this, \sync, {
			postln("bus " + this + "received sync. setting to: " + val);
			this.set(val);
		});
	}
	pget { | format = "%" |
		this.get({ | v | format(format, v).postln; })
	}
}

+ Symbol {
	// compose event to switch busses in a player
	// usage:
	// \parameter @> \targetbus ++>.envir \player;
	@> { | targetBus |
		^().put(this.busify, targetBus.bus.index);
	}
	pget { | format = "%" | ^this.bus.pget(format) }
	blag { | lag = 0.1 | ^this.bin.lag(lag) }
	bamp { | attack = 0.01, decay = 0.1 | ^this.bin.amp(attack, decay); }
	br { | val | ^this.bin(val) } // alias similar to ar, kr
	bin { | val | ^this.busIn(val) } // input from a named kr bus.
	//synonym. (sic!)
	busIn { | val |
		// bus in
		// Restore following line if needed:
		// ^In.kr(this.bus(val).index)
		var bus;
		bus = this.bus(val);
		// use b_ prefix for controls which refer to buses.
		^In.kr(this.busify.kr(bus.index));
	}
	// prepend b_. Used to mark controls that read from busses
	busify { ^("b_" ++ this).asSymbol }

	bus { | val, rate = \control, numchans = 1, server |
		// Return the bus for this symbol.
		// If bus does not exist, create new one.
		// If val is not nil, then set the bus to val.
		// Works with new AND already existing busses.
		var bus;
		server = server.asTarget.server;
		bus = Library.at(Bus, server, rate, this);
		if (bus.isNil) {
			bus = Bus.perform(rate, server, numchans);
			{
				server.sync;
				val !? { bus.set(val) };
				// bus.changed(\sync);
			}.fork;
			Library.put(Bus, server, rate, this, bus);
		}{
			val !? { bus.set(val) };
		};
		^bus;
	}
}


+ Function {
	@> { | bus, player | // play as kr funcction in bus (or player) name
		{
			Out.kr(bus.bus, this.value.kdsr);
			// A2K.kr(Silent.ar).kdsr;
		}.playInEnvir(player ? bus, \busses);
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
	@> { | bus | // set bus value
		// works with new AND already existing busses.
		// Stop processes playing in this bus before setting a new value:
		nil @> bus;
		bus.bus(this).set(this);
	}
}

+ Nil {
	@> {| bus, player | // Stop player for bus
		if ((player ? bus).player(\busses).isPlaying) {
			(player ? bus).player(\busses).free;
		}
	}
}
