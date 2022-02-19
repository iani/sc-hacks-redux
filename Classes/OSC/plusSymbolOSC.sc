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

	evalOSC { | receiver, index = 1 |
		var codeReceived, result;
		OSC.add(receiver ? this, this, { | notification, message |
			// postf("received this over osc: %\n", message);
			// postf("I'll be forwarding it to: %\n", address);
			postf("Remote evaluation: /* \%\n */\n", message[index]);
			postf("The interpreter is: %", thisProcess.interpreter);
			codeReceived = message[index];
			postf("the code received is: %\n", codeReceived);
			"now I will evaluate".postln;

			// result = thisProcess.interpreter.interpret("10000.rand");
			result = thisProcess.interpreter.interpret(codeReceived);
			"I have evaluated and now I will try posting the result".postln;
			result.postln;
			"I posted the result. How was it?".postln;
			postf("-> %", );
		})
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