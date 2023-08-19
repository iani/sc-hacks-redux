/* 27 Jul 2023 23:16

*/

PlainSynth : SynthTemplate {
	playView { | view, preset | // new version: Wed 16 Aug 2023 09:33
		var buffermenu;
		postln("this is PlainSynth.playView");
		buffermenu = Buffer.all collect: { | p | [p, { preset.switchBuffer(p) }] };
		^HLayout(
			CheckBox().string_("play").maxWidth_(50)
			.action_({ | me |
				if (me.value) { preset.play }{ preset.stop }
			})
			.addNotifier(preset.presetList, \stopped, { | n, who |
				if (who !== preset) { n.listener.value = false };
			})
			// TODO: FIX PRESET!!!!!:
			.addNotifier(preset.envir, preset.player, { | n |
				// "Received notification from envir".postln;
				if (envir(preset.player).isPlaying) {
					n.listener.value = false;
					n.listener.focus(true);
				}
			}),
			StaticText().maxWidth_(20).string_(preset.index.asString),
			StaticText().maxWidth_(100).string_(preset.playfunc.asString),
			StaticText().maxWidth_(500).string_("          "),
			preset.templateMenu,
			Button().states_([["-"]]).maxWidth_(15).action_({ preset confirmRemove: view })
		).spacing_(0)
	}
}
