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
	classvar sendAddress, <oscRecvFunc;
	classvar <>verbose = false;
	classvar <oscMessage = \code;
	classvar <>notifier; // Only if notifier is OscGroups, then
	// the message is broadcast via OscGroups
	// See methods enableCodeBroadcasting, disableCodeBroadcasting
	classvar <>localUser = \localuser; // TODO: delete this if it is not used!!!

	*initClass {
		StartUp add: {
			thisProcess.interpreter.preProcessor = { | code |
			Interpreter.changed(\code, code);
			code;
			}
		}
	}
	// this may no longer be valid on  1 Sep 2022 20:13
	*isEnabled { ^OSC.listensTo(oscMessage, oscMessage); } // NEEDS CHECKING!
	*isForwarding {
		// ^thisProcess.interpreter.preProcessor.notNil;
		// "isForwarding not implemented! look into NOtification!".postln;
		// look into this:
		//			*listeningto { | notifier |
		^this isListeningTo: Interpreter;
	}

	*forward { | message |
		// forward an osc message to OscGroupsClient
		// default message is '/minibee/data'
		message ?? { message = '/minibee/data' };

		message.asOscMessage >>>.forward { | n, msg, time, addr, port |
			// postln("time" + time + "addr" + addr + "port" + port)
			if (port == 57120) {
				sendAddress.sendMsg(*msg);
			}
		}
		//		'/minibee/data' >>> { | n, msg |
		//	OscGroups.sendAddress.postln;
		// msg.postln;
	}

	*unforward { | message | // stop forwarding message to OscGroupClient
		//
		message ?? { message = '/minibee/data' };
		message = message.asOscMessage;
		OSC.remove(message, 'forward');
	}

	*enable {
		this.makeSendAddress;
		this.enableCodeForwarding;
		this.enableCodeReception;
		CmdPeriod add: this;
		"OscGroups enabled".postln;
		this.changedStatus;
	}
	*disable {
		this.disableCodeForwarding;
		this.disableCodeReception;
		CmdPeriod remove: this;
		"OscGroups disabled".postln;
		this.changedStatus;
	}

	*broadcast { | message ... args |
		if (this.isEnabled) {
		sendAddress.sendMsg(message, *args);
		}{
			postln("OscGroups is disabled.");
			postln("Cannot broadcast " + message + args);
			postln("To enable OscGroups run: OscGrups.enable");
		}
	}

	*enableCodeForwarding {
		// send evaluated code to sendAddress using oscMessage and adding localUser
		this.addNotifier(Interpreter, \code, { | n, code |
			this.changed(\localcode, code); // OSCRecorder records the code here.
			sendAddress.sendMsg(oscMessage, code);
		});
		this.changedStatus;
	}

	*disableCodeForwarding {
		 // deactivate sharing by settging Interpreter's preprocessor to nil.
		// localUser.unshare;
		this.removeNotifier(Interpreter, \code);
		this.changedStatus;
	}

	*enableCodeReception { // TODO: Use oscRecvPort instead
		thisProcess.openUDPPort(22245); // oscRecvPort
		oscMessage.evalOSC;
		this.changedStatus;
	}

	*openUDPPort { // TODO: Use oscRecvPort instead
		thisProcess.openUDPPort(22245); // oscRecvPort
	}

	*disableCodeReception {
		oscMessage.unevalOSC;
		this.changedStatus;
	}

	*changedStatus {  this.changed(\status) }

	*sendAddress { ^sendAddress ?? { sendAddress = this.makeSendAddress } }

	*makeSendAddress {
		sendAddress = NetAddr("127.0.0.1", oscSendPort);
		postf("OscGroups set OSC send port to: %\n", oscSendPort);
		^sendAddress;
	}

	*askLocalUser { "OscGroups askLocalUser method disabled" }

	*forceBroadcastCode { | string |
		// TODO: is this method superseeded by broadcast?
		// send string to sendAddress independently of current status.
		// Sends even if OscGroups is disabled.
		sendAddress.sendMsg(oscMessage, string, localUser);
	}

	*runLocally { | func |
		var isCodeForwarding;
		isCodeForwarding = this.isForwarding;
		// postln("BEFORE: should I restore forwarding?" + isCodeForwarding);
		this.disableCodeForwarding;
		func.value;
		// postln("AFTER: should I restore forwarding?" + isCodeForwarding);
		if (isCodeForwarding) {
			// "Enabling code forwarding - return to original state".postln;
			this.enableCodeForwarding;

		}{
			// "I will not enable code forwarding!".postln;
		};
	}

	*cmdPeriod {
		// Remotely only execute core CmdPeriod method.
		"Sending CmdPeriod to OscGroups".postln;
		sendAddress.sendMsg(oscMessage, "OscGroups.remoteCmdPeriod")
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

	*startClient {
		// 22243 port number should be different for each computer running in one LAN
		"OscGroupClient 64.225.97.89 22242 22243 22244 22245 iani ianipass nikkgroup nikkpass".runInTerminal;
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
					["Disable code forwarding"],
					["Enable code forwarding"]
				])
				.action_({ | me |
					myself.perform([
						\enableCodeForwarding,
						\disableCodeForwarding
					][me.value]);
				})
				.addNotifier(this, \status, { | n |
					// "Checking OscGroups gui forwarding button".postln;
					// n.notifier.postln;
					postf(
						"osc groups forwarding? %\n",
						n.notifier.isForwarding
					);
					if (n.notifier.isForwarding) {
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