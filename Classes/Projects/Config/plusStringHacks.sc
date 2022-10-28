/*  9 Aug 2021 23:38
Utility: Iterate an action on all files that match a search.


*/


+ String {
	stripInitialBlanks {
		var b = 0; // blank
		while { this[b] === $\n } {  b = b + 1 };
		if (b == this.size) { ^this } { ^this[b..] }
	}
	header { // return the first line
		var nonblank, nl;
		nonblank = this.stripInitialBlanks;
		nl = nonblank find: "\n";
		nl ?? { ^nonblank };
		^nonblank.copyRange(0, nl - 1);
	}
	interpretIn { | envir |
		envir use: { this.interpret }
	}
	pathMatchDo { | action |
		// perform action on each non-directory path that is matched by me
		this.pathMatch.reject(_.isFolder).do(action.(_))
	}

	fileTypesDo { | action ... types |
		types do: { | type | this.fileTypeDo(action, type) }
	}

	fileTypeDo { | action, type = "scd" |
		//		"testing fileTypeDo".postln;
		postf("iterating over file type: %\n", type);
		postf("path is: %\n", (this +/+ ("*." ++ type)));
		postf("pathmatch is: %\n",  (this +/+ ("*." ++ type)).pathMatch);
		(this +/+ ("*." ++ type)) pathMatchDo: action;
	}

	filesFoldersDo { | action ... fileTypes |
		this.fileTypesDo(action, *fileTypes);
		this.subDirsDo(action, *fileTypes);
	}

	subDirsDo { | action ... fileTypes |
		postf("iterating over sudirs of %\n", this);
		postf("found following subdirs: %\n", this.subDirs);
		this.subDirs do: { | p |
			p.fileTypesDo(action, *fileTypes);
		}
	}

	subDirs { ^(this +/+ "*").pathMatch.select(_.isFolder); }
	isFolder { ^this.last.isPathSeparator }

	loadSynthDef { | server |
		var val;
		server ?? { server = Server.default };
		val = this.load;
		postf("the value of \n%\nis %\n", this, val);
	}

	loadAudiofile { | server |
		var name, buffer;
		server ?? { server = Server.default };
		name = PathName(this).bufferName;
		// postf("loadBuffer: name is: %\n", name);
		if (Library.at(Buffer, name).notNil) {
			"A buffer named '%' already exists. Skipping this".format(name).warn;
		}{
			buffer = Buffer.readWithInfo(server, this);
			buffer !? {
				postf("Loaded buffer %, % channels, %\n",
					name, buffer.numChannels, buffer.dur.formatTime);
				Library.put(Buffer, name, buffer)
			};
		}
	}

	numFolders {
		^PathName(this).numFolders;
	}
}

+ PathName {
	bufferName {
		// return fileNameWithoutExtension.asSymbol, or:
		// if buffer already exists with that name, then:
		// return (folderName ++ "_" ++ fileNameWithoutExtension).asSymbol
		var bufName;
		bufName = this.fileNameWithoutExtension;
		if (Library.at(Buffer, bufName.asSymbol).notNil) {
			bufName = this.folderName ++ "_" ++ bufName;
		};
		^bufName.asSymbol;
	}

	numFolders {
		var numFolders = 1, but1;
		but1 = fullPath.size - 1;
		fullPath do: { | char, i |
			if (i > 0 and: { i != but1 } and: { char.isPathSeparator }) {
				numFolders = numFolders + 1;
			};
		};
		^numFolders;
	}
}