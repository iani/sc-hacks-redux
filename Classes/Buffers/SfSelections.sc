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

	getSelectionsFromSfv { | sfv |
		selections = sfv.selections;
	}
	restoreSelectionsToSfv { | sfv | selections do: { | s, i |
		// if (s.sum > 0) {
		// 	postln("selection !!!!!!!!!!!" + i + "is" + s);
		// }{
		// 	postln("selection" + i + "is 0")
		// };
		sfv.setSelection(i, s);}
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
		currentSelection = selections[0];
		// create params:
		params = { | n | BufSoundParams(this, this.playfunc, n); } ! 64;
		currentParam = params[0];
		// Do NOT employ Notifications. DO NOT RE-ADD THESE!:
		// (Instead, trigger actions explicitly trough messages from sbgui.)
		// this.addNotifier(sbgui, \selection, { this.getSelectionFromGui });
		// this.addNotifier(sbgui, \selectionIndex, { this.setCurrentSelection });
		// find out where the home folder is.
		// this.class.withFolder({ | f |
			// homefolder = f;
			// postln("SfSelections saves scripts in" + homefolder);
		// })
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
		// postln("Debugging SfSelections setPlayfunc. currentParam is:" + currentParam +
		// "playfunc is" + argPlayfunc);
		currentParam.setPlayfunc(argPlayfunc);
	}

	asCode {
		var scodes, bufname;
		bufname = buffer.name;
		selections[..62] do: { | s, i | if (s.sum > 0) {
			scodes = scodes add: this.selectionCode(i, bufname)
		}};
		scodes !? {
			scodes = [
				format("/*selections for % saved at %*/\n", bufname, Date.getDate.stamp)
			] ++ scodes;
		};
		^scodes;
	}

	selectionCode { | index, bufname |
		var code, theselection;
		theselection = params[index].asDict;
		code = format("//: % % (%)\n", bufname, theselection[\playfunc], index);
		code = code ++ theselection.asCompileString ++ "\n";
		^code;
	}

	save {
		var nonEmpty;
		selections[..62] do: { | s, i |
			if (s.sum > 0) { nonEmpty = nonEmpty add: i }
		};
		if (nonEmpty.size == 0) {
			^postln("There are no selections to save for buffer" + this.bufName);
		};
		// postln("saving these selections:" + nonEmpty);
		this.class.withFolder({ | path |
			var thecode;
			homefolder = path;
			path = path +/+ format("%_%.scd",
				this.bufName,
				Date.getDate.stamp);
			// postln("Saving SfSelection to:" + path);
			thecode = this.selectionsAsCode(nonEmpty);
			File.use(path, "w", { | f | f.write(thecode) });
			Document.open(path);
		});
	}

	selectionsAsCode { | sel_ind |
		var code, theselection, bufname;
		bufname = buffer.name;
		code = format (
			"/*selections for % saved at %*/\n",
			this.bufName,
			Date.getDate.stamp
		);
		sel_ind do: { | i |
			theselection = params[i].asDict;
			code = code ++ format("//:[1] % % (%)\n", bufname, theselection[\playfunc], i);
			code = code ++ theselection.asCompileString ++ "\n";
		};
		^code;
	}

	*defaultPath {
		^PathName(Platform.userHomeDir +/+ "sc-projects/BufferPlayers/").fullPath;
	}
	soundfileview { ^sbgui }
	addSelectionFromDict { | dict |
		var start, end, dur, index;
		buffer.postln;
		buffer.name.postln;
		index = dict[\selectionNum];
		// postln("importing selection for buffer" + buffer.name + "and number" + index);
		start = dict[\startframe];
		end = dict[\endframe];
		if (start.notNil and: { end.notNil }) {
			dur = end - start;
		}{
			postln("WARNING! selection" + index + "found no frame data");
			start = dur = 0;
		};
		selections[index] = [start, dur].postln;
		params[index].importDict(dict);
	}

	paramCloneMenu { // prototype - not used.
		var n;
		selections do: { | s, i |
			if (s.sum > 0) {
				n = n add:
				[
					this.infostring[i],
					{
						postln("will handle this:" + this.infostring[i]);
					}
				]
			};
		};
		^n;
	}

	firstFreeSelection {
		var n = 0;
		while { n < 65 and: { selections[n].sum > 0 } } { n = n + 1 };
		if (n < 64) { ^n } { ^nil }
	}

	nonNullSelectionsInfo {
		var n;
		selections do: { | s, i |
			if (s.sum > 0) {
				n = n add: this.infostring(i);
			};
		};
		^n;
	}

	nonNullSelectionsParams {
		var n;
		selections do: { | s, i |
			if (s.sum > 0) {
				n = n add: this.params[i];
			};
		};
		^n;
	}

	nonNullSelections {
		var n;
		selections do: { | s, i | if (s.sum > 0) { n = n add: [i, s]; }; };
		^n;
	}
	nullSelections {
		var n;
		selections do: { | s, i | if (s.sum == 0) { n = n add: [i, s]; }; };
		^n;
	}

	numFreeSelections {
		var n = 63;
		selections do: { | s, i | if (i < 64 and: { s.sum > 0}) { n = n - 1; }; };
		^n;
	}

	cloneParam { | sourceParam, selectionSlot |
		var cloneDict, theSelection, startFrame, endFrame, sourceNumFrames, targetNumFrames;
		// create a clean deep copy (avoiding deepCopy to be sure...);
		cloneDict = sourceParam.asDict.asCompileString.interpret;
		theSelection = selections[selectionSlot];
		postln("confirming that selection " + selectionSlot + "is empty" + theSelection);
		buffer.postln;
		startFrame = cloneDict[\startframe];
		endFrame = cloneDict[\endframe];
		sourceNumFrames = endFrame - startFrame;
		targetNumFrames = buffer.numFrames;
		postln("constraining endframe" + endFrame + "to numFrames" + targetNumFrames);
		endFrame = endFrame min: targetNumFrames;
		startFrame = endFrame - sourceNumFrames max: 0;
		postln("the resulting startframe is" + startFrame + "and Endframe is" + endFrame);
		theSelection[0] = startFrame;
		theSelection[1] = endFrame - startFrame;
		cloneDict[\startframe] = startFrame;
		cloneDict[\endframe] = endFrame;
		cloneDict[\buf] = buffer.name;
		cloneDict[\selectionNum] = selectionSlot;
		sbgui.changed(\selection);
		params[selectionSlot] importDict: cloneDict;
	}

	infostring { | selIndex |
		^buffer.name ++ ":" ++ params[selIndex].playfunc ++ selections[selIndex];
	}
	bufStats {
		^format("% (%)", buffer.name, this.numFreeSelections)
	}
}