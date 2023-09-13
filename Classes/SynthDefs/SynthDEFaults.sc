/* 13 Sep 2023 13:33
Add synthdefs from subfolder /ChordSynthDefs/

Name each synthdef by the name of the file that contains its code.
The file returns just the function.  The loader cretes and adds the
SynthDef based on the function and the name of the file.

This class loads the SynthDEFaults copied from:
https://sccode.org/1-5aD

// SynthDEFaults - A Collection of Tradicional SynthDefs-
//under GNU GPL 3 as per SuperCollider license
//Organized by Zé Craum

*/

SynthDEFaults {
	classvar <defpaths, <defs, <folders;
	*initClass {
		ServerBoot add: { this.loadDefs	}
	}

	*loadDefs {
		var myname;
		// { "LOADING DEFS".postln; } ! 100;
		myname = this.asString;
		folders = PathName(this.parentPath +/+ myname ++ "/").folders;
		// { folders.postln; } ! 100;

		defs = MultiLevelIdentityDictionary();
		folders do: { | p |
			(p.fullPath +/+ "*.scd").pathMatch do: { | p |
				var name, def;
				name = p.name;
				PathName(p).folderName.postln;
				defs.put(
					PathName(p).folderName.asSymbol,
					name,
					SynthDef(name, p.load).add
				)
			}
		}
	}

	*categories { ^defs.dictionary.keys.asArray.sort }
	*defNames { | category |
		^defs[category].keys.asArray.sort;
	}

}