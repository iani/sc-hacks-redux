/* 29 Jul 2023 23:02
Redoing this to make it possible to remove Preset views.
*/

PresetListGui {
	var <presetList;
	// var <i;
	*new { | presetList |
		^this.newCopyArgs(presetList);
	}

	gui {
		postln("PresetListGui stats: player:" + this.player
			+ "numPresets:" + presetList.presets.size
			+ "first preset player in dict:" + presetList.presets.first.dict[\player]);
		this.window.front; // doubling window.front ... not needed?
	}

	window {
		var window, canvas, layout;
		// i = 0;
		window = ScrollView(bounds:Rect(0,0,700,700).center_(Window.availableBounds.center));
		canvas = View();		​
		layout = VLayout();
		window.canvas = canvas; // window < (canvas = view) < layout.
		canvas.layout = layout;
		layout add: this.makeHeader;
		presetList.presets do: { | p | layout add: p.view };
		layout.add(nil); // stretch remaining empty space
		^window.front;
	}

	windowOLD {
		var scroll, canvas, layout;
		scroll = ScrollView(bounds:Rect(0,0,700,700).center_(Window.availableBounds.center));
		scroll.name = presetList.name;
		layout = VLayout();
		// layout.add(
		// 	View().background_(Color.black).layout_(
		// 		HLayout(
		// 			Button().states_([["Add"]])
		// 			.action_({ layout.insert(this.makeEntry) }),
		// 			nil // stretch remaining empty space
		// 		)
		// 	)
		// );
		canvas = View();
		canvas.layout = layout;
		scroll.canvas = canvas;
		presetList.presets.reverse do: { | p | layout.insert(p.view) };
		layout.insert(this.makeHeader);
		scroll.onClose = { | me |
			// postln("closed:" + me);
			presetList.removeActive;
			this.objectClosed;
		};
		^scroll.front;
	}

	/*
	makeEntry {
		var view;
		view = View().background_(Color.rand).layout_(
			HLayout(
				TextField().string_( ("This is entry number ") ),
				Button().states_([["Delete"]]).action_({view.remove;})
			)
		);
		^view
	}
	*/

	makeHeader {
		var view;
		view = View().background_(Color.rand);
		view.layout_(
			HLayout(
				StaticText().string_( ("Presets for:" + this.player) ),
				Button().states_([["Edit"]]).action_({ this.openSource }),
				Button().states_([["Open other player"]])
				.menuActions(PresetList.playerMenu)
				.addNotifier(PresetList, \activeLists, { | n |
					n.listener.menuActions(PresetList.playerMenu)
				}),
				Button().states_([["Stop All Local", Color.red, Color.white]])
				.action_({ CmdPeriod.run }),
				Button().states_([["Stop All Global", Color.yellow, Color.red]])
				.action_({ "CmdPeriod.run".share }),
			)
		);
		^view
	}

	player { ^presetList.player }

	openSource { presetList.openSource }
	/*
	makePresetLayout {
		var view = View().background_(Color.rand).layout_(
			HLayout(
				TextField().string_( ("This is entry number " + i.asString) ),
				Button().states_([["Delete"]]).action_({view.remove; i = i - 1;})
			)
		);
		i = i + 1;
		^view
	}
	*/
}