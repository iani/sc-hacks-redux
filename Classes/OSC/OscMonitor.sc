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
			Button()
			.states_([["Record OSC"], ["Stop Recording OSC"]])
			.action_({ | me |
				[\disable, \enable][me.value].postln;
				OSCRecorder3.perform([\disable, \enable][me.value]);
			})
			.addNotifier(OSCRecorder3, \enabled_p, { | n, enabled_p |
				postln("OSC Recording status in now: " + enabled_p);
				if(enabled_p) { n.listener.value = 1 } { n.listener.value = 0 }
			}),
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