/* 13 Jun 2023 11:38

*/

FileList {

	// Holds an array pf paths of files containng OSC messages.
	// These are read to create a list of messages that is played
	// by an OscPlayer
	var <timestamp, <paths, <>name;

	*new { | paths, name = "" |
		^this.newCopyArgs(Date.getDate.stamp, paths ? [], name)
	}

	*fromUser { | doneAction |
		Dialog.openPanel({ | argPaths |
			doneAction.(this.new(argPaths));
		}, multipleSelection: true)
	}

	asString {
		^timestamp + name
	}
}
