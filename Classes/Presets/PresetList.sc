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
		activeLists = Set();
		this.loadPlayers;
		this.loadPresets;
	}

	*loadPlayers {
		var loaded;
		loaded = (this.parentPath +/+ "playerIdConverters.scd").load.value;
		players = loaded collect: _.player; // collect the player's names
		playerIdConverters = IdentityDictionary();
		loaded do: { | l | playerIdConverters[l.player] = l };
	}

	*loadPresets {
		dict = IdentityDictionary();
		this.scriptsInLib do: { | p |
			var n;
			n = PathName(p).fileNameWithoutExtension.asSymbol;
			dict[n] = this.new(p);
		}
	}

	*fromPath { | path, player | ^this.newCopyArgs(path).init(player ?? { players.first }); }
	// To be rewritten:
	*new { | path, player | ^this.newCopyArgs(path).init(player ?? { players.first }); }

	*allNames { ^dict.keys.asArray.sort}
	*all { ^this.allNames collect: { | n | dict[n] } }
	*first { ^this.all.first }

	init { | argPlayer |
		code = File(path, "r").readAllString;
		snippets = code.snippets;
		// presets = snippets collect: { | s, i | Preset(this, i, s) };
		// New method - allowing scoreplayers in the future
		// test stage. Next create ScorePlayers if the snippet is a string or symbol.
		presets = snippets collect: { | s, i |
			var seed;
			seed = s.interpret;
			switch (seed.class,
				Event, {
					// Choose subclass of Presets depending on playfunc?
					Preset.newCopyArgs(this, i, s).importDict(seed)
				},
				Symbol, {
					ScorePlayer(this, i, seed)
				},
				String, {
					ScorePlayer(this, i, seed)
				}
			)
		};
		player = argPlayer; // gui's should not permit 2 players in same system?
		// when a list opens, it checks available players by consulting activeLists.
	}

	openSource { Document open: path }
	name { ^PathName(path).fileNameWithoutExtension.asSymbol }

	gui {
		this.addActive;
		Registry(this, this.name, {
			this.changed(\activeLists);
			PresetListGui(this).gui;
		})
	}

	*playerMenu {
		// this.availablePlayers.postln;
		^this.availablePlayers collect: { | p | [p, { | me |
			// postln("you selected player" + p ++". Now making gui!");
			// TODO: customize path choice.
			// PresetList(this.first.path, p.asSymbol).gui;
			this.presetListChoiceGui(p)
		}] }
	}

	*presetListChoiceGui { | p |
		^this.vlayoutKey(\listChoice,
			StaticText().string_("Choose preset list for player" + p + "(Press enter to open)"),
			ListView().items_(dict.keys.asArray.sort)
			.action_({ | me |
				postln("Selected preset:" + me.item);
				// postln("chosen preset" + dict[me.item]);
			})
			.enterKeyAction_({ | me |
				// postln("my item" + me.item);
				postln("Selected preset:" + me.item);
				// postln("chosen preset" + dict[me.item]);
				// postln("chosen preset" + dict[me.item]);
				postln("chosen player" + p);
				postln("chosen path" + dict[me.item].path);
				this.fromPath(dict[me.item].path, p).gui;
			})
		)
	}

	*presetSelectionGui {
		var presetnames, selectedPreset, selectedPlayer;
		this.loadPresets; // update every time
		presetnames = dict.keys.asArray.sort;
		selectedPreset = presetnames.first;
		selectedPlayer = this.availablePlayers.first;
		this.vlayoutKey(\presetSelection,
			HLayout(
				StaticText().string_("SelectPreset:"),
				StaticText().string_("Select Player:")
			),
			HLayout(
				ListView()
				.items_(presetnames)
				.action_({ | me | selectedPreset = me.item.postln; }),
				ListView()
				.items_(this.availablePlayers)
				.action_({ | me | selectedPlayer = me.item.postln; })
				.addNotifier(this, \activeLists, { | n |
					n.listener.items = this.availablePlayers;
					selectedPlayer = this.availablePlayers.first;
				})
			),
			Button().states_([["open"]])
			.action_({
				postln("you selected preset list:" + selectedPreset + "and player:" + selectedPlayer);
				// postln("the preset list has player" + selectedPreset.player);
				postln("the path is" + dict[selectedPreset].path);
				this.fromPath(dict[selectedPreset].path, selectedPlayer).gui;
			})
		)
		.bounds_(Rect(0, 0, 400, 300).center_(Window.availableBounds.center))
	}

	availablePlayers { ^this.class.availablePlayers }

	*availablePlayers {
		var available;
		available = players.copy;
		// postln("available players before test:" + available);
		// postln("activeLists:" + activeLists);
		activeLists do: available.remove(_);
		// postln("available players after test:" + available);
		^available;
	}

	addActive {
		activeLists add: this.player;
		this.class.changed(\activeLists);

	}

	removeActive {
		// postln("before removeActive availablePlayers:" + this.availablePlayers);
		activeLists remove: this.player;
		this.class.changed(\activeLists);
		// postln("after removeActive availablePlayers:" + this.availablePlayers);
	}
}