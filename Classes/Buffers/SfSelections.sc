/* 10 Jul 2023 08:53
Remember SoundFileView selections because sfv seems to forget them when zooming.

*/

SfSelections {
	var <sbgui, <selections, <currentSelection, <currentSelectionIndex;
	*new { | sbgui |
		^this.newCopyArgs(sbgui, { [0, 0] } ! 64).init;
	}

	init {
		currentSelection = selections[0];
		this.addNotifier(sbgui, \selection, { this.setSelection });
	}

	startFrame { ^selections[currentSelectionIndex][0]; }
	endFrame { ^selections[currentSelectionIndex].sum }
	numFrames { ^selections[currentSelectionIndex][1] }
	currentSelectionValues { ^selections[currentSelectionIndex]; }

	setCurrentSelectionValues { | lo, hi |
		selections[currentSelectionIndex] = [lo, hi];
	}

	setSelection { // need a better name!
		currentSelectionIndex = sbgui.sfv.currentSelection;
		currentSelection = sbgui.sfv.selection(currentSelectionIndex);
		selections[currentSelectionIndex] = currentSelection;
	}

	edited { // indices of selections edited
		var edited = [];
		selections.select({ | s | (s.sum > 0) }).do({ | s |
			edited = edited add: selections.indexOf(s)
		});
		edited remove: 63;
		^edited;
	}
}