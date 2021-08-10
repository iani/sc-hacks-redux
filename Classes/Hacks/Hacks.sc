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
			Server.default doWhenReallyBooted: {
				this.loadBuffers;
				this.loadSynthDefs;
			};
			ServerQuit.add({
				Library.put(Buffer, nil);
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
		"loading synthdefs ...".postln;
	}
}