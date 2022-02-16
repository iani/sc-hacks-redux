/* 14 Feb 2022 10:57
Use Notification to add OSC functions.
*/

+ Symbol {
	>>> { | func, key | // add OSC response to this message under key
		// One can add different functions for the same message under different keys
		OSC.add(key ? this, this, func)
	}

	<<< { | key | // remove action registered under this message and key pair.
		this.removeOSC(key);
	}

	>>? { | key | // does OSC respond to this message under this key?

	}

	removeOSC { | receiver |
		OSC.remove(receiver ? this, this);
	}

	>>@ { | address |
		this.forwardOSC(address)
	}

	forwardOSC { | address, receiver = \forward |
		OSC.add(receiver, this, { | notification, message |
			// postf("received this over osc: %\n", message);
			// postf("I'll be forwarding it to: %\n", address);
			address.sendMsg(*message);
		});
	}

	unforwardOSC { | receiver = \forward |
		this.removeOSC(receiver);
	}

	evalOSC { | index = 1, receiver = \eval |
		OSC.add(receiver, this, { | notification, message |
			// postf("received this over osc: %\n", message);
			// postf("I'll be forwarding it to: %\n", address);
			thisProcess.interpreter.interpret(message[index]);
		})
	}

	unevalOSC { | receiver = \eval |
		this.removeOSC(receiver);
	}

	share { | address, message = \code |
		// share with message and user name
		address = address ?? { OscGroups.sendAddress };
		postf("Start sending, addr: %, message %, user %\n",
			address, message.asCompileString, this.asCompileString
		);
		thisProcess.interpreter.preProcessor = { | code |
			address.sendMsg(message, code.asCompileString, this);
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