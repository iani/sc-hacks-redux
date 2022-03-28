/* 24 Feb 2022 11:53

See OSCRecorder.org

*/

OSCData {
	// limit size of a session to 10000 elements.
	classvar <>maxsize = 10000; // maximum size of data array
	classvar <>directory = "osc-data"; // where to store session data
	classvar activeSessions; //  Dictionary of OSCDataSession.key -> OSCDataSession
	classvar <loadedSession; // session loaded from file, used for playback.
	classvar <selectedSessionPath; // path of session selected from gui
	var <>messages; // Set of messages being recorded. If empty, record everything
	var <data; // The recorded data
	var <>sessionStart, <recordingStart, <recordingStop; // recording timestamps
	var <>name; // name for directory containing this session's data files
	var <playbackRoutine, <playbackAddr;

	*fileNames { ^this.files.collect(_.fileName); }
	*files { ^this.dataPath.deepFiles.select({ | p | p.extension == "scd" }); }
	*sessionPaths { ^this.dataPath.folders }
	*sessionNames { ^this.sessionPaths collect: _.folderName }
	*dataPath { ^PathName(Platform.userHomeDir +/+ directory); }

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

	enable {
		OSC addDependant: this;
		postln("Enabled OSCData Session" + name);
	}
	disable {
		OSC removeDependant: this;
		postln("Disabled OSCData Session" + name);
	}

	save {
		var path;
		path = this.dataFilePath;
		{
			this.writeArchive(path);
			postln("Saved OSC Data in: " + path);
		}.fork; // avoid delay in main thread
	}


	*selectSession { // select session from a gui list
		this.window({ | w |
			var sessionPaths, sessionNames;
			sessionPaths = this.sessionPaths;
			sessionNames = this.sessionNames;
			w.name = "OSC data sessions";
			w.bounds = Rect(0, 0, 600, 300);
			w.layout = VLayout(
				HLayout(
					ListView()
					.selectedStringColor_(Color.red)
					.hiliteColor_(Color.gray(0.9))
					.items_(sessionNames)
					.enterKeyAction_({ | me |
						this.loadSession(sessionPaths[me.value]);
					})
					.action_({ | me |
						// sessionPaths[me.value].postln;
						// sessionPaths[me.value].files.postln;
						w.changed(\sessionPath, sessionPaths[me.value])
					}),
					// .valueAction_(0),
					ListView()
					.selectedStringColor_(Color.blue)
					.hiliteColor_(Color.gray(0.9))
					.addNotifier(w, \sessionPath, { | n, path |
						var files;
						files = path.files.select({ | f |
							f.extension == "scd"
						});
						if (files.size == 0) {
							n.listener.items = ["--- (No files in this folder) ---"]
						}{
							n.listener.items = files.collect(_.fileName)
						};
						// path.postln;
					})
				),
				HLayout(
					StaticText().string_("Selected session: (click to load)"),
					Button().states_([["-"]])
					.action_({
						if (selectedSessionPath.notNil) {
							postln("Loading session" + selectedSessionPath.folderName);
							this loadSession: selectedSessionPath;
						}{
							"There is no selected session to load".postln;
						}
					})
					.addNotifier(w, \sessionPath, { | n, path |
						selectedSessionPath = path;
						n.listener.states = [[path.folderName]]
					})
				),
				HLayout(
					StaticText().string_("Loaded session: (click to playback)"),
					Button().states_([["-"]])
					.addNotifier(this, \loadedSession, { | n, session |
						n.listener.states = [[session.name]]
					})
					.action_({ this.startPlayback })
				),
				HLayout(
					StaticText()
					.string_("Num. messages: - Duration: - Sending to: -  Messages sent:")
					.addNotifier(this, \loadedSession, { | n |
						this.displayLoadedSession(n.listener)
					})
					.addNotifier(this, \playbackStarted, { | n |
						this.displayLoadedSession(n.listener)
					})
					.addNotifier(this, \playbackStopped, { | n |
						this.displayLoadedSession(n.listener)
					}),
					NumberBox().maxWidth_(80)
					.addNotifier(this, \playbackIndex, { | n, index |
						n.listener.value = index + 1;
					})
				)
			);
		})
	}

	*displayLoadedSession { | stringView  |
		stringView.string_(
			format(
				"Num. messages: % Duration: % Sending: % Addr: %  Sent:",
				loadedSession.data.size,
				loadedSession.duration.round(0.001),
				["OFF", "ON"][this.isPlaying.binaryValue],
				this.playbackAddressString;
			);
		)
	}

	*playbackAddressString {
		loadedSession ?? { ^"-" };
		^loadedSession.formatAddr;
	}

	formatAddr {
		playbackAddr ?? { ^"-" };
		^format("%:%", playbackAddr.hostname, playbackAddr.port);
	}

	*loadSession { | pathName |
		var sessionsLoaded;
		// pathName.files.select({ | f | f.extension == "scd" }).postln;
		sessionsLoaded = pathName.files.select({ | f | f.extension == "scd" })
		.collect({| p | Object.readArchive(p.fullPath) });
		if (sessionsLoaded.size > 0) { // concatenate data to one session
			loadedSession = sessionsLoaded.first;
			loadedSession addSessionData: sessionsLoaded[1..];
			this.changed(\loadedSession, loadedSession);
		};
	}


	addSessionData { | otherSessions | // add data from other sessions
		otherSessions do: { | session |
			data = data ++ session.data;
		}
	}

	update { | osc, inMsg ... args |
		if ((messages.size == 0) or: { messages includes: inMsg }) {
			data add: args;
			if (data.size >= maxsize) {
				this.saveAndStartNextSession;
			}
		}
	}

	duration {
		^data.flop[1].differentiate.put(0, 0).sum;
	}

	*startPlayback { | addr |
		if (loadedSession.notNil) {
			loadedSession.startPlayback(addr);
		}{
			"There is no loaded session to playback. Skipping this".postln;
		}
	}

	startPlayback { | addr |
		var dt;
		if (this.isPlaying) {
			^postln("Session" + name + "is already playing back. Skipping this.");
		};
		dt = data.flop[1].differentiate;
		dt[0] = 0;
		addr ?? { addr = NetAddr.localAddr };
		playbackAddr = addr;
		postln("Starting playback of session" + name);
		playbackRoutine = {
			this.class.changed(\playbackStarted, this);
			dt do: { | delay, index |
				delay.wait;
				playbackAddr.sendMsg(*data[index][0]);
				this.class.changed(\playbackIndex, index);
			};
			playbackRoutine	= nil;
			this.class.changed(\playbackStopped, this);
		}.fork;
	}

	*isPlaying { ^loadedSession.isPlaying }
	isPlaying { ^playbackRoutine.notNil }

	*stopPlayback {
		if (loadedSession.notNil) {
			loadedSession.stopPlayback;
		}{
			"There is no loaded session to stop. Skipping this".postln;
		}
	}

	stopPlayback {
		if (this.isPlaying) {
			playbackRoutine.stop;
			playbackRoutine	= nil;
			this.class.changed(\playbackStopped, this);
			postln("Session" + name + "stopped playing back.");
		}{
			postln("Session" + name + "is not playing back. Skipping this.");
		}
	}
}