/* 24 Feb 2022 11:53

See OSCRecorder.org

*/

OSCData {
	// limit size of a session to 10000 elements.
	classvar <>maxsize = 10000;
	classvar <>directory = "osc-data";
	classvar activeSessions; //  Dictionary of OSCDataSession.key -> OSCDataSession
	var <>messages; // Set of messages being recorded. If empty, record everything
	var <data; // The recorded data
	var <>sessionStart, <recordingStart, <recordingStop; // recording timestamps
	var <>name; // name for directory containing this session's data files

	*fileNames { ^this.files.collect(_.fileName); }
	*files { ^this.dataPath.deepFiles.select({ | p | p.extension == "scd" }); }
	*dataPath {
		^PathName(Platform.userHomeDir +/+ directory);
	}

	*record { | ... messages | // messages to record.
		/*  If an instance exists for these messages, then it is already
			recording, therefore do nothing.
			If not, create a new instance and start recording.
		*/
		var newOscData;
		messages = Set.newFrom(messages collect: _.asOscMessage);
		newOscData = this.activeSessions[messages];
		if (newOscData.isNil) {
			postln("Creating new OSCData instance to record messages:" + messages.asArray);
			newOscData = this.newFromMessages(messages);
			newOscData.stampSessionStart.stampRecordingStart.makeName.makeFolder.enable;
		}{
			postln("an OSCData is already recording messages:" + messages.asArray);
			"Returning the active OSCData instance found".postln;
		};
		^newOscData;
	}

	*newFromMessages { | messages |
		var newOscData;
		newOscData = this.newCopyArgs(messages, Array(maxsize));
		this.activeSessions[messages] = newOscData;
		^newOscData;
	}

	saveAndStartNextSession {
		/* save this instance, remove it from OSC dependants and from activeSessions,
			create new instance with same messages, sessionStart and name,
			add it to activeSessions and to OSC dependantss.
		*/
		this.disable;
		this.save;
		this.class.newFromMessages(messages)
		.name_(name)
		.sessionStart_(sessionStart)
		.stampRecordingStart
		.enable;
	}

	stampSessionStart { sessionStart = Date.localtime.stamp; }
	stampRecordingStart { recordingStart = Date.localtime.stamp }
	makeName {
		name = "OSCData" ++ Date.localtime.stamp ++ "_" ++ (UniqueID.next % 1000);
	}
	makeFolder {
		this.dataFolder.mkdir;
		postln("OSCData made folder" + this.dataFolder);
	}

	dataFolder { ^(this.class.dataPath +/+ name).fullPath; }

	dataFilePath {
		^PathName(this.dataFolder +/+ name ++ ".scd").fullPath;
	}

	*forMessages { | messages |
		var oscdata;
		oscdata = this.activeSessions[messages];
		oscdata ?? { oscdata = this.newCopyArgs(messages); };
		^oscdata;
	}

	init {
		data = Array.new(maxsize);
		recordingStart = sessionStart = Date.localtime;
		name = "OSCData" ++ Date.localtime.stamp ++ "_" ++ (UniqueID.next % 1000);
		this.activeSessions[messages] = this;
		OSC addDependant: this;
	}

	*stopRecording { | ... messages |
		this.activeSessions[this makeSessionKey: messages].stop;
	}

	*activeSessions {
		activeSessions ?? { activeSessions = Dictionary() };
		^activeSessions
	}

	enable {  OSC addDependant: this; }
	disable {  OSC removeDependant: this; }

	// stop {
	// 	OSC removeDependant: this;
	// 	this.class.activeSessions[key] = nil;
	// 	this.changed(\stopped);
	// 	this.class.changed(\stopped, this);
	// 	recordingStop = Date.localtime;
	// 	this.save;
	// }

	save {
		var path;
		path = this.dataFilePath;
		{ this.writeArchive(path); }.fork; // avoid delay in main thread
		postln("Saved OSC Data in: " + path);
	}


	*loadDialog {

	}

	*loadAll {
		// ^this.directory.matchingFilesCollect({ |pn|
		// 	Object.readArchive(pn.fullPath);
		// }, "OscData_*");
	}

	*loadAllDict {
		// r dict;
		// dict = IdentityDictionary();
		// this.directory.matchingFilesCollect({ |pn|
		// 	Object.readArchive(pn.fullPath);
		// }, "OscData_*")
		// do: { | d | dict[d.name] = d }
		// ^dict;
	}

	update { | osc, inMsg ... args |
		if ((messages.size == 0) or: { messages includes: inMsg }) {
			data add: args;
			if (data.size >= maxsize) {
				this.saveAndStartNextSession;
			}
		}
	}



}