/* 23 Feb 2022 20:36
Record all incoming messages in an array.
Either record dall incoming messages, or record only those
messages that are included in instance variable messages.
For example:

If messages is nil, record everything.
If messages is ['/tr', '/n_go'] then only record messages
that match '/tr' or '/n_go'.

Initial implementation: Just record everything.

Features to add later - possibly delegated to other Classes (!):

Saving to file, reading from file, replaying, sorting into groups


*/

OSCRecorder {
	classvar recordings, <currentRecording;
	classvar <>messages; // messages that we want to record.
	// If empty, then record everything.
	classvar <directory;

	*recordings {
		recordings ?? { recordings = IdentityDictionary();  };
		^recordings;
	}

	*start {
		if (this.isRecording) { ^"RecordOSC is already running. Will not restart".postln; };
		this.initRecording;
		OSC addDependant: this;
		this.changed(\started);
	}

	*initRecording {
		currentRecording = List();
		this.recordings[Date.getDate.stamp.asSymbol] = currentRecording;
	}

	*isRecording { ^currentRecording.notNil; }

	*stop {
		OSC removeDependant: this;
		currentRecording = nil;
		this.changed(\stopped);
	}

	*update { | osc, changedmsg ... args |
		// Id there no messages then add args to recording
		if (messages.size == 0) {
			currentRecording add: args;
		}{ // else only add them if changedmsg
			// is included in the messages hhat we want to record.
			if (messages includes: changedmsg) {
				currentRecording add: args;
			}
		}
	}

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
