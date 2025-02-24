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

	pget { | player, format = "%" | ^this.bus(nil, player).pget(format) }
	blag { | lag = 0.1 | ^this.bin.lag(lag) }
	bamp { | attack = 0.01, decay = 0.1 | ^this.bin.amp(attack, decay); }

	sr { ^In.kr(this.sensorbus.index) }
	sensorbus { ^this.bus(nil, \sensors) }
	//  kr: ((~buf ? \default).buf ?? { \default.buf }).bufnum;
	brbuf {
		var bufnum;
		bufnum = ((~buf ? \default).buf ?? { \default.buf }).bufnum;
		^this.br(bufnum);
	}
	br { | val | ^this.bin(val) } // alias similar to ar, kr
	bin { | val | ^this.busIn(val) } // input from a named kr bus.
	//synonym. (sic!)
	busIn { | val |
		// bus in
		// Restore following line if needed:
		// ^In.kr(this.bus(val).index)
		var bus;
		bus = this.bus(val);
		// postln("debugging busIn. Bus is:" + bus + "index is" + bus.index);
		// use b_ prefix for controls which refer to buses.

		^In.kr(this.busify.kr(bus.index));
	}
	// prepend b_. Used to mark controls that read from busses
	busify { ^("b_" ++ this).asSymbol }

	// bus { | val, player, rate = \control, numchans = 1, server |
	bus { | val, envirName, rate = \control, numchans = 1, server |
		// Return the bus for this symbol, from environment named envirName.
		// If bus does not exist, create new one.
		// If val is not nil, then set the bus to val.
		// Works with new AND already existing busses.
		var bus, envir;
		server = server.asTarget.server;
		// get envirName from currentEnvironment (if needed):
		envirName = envirName ?? { currentEnvironment.name ?? { ~mediator } };
		envir = Mediator.at(envirName);
		bus = envir.busses.at(this);
		if (bus.isNil) {
			bus = Bus.perform(rate, server, numchans);
			{
				server.sync;
				val !? { bus.set(val) };
				Bus.changed(this, envirName); // reset new bus numbers in other objects?
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

