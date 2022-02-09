/*  9 Aug 2021 23:38
Utility: Iterate an action on all files that match a search.


*/


+ String {
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
}