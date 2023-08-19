/* 29 Jul 2023 23:02
Redoing this to make it possible to remove Preset views.
*/

PresetListGui {
	var <presetList;
	var <layout; // add/remove items here
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
		var window, canvas;
		// i = 0;
		window = ScrollView(bounds:Rect(0,0,800,700).center_(Window.availableBounds.center));
		canvas = View();		​
		layout = VLayout();
		window.canvas = canvas; // window < (canvas = view) < layout.
		canvas.layout = layout;
		layout add: this.makeHeader;
		presetList.presets do: { | p | layout add: p.view };
		layout.add(nil); // stretch remaining empty space
		layout.addNotifier(presetList, \insert, { | n, view, index |
			[n.listener, view, index].postln;
			postln("will insert" + view + "into" + n.listener + "at" + index);
			this.insert(view, index);
		});
		^window.front;
	}

	insert { | view, index | layout.insert(view, index + 1);} // skip initial non-preset element


	makeHeader {
		var view;
		view = View().background_(Color.rand);
		view.layout_(
			HLayout(
				StaticText().string_( ("Presets for:" + this.player) ),
				Button().states_([["Open other player"]])
				.menuActions(PresetList.playerMenu)
				.addNotifier(PresetList, \activeLists, { | n |
					n.listener.menuActions(PresetList.playerMenu)
				}),
				Button().states_([["Stop All Local", Color.red, Color.white]])
				.action_({ CmdPeriod.run }),
				Button().states_([["Stop All Global", Color.yellow, Color.red]])
				.action_({ "CmdPeriod.run".share }),
				Button().states_([["Edit"]]).action_({ this.openSource }),
				Button().states_([["Clean"]]).action_({ presetList.clean }),
				Button().states_([["Save"]]).action_({ presetList.save })
			)
		);
		^view
	}

	player { ^presetList.player }

	openSource { presetList.openSource }

}