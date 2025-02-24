/* 11 Jun 2023 03:46
Forward incoming messages matching a given message to a given address.

Note: This class does what OSC.forward and OSC.unforward does.
However, it keeps messages in a single dictionary making it easier
to display and edit them, and it refuses to forward to LocalAddr,
to avoid loops.

It is possible to implement the above features to OSC, but we will
not do that now, because it is unsure that the OSC implementation
of forwarding is more efficient than that of OscForwarder.

Examples:
- Forward sensor messages to OscGroups client port
- Forward sensor messages to an application listening locally to another port
	eg. openFrameworks listening to NetAddr("127.0.0.1", 10000);

Note: To forward a single message just once, use ForwardOsc.
*/

OscForwarder {
	classvar messages;

	*messages {
		messages ?? { messages = IdentityDictionary() };
		^messages;
	}

	*add { | message, addr |
		var addresses;
		if (addr.class === Integer) { addr = NetAddr("127.0.0.1", addr); };
		if (addr == LocalAddr()) {
			^"OscForwarder refuses to forward to local address".postln;
		};
		addresses = this.messages[message];
		addresses ?? { addresses = Set(); this.messages[message] = addresses };
		addresses add: addr;
	}

	*remove { | message, addr |
		var addresses;
		if (addr.class === Integer) { addr = NetAddr("127.0.0.1", addr) };
		addresses = this.messages[message];
		addresses !? { addresses remove: addr };
		if (addresses.size == 0) { this.messages[message] = nil };
	}

	*enable { OSC addDependant: this; }
	*disable { OSC removeDependant: this; }
	*enabled { ^OSC.dependants includes: this; }
	*update { | sender, message, messageWithArgs |
		// "OscForwarder received:".postln;
		// message.postln;
		// postln("Message with args is:" + messageWithArgs);
		// "update method does not know how to forward this yet".postln;
		this.messages[message] do: { | addr |
			addr.sendMsg(*messageWithArgs)
		}
	}

	*gui {

	}
}