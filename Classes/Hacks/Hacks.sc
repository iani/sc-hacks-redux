//:  9 Aug 2021 16:08
/*
	Configure Server Options
	Load synthdefs and audio files
*/
Hacks {
	classvar <startupFolder;
	*initClass {
		StartUp add: {
			startupFolder = "~/sc-hacks-config";
			this.serverConfig;
			Server.default doWhenReallyBooted:  { | server |
				this.loadBuffers;
				server.sync;
				postf("% finished loading buffers\n", server);
				this.loadSynthDefs;
				postf("% finished loading synthdefs\n", server);
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
			"Loading Hacks server config scripts...",
			"... Hacks server config done",
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

	*loadSynthDefs {
		var def, name;
		this.subdirDo(
			"loading synthdefs ...".postln,
			"... synthdefs loaded".postln,
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
			"loading postload ...".postln,
			"... postload loaded".postln,
			"postload",
			{ | p |
				postf("loading: %\n", p);
				p.load;
			},
			"scd"
		)
	}
}