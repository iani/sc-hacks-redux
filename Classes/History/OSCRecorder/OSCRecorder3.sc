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
			"OSCRecorder3 file is not open for writing. Please check!".postln;
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
		var thePath, numAttempts = 0;
		thePath = this.fullPath;
		postln("OSCRecorder3 will open new file here:");
		postln(thePath);
		file = File.open(thePath, "w");
		if (file.isOpen) {
			"The file is open. Continuing with the recording.".postln;
		}{
			{
				"Trying to open the file again - waiting for folder in file system".postln;
				while (file.isOpen.not) do: {
					"Trying to open the file again - waiting for folder in file system".postln;
					file = File.open(thePath, "w");
					numAttempts = numAttempts + 1;
					if (numAttempts > 10) {
						"ERROR: OSCRecorder3 failed to open file!!!!!".postln;
						thePath.postln;
						"ABORTING RECORDING SESSION. PLEASE CHECK FILE SYSTEM".postln;
						^nil;
					};
					0.1.wait;
				};
				"FILE WAS CREATED. RECORDING HAS STARTED".postln;
			}.fork;
		}

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
			file.close;
			postln("OSCRecorder3 closed file:\n" + currentRecordingPath);
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

	*enable {
		if (this.isEnabled) {
			^"OSCRecorder is already running. Skipping this.".postln;
		};
		{ // leave time for directory to exist before making file!
			this.makeDirectory;
			0.5.wait; // 15 Nov 2022 10:23 0.1 was too short?
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

	*makeDirectory { // runs a synchronous command on operating system
		// is called by enable inside a fork, therefore does not delay execution.
		var errorCode;
		this.makeDailySubfolderTimestamp;
		// run command synchronously and collect error:
		errorCode = ("mkdir -p " ++ this.folderPath.replace(" ", "\\ ")).systemCmd;
		// TODO: find out which error signifies a problem, and catch it here
		// if (errorCode == ??? ) { issue a warning }
	}

	*makeDailySubfolderTimestamp {
		sessionStamp = Date.localtime.stamp;
		subFolder = Date.getDate.dayStamp;
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