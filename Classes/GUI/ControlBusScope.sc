/* 10 Nov 2022 14:30
A scope for viewing any named bus in the system.

Note: After rebooting a server, old bus numbers are invalid.
We therefore close the window when the server quits.

*/

ControlBusScope {
	var bus, <busname = \test, <envirname = \envir, <xzoom = 1, <yzoom = 1;
	var <buffer, <synth;

	*default { ^this.fromLib(\default); }

	init {
		// Note: After rebooting a server, old bus numbers are invalid.
		// Therefore close the window when the server quits.
		// TODO: Add way to update bus numbers when the bus is recreated

		// TODO: buffer creation only in one place
		buffer = ScopeBuffer.alloc(Server.default);
		ServerBoot add: {
			SynthDef("ControlBusScope", { | index, bufnum |
				var z;
				z = K2A.ar(In.kr(index, 1));
				ScopeOut2.ar(z, index);
			}).load;
			this.makeBuffer;
			this.changed(\serverBoot); // maybe do stuff later
		};
		ServerQuit add: {
			synth = nil;
			bus = nil;
			buffer = nil;
			this.changed(\serverQuit); // close window if it exists
		}

	}

	makeBuffer {
		buffer = ScopeBuffer.alloc(Server.default);
		this.changed(\buffer, buffer.index);
	}

	bufnum { ^buffer.bufnum }

	index { ^this.bus.index }

	bus {
		// this can invalidate buses stored in InputXyz
		// or other places.
		bus ?? { bus = busname.bus(nil, envirname) };
		^bus;
	}

	start {
		synth = Synth("ControlBusScope", [
			index: this.index, bufnum: this.bufnum
		]);
	}

	stop {

	}

	*gui { ^this.default.gui }

	gui {
		// TODO: where to to put this?
		// .addNotifier(this, \setbus, { | n, index |
					// n.listener.index = index;
				// })
		^this.hlayout(
			[VLayout(
				[ListView(), stretch: 1],
				[ListView(), stretch: 6]
			), stretch: 1],
			[
				ScopeView()
				.server_(Server.default)
				.bufnum_(this.bufnum)
				.addNotifier(this, \buffer, { | n, bufnum |
					n.listener.bufnum_(bufnum)
				}),
				stretch: 5
			]
		).addNotifier(this, \serverQuit, { | n |
			n.listener.close
		});

	}
}