/* 23 May 2021 14:02
Share evaluated code via OSCGroups

Note: You should have compiled OscGroupsClient and have started it via command line.
Example:
./OscGroupClient 64.225.97.89 22242 22243 22244 22245 username userpass nikkgroup nikkpass

OscGroups.disable;
OscGroups.enable;

Version 2 19  4 2025 10:40
*/

OscGroups {
	classvar <oscSendPort = 22244, <>oscRecvPort = 22245;
	classvar <sendAddress, <localAddress;
	classvar <>verbose = false, <enabled = false;
	classvar <codeMessage = '/code';
	*initClass {
		StartUp add: {
			this.makeLocalAddress;
			this.makeSendAddress;

		}
	}

	*makeLocalAddress { localAddress = NetAddr.localAddr; }
	*makeSendAddress { sendAddress = NetAddr("127.0.0.1", oscSendPort); }

	*forward { | code, userId |
		if (enabled) {
			postln("     ----- Osc forwards code from user" + userId + "-----");
			sendAddress.sendMsg(codeMessage, code, userId);
		};
	}

	*codeMessage_ { | message = '/code' |
		var reenable;
		reenable = enabled;
		this.disable;
		codeMessage = message;
		if (reenable) { this.enable };
	}

	*enable {
		enabled = true;
		OSC.add(codeMessage, { | n, msg |
			User.run(msg[1].asString, msg[2]);
		});
	}

	*disable {
		enabled = false;
		OSC remove: codeMessage;
	}
}