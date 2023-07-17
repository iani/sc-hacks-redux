/* 10 Jul 2023 08:53
Remember SoundFileView selections because sfv seems to forget them when zooming.

*/

SfSelections {
	var <sbgui, <selections, <currentSelection, <currentSelectionIndex = 0;
	var <params; // Array of SoundParams instances. Each param holdes a dictionary
	// of parameter values used for playing the sound, and paramater specs for creating
	// a gui to edit these parameters.
	var <currentParam; // SoundParams instance for currentSelection
	// does the playing.
	*new { | sbgui |
		^this.newCopyArgs(sbgui, { [0, 0] } ! 64).init;
	}

	bufName { ^sbgui.name }
	playfunc { ^sbgui.playfunc }
	playfunc_ { | playfunc | // replace currentParam with a param from the playfunc's template
		var newparam;
		currentParam.close; // stop sound and close gui
		// newparam =
	}
	init {
		currentSelection = selections[0];
		// create params:
		params = { SoundParams(this, this.playfunc); } ! 64;
		currentParam = params[0];
		// Do NOT employ Notifications. DO NOT RE-ADD THESE!:
		// (Instead, trigger actions explicitly trough messages from sbgui.)
		// this.addNotifier(sbgui, \selection, { this.getSelectionFromGui });
		// this.addNotifier(sbgui, \selectionIndex, { this.setCurrentSelection });

	}

	// EXPERIMENTAL _ IMPORTANT _ CHECK!
	setSelectionFromGui { | index, frameSpecs |
		// set current selection dimensions.
		// Called when the user changes selection dimensions with the mouse on
		// the SoundFileView (see its action_ method).  Store frameSpecs and send them to synth.
		// sfv and selections index are now the same. No need to update index
		// but we do it anyway:
		// postln("debugging setSelectionFromGui : index" + index + "frameSpecs" + frameSpecs);
		// if (true) { ^nil };
		currentSelectionIndex = index;
		currentSelection = frameSpecs;
		selections[currentSelectionIndex] = currentSelection;
		currentParam = params[index];
		currentParam.setFrame(frameSpecs);
	}
	setCurrentSelectionIndex { | index |
		// Change the current selection to a different selection
		// chosen by the user.  Update all internal caches belonging to this.
		// If the current selection is different, and is playing,
		// then stop it.
		postln("debugging setCurrentSelectionIndex : index" + index);
		// if (true) { ^nil };
		if (currentSelectionIndex != index) { currentParam.stop; };
		// update gui!
		currentSelectionIndex = index;
		currentSelection = selections[index];
		currentParam = params[index];
		sbgui.changed(\selectionIndex); // update sfv selection
		sbgui.changed(\selection); // update all other gui elements
		// no actions if the selection did not change
	}

	startFrame {
		// postln(
		// 	"selections startframe index: " + currentSelectionIndex
		// 	+ "selections[index]" + selections[currentSelectionIndex][0]
		// 	+ "startframe" + selections[currentSelectionIndex][0]
		// );
		^selections[currentSelectionIndex][0]; }
	endFrame { ^selections[currentSelectionIndex].sum }
	numFrames { ^selections[currentSelectionIndex][1] }
	currentSelectionValues { ^selections[currentSelectionIndex]; }

	setCurrentSelectionValues { | lo, hi |
		selections[currentSelectionIndex] = [lo, hi];
		currentParam.setFrame([lo, hi]);
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
		currentParam.gui;
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

	setPlayfunc { | argPlayfunc |
		currentParam.close; // stop synth, close gui;
		currentParam.playfunc = argPlayfunc;
		currentParam.setParam(\playfunc, argPlayfunc);
		// Experimental: merge parameters from new playfunc template!
		currentParam.makeParams;
		currentParam.mergeParams2Dict;
		// We lose all settings. Don't
	}
}