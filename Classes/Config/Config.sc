//:  9 Aug 2021 16:08
/*
	Configure Server Options
	Load synthdefs and audio files
*/
Config {
	classvar <startupFolder;
	classvar <>projectName = "";
	*initClass {
		StartUp add: {
			startupFolder = "~/sc-hacks-config";
			this.serverConfig;
			Server.default doWhenReallyBooted:  { | server |
				this.loadBuffers;
				server.sync;
				postf("% finished loading buffers\n", server);
				this.loadSynthDefs;
				server.sync;
				postf("% finished loading synthdefs\n", server);
				this.loadProjectServerScripts;
				this.loadPostload;
			};
			ServerQuit.add({
				Library.put(Buffer, nil);
				Library.put(\synthdefs, nil);
			}, Server.default);
		}
	}

	*serverConfig {
		this.subdirDo(
			"Loading server config scripts...",
			"... server config done",
			"serveroptions",
			{ | p |
				postf("loading: %\n", p);
				p.load;
			},
			"scd"
		);
	}

	*subdirDo { | startmessage, endmessage, subfolder, action ... filetypes |
		startmessage.postln;
		(startupFolder +/+ subfolder).filesFoldersDo(
			action, *filetypes
		);
		endmessage.postln;
	}

	*loadBuffers {
		// "loading buffers ...".postln;
		this.subdirDo(
			"loading audio files ...",
			"... buffers loaded",
			"audiofiles",
			{ | p |
				postf("loading: %\n", p);
				p.loadAudiofile;
				// p.load;
			},
			"wav",
			"WAV",
			"aif",
			"aiff"
		)
	}

	*loadScScriptFiles { | subdir,
		beforeMessage = "loading scripts ...",
		afterMessage = "... scripts loaded" |
		this.subdirDo(
			beforeMessage,
			afterMessage,
			subdir,
			{ | p |
				postf("loading: %\n", p);
				p.load;
			},
			"scd"
		)
	}

	*loadSynthDefs {
		var def, name;
		this.subdirDo(
			"loading synthdefs ...",
			"... synthdefs loaded",
			"synthdefs",
			{ | p |
				postf("loading: %\n", p);
				name = PathName(p).fileNameWithoutExtension.asSymbol;
				def = p.load;
				def = def.asSynthDef(name: name);
				def.add;
				Library.put(\synthdefs, name, def);
			},
			"scd"
		)
	}

	*loadPostload {
		this.subdirDo(
			"loading postload ...",
			"... postload loaded",
			"postload",
			{ | p |
				postf("loading: %\n", p);
				p.load;
			},
			"scd"
		);
	}



	*startProject {

	}

	*stopProject {

	}

	*loadProjectServerScripts {
		postf("Loading server scripts for project: %\n", projectName);
		// postf("Project name size is: %\n", projectName.size);
		if (projectName.size == 0) {
			"No project is specified. Not loading project scripts.".postln;
		}{
			this.subdirDo(
				format("loading server boot scripts for project: % ...\n", projectName),
				"... server boot scripts loaded",
				(),
				{  },
			)
		};

	}
}