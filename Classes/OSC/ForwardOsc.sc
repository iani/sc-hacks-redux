/* 28 Nov 2022 10:24
Forward osc messages to a list of addresses.

	Operator options:
	with adverb: send locally to port given by adverb
	without adverb:
    option 1: send to all
    option 2: send to default address

	Design choice:
	without argument: send to all clients
	with argument: send to local address at port given by argument

*/

ForwardOsc {
	classvar >clients;
	classvar <>addr; // default address;

	*initClass {
		Class.initClassTree(NetAddr);
		addr = NetAddr.localAddr;
	}

	clients {
		clients ?? { clients = Set() };
		^clients;
	}

	*add { | addressOrPort |
		addressOrPort ?? { addressOrPort = LocalAddr() };
		if (addressOrPort isKindOf: Integer) {
			addressOrPort = NetAddr("127.0.0.1", addressOrPort);
		};
		this.clients add: addressOrPort;
	}

	forward { | message, port |
		if (port.isNil) {
			NetAddr("127.0.0.1", port).sendMsg(message);
		}{
			clients do: { | c | c.sendAddr(*message) }
		}
	}
}

+ Array {
	>>> { | message, port |
		([message] ++ this).forwardOsc(port);
	}

	fosc { | port | this.forwardOsc(port) }
	forwardOsc { | port | ForwardOsc.forward(this, port); }
}

+ SimpleNumber {
	>>> { | message, port |
		[message, this].forwardOsc(port);
	}
}
