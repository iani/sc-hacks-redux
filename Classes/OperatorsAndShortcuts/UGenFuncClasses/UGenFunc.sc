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
	*specs {
		var specpath;
		specpath = (
			PathName(this.filenameSymbol.asString).pathOnly ++
			PathName(this.filenameSymbol.asString).fileNameWithoutExtension
			++ ".scd"
		);
		if (File.exists(specpath)) {
			^specpath.load;
		}{
			postln("I cannot find specs file for" + this);
			"Returning empty specs".postln;
			^()
		}
	}
}