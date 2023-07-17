/*  1 Jul 2023 12:02

*/

SoundBufferGui {
	var buffer, <sfv, colors;
	var selection;
	var <>selections; // remember selections because sfv seems to forget them.
	var <playfuncs; // TODO: transfer these from EditSoundPlayer. Use a different directory
	var <playfunc = \phasebuf; // name of playfunc selected from menu
	var <zoomfrac = 1, scrollpos = 0;
	// to load them.
	*initClass {
		// TODO: Rewrite this to use own playfuncs from sc-hacks repository,
		// and if found, also extra funcs from sc-project.
		// StartUp add: { EditSoundPlayer.loadIfNeeded }
	}

	*default { ^this.new(Buffer.all.first.buf) }

	*new { | buffer |
		 ^Registry(this, buffer, { this.newCopyArgs(buffer) })
	}

	*gui { ^this.default.gui }

	name { ^buffer.name }
	gui {
		this.bl_(1400, 400).hlayout(
		selections = SfSelections(this);
		colors = (((1..9).normalize * (2/3) + (1/3)).collect({ | i |
			[
				Color(0, i, 0), Color(0, 0, i),
				Color(i, i, 0), Color(i, 0, i), Color(0, i, i), Color(i, 0, 0), Color(i, i, i)
			] // last selection reserved for deactivation. Color = black.
		}).flat.reverse add: (Color.black));
		// decorative detail:
		colors[0] = Color(0.95, 0.95, 0.5);
			VLayout(
				sfv = this.sfView,
				this.rangeSlider,
				this.posDisplay(sfv)
			),
			this.selectionListView,
			this.selectionEditedView
		).name_(PathName(buffer.path).fileNameWithoutExtension);
		/*
		{ // switch to first safely editable selection!
			this.switchToNewSelection(0);
		}.defer(0.5);
		*/
	}

	// basic selection actions to use throughout for selection management
	switchToNewSelection { | index | // (NEW)
		// User switched to a new selection.
		// Called by view elements that choose selection index.
		// Tell the view + selection to switch selection index.
		sfv.currentSelection = index;
		selections.setCurrentSelectionIndex(index);
	}

	// modifyCurrentSelection { | startFrame, numFrames | // NEW
		// User modified the frame range of current selection.
		// Called
		// Upd
	// }

	// prevent modification of useable selectios by mouselicks,
	// by setting the current selection index to 63 (unused by this app).
	divertSelection { sfv.currentSelection = 63; }

	zeroSelection {
		sfv.setSelection(sfv.currentSelection, [0, 0]);
		this.changed(\selection);
	}

	selectionFrac {
		selections.currentSelection;
		^(selections.currentSelection[1] / buffer.numFrames);
	}

	selectionBegFrac {
		selections.currentSelection;
		^(selections.currentSelection[0] / buffer.numFrames);
	}

	sfView {
		sfv = SoundFileView()
		.background_(Color(0.25, 0.1, 0.1))
		.soundfile_(SoundFile(buffer.path))
		.readWithTask(0, buffer.numFrames, { this.setSelection(0) })
		.timeCursorOn_(true)
		.keyDownAction_({ | me, char ... args |
			switch(char,
				$-, { this.clearSelection; },
				$=, { this.selectAll },
				$z, { this.toggleSelectionZoom }, // TO BE REMOVED?
				$a, { this.selectAll },
				$c, { this.clearSelection },
				$p, { this.play },
				$., { this.stop },
				$ , { this.togglePlay; },
				$e, { this.edit },
				$1, { this.moveSelectionStartBy(1); },
				$2, { this.moveSelectionStartBy(10); },
				$3, { this.moveSelectionStartBy(100); },
				$4, { this.moveSelectionStartBy(1000); },
				$5, { this.moveSelectionStartBy(10000); },
				$6, { this.moveSelectionStartBy(-1); },
				$7, { this.moveSelectionStartBy(-10); },
				$8, { this.moveSelectionStartBy(-100); },
				$9, { this.moveSelectionStartBy(-1000); },
				$0, { this.moveSelectionStartBy(-10000); },
				$q, { this.moveSelectionDurBy(1); },
				$w, { this.moveSelectionDurBy(10); },
				$e, { this.moveSelectionDurBy(100); },
				$r, { this.moveSelectionDurBy(1000); },
				$t, { this.moveSelectionDurBy(10000); },
				$y, { this.moveSelectionDurBy(-1); },
				$u, { this.moveSelectionDurBy(-10); },
				$i, { this.moveSelectionDurBy(-100); },
				$o, { this.moveSelectionDurBy(-1000); },
				$p, { this.moveSelectionDurBy(-10000); },

				$>, { sfv.scroll(1/10);  this.changed(\zoom) },
				$<, { sfv.scroll(-1/10);  this.changed(\zoom) },
				$R, { this.focusRangeSlider },
				$Z, { this.toggleSelectionZoom }
			);
		})
		.mouseUpAction_({ |view, x, y, mod| //
			// "MOUSEUP ACTION ".post;
			// sfv.currentSelection.post; " -- ".post;
			sfv.selection(sfv.currentSelection).postln;
			// store selection range in selections, and send to sound (if playing);
			// "mouseUpAction skipped the next line. restore it".postln;
			// // selections.setSelectionFromGui(
			// 	sfv.currentSelection,
			// 	sfv.selection(sfv.currentSelection));
			// this.sendSelectionToServer; // OBSOLETE. selections does this.
			// Prevent zeroing of current selection by next click:
			// "mouseUpAction skipped the next line. restore it".postln;
			this.divertSelection; // this does not send data to selections!
		})
		.action_({ | me | // Runs both on mouseclick amd on mousedrag.
			// Mouseclick immediately changes the current selection.
			// To avoid zeroing the current selection inadvertedly,
			// Change the current selection to 63 as whenever the size is < 100.
			if (sfv.selectionSize(sfv.currentSelection) < 100) {
				// Mouse will modify the selection which is not used in this app.
					// "sfv action skipped the next line. restore it".postln;
				this.divertSelection;
				// Method above is equivalent to:
				// sfv.currentSelection = 63; // divert to last selection
			}{  // when range dragged is > 100, set currentSelection index to the last
				// selection chosen by the user.
					// "sfv action skipped the next line. restore it".postln;
				// postln("selection index" + sfv.currentSelection
				// 	+ "selection size:" + sfv.selectionSize(sfv.currentSelection));
				// Note: the first time >= 100 there will be a glitch/inaccurate
				// frames left from null selection. But the effect
				// when changing the loop dimensions live is negligible.

				sfv.currentSelection = selections.currentSelectionIndex;
				// postln("frames after changing to current selection" +
				// 	sfv.selection(sfv.currentSelection)
				// );
				selections.setSelectionFromGui(
					sfv.currentSelection,
					sfv.selection(sfv.currentSelection)
				);
				this.changed(\selection);
			};
		})
		.addNotifier(this, \selectionIndex, { | n |
			postln("updating sfv selection index to" + selections.currentSelectionIndex);
			sfv.currentSelection = selections.currentSelectionIndex;
		})
		.addNotifier(this, \focusSoundFileView, { | n |
			"Sound File View will focus!".postln;
			n.listener focus: true;
		});
		colors do: { | c, i | sfv.setSelectionColor(i, c) };
		^sfv;
	}

	posDisplay { // | sfv |
		^HLayout(
			StaticText().string_("selection")
			.addNotifier(this, \selection, { | n |
				var index;
				index = this.selectionIndex;
				n.listener.background = colors[index];
				n.listener.stringColor = colors[index].complement;
			}),
			NumberBox()
			.maxWidth_(40)
			.decimals_(0)
			.clipHi_(63)
			.clipLo_(0)
			.action_({ | me | this.setSelectionIndex(me.value.asInteger); })
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionIndex; }),
			StaticText().string_("begin"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionStart; }),
			StaticText().string_("end"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionEnd; }),
			StaticText().string_("dur"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionDur;}),
			Button()
			.states_([["tweak synth"]])
			.action_({ this.openParameterGui }),
			StaticText().string_("playfunc:"),
			Button()
			.canFocus_(false)
			.states_([["phasebuf", Color.red, Color.white]])
			.action_({ | me | Menu(
				*SynthTemplate.playfuncs.keys.asArray.sort
				.collect({ | f | MenuAction(f.asString, {
					me.states_([[f.asString]]);
					this.setPlayfunc(f.asSymbol);
				})})
			).front }),
			Button()
			.states_([["test"]])
			.action_({ this.test })
		)
	}

	setPlayfunc { | argPlayfunc |
		playfunc = argPlayfunc;
		selections.setPlayfunc(playfunc);
	}

	selectionIndex { ^selections.currentSelectionIndex }
	selectionStart { ^this.startFrame / buffer.sampleRate; }
	selectionEnd { ^this.endFrame / this.sampleRate;}
	selectionDur { ^this.numFrames / this.sampleRate; }
	startFrame { ^selections.startFrame; }
	endFrame { ^selections.endFrame; }
	numFrames { ^selections.numFrames }
	sampleRate { ^buffer.sampleRate }

	selectionListView {
		^ListView().maxWidth_(30)
		.items_((0..63))
		.colors_(colors)
		.action_({ | me |
			me.hiliteColor = colors[me.value];
			me.selectedStringColor_(colors[me.value].complement);
			this.setSelectionIndex(me.value.asInteger);
		})
		.addNotifier(this, \selection, { | n |
			// postln("listener" + n.listener + "value:" + n.listener.value);
			n.listener.value = this.selectionIndex;
			n.listener.hiliteColor = colors[n.listener.value];
			n.listener.selectedStringColor_(colors[n.listener.value].complement);
		})
		.keyDownAction_({ | me, char ... args |
			switch(char.ascii,
				122, { this.toggleSelectionZoom },//z
				127, { this.zeroSelection },// delete
			);
		})
	}

	selectionEditedView {
		^VLayout(
			NumberBox().maxWidth_(20)
			.decimals_(0)
			.clipHi_(63)
			.clipLo_(0)
			.action_({ | me | this.setSelectionIndex(me.value.asInteger); })
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionIndex; }),
			ListView().maxWidth_(20)
			.hiliteColor_(Color.white)
			.selectedStringColor_(Color.red)
			.action_({ | me | this.setSelectionIndex(me.value.asInteger); })
			.addNotifier(this, \selection, { | n, index |
				var theindex;
				n.listener.items = selections.edited;
				theindex = n.listener.items indexOf: selections.currentSelectionIndex;
				theindex !?  { n.listener.value = theindex };
			})
		)
	}

	focusRangeSlider {
		this.changed(\focusRangeSlider)
	}

	edit {
		"edit not yet implemented".postln;
		// BufCode(this).makeDoc;
	}

	start { this.play }
	play { // TODO: rewrite this. SfSelections should play the current selection
		// SfSelections.playCurrentSelection;
		if (this.selectionDur == 0) {
			^postln("refusing to play selection" + selections.currentSelectionIndex
				+ "because its duration is 0");
		};
		postln("playing selection" + selections.currentSelectionIndex + "of duration"
		+ this.selectionDur + "with" + playfunc);
		buffer.name.perform(
			'@@',
			(
				startpos: this.selectionStart,
				dur: this.selectionDur,
				startframe: this.startFrame, // for frame-based players like PhaseBuf_
				endframe: this.endFrame, // for frame-based players like PhaseBuf_
			),
			(playfunc ?? { \phasebuf }));
	}
	stop {
		buffer.name.envir.stopSynths;
	}

	togglePlay {
		if (this.isPlaying) { this.stop } { this.start };
	}

	isPlaying {
		^buffer.name.envir[buffer.name].isPlaying;
	}

	maximizeCurrentSelection {// set current selection to full size of buffer
		// set the range of current selection to 0-full buffer range
		// the current selection is taken from selections, not from sfv,
		// because sfv has "null" selection 63 after mouseUp.
		// var restore;
		// restore = this.currentSelection; ///????
		selections.setSelectionFromGui(
			selections.currentSelectionIndex, [0, buffer.numFrames]
		);
		this.changed(\selection);
	}

	clearCurrentSelection { // TODO: Rework and check this.
		// var restore;
		// restore = this.currentSelection;
		// sfv.setSelection(selections.currentSelectionIndex, [0, 0]);
		// this.changed(\selection);
		// sfv.currentSelection = restore;
		// this.changed(\selection);
		selections.setSelectionFromGui(selections.currentSelectionIndex, [0, 0]);
		this.changed(\selection);
	}

	// EXPERIMENTAL _ IMPORTANT _ CHECK!
	setSelectionIndex { | index | selections.setCurrentSelectionIndex(index) }

	// setSelection { | beg, duration | // TODO: implement this
	// 	// set frames of current selection to beginning and end
	// 	// test version:
	// 	selections.setCurrentSelectionValues(beg, duration);
	// 	this.getSelection;
	// }

	performOnSelection { | method, frames | // used by methods below.
		// modify selections' selection by applying method with frames,
		// set sfv selection to it, and divert.
		selections.perform(method, frames);
		this.getSelection;
	}
	getSelection {
		// set sfv views current selection index + values from selecgtions
		// divert selection to null (63)
		// cause update of views
		this.restoreSelectionValuesFromSelections;
		this.divertSelection;
	}

	moveSelectionStartBy { | frames | // move start only. dur changes
		this.performOnSelection(\moveSelectionStartBy, frames);
	}

	moveSelectionDurBy { | frames | // move dur only
		this.performOnSelection(\moveSelectionDurBy, frames);
	}
	moveSelectionBy { | frames | // move start, keeping dur as is
		this.performOnSelection(\moveSelectionBy, frames);
	}

	resizeSelectionBy { | frames | // move start + end by equal amounts
		this.performOnSelection(\resizeSelectionBy, frames);
	}

	currentSelection { ^selections.currentSelectionIndex }

	restoreSelectionIndex {
		sfv.currentSelection = this.currentSelection;
		this.changed(\selection);
	}

	restoreSelectionValuesFromSelections {
		sfv.setSelection(selections.currentSelectionIndex,
			selections.selections[selections.currentSelectionIndex]
		)
	}

	toggleSelectionZoom {
		if (this.isZoomedOut) {
			this.zoomSelection;
		}{
			this.zoomOut;
		};
		this.changed(\zoom);
	}

	isZoomedOut {
		^sfv.xZoom.round(0.00001) == buffer.dur.round(0.00001);
	}

	zoomSelection {
		{
			sfv.zoomSelection(selections.currentSelectionIndex);
			sfv.setSelection(selections.currentSelectionIndex, selections.currentSelection);
			this.divertSelection;
			this.changed(\zoom);
		}.fork(AppClock);
	}

	zoomOut {
		sfv.zoomToFrac(1);
		sfv.setSelection(selections.currentSelectionIndex, selections.currentSelection);
		this.restoreSelectionIndex;
		this.divertSelection;
		this.changed(\zoom);
	}

	rangeSlider {
		^RangeSlider().orientation_(\horizontal)
		.palette_(QPalette.dark)
		.knobColor_(Color.green)
		.addNotifier(this, \zoom, { | n |
			var offsetRatio, scrollRatio, scrollPos;
			// postln("updating zoom view");
			scrollPos = sfv.scrollPos;
			scrollRatio = sfv.viewFrames / buffer.numFrames;
			offsetRatio = scrollRatio * scrollPos;
			// postln("scrollRatio" + scrollRatio + "scrollPos" + scrollPos);
			n.listener.lo = 1 - scrollRatio * scrollPos;
			n.listener.hi = 1 - scrollRatio * scrollPos + scrollRatio;
		})
		.mouseUpAction_({ |view, x, y, mod| //
			// this.sendSelectionToServer;
			this.divertSelection;
		})
		.focusColor_(Color.red)
		.keyDownAction_({ | me, char |
			switch(char,
				$s, { // set current selection to zoom
					this.restoreSelectionIndex;
					selections.setCurrentSelectionValues(
						(me.lo * buffer.numFrames).asInteger,
						sfv.viewFrames
					);
					this.restoreSelectionValuesFromSelections;
					this.divertSelection;
				},
				$z, { this.focusSoundFileView; },
				$1, { this.zoomToFrac(zoomfrac - 0.0001); },
				$2, { this.zoomToFrac(zoomfrac - 0.001); },
				$3, { this.zoomToFrac(zoomfrac - 0.01); },
				$4, { this.zoomToFrac(zoomfrac - 0.1); },
				$5, { this.zoomToFrac(1); },
				$6, { this.toggleSelectionZoom; },
				$7, { this.zoomToFrac(zoomfrac + 0.1); },
				$8, { this.zoomToFrac(zoomfrac + 0.01); },
				$9, { this.zoomToFrac(zoomfrac + 0.001); },
				$0, { this.zoomToFrac(zoomfrac + 0.0001); },
				$q, { this.scrollTo(scrollpos - 0.0001); },
				$w, { this.scrollTo(scrollpos - 0.001); },
				$e, { this.scrollTo(scrollpos - 0.01); },
				$r, { this.scrollTo(scrollpos - 0.1); },
				$t, { this.scrollTo(0); },
				$y, { this.scrollTo(1); },
				$u, { this.scrollTo(scrollpos + 0.1); },
				$i, { this.scrollTo(scrollpos + 0.01); },
				$o, { this.scrollTo(scrollpos + 0.001); },
				$p, { this.scrollTo(scrollpos + 0.0001); },
				// $(, { sfv.scrollTo(0); this.changed(\zoom) },
				$>, { this.zoomToFrac(zoomfrac + 0.1); },
				$<, { this.zoomToFrac(zoomfrac - 0.1); },
				$z, { this.focusSoundFileView },
				$Z, { this.focusSoundFileView },
				$G, { this.openParameterGui },
				$ , { this.togglePlay }
			)
		})
		.action_({ | me |
			var zoomratio;
			zoomratio = me.hi-me.lo;
			sfv.zoomToFrac(zoomratio);
			sfv.scrollTo(scrollpos = (me.lo / ( 1 - zoomratio)));
			zoomfrac = me.range;
		})
		.addNotifier(this, \focusRangeSlider, { | n |
			n.listener focus: true;
		})
	}

	zoomToFrac { | frac |
		sfv.zoomToFrac(zoomfrac = frac.clip(0, 1));
		this.scrollTo(scrollpos);
		this.changed(\zoom);
	}

	scrollTo { | pos |
		sfv.scrollTo(scrollpos = pos.clip(0, 1));
		this.changed(\zoom);
	}

	focusSoundFileView {
		postln("switching focus to sound view");
		this.changed(\focusSoundFileView);
	}

	// setSelectionIndex { | index |
	// 	sfv.currentSelection = index;
	// 	selections.changeSelectionIndex(index);
	// 	// update gui elements:
	// 	this.changed(\selection);
	// }

	openParameterGui { selections.openParameterGui; }

	test {
	}

	bufName { ^buffer.name }
	envir {
		^buffer.name.envir;
	}
}