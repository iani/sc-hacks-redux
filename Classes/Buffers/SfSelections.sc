/* 10 Jul 2023 08:53
Remember SoundFileView selections because sfv seems to forget them when zooming.

*/

SfSelections {
	var <sbgui, <selections, <currentSelection;
	*new { | sbgui |
		^this.newCopyArgs(sbgui, { | i | [i, 0, 0] } ! 64).init;
	}

	init {
		// postln("sbgui is:" + sbgui);
		currentSelection = selections[0];
		this.addNotifier(sbgui, \selection, { this.setSelection });
	}

	setSelection {
		currentSelection = sbgui.sfv.currentSelection;
		currentSelection.postln;
	}
}