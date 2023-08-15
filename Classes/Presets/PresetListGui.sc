/* 29 Jul 2023 23:02

*/

PresetListGui {
	var <presetList;
	*new { | presetList |
		^this.newCopyArgs(presetList);
	}

	gui {
		postln("PresetListGui stats: player:" + this.player
			+ "numPresets:" + presetList.presets.size
			+ "first preset player in dict:" + presetList.presets.first.dict[\player]);
		this.window.front;
	}

	window {
		var scroll, canvas, layout;
		scroll = ScrollView(bounds:Rect(0,0,700,700).center_(Window.availableBounds.center));
		scroll.name = presetList.name;
		layout = VLayout();
		layout.add(
			View().background_(Color.black).layout_(
				HLayout(
					Button().states_([["Add"]])
					.action_({ layout.insert(this.makeEntry) }),
					nil // stretch remaining empty space
				)
			)
		);
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
		view = View().background_(Color.rand).layout_(
			HLayout(
				StaticText().string_( ("Presets for:" + this.player) ),
				Button().states_([["Open other player"]])
				.menuActions(PresetList.playerMenu)
				.addNotifier(PresetList, \activeLists, { | n |
					n.listener.menuActions(PresetList.playerMenu)
				})
			)
		);
		^view
	}

	player { ^presetList.player }
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