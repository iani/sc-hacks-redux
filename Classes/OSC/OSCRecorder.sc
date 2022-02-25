/* 23 Feb 2022 20:36
See OSCRecorder.org
*/

OSCRecorder {
	classvar recordings, <currentRecording;
	// this should be moved to OSCDataSession:
	classvar <>messages; // messages that we want to record.
	// If empty, then record everything.
	classvar <directory;
	classvar <activeSessions; //  Dictionary of OSCDataSession.key -> OSCDataSession

	*recordings {
		recordings ?? { recordings = IdentityDictionary();  };
		^recordings;
	}

	*start { | ... messages | // messages to record.
		var key, session;
		key = this makeSessionKey: messages;
		session = activeSessions[key];
		session ?? {
			session = OSCData(*messages);
			activeSessions[key] = session;
			session.start;
		};
		^session;
	}

	*stop { | ... messages |
		^activeSessions[this makeSessionKey: messages].stop;
	}

	*makeSessionKey { | messages |
		^Set.newFrom(messages collect: _.asOscMessage);
	}


	*initRecording {
		currentRecording = List();
		this.recordings[Date.getDate.stamp.asSymbol] = currentRecording;
	}

	*isRecording { ^currentRecording.notNil; }

	*save { | path |
		/* // OLD VERSION.  Saving-loading all sessions as one object on one file
			// In this version the file would keep getting larger as it accumulates sessinos.
			var savePath;
			savePath = ((this.getDirectory +/+ ("OscData_" ++ Date.getDate.stamp)).fullPath ++ ".scd").postln;
			this.recordings.writeArchive(savePath);

		*/
		// new version: write each recording session to separate file.
		// Enables reloading single recording sessions.

		recordings keysValuesDo: { | sessionName, data |
			var savePath;
			savePath = ((this.getDirectory +/+ ("OscData_" ++ sessionName.asString)).fullPath ++ ".scd").postln;
			data.writeArchive(savePath);
		}

	}

	*load { | path |

	}

	*getDirectory {
		directory ?? { directory = PathName(Platform.userAppSupportDir); };
		^directory;
	}


}
