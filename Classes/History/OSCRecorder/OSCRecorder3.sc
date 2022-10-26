/* 30 Aug 2022 13:17
Redo of OSCRecorder2

Using new format to enable saving of code and of large files (> 100 entries).

OSCRecorder3.rootDir = "/tmp/";

*/

OSCRecorder3 {
	classvar <rootFolder = "OSCData", <subFolder = "", <>fileHeader = "", <data;
	classvar <>rootDir;
	classvar <>maxItems = 1000; // Keep files small!
	classvar <file; // the file where the data are stored.
	classvar <>excludedMessages;
	classvar <>verbose = false;

	*initClass {
		excludedMessages = [
			'/cbmon', '/status.reply', '/done', '/n_end',
			'/recordingDuration', '/n_go', '/d_removed', '/synced'
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
			"saving OSC data in new file: ".post;
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
		^(this.root +/+ rootFolder +/+ subFolder).fullPath;
	}

	*root {
		if (rootDir.isNil) {
			^PathName(Platform.userAppSupportDir);
		}{
			^PathName(rootDir)
		}
	}

	*fullPath {
		^this.folderPath +/+ fileHeader ++ Date.getDate.stamp ++ ".scd";
	}

	*makeDirectory {
		this.makeDailySubfolderTimestamp;
		("mkdir -p " ++ this.folderPath.replace(" ", "\\ ")).unixCmd
	}

	*makeDailySubfolderTimestamp {
		subFolder = Date.getDate.dayStamp;
	}

	*enable {
		{ // leave time for directory to exist before making file!
			this.makeDirectory;
			0.1.wait;
			this.newFile;
			OSC addDependant: this;
			this.addNotifier(Interpreter, \code, { | n, code |
				this.addData(Main.elapsedTime, ['/code', code]);
			});
		}.fork
	}

	*disable {
		OSC removeDependant: this;
		this.removeNotifier(Interpreter, \code);
		this.closeFile;
		// TODO: Check with OscGroups if \code is the message watched
		this.removeNotifier(OscGroups, \code);
	}

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