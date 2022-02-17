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
	classvar sendAddress, <oscRecvFunc;
	classvar <>verbose = false;
	classvar <oscMessage = \code;
	classvar <>notifier; // Only if notifier is OscGroups, then
	// the message is broadcast via OscGroups
	// See methods enableCodeBroadcasting, disableCodeBroadcasting
	classvar <>localUser = \localuser;

	*enable {
		this.makeSendAddress;
		this.enableCodeForwarding;
		this.enableCodeReception;
		"OscGroups enabled".postln;
		this.changedStatus;
	}
	*disable {
		this.disableCodeForwarding;
		this.disableCodeReception;
		"OscGroups disabled".postln;
		this.changedStatus;
	}

	*changedStatus {  this.changed(\status) }

	*sendAddress { ^sendAddress ?? { sendAddress = this.makeSendAddress } }

	*makeSendAddress {
		sendAddress = NetAddr("127.0.0.1", oscSendPort);
		postf("OscGroups set OSC send port to: %\n", oscSendPort);
		^sendAddress;
	}

	*enableCodeForwarding {
		oscMessage.share(sendAddress);
		this.changedStatus;
	}

	*disableCodeForwarding {
		oscMessage.unshare;
		this.changedStatus;
	}

	*enableCodeReception {
		oscMessage.evalOSC;
		this.changedStatus;
	}

	*disableCodeReception {
		oscMessage.unevalOSC;
		this.changedStatus;
	}

	*askLocalUser { "OscGroups askLocalUser method disabled" }


	*forceBroadcastCode { | string |
		// send string to sendAddress independently of current status.
		// Sends even if OscGroups is disabled.
		sendAddress.sendMsg(oscMessage, string, localUser);
	}

	*runLocally { | func |
		this.disableCodeForwarding;
		func.value;
		this.enableCodeForwarding;
	}

	*isEnabled { ^OSC.respondsTo(oscMessage, oscMessage); }
	*isForwarding { ^thisProcess.interpreter.preProcessor.notNil; }

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