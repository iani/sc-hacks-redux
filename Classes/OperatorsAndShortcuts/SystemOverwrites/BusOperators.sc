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
		this.get({ | v | format(format, v, this).postln; })
	}

	scope { | name = \bus |
	// open a Stethoscope on this bus index.
		var scope;
		scope = Stethoscope.fromLib(this)
		.setRate(this.rate)
		.setNumChannels(1)
		.setName(name);
		// must be done later because ... good programming by smart guys
		{  scope.setIndex(index); }.defer(0.5);
		this.addNotifier(scope, \stopped, {
			Library.put(Stethoscope, this, nil);
		});
		ServerQuit add: { scope.quit };
		^scope;
	}
}

+ Symbol {
	// compose event to switch busses in a player
	// usage:
	// \parameter @> \targetbus ++>.envir \player;

	// <+ { | value, envir | envir.envir.put(this, value); }
	@> { | targetBus, player |
		^().put(this.busify, targetBus.bus(nil, player ? currentEnvironment.name).index);
	}
	pget { | player, format = "%" | ^this.bus(nil, player).pget(format) }
	blag { | lag = 0.1 | ^this.bin.lag(lag) }
	bamp { | attack = 0.01, decay = 0.1 | ^this.bin.amp(attack, decay); }

	sr { ^In.kr(this.sensorbus.index) }
	sensorbus { ^this.bus(nil, \sensors) }
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

	bus { | val, player, rate = \control, numchans = 1, server |
		// Return the bus for this symbol.
		// If bus does not exist, create new one.
		// If val is not nil, then set the bus to val.
		// Works with new AND already existing busses.
		var bus, envir;
		server = server.asTarget.server;
		player = player ?? { currentEnvironment.name };
		envir = Mediator.at(player);
		bus = envir.busses.at(this);
		if (bus.isNil) {
			bus = Bus.perform(rate, server, numchans);
			{
				server.sync;
				val !? { bus.set(val) };
				Bus.changed(this, player); // reset new bus numbers in other objects?
			}.fork;
			envir.busses.put(this, bus);
		}{
			val !? { bus.set(val) };
		};
		^bus;
	}

	// 30 Nov 2022 14:47
	pb { | player = \pb |
		^Pb(this, player)
	}
}


+ Function {
	@> { | bus, player | // play as kr funcction in bus (or player) name
		player ?? { player = currentEnvironment.name };
		{
			Out.kr(bus.bus(nil, player), this.value.kdsr);
			// A2K.kr(Silent.ar).kdsr;
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
	@> { | bus, playerEnvir = \sensors | // set bus value
		// works with new AND already existing busses.
		// Stop processes playing in this bus before setting a new value:
		bus.stopPlayer(playerEnvir);
		// postln("playerEnvir:" + playerEnvir);
		// postln("bus " + bus + "at playerEnvir: " + bus.bus(nil, playerEnvir));
		bus.bus(nil, playerEnvir ? currentEnvironment.name).set(this);
	}
}

+ Nil {
	@> { | busPlayer, envir | // Stop player for bus
		// stop player with same name as bus in an environment
		busPlayer.stopPlayer(envir, 0);
	}
}
