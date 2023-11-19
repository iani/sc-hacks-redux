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

	gui2 { // narrower window - for multiple preset lists
		postln("PresetListGui stats: player:" + this.player + "numPresets:" + presetList.presets.size);
		this.window2.front;
	}

	window2 {
		var window, canvas;
		// i = 0;
		window = ScrollView(bounds:Rect(0,0,350,700).center_(Window.availableBounds.center));
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
		// postln("PresetListGui insert. view" + view + "index" + index);
		view.addNotifier(presetList, \reload, { view.remove });
		layout.insert(view, index + 1);
	} // skip initial non-preset element

	makeHeader {
		var view;
		presetList.name.postln;
		view = View().background_(Color(*Array.rand(3, 0.8, 1.0)));
		view.layout_(
			HLayout(
				StaticText().string_(presetList.name.asString),
				// StaticText().string_("" ++ presetList.name ++ ":" ++ this.player),
				Button().states_([[presetList.player.asString]])
				.menuActions(presetList.playerMenu)
				.addNotifier(presetList, \player, { | n |
					n.listener.states_([[presetList.player.asString]])
				})
				.addNotifier(PresetList, \activeLists, { | n |
					n.listener.menuActions(presetList.playerMenu)
				}),
				Button().states_([["Stop All Local", Color.red, Color.white]])
				.action_({ CmdPeriod.run }),
				Button().states_([["Stop All Global", Color.yellow, Color.red]])
				.action_({ "CmdPeriod.run".share }),
				Button().states_([["Edit"]]).maxWidth_(50).action_({ this.openSource }),
				Button().states_([["Reload"]]).maxWidth_(50).action_({ presetList.reload }),
				Button().states_([["Save"]]).maxWidth_(50).action_({ presetList.save }),
				Button().states_([["Clone Buffers"]]).action_({ presetList.cloneBuffers }),
				Button().states_([["Scores"]]).maxWidth_(50)
				.action_({ FileNavigator.browseHacksScores }),
				Button().states_([["Scope"]]).maxWidth_(50)
				.action_({ Server.default.scope; Server.default.meter; })
			)
		);
		^view
	}

	player { ^presetList.player }

	openSource { presetList.openSource }

}
