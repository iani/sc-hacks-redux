/* 23 May 2021 14:02
Share evaluated code via OSCGroups
(see link Ross Bencina...)

This could be a NamedSingleton, but let's keep things simple for now.

Note: You should have compiled OscGroupsClient and have started it via command line.

Example: 

./OscGroupClient 64.225.97.89 22242 22243 22244 22245 username userpass nikkgroup nikkpass


*/

OscGroups {
	classvar <oscSendPort = 22244, <oscRecvPort = 22245;
	classvar <sendAddress, <oscRecvFunc;
	classvar <>username = "user";
	//	classvar <>

	*initClass {
		StartUp add: { this.init };
	}

	*init {
		"INITING OSCGROUPS =====================================".postln;
		sendAddress = NetAddr("127.0.0.1", oscSendPort);
		oscRecvFunc = OSCFunc({ | msg |
			var code, result;
			code = msg[1];
			postf("REMOTE EVALUATION: %\n", code);
			result = thisProcess.interpreter.interpret(code.asString);
			postf("remote: -> %\n", result);
		}, "/code", recvPort: oscRecvPort).fix;
		
		thisProcess.interpreter.preProcessor = { | code |
			\tester.changed(\code, code);
			code;
		};

		\forwarder.addNotifier(\tester, \code, { | notifier, message |
			sendAddress.sendMsg('/code', message);
		});
	}

	*startClientIfNeeded {
		/* only start client if you have not received any ping messages 
			for 10 seconds. */
		var pingReceived = false;
		var pingListener;
		pingListener = OSCFunc({
			
			
		}, "/...", recvPort: oscRecvPort).fix;
		
	}

	*startClient {
		
		
	}
	
}