/* 23 May 2021 14:02
Share evaluated code via OSCGroups
(see link Ross Bencina...)

This could be a NamedSingleton, but let's keep things simple for now.
*/

OscGroups {
	classvar <sendAddress, <oscRecvFunc;

	*init {
		"INITING OSCGROUPS =====================================".postln;
		sendAddress = NetAddr("127.0.0.1", 22244);
		oscRecvFunc = OSCFunc({ | msg |
			var code, result;
			code = msg[1];
			// "received message code".postln;
			postf("REMOTE EVALUATION: %\n", code);
			// "now i am going to execute the code".postln;
			result = thisProcess.interpreter.interpret(code.asString);
			// postf("the result of the evaluation is: %\n", result);
			postf("remote: -> %\n", result);
		}, "/code", recvPort: 22245).fix;
		
		thisProcess.interpreter.preProcessor = { | code |
			// postf("Testing. SHOULD SEND this code to oscgroups: %\n ", code);	
			// postf("Testing. The ssend address is: %\n ", ~oscSendAddress);
			\tester.changed(\code, code);
			code;
		};

		\forwarder.addNotifier(\tester, \code, { | notifier, message |
			// postf("the notifier is %, the message is: %\n", notifier, message);
			// postf("the address is %\n", sendAddress);
			sendAddress.sendMsg('/code', message);
		});
	}
}