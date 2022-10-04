/*  4 Oct 2022 10:00
Chat via OSC Groups
*/

Chat {
	classvar history, >username;
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
					postln("sending to chat:" + me.string);
					OscGroups.broadcast(\chat, me.string, this.username);
					this.receiveMessage(me.string, this.username);
				})
			)
		});
	}
}