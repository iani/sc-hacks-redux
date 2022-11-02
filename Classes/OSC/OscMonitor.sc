/*  2 Nov 2022 10:52
Track incoming OSC messages.
Open windows for viewing input arriving at any selected message.
*/

OscMonitor {
	classvar <messages;
	*enable {
		messages ?? { messages = Set() };
		OSC addDependant: this;

	}
	*disable { OSC removeDependant: this; }
	*update { | sender, message |
		var size;
		size = messages.size;
		messages add: message;
		if (messages.size > size) { this changed: \messages }
	}

	*gui {
		this.enable;
		this.tl_.vlayout(
			// Button().states_([["update list"]]).action_({ this.changed(\messages) }),
			ListView()
			.addNotifier(this, \messages, { | n |
				n.listener.items = messages.asArray.sort;
			})
			.enterKeyAction_({ | me |
				me.item.asSymbol.watch;
			})
		);
		{ this changed: \messages } defer: 1.0;
	}
}