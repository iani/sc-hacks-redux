/* 23 Feb 2022 20:36
See OSCRecorder.org

26 Mar 2022 11:15: Factoring this code into OSCData
*/

OSCRecorder {
	classvar activeSessions; //  Dictionary of OSCDataSession.key -> OSCDataSession

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

	*makeSessionKey { | messages |
		^Set.newFrom(messages collect: _.asOscMessage);
	}

}
