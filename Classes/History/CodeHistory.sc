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
		// postln("code received!!!. TIME" + time + "CODE" + code + "SENDER" + sender);
		history = history add: [time, code, sender];
		this.changed(\entries);
	}

	*gui {
		this.vlayout(
			ListView()
			.items_(this.listEntries)
			.addNotifier(this, \entries, { | n |
				n.listener.items = this.listEntries;
			})
			.action_({ | me |
				// me.value.postln;
				this.changed(\selectedEntry, me.value)
			})
			.enterKeyAction_({ | me |
				this.evalItem(me.value)
			}),
			TextView()
			.addNotifier(this, \selectedEntry, { | n, entry |
				n.listener.string = history[entry][1]
			})
			.enterInterpretsSelection_(true)
		)
	}

	*listEntries {
		// return array of strings "timestamp: <user>"
		^history collect: { | e | format("%:<%>", e[0].round(0.01), e[2]); }
	}

	*evalItem { | index |
		var code;
		code = history[index][1];
		code.interpret;
		// TODO: Is there a way to interpret + call the preProcessor func?
		Interpreter.changed(\code, code); // add the interpreted item to history, and:
		// ... if code forwarding is enabled, then send it to OscGroups.
	}
}