/* 10 Jul 2023 08:53
Remember SoundFileView selections because sfv seems to forget them when zooming.

*/

SfSelections {
	var <sbgui, <selections, <currentSelection, <currentSelectionIndex;
	*new { | sbgui |
		^this.newCopyArgs(sbgui, { | i | [i, 0, 0] } ! 64).init;
	}

	init {
		currentSelection = selections[0];
		this.addNotifier(sbgui, \selection, { this.setSelection });
	}

	setSelection {
		currentSelectionIndex = sbgui.sfv.currentSelection;
		currentSelection = sbgui.sfv.selection(currentSelectionIndex);
	}
}