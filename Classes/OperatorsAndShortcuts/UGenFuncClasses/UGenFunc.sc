/*  4 Jul 2023 18:22
SuperClass for all shortcut Functions for creating UGens.

Also used to provide specs for creating guis that use these funcs.
The specs are read from files with the same name and in the same folder
as each of the classes, but ending in .scd instead of .sc.

The settings are read by the method that accesses them, so they
are updated to the latest version of the file at the time of access.

For use to write code for playfuncs for SynthFileEvent.
See examples in ~/projects-sc/AudioSampleEvents/default/playfuncs/

*/

UGenFunc {
	// specs cannot be a classvar because subclasses need their own specs.
	// Registry permits this.
	*specs { ^Registry(this, { this.loadSpecs }); }

	// for debugging
	*loadSpecs {
		var path, result;
		path = this.specPath;
		// path.postln;
		if (File exists: path) {
			// postln("found" + path);
			result = path.load;
		}{
			result = []
		};
		^result;
	}

	*specPath {
		^(
			PathName(this.filenameSymbol.asString).pathOnly ++
			PathName(this.filenameSymbol.asString).fileNameWithoutExtension
			++ ".scd"
		)
	}

	*bplay { | buffer = \default, envir, event |
		event ?? { event = () };
		event[\buf] = buffer;
		this.play(event, envir);
	}

	*play { | event, envir |
		envir !? { envir.push };
		event ?? { () } keysValuesDo: { | key, value |
			// value.perform('@>', key);
			currentEnvironment[key] = value;
		};
		currentEnvironment pf: { this.ar };
	}
}