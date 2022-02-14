/* 14 Feb 2022 10:57
Use Notification to add OSC functions.
*/

+ Symbol {
	>>> { | func, receiver |
		OSC.add(receiver ? this, this, func)
	}

	<<< { | receiver |
		this.removeOSC(receiver);
	}

	removeOSC { | receiver |
		OSC.remove(receiver ? this, this);
	}

	>>@ { | address |
		this.forwardOSC(address)
	}

	/*
	forwardOSC { | address, preprocessor |
		preprocessor ?? {
			^this >>> { | ... args |
				address.sendMsg(preprocessor.(*args))
			}
		}{
			address.sendMsg({ | ... args | })
		}
	}

	connect {
		this.forwardOSC(OscGroups.sendAddress);
	}

	disconnect {

	}
	*/

}