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
		postln("PresetListGui stats: player:" + this.player + "numPresets:" + presetList.presets.size);
		this.window.front;
	}

	window {
		var window, canvas;
		// i = 0;
		window = ScrollView(bounds:Rect(0,0,1000,700).center_(Window.availableBounds.center));
		canvas = View();​
		layout = VLayout();
		window.canvas = canvas; // window < (canvas = view) < layout.
		canvas.layout = layout;
		layout add: this.makeHeader;
		this.addPresetViews;
		// layout.add(nil); // stretch remaining empty space
		layout.addNotifier(presetList, \insert, { | n, view, index |
			this.insert(view, index);
		});
		layout.addNotifier(presetList, \remakeViews, { this.addPresetViews });
		window.onClose_({
			"My window closed. Player should return".postln;
			presetList.windowClosed;
		});
		^window.front;
	}

	addPresetViews {
		presetList.presets do: { | p, i |
			// var view;
			// view = p.view;
			// view.addNotifier(presetList, \reload, { view.remove });
			// layout add: view
			this.insert(p.view, i);
		};
		layout add: nil;
	}

	insert { | view, index |
		view.addNotifier(presetList, \reload, { view.remove });
		layout.insert(view, index + 1);
	} // skip initial non-preset element

	makeHeader {
		var view;
		presetList.name.postln;
		view = View().background_(Color.rand);
		view.layout_(
			HLayout(
				StaticText().string_("" ++ presetList.name ++ ":" ++ this.player),
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
				Button().states_([["Reload"]]).action_({ presetList.reload }),
				Button().states_([["Save"]]).action_({ presetList.save }),
				Button().states_([["clonebufs"]]).action_({ presetList.cloneBuffers })
			)
		);
		^view
	}

	player { ^presetList.player }

	openSource { presetList.openSource }

}