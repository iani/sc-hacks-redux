/* 20 Aug 2023 19:44

*/

PdeleteButton : PresetView {
	var presetview;

	*new { | preset, view | ^this.newCopyArgs(preset, view) }

	view {
		^Button().states_([["-"]]).maxWidth_(15).action_({ this.confirmRemove })
	}

	confirmRemove {
		{
			postln("will now remove preset" + preset.index + "from the preset list");
			presetview !? { presetview.remove };
			preset.presetList remove: this;
		}.confirm("Do you really want to remove preset no." + preset.index + "?");
	}

}