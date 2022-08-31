/* 30 Aug 2022 13:17
Redo of OSCRecorder2

Using new format to enable saving of code and of large files (> 100 entries).

*/

OSCRecorder3 {
	classvar <rootFolder = "OSCData", <subFolder = "", <>fileHeader = "", <data;
	classvar <>maxItems = 1000; // Keep files small!
	classvar <file; // the file where the data are stored.

	*initClass {
		ShutDown add: { this.closeFile };
	}
	*update { | self, cmd, msg, time, addr, port |

		this.addData(time, msg, addr.addr, port);
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
			this.newFile;
			"OSC data saved in: ".post;
			this.fullPath.postln;
		}
	}

	*newFile {
		this.closeFile;
		file = File.open(this.fullPath, "w");
	}

	*closeFile {
		file !? { file.close };
		data = [];
	}

	*folderPath {
		^(PathName(Platform.userAppSupportDir) +/+ rootFolder +/+ subFolder).fullPath;
	}

	*fullPath {
		^this.folderPath +/+ fileHeader ++ Date.getDate.stamp ++ ".scd";
	}

	*makeDirectory {
		("mkdir " ++ this.folderPath.replace(" ", "\\ ")).unixCmd
	}

	*enable {
		{ // leave time for directory to exist before making file!
			this.makeDirectory;
			0.1.wait;
			this.newFile;
			OSC addDependant: this;
			// TODO: Check with OscGroups if \code is the message watched
			this.addNotifier(OscGroups, \localcode, { | n, code |
				"Testing recording of local code".postln;
				this.addData(Main.elapsedTime, ['/code', code]);
			}); // TODO: add code to self
		}.fork
	}

	*disable {
		OSC removeDependant: this;
		this.removeNotifier(OscGroups, \localcode);
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