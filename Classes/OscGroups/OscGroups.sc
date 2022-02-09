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

DisabledOscGroups {
	// This is an empty class to use as notifier in OscGroups.
	// When notifier in OscGroups is set to the DisabledOscGroups,
	// then OscGroups will NOT broadcast evaluated code,
	// but WILL evaluate code received by other users. See
	// OscGroups methods enableCodeBroadcasting, disableCodeBroadcasting.
}

OscGroups {
	classvar <oscSendPort = 22244, <oscRecvPort = 22245;
	classvar <sendAddress, <oscRecvFunc;
	classvar <>username = "user";
	classvar <>verbose = false;
	classvar <changedMessage = \code;
	classvar <>notifier; // Only if notifier is OscGroups, then
	// the message is broadcast via OscGroups
	// See methods enableCodeBroadcasting, disableCodeBroadcasting

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
		this.enableCodeBroadcasting;
		\forwarder.addNotifier(this, changedMessage, { | notifier, message |
			if (verbose) {
				postf("/* % sending: */ %\n", this, message);
			};
			sendAddress.sendMsg('/code', message);
		});
		thisProcess.interpreter.preProcessor = { | code |
			this.changed(changedMessage, code);
			code;
		};
		CmdPeriod add: this;
		"OscGroups enabled".postln;
		this.changed(\status);
	}

	*runLocally { | func |
		this.disableCodeBroadcasting;
		func.value;
		this.enableCodeBroadcasting;
	}

	*disable {
		oscRecvFunc.free;
		oscRecvFunc = nil;
		\forwarder.addNotifier(this, \code, {});
		"OscGroups disabled".postln;
		this.changed(\status);
	}

	*isEnabled { ^oscRecvFunc.notNil; }
	*isBroadcasting { ^notifier === this }

	*cmdPeriod {
		// Remotely only execute core CmdPeriod method.
		"Sending CmdPeriod to OscGroups".postln;
		sendAddress.sendMsg('/code', "OscGroups.remoteCmdPeriod")
	}

	*remoteCmdPeriod {
		// run basic cmdperiod actions when called via OscGroups
		// Skip cmdPeriod as it would loop sending cmd period to OscGroups
		SystemClock.clear;
		AppClock.clear;
		TempoClock.default.clear;
		// Following would cause endless loop inside OscGroups:
		// objects.copy.do({ arg item; item.doOnCmdPeriod;  });
		Server.hardFreeAll; // stop all sounds on local servers
		Server.resumeThreads;
	}

	*enableCodeBroadcasting  {
		notifier = this;
		"OSC broadcasting enabled.".postln;
		this.changed(\status);
	}

	*disableCodeBroadcasting {
		notifier = DisabledOscGroups;
		"OSC broadcasting disabled.".postln;
		this.changed(\status);
	}

	*enableCodeEvaluation {
		// TODO: Implement this method
	}

	*disableCodeEvaluation {
		// TODO: Implement this method
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

	*gui { // Window with button to enable / disable code broadcasting
		var myself;
		myself = this;
		this.br_(200, 100).window({ | w |
			w.layout = VLayout(
				Button()
				.states_([
					["Disable OscGroups"],
					["Enable OscGroups"]
				])
				.action_({ | me |
					myself.perform([
						\enable,
						\disable
					][me.value]);
				})
				.addNotifier(this, \status, { | n |
					// "Checking OscGroups gui status button".postln;
					// n.notifier.postln;
					postf(
						"osc groups enabled? %\n",
						n.notifier.isEnabled
					);
					if (n.notifier.isEnabled) {
						n.listener.value = 0
					}{
						n.listener.value = 1
					}
				}),
				Button()
				.states_([
					["Disable code broadcasting"],
					["Enable code broadcasting"]
				])
				.action_({ | me |
					myself.perform([
						\enableCodeBroadcasting,
						\disableCodeBroadcasting
					][me.value]);
				})
				.addNotifier(this, \status, { | n |
					// "Checking OscGroups gui broadcasting button".postln;
					// n.notifier.postln;
					postf(
						"osc groups broadcasting? %\n",
						n.notifier.isBroadcasting
					);
					if (n.notifier.isBroadcasting) {
						n.listener.value = 0
					}{
						n.listener.value = 1
					}
				})
			);
			this.changed(\status);
		});
	}
}