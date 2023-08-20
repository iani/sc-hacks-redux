/* 21 Aug 2023 08:09

*/

PresetView : PresetViewTemplate {
	view {
		var view;
		{ preset.changed(\gui) }.defer(0.1);
		view = View().background_(Color(*Array.rand(3, 0.8, 1.0)));
		view.layout_(
			VLayout(
				preset.template.playView(view, preset),
				this.paramView
			)
		);
		view.addNotifier(preset.presetList, \reload, { view.remove });
		^view;
	}

	paramView { ^VLayout(*preset.params.collect({ | p | p.gui })) }
}