/* 23 May 2021 14:02
Share evaluated code via OSCGroups
(see link Ross Bencina...)

This could be a NamedSingleton, but let's keep things simple for now.

Note: You should have compiled OscGroupsClient and have started it via command line.

Example: 

./OscGroupClient 64.225.97.89 22242 22243 22244 22245 username userpass nikkgroup nikkpass

OscGroups.disable;
OscGroups.enable;
*/

OscGroups {
	classvar <oscSendPort = 22244, <oscRecvPort = 22245;
	classvar <sendAddress, <oscRecvFunc;
	classvar <>username = "user";
	classvar <>verbose = false;
	//	classvar <>

	*initClass {
		StartUp add: { this.init };
	}

	*init {
		"INITING OSCGROUPS =====================================".postln;
		sendAddress ?? {
			"To enable OscGroups evaluate:\nOscGroups.enable;\n".postln;
		}
	}

	*enable {
		oscRecvFunc !? { oscRecvFunc.free };
		sendAddress = NetAddr("127.0.0.1", oscSendPort);
		oscRecvFunc = OSCFunc({ | msg |
			var code, result;
			code = msg[1];
			postf("REMOTE EVALUATION: %\n", code);
			result = thisProcess.interpreter.interpret(code.asString);
			postf("remote: -> %\n", result);
			msg.postln;
		}, "/code", recvPort: oscRecvPort).fix;
		\forwarder.addNotifier(this, \code, { | notifier, message |
			if (verbose) {
				postf("/* % sending: */ %\n", this, message);
			};
			sendAddress.sendMsg('/code', message);
		});
		thisProcess.interpreter.preProcessor = { | code |
			// \tester.changed(\code, code);
			this.changed(\code, code);
			code;
		};
		CmdPeriod add: this;
		"OscGroups enabled".postln;
	}

	*disable {
		oscRecvFunc.free;
		\forwarder.addNotifier(this, \code, {});
		CmdPeriod remove: this;
		"OscGroups disabled".postln;
	}

	*cmdPeriod {
		// Remotely only execute core CmdPeriod method.
		"Sending CmdPeriod to OscGroups".postln;
		sendAddress.sendMsg('/code', "OscGroups.remoteCmdPeriod.")
	}

	*remoteCmdPeriod {
		// run basic cmdperiod actions when called via OscGroups
		// Skip cmdPeriod as it would loop sending cmd period to OscGroups

		SystemClock.clear;
		AppClock.clear;
		TempoClock.default.clear;
		// This would cause endless loop inside OscGroups:
		// objects.copy.do({ arg item; item.doOnCmdPeriod;  });

		Server.hardFreeAll; // stop all sounds on local servers
		Server.resumeThreads;
	}

	*enableCodeEvaluation {
		
		
	}

	*disableCodeEvaluation {
		
		
	}
	
	*enableCodeForwarding {
		
		
	}

	*disableCodeForwarding {
		
		
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
		"OscGroupClient 64.225.97.89 22242 22243 22244 22245 iani ianipass nikkgroup nikkpass".unixCmd;
	}

}