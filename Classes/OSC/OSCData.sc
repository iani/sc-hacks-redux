/* 24 Feb 2022 11:53

See OSCRecorder.org

*/

OSCData {
	// limit size of a session to 10000 elements.
	classvar <>maxsize = 10000;
	classvar <>directory;
	var <>messages; // The messages being recorded. If nil. Record everything
	var <data; // The recorded data
	var <name; // date stamp plus messages.

	*new { | ... messages |
		^this.newCopyArgs(messages, List()).init;
	}

	init { name = this.makeName(messages) }

	makeName { | argMessages |
		// set the name from now's date stamp plus
		// all the messages separated by _
		var newName;
		newName = messages
		.collect(_.asOscMessage)
		.collect({ | x |
			x = x.asString;
			if (x[0] === $/) { x[1..] } { x }
		});
		^Date.getDate.stamp.sepcatList("_", newName).asSymbol;
	}

	save {
		var savePath;
		savePath = ((this.getDirectory +/+ ("OscData_" ++ name.asString)).fullPath ++ ".scd").postln;
		this.writeArchive(savePath);
	}

	getDirectory {
		directory ?? { directory = PathName(Platform.userAppSupportDir); };
		^directory;
	}

	*load {  }

	start {
		OSC addDepndant: this;
		this.changed(\started);
		OSCRecorder.changed(\started, this);
	}

	stop {
		OSC removeDepndant: this;
		this.changed(\stopped);
		OSCRecorder.changed(\stopped, this);
	}

	remove { OSC removeDependant: this }

	update { | osc, inMsg ... args |
		if (messages.isNil or: { messages includes: inMsg }) {
			data add: args;
			if (data.size >= maxsize) {
				// cause OSCRecorder to start a new session:
				this.changed(\sizelimit)
			}
		}
	}

}