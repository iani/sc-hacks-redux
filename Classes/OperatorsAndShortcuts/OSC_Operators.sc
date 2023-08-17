/* 23 Feb 2022 08:01

57110.trace;
57110.untrace;
*/
+ Integer {

	trace {
		Listener(OSC, { | model, msg, msgplusdata, time, replyAddr, recvPort  |
			if (replyAddr.port == this) {
				postln("Received: " + msgplusdata + " from " + replyAddr + " at time: " + time);
			}
		}, \trace);
		postf("Tracing messages from port %\n", this);
	}

	untrace {
		Listener.remove(OSC, \trace);
		postf("Stopped tracing messages from port %\n", this);
	}

	forward  { | netAddr |
		// Forward messages from receiver port to another address
		Listener(OSC, { | model, msg, msgplusdata, time, replyAddr, recvPort  |
			if (replyAddr.port == this) {
				netAddr.sendMsg(*msgplusdata);
			};
		}, NetAddr.localAddr.asString.asSymbol);
		postln("Forwarding messages from port " + this + " to " + netAddr)
	}

	unforward { | netAddr |
		Listener.remove(OSC, NetAddr.localAddr.asString.asSymbol);
		postln("Stopped forwarding messages from port " + this + " to " + netAddr)

	}

}


+ Symbol {
	trace {
		this >>>.trace { | ... args | args.postln; };
	}

	traceChanges {
		this addDependant: Trace;
	}

	untrace { this >>>.trace nil }

	|>|  { | action, key |
		// run an action when receiving osc message
		// this: the name of the message ("/" is prepended if needed).
		// key: can be used to add many different actions to one message
		// Defaults to this.
		// Action: A function to run.
		this.addOsc(action, key);
	}

	addOsc { | action, key |
		(key ? this).addNotifier(OSC, this.asOscMessage, action);
	}

	removeOsc { | key |
		(key ? this).removeNotifier(OSC, this.asOscMessage);
	}
}