/* 23 Feb 2022 20:36
See OSCRecorder.org
*/

OSCRecorder {
	classvar activeSessions; //  Dictionary of OSCDataSession.key -> OSCDataSession

	*makeSessionKey { | messages |
		^Set.newFrom(messages collect: _.asOscMessage);
	}

	*start { | ... messages | // messages to record.
		OSCData(messages).start;
	}

	*stop { | ... messages |
		activeSessions[this makeSessionKey: messages].stop;
	}

	*activeSessions {
		activeSessions ?? { activeSessions = Dictionary() };
		^activeSessions;
	}

}
