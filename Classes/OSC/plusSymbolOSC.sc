/* 14 Feb 2022 10:57
Use Notification to add OSC functions.
*/

+ Symbol {
	>>> { | func, key | // add OSC response to this message under key
		// One can add different functions for the same message under different keys
		// The receiver becomes the message that OSC will respond to.
		// The key can optionally be used to add several actions for the same message.
		// The key becomes a Notification's listener, and the receiver becomes
		// the Notification's message.
		this.addAction(func, key);
	}

	addAction { | func, key |
		// message, function, key
		OSC.add(this, func, key)
	}

	<<< { | key | // remove action registered under this message and key pair.
		this.removeOSC(key);
	}

	removeOSC { | key |
		OSC.remove(this.asOscMessage, key);
	}

	>>? { | key | // does OSC respond to this message under this key?

	}

	>>@ { | address |
		this.forwardOSC(address)
	}

	forwardOSC { | address, receiver = \forward |
		OSC.add(receiver, this.asOscMessage, { | notification, message |
			// postf("received this over osc: %\n", message);
			// postf("I'll be forwarding it to: %\n", address);
			address.sendMsg(*message);
		});
	}

	unforwardOSC { | receiver = \forward |
		this.removeOSC(receiver);
	}

	evalOSC { | receiver, index = 1 |
		OSC.add(receiver ? this, { | notification, message |
			var code;
			code = message[index].asString;
			postf("Remote evaluation: /* \%\n */\n", code);
			{	// permit window operations via remote evaluated code
				code.interpret.postln;
				OscGroups.changed(\evalCode, code);
			}.defer;
		}, this)
	}

	unevalOSC { | receiver |
		this.removeOSC(receiver ? this);
	}

	share { | address, message = \code |
		// share with message and user name
		address = address ?? { OscGroups.sendAddress };
		postf("Start sending, addr: %, message %, user %\n",
			address, message.asCompileString, this.asCompileString
		);
		thisProcess.interpreter.preProcessor = { | code |
			address.sendMsg(message, code, this);
			code;
		}
	}

	unshare {
		thisProcess.interpreter.preProcessor = nil;
		"Stopped sharing code with OSCGroups".postln;
	}

	record {

	}

	stopRecording {

	}

}