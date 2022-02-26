/* 24 Feb 2022 11:53

See OSCRecorder.org

*/

OSCData {
	// limit size of a session to 10000 elements.
	classvar <>maxsize = 10000;
	var <>messages; // The messages being recorded. If nil. Record everything
	var <data; // The recorded data
	var <name; // date stamp plus messages.
	var <key; // set of messages for storing in activeSessions
	classvar >directory;

	*new { | messages |
		^this.newCopyArgs(messages collect: _.asOscMessage, List()).init;
	}

	init {
		// set the name from now's date stamp plus
		// all the messages separated by _
		key = OSCRecorder makeSessionKey: messages;
		name = Date.getDate.stamp.sepcatList(
			"_",
			key.asArray.sort .collect({ | m | m.asString[1..] })
		)
	}

	start { // only start if not already running:
		OSCRecorder.activeSessions[key] ?? { // only nil if not running
			OSCRecorder.activeSessions[key] = this;
			OSC addDependant: this;
			this.changed(\started);
			OSCRecorder.changed(\started, this);
		};
		postln("Started OSC data recording:" + name)
	}
	stop {
		OSCRecorder.activeSessions[key] ?? { ^this };
		OSC removeDependant: this;
		OSCRecorder.activeSessions[key] = nil;
		this.changed(\stopped);
		OSCRecorder.changed(\stopped, this);
		this.save;
	}
	save {
		var savePath;
		savePath = ((this.directory +/+ ("OscData_" ++ name.asString)).fullPath ++ ".scd").postln;
		this.writeArchive(savePath);
		postln("Saved OSC Data in: " + PathName(savePath).fileName);
	}

	directory { ^this.class.directory }
	*directory {
		directory ?? { directory = PathName(Platform.userAppSupportDir); };
		^directory;
	}

	*loadDialog {

	}

	*loadAll {
		^this.directory.matchingFilesCollect({ |pn|
			Object.readArchive(pn.fullPath);
		}, "OscData_*");
	}

	*loadAllDict {
		var dict;
		dict = IdentityDictionary();
		this.directory.matchingFilesCollect({ |pn|
			Object.readArchive(pn.fullPath);
		}, "OscData_*")
		do: { | d | dict[d.name] = d }
		^dict;
	}

	remove { OSC removeDependant: this }

	update { | osc, inMsg ... args |
		if ((messages.size == 0) or: { messages includes: inMsg }) {
			data add: args;
			if (data.size >= maxsize) {
				this.stop;
				this.class.new(messages).start;
			}
		}
	}

}