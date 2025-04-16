/* 30 Aug 2022 13:17
Redo of OSCRecorder2

Making file saving work!

*/

OSCRecorder2 {
	classvar <rootFolder = "OSCData", <subFolder = "", <>fileHeader = "", <data;
	classvar <maxItems = 100; // readArchive fails for large arrays!

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

	*addData { | ... argData |
		data = data add: argData;
		this.saveIfNeeded;
	}

	*saveIfNeeded {
		if (data.size >= maxItems) {
			this.saveData(data);
			data = [];
		}
	}

	*saveData { | argData |
		this.fullPath.postln;
		{
			argData.writeArchive(this.fullPath)
		}.defer(0);
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
		this.makeDirectory;
		OSC addDependant: this;
		// TODO: Check with OscGroups if \code is the message watched
		this.addNotifier(OscGroups, \code, {  }); // TODO: add code to self
	}

	*disable {
		OSC removeDependant: this;
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