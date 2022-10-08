/*  6 Oct 2022 13:52
Record code evaluated locally from the user as well as
code received via OscGroups.

Provide a gui for viewing and executing recorded code items.

*/

CodeHistory {
	classvar <history;

	*initClass {
		StartUp add: { // Start recording at startup
			this.enable;
		}
	}

	*enable {
		// .code for readability: explicitly listen at key \code
		\code >>>.code { | n, message |
			this.receiveCode(*message[1..]);
		};
		this.addNotifier(OscGroups, \localcode, { | n, code |
			this.receiveCode(Main.elapsedTime, code, OscGroups.localUser);
		})
	}

	*disable {
		\code <<< \code;
		this.removeNotifier(OscGroups, \localcode);
	}

	*receiveCode { | time, code, sender |
		postln("code received!!!. TIME" + time + "CODE" + code + "SENDER" + sender);
		history = history add: [time, code, sender];
		this.changed(history);
	}

	*gui {

	}
}