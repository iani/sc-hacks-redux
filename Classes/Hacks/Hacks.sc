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
			Server.default doWhenBooted: {
				{ "hello world".postln; } ! 100;
				this.loadBuffers;
				this.loadSynthDefs;
			}
		}
	}

	*serverConfig {
		var loadPath;
		loadPath = startupFolder +/+ "/serveroptions/*.scd";
		"Loading server option config files".postln;
		postf("Server option directory is: \n%\n", loadPath);
		loadPath.pathMatch do: { | p |
			postf("Loading: %\n", p);
			p.load;
		};
	}

	*loadBuffers {
		"loading buffers ...".postln;
	}

	*loadSynthDefs {
		"loading synthdefs ...".postln;
	}

}