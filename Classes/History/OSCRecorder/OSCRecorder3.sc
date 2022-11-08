/* 30 Aug 2022 13:17
Redo of OSCRecorder2

Using new format to enable saving of code and of large files (> 100 entries).

OSCRecorder3.rootDir = "/tmp/";

*/

OSCRecorder3 {
	classvar <rootFolder = "OSCData", <subFolder = "", <sessionStamp;
	classvar <>fileHeader = "", <data;
	classvar <>rootDir;
	classvar <>maxItems = 1000; // Keep files small!
	classvar <file; // the file where the data are stored.
	classvar <>excludedMessages;
	classvar <>verbose = false;
	classvar <currentRecordingPath = "";

	*initClass {
		excludedMessages = [
			'/cbmon', '/status.reply', '/done', '/n_end',
			'/recordingDuration', '/n_go', '/d_removed', '/synced', '/minibee/rssi'
		];
		ShutDown add: { this.closeFile };
	}

	*exclude { | ... argMessages |
		excludedMessages = excludedMessages ++ argMessages;
	}

	*update { | self, cmd, msg, time, addr, port |
		if (excludedMessages includes: msg[0]) {
			// do not record excluded message!
			if (verbose) { postln("osc recorder ignores: " + msg[0]); };
		}{
			this.addData(time, msg);
		}
	}

	*rootFolder_ { | argRootFolder |
		rootFolder = argRootFolder;
		this.makeDirectory;
	}

	*subFolder_ { | argSubFolder |
		subFolder = argSubFolder;
		this.makeDirectory;
	}

	*addData { | time, msg |
		if (file.isNil or: { file.isOpen.not}) {
			^nil; // do not save or record if file is closed!
		};
		data = data add: [time, msg];
		file.putString("\n//:--[" ++ time.asCompileString ++ "]\n");
		file.putString(msg.asCompileString);
		this.saveIfNeeded;
	}

	*saveIfNeeded {
		if (data.size >= maxItems) {
			"saving OSC data in new file: ".postln;
			this.fullPath.postln;
			this.saveAndContinue;
		}
	}

	*newFile {
		file = File.open(this.fullPath, "w");
	}

	*saveAndContinue {
		var oldFile;
		file !? {
			oldFile = file;
			this.newFile;
			{ oldFile.close; }.defer(0.1);
		};
		data = [];
	}

	*closeFile {
		// "DEBUGGING. file is:".postln;
		// file.postln;
		file !? {
			// "I will closse the file".postln;
			file.close;
		}
	}


	*folderPath {
		^(this.root +/+ rootFolder +/+ subFolder +/+ sessionStamp).fullPath;
	}

	*root {
		if (rootDir.isNil) {
			^this.osDependentRootDir;
		}{
			^PathName(rootDir)
		}
	}

	*osDependentRootDir {
		if (thisProcess.platform.class === OSXPlatform) {
			rootFolder = "SuperCollider Recordings";
			^PathName(Platform.userHomeDir +/+ "Music")
		}{
			^PathName(Platform.userAppSupportDir);

		}
	}

	*fullPath {
		^currentRecordingPath = this.folderPath +/+ fileHeader ++ Date.getDate.stamp ++ ".scd";
	}

	*makeDirectory {
		this.makeDailySubfolderTimestamp;
		("mkdir -p " ++ this.folderPath.replace(" ", "\\ ")).unixCmd
	}

	*makeDailySubfolderTimestamp {
		sessionStamp = Date.localtime.stamp;
		subFolder = Date.getDate.dayStamp;
	}

	*enable {
		if (this.isEnabled) {
			^"OSCRecorder is already running. Skipping this.".postln;
		};
		{ // leave time for directory to exist before making file!
			this.makeDirectory;
			0.1.wait;
			this.newFile;
			OSC addDependant: this;
			this.addNotifier(Interpreter, \code, { | n, code |
				this.addData(Main.elapsedTime, ['/code', code]);
			});
			this.changed(\enabled_p, true);
			postln("!!!!!!! Recording OSC Data at:");
			postln(currentRecordingPath);
		}.fork
	}

	*disable {
		OSC removeDependant: this;
		this.removeNotifier(Interpreter, \code);
		this.closeFile;
		// TODO: Check with OscGroups if \code is the message watched
		this.removeNotifier(OscGroups, \code);
		this.changed(\enabled_p, false);
		postln("OSCRecorder3 stopped recording at:");
		postln(currentRecordingPath);
	"OSCRecorder"
	}

	*isRecording { ^this.isEnabled; }
	*isEnabled { ^OSC.dependants includes: this }

	*addLocalCode { | code |
		// also record code executed locally
		if (this.isEnabled) {
			/*	*update { | self, cmd, msg, time, addr, port |
		this.addData(time, msg, addr.addr, port);
	}
			*/
			//			this.addData(this, '/code',
			//			['/code', code],
			//          Main.elapsedTime,
			//          NetAddr.localAddr.addr,
			//          NetAddr.localAddr.port
			//          )
		}
	}
}