/* 30 Aug 2022 13:17
Redo of OSCRecorder2

Making file saving work!

*/

OSCRecorder2 {
	classvar <rootFolder = "OSCData", <subFolder = "", <data;
	classvar <maxItems = 1000;

	*initClass {
		StartUp add: { "OSCRecorder2 is now initing at startup".postln; }
	}
	*update { | self, cmd, msg, time, addr, port |
		// postln("time:" + time + "data" + msg + "addr" + addr + "hostname" + addr.hostname + "port" + port);
		this.addData(time, msg, addr, port);
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
		{
			// Array.rand(10, 0, 10).writeArchive(this.fullPath)
			argData.writeArchive(this.fullPath)
		}.defer(0);
	}

	*folderPath {
		^(PathName(Platform.userAppSupportDir) +/+ rootFolder +/+ subFolder)
		.fullPath.replace(" ", "\\ ");
	}

	*fullPath {
		var filePath;
		"will be making a path".postln;
		filePath = this.folderPath ++ Date.getDate.stamp ++ ".scd";
		filePath.postln;
	}

	*makeDirectory {
		("mkdir " ++ this.folderPath).unixCmd
	}

	*enable {
		this.makeDirectory;
		OSC addDependant: this;
	}

	*disable {
		OSC removeDependant: this;
	}

	*isEnabled { ^OSC.dependants includes: this }
}