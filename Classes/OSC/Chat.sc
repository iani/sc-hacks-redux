/*  4 Oct 2022 10:00
Chat via OSC Groups
*/

Chat {
	classvar history, >username;
	*initClass {
		StartUp add: { // Start recording at startup
			// .chat for readability: explicitly add this action for key \chat.
			\chat >>>.chat { | n, message |
				this.receiveMessage(*message[1..]);
			}
		}
	}
	*history {
		^history ? [];
	}

	*username {
		username ?? { username = "User" + this.hash.asString; };
		^username;
	}

	*receiveMessage { | message, user |
		history = history.add(format("%:%:% <%>: %",
			Date.localtime.hour, Date.localtime.minute, Date.localtime.second,
			user ? "", message));
		this.changed(\history);
	}

	*gui {
		^this.window({ | w |
			w.view.layout = VLayout(
				StaticText().string_("Messages history:"),
				ListView()
				.items_(this.history)
				.addNotifier(this, \history, { | n |
					n.listener.items = this.history;
				}),
				HLayout(
					StaticText().string_("your name:"),
					TextField().string_(this.username)
					.action_({ | me |
						username = me.string;
						this.changed(\username, me.string);
					})
				),
				StaticText().string_("Type <enter> to send chat message:"),
				TextField().action_({ | me |
					this.broadcastMessage(me.string);
				})
			)
		});
	}
	*broadcastMessage { | argString |
		OscGroups.broadcast(\chat, argString, this.username);
		this.receiveMessage(argString, this.username);
	}
}