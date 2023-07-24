/* 10 Jul 2023 08:53
Remember SoundFileView selections because sfv seems to forget them when zooming.
*/

SfSelections {
	classvar <homefolder;
	var <sbgui, <buffer, <selections, <currentSelection, <currentSelectionIndex = 0;
	var <params; // Array of SoundParams instances. Each param holdes a dictionary
	// of parameter values used for playing the sound, and paramater specs for creating
	// a gui to edit these parameters.
	var <currentParam; // SoundParams instance for currentSelection
	// does the playing.
	// var <>buffer;
	*new { | sbgui, buffer |
		^this.newCopyArgs(sbgui, buffer, { [0, 0] } ! 64).init;
	}

	restoreSelectionsToSfv { | sfv |
		selections do: { | s, i | sfv.setSelection(i, s);}
	}
	player { ^sbgui.player }
	bufName { ^buffer.name }
	playfunc { ^sbgui.playfunc }
	playfunc_ { | playfunc | // replace currentParam with a param from the playfunc's template
		var newparam;
		currentParam.close; // stop sound and close gui
		// newparam =
	}
	init {
		buffer = sbgui.buffer;
		currentSelection = selections[0];
		// create params:
		params = { SoundParams(this, this.playfunc); } ! 64;
		currentParam = params[0];
		// Do NOT employ Notifications. DO NOT RE-ADD THESE!:
		// (Instead, trigger actions explicitly trough messages from sbgui.)
		// this.addNotifier(sbgui, \selection, { this.getSelectionFromGui });
		// this.addNotifier(sbgui, \selectionIndex, { this.setCurrentSelection });
		// find out where the home folder is.
		this.class.withFolder({ | f |
			homefolder = f;
			// postln("SfSelections saves scripts in" + homefolder);
		})
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
		// postln("debugging setCurrentSelectionIndex : index" + index);
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

	save {
		var nonEmpty;
		selections do: { | s, i |
			if (s.sum > 0) { nonEmpty = nonEmpty add: i }
		};
		if (nonEmpty.size == 0) {
			^postln("There are no selections to save for buffer" + this.bufName);
		};
		postln("saving these selections:" + nonEmpty);
		this.class.withFolder({ | path |
			var thecode;
			homefolder = path;
			path = path +/+ format("%_%.scd",
				this.bufName,
				Date.getDate.stamp);
			postln("Saving SfSelection to:" + path);
			thecode = this.selectionsAsCode(nonEmpty);
			File.use(path, "w", { | f | f.write(thecode) });
			Document.open(path);
		});
	}

	selectionsAsCode { | sel_ind |
		var code, theselection;
		code = format (
			"/*selections for % saved at %*/\n",
			this.bufName,
			Date.getDate.stamp
		);
		sel_ind do: { | i |
			theselection = params[i];
			code = code ++ format("//:[1] (%)\n", i);
			code = code ++ theselection.asDict.asCompileString ++ "\n";
		};
		^code;
	}

	*defaultPath {
		^PathName(Platform.userHomeDir +/+ "sc-projects/BufferPlayers/").fullPath;
	}
	soundfileview { ^sbgui }
	addSelectionFromSnippet { | index, snippet |
		var thedict, start, end, dur;
		thedict = snippet.interpret;
		start = thedict[\startframe];
		end = thedict[\endframe];
		if (start.notNil and: { end.notNil }) {

		};
		// selections[]
	}
}