/* 14 Feb 2022 10:31

Enable alternative OSC reception mechanism using the OSC class.

Add \OSC.changed(\osc)

OSC.addDependant({ | ... args | args.postln; })

*/

+ Main {

	recvOSCmessage { arg time, replyAddr, recvPort, msg; // ... args;
		// this method is called when an OSC message is received.
		// postln("time:" + time + " replyAddr " + replyAddr + " recvPort " + recvPort + " msg " + msg + " args " + args);
		// replacing this to enable OSC disabling
		// Following is now equivalent to OSC.respondTo - when OSC is enabled:
		// OSC.changed(msg[0], msg, time, replyAddr, recvPort); // use OSC class
		// Perform OSC.changed ... when enabled:
		OSC.respondTo(time, replyAddr, recvPort, msg); // respond only when OSC is enabled
		recvOSCfunc.value(time, replyAddr, msg);
		prRecvOSCFunc.value(msg, time, replyAddr, recvPort); // same order as OSCFunc
		OSCresponder.respond(time, replyAddr, msg);
	}
}