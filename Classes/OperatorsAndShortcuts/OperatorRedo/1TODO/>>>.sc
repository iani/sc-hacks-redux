//Fri 23 Feb 2024 12:19
//ForwardOsc - Array
//ForwardOsc - SimpleNumber
//plusSymbolOSC - Symbol

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

+ Symbol {
	>>> { | func, key | // add OSC response to this message under key
		// One can add different functions for the same message under different keys
		// The receiver becomes the message that OSC will respond to.
		// The key can optionally be used to add several actions for the same message.
		// The key becomes a Notification's listener, and the receiver becomes
		// the Notification's message.
		this.addAction(func, key);
	}
}