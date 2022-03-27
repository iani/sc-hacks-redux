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

	// 26 Mar 2022 11:11 transferred from OSCRecorder
	classvar activeSessions; //  Dictionary of OSCDataSession.key -> OSCDataSession

	*directory {
		directory ?? { directory = Project.oscDataPath };
		^directory;
	}
	*files {
		^this.directory.deepFiles.select({ | p | p.extension == "scd" });
	}
	*fileNames {
		^this.files.collect(_.fileName);
	}

	*record { | ... messages | // messages to record.
		// OSCData(messages).start;
		this.new(messages).start;
	}

	*stopRecording { | ... messages |
		this.activeSessions[this makeSessionKey: messages].stop;
	}

	*activeSessions {
		activeSessions ?? { activeSessions = Dictionary() };
		^activeSessions;
	}

	*makeSessionKey { | messages |
		^Set.newFrom(messages collect: _.asOscMessage);
	}

	// 26 Mar 2022 11:12 end of code from OSCrecorder

	*new { | messages |
		^this.newCopyArgs(messages collect: _.asOscMessage, List()).init;
	}

	init {
		// set the name from now's date stamp plus
		// all the messages separated by _
		key = this.class makeSessionKey: messages;
		name = Date.getDate.stamp.sepcatList(
			"_",
			key.asArray.sort.collect({ | m | m.asString[1..] })
		)
	}

	start { // only start if not already running:
		this.class.activeSessions[key] ?? { // only nil if not running
			this.class.activeSessions[key] = this;
			OSC addDependant: this;
			this.changed(\started);
			this.class.changed(\started, this);
		};
		postln("Started OSC data recording:" + name)
	}
	stop {
		this.class.activeSessions[key] ?? { ^this };
		OSC removeDependant: this;
		this.class.activeSessions[key] = nil;
		this.changed(\stopped);
		this.class.changed(\stopped, this);
		this.save;
	}
	save {
		var savePath;
		savePath = ((this.directory +/+ ("OscData_" ++ name.asString)).fullPath ++ ".scd").postln;
		this.writeArchive(savePath);
		postln("Saved OSC Data in: " + PathName(savePath).fileName);
	}

	directory { ^this.class.directory }

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