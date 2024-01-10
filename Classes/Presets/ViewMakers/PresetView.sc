/* 21 Aug 2023 08:09

*/

PresetView : PresetViewTemplate {
	var <commentsView;
	view {
		var view;
		// postln("Testing code==================================");
		// preset.code.comments.postln;
		{ preset.changed(\gui) }.defer(0.1);
		view = View().background_(Color(*Array.rand(3, 0.8, 1.0)));
		view.layout_(
			VLayout(
				preset.template.playView(view, preset),
				this.makeCommentsView,
				this.paramView
			)
		);
		view.addNotifier(preset.presetList, \reload, { view.remove });
		// preset.commentsView = commentsView;
		^view;
	}

	paramView { ^VLayout(*preset.params.collect({ | p | p.gui })) }
	makeCommentsView {
		^HLayout(
			commentsView = TextView().maxHeight_(40).string_(preset.comments)
			.keyDownAction_({ | me ... args |
				preset.comments = me.string;
				me.defaultKeyDownAction(me, *args)
			})
			// .addNotifier(this, \save, { | n | n.listener.string.postln; })//,
			// Button().maxWidth_(10).states_([["!"]])
			// .action_({ | me |
			// 	this.changed(\save);
			// })
		)
	}
}