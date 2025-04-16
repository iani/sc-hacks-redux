/* 29 Sep 2023 07:09
Send OSC to openFrameworks to control graphics.
(Ad hoc work for the Magnetic Dance show. Can be generalized for other
projects later.)

Can broadcast messages to a Set of local NetAddr with different ports.

*/

Ofx {
	classvar >addr; // collection of NetAddr to send messages to

	*initClass {
		StartUp add: {
			this.addLocal(10000)
		}
	}

	*addr { ^addr ?? { addr = Set() } }

	*addLocal { | portNum = 10000 |
		this.addr add: NetAddr("127.0.0.1", portNum);
	}

	*broadcast { | msg | // broadcast entire message to all ports
		this.addr do: { | a | a.sendMsg(*msg) }
	}

	*forward { | msg | // forward message matching msg to all ports
		msg >>> { | n, message |
			this broadcast: message;
		}
	}

}