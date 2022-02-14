/* 14 Feb 2022 10:57
Use Notification to add OSC functions.
*/

+ Symbol {
	>>> { | func, receiver |
		receiver = receiver ? this;
		receiver.addNotifier(\OSC, this, func);
	}

	removeOSC { | receiver |
		this <<< (this ? receiver);
	}

	<<< { | receiver |
		receiver.removeNotifier(\OSC, this);
	}
}