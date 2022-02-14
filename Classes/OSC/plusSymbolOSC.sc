/* 14 Feb 2022 10:57
Use Notification to add OSC functions.
*/

+ Symbol {
	>>> { | func, receiver |
		OSC.add(receiver ? this, this, func)
	}

	removeOSC { | receiver |
		OSC.remove(receiver ? this, this);
	}

	<<< { | receiver |
		this.removeOSC(receiver);
	}
}