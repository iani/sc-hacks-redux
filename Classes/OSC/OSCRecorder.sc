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

RecordOSC {
	classvar recordings, <currentRecording;
	classvar <>messages; // messages that we want to record.
	// If empty, then record everything.

	*recordings {
		recordings ?? { recordings = IdentityDictionary;  };
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
		this.recordings[Main.getDate.stamp.asSymbol] = currentRecording;
	}

	isRecording { ^currentRecording.notNil; }

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

	}

	*load { | path |

	}
}
