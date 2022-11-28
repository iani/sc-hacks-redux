/* 28 Nov 2022 10:24
Forward osc messages to a list of addresses.
*/

ForwardOsc {
	classvar >clients;

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

	forward { | message |
		clients do: { | c | c.sendAddr(*message) }
	}
}


+ Array {
	fosc { this.forwardOsc }
	forwardOsc { ForwardOsc.forward(this); }
}