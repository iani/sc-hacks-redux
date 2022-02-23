/* 23 Feb 2022 08:01

57110.trace;
57110.untrace;
*/
+ Integer {

	trace {
		Listener(OSC, { | model, msg, msgplusdata, time, replyAddr, recvPort  |
			// OSC.changed(msg[0], msg, time, replyAddr, recvPort);
			// postln("listener heard: model" + model + " message " + msg
			// 	+ " time " + time + " addr " + replyAddr + " recvPort " + recvPort);
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
		// Forward
		// this should be a symbol method
		// it should imitate Route object from pd/MAX
		Listener(OSC, { | model, msg, msgplusdata, time, replyAddr, recvPort  |
			// OSC.changed(msg[0], msg, time, replyAddr, recvPort);
			// postln("listener heard: model" + model + " message " + msg
			// 	+ " time " + time + " addr " + replyAddr + " recvPort " + recvPort);
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

	|>|  { | index |
		// this should be a symbol method
		// it should imitate Route object from pd/MAX
		"operator not yet implemented".postln;
		// prototype:
		// OSCFunc.addNotifier(this, { | msg |
		//     msg[index] ... do something here?????
		//     // how to make this chainable to a function?
		//     maybe: |>| { | action, index | } ???????
 		// })
		//
		// or maybe set a bus?
		// { | [lo, hi], index | }
	}
}