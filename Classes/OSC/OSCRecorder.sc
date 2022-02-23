/* 23 Feb 2022 20:36
Record all incoming messages in an array.

Initial implementation: Just record everything.

Features to add later - possibly delegated to other Classes (!):

Saving to file, reading from file, replaying, sorting into groups

*/

RecordOSC {
	classvar recordings, <currentRecording;

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

	*update { | osc ... args |
		currentRecording add: args;
	}

	*save { | path |

	}

	*load { | path |

	}
}
