/* 29 Jul 2023 15:27
Create gui for handling an Array of Presets.

A PresetList can play its presets only in one player.  To keep things clean,
when a new preset is started, the synths from the previous preset
of this player are stopped.

*/

PresetList {
	classvar <dict, <players, <playerIdConverters;
	classvar <activeLists; // lists whose gui is open
	var <path, <code, <snippets, <presets;
	var <player; // setting the player stops the previous one
	var <currentPreset;

	*initClass {
		StartUp add: { this.init }
	}
	*parentPath { ^PathName(this.filenameSymbol.asString).parentPath }
	*scriptsInLib {
		^PathName(this.parentPath +/+ "PresetScripts" +/+ "*.scd").pathMatch;
	}

	*init {
		this.loadPlayers;
		this.loadPresets;
	}

	*loadPlayers {
		var loaded;
		loaded = (this.parentPath +/+ "playerIdConverters.scd").load.value;
		players = loaded collect: _.player;
		playerIdConverters = IdentityDictionary();
		loaded do: { | l | playerIdConverters[l.name] = l };
	}

	*loadPresets {
		dict = IdentityDictionary();
		this.scriptsInLib do: { | p |
			var n;
			n = PathName(p).fileNameWithoutExtension.asSymbol;
			dict[n] = this.new(p);
		}
	}

	*new { | path |
		^this.newCopyArgs(path).init;
	}

	*allNames { ^dict.keys.asArray.sort}
	*all { ^this.allNames collect: { | n | dict[n] } }
	*first { ^this.all.first }

	init {
		code = File(path, "r").readAllString;
		snippets = code.snippets;
		presets = snippets collect: { | s, i | Preset(this, i, s) };
		player = players.first; // gui's should not permit 2 players in same system?
		// when a list opens, it checks available players by consulting activeLists.
	}
}