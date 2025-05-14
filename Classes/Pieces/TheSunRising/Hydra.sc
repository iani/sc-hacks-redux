// 水 14  5 2025 16:29
// Translate/send messages to hydra - on local address

Hydra {
	classvar <address, >port = 5173;

	*initClass {
		StartUp add: {
			this.port = port; // make address with default port
			OSC.addArgs(\hydra, { |  message ... args |
				this.perform(message, *args);
			});
		};
	}

	*port { | argPort = 5173 |
		port = argPort;
		address = NetAddr("127.0.0.1", port);
	}

	*test {


	}

	*do { |  message ... args |
		address.sendMsg(message, *args);
	}

	// forward message and arguments received to hydra, as is
	*forward { | message |
		OSC.add(message, { | n, msg |
			// postln("received message" + msg[0] + "with args" + msg[1..]);
			address.sendMsg(*msg);
		})
	}

}