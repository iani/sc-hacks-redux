/* 13 Sep 2023 13:33
Add synthdefs from subfolder /ChordSynthDefs/

Name each synthdef by the name of the file that contains its code.
The file returns just the function.  The loader cretes and adds the
SynthDef based on the function and the name of the file.
*/

ChordSynthdefLoader {
	classvar <defpaths, <defs, <folders;
	*initClass {
		ServerBoot add: { this.loadDefs	}
	}

	*loadDefs {
		defpaths = PathName(
			this.parentPath +/+ "ChordSynthDefs" +/+ "*.scd"
		).pathMatch;
		folders = PathName(this.parentPath +/+ "ChordSynthDefs/").folders;
		defs = IdentityDictionary();
		defpaths do: { | p |
			var name, def;
			name = p.name;
			def = SynthDef(name, p.load).add;
			defs[p.name] = def;
		}
	}

	*defNames {
		^defs.keys.asArray.sort;
	}

}