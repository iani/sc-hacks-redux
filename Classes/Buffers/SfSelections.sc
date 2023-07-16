/* 10 Jul 2023 08:53
Remember SoundFileView selections because sfv seems to forget them when zooming.

*/

SfSelections {
	var <sbgui, <selections, <currentSelection, <currentSelectionIndex;
	var <params; // Array of SoundParams instances. The selected param instance
		// selection updates my envir at every start or duration frames change
		// should selections store the current param separately?
	var <currentParam;
	// does the playing.
	*new { | sbgui |
		^this.newCopyArgs(sbgui, { [0, 0] } ! 64).init;
	}

	init {
		currentSelection = selections[0];
		// can we use the same method for both of the following?
		// Trigger actions explicitly trough messages from sbgui.
		// Do NOT employ Notifications:
		// this.addNotifier(sbgui, \selection, { this.getSelectionFromGui });
		// this.addNotifier(sbgui, \selectionIndex, { this.setCurrentSelection });

	}

	// EXPERIMENTAL _ IMPORTANT _ CHECK!
	setSelectionFromGui { | index, frameSpecs |
		// set current selection dimensions.
		// Called when the user releases the button.  Also send to synth.
		// sfv and selections index are now the same. No need to update,
		// NOTE but we do it anyway:
		postln("debugging setSelectionFromGui : index" + index + "frameSpecs" + frameSpecs);
		if (true) { ^nil };
		currentSelectionIndex = index;
		currentSelection = frameSpecs;
		selections[currentSelectionIndex] = currentSelection;
		currentParam = params[index];
		// set the environment variables and send to synth:
		currentParam.updateSelectionFrames(currentSelection);
	}
	setCurrentSelectionIndex { | index |
		// Change the current selection to a different selection
		// chosen by the user.  Update all internal caches belonging to this.
		// If the current selection is different, and is playing,
		// then stop it.
		// Also update gui!
		postln("debugging setCurrentSelectionIndex : index" + index);
		if (true) { ^nil };
		if (currentSelectionIndex != index) {
			currentParam.stop;
			currentSelectionIndex = index;
			currentSelection = selections[index];
			currentParam = params[index];
			sbgui.changed(\selection); // update all gui elements
		}{
			// no actions if the selection did not change
		}
	}

	startFrame { ^selections[currentSelectionIndex][0]; }
	endFrame { ^selections[currentSelectionIndex].sum }
	numFrames { ^selections[currentSelectionIndex][1] }
	currentSelectionValues { ^selections[currentSelectionIndex]; }

	setCurrentSelectionValues { | lo, hi |
		selections[currentSelectionIndex] = [lo, hi];
	}

	moveSelectionStartBy { | frames = 1000 | // move start only. dur changes
		this.setCurrentSelectionValues(
			*(this.currentSelectionValues + [frames, frames.neg]); // frames
		);

	}

	moveSelectionDurBy { | frames = 1000 | // move dur only
		this.setCurrentSelectionValues(
			*(this.currentSelectionValues + [0, frames]); // frames
		);
	}

	moveSelectionBy { | frames = 1000 | // move start, keeping dur as is
		this.setCurrentSelectionValues(
			*(this.currentSelectionValues + [frames, 0]);
		);
	}

	resizeSelectionBy { | frames = 1000 | // move start + end by equal amounts
		this.setCurrentSelectionValues(
			*(this.currentSelectionValues + [frames, (2 * frames).neg]);
		);
	}

	edited { // indices of selections edited
		var edited = [];
		selections.select({ | s | (s.sum > 0) }).do({ | s |
			edited = edited add: selections.indexOf(s)
		});
		edited remove: 63;
		^edited;
	}

	openParameterGui {

	}

	// must review this.
	// selections calls it everytime a selection changes start or duration frames
	updateParams { | argParams |
		var paramEnvir;
		paramEnvir = this.currentParams.envir;
		paramEnvir[\startframe] = this.startFrame;
		paramEnvir[\endframe] = this.endFrame;

	}

	currentParams { ^params[currentSelectionIndex] }
}