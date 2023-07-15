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
		StartUp add: { EditSoundPlayer.loadIfNeeded }
	}

	*default { ^this.new(Buffer.all.first.buf) }

	*new { | buffer | ^this.newCopyArgs(buffer) }
	*gui { this.default.gui }

	name { ^buffer.name }
	gui {
		selections = SfSelections(this);
		colors = (((1..9).normalize * (2/3) + (1/3)).collect({ | i |
			[
				Color(0, i, 0), Color(0, 0, i),
				Color(i, i, 0), Color(i, 0, i), Color(0, i, i), Color(i, 0, 0), Color(i, i, i)
			] // last selection reserved for deactivation. Color = black.
		}).flat.reverse add: (Color.black));
		// decorative detail:
		colors[0] = Color(0.95, 0.95, 0.5);
		this.bl_(1400, 400).hlayout(
			VLayout(
			sfv = this.sfView,
			this.rangeSlider,
				this.posDisplay(sfv)
			),
			ListView().maxWidth_(30)
			.items_((0..63))
			.colors_(colors)
			.action_({ | me |
				me.hiliteColor = colors[me.value];
				me.selectedStringColor_(colors[me.value].complement);
				this.changed(\selectionIndex, me.value);
			})
			.keyDownAction_({ | me, char ... args |
				switch(char.ascii,
					122, { this.toggleSelectionZoom },//z
					127, { this.zeroSelection },// delete
				);
			}),
			this.editIndexDisplay
		).name_(PathName(buffer.path).fileNameWithoutExtension);
		{ // switch to first safely editable selection!
			sfv.currentSelection = 0;
			this.changed(\selection);
			this.changed(\selectionIndex, 0);

		}.defer(0.5);
	}

	editIndexDisplay {
		^VLayout(
			NumberBox().maxWidth_(20)
			.decimals_(0)
			.clipHi_(63)
			.clipLo_(0)
			.action_({ | me |
				sfv.currentSelection = me.value.asInteger;
				this.changed(\selection);
			})
			.addNotifier(this, \selectionIndex, { | n, index |
				n.listener.value = index;
				sfv.currentSelection = n.listener.value.asInteger;
				this.changed(\selection);
			}),
			ListView().maxWidth_(20)
			.hiliteColor_(Color.white)
			.selectedStringColor_(Color.red)
			.action_({ | me |
				sfv.currentSelection = me.value.asInteger;
				this.changed(\selection);
			})
			.addNotifier(this, \selection, { | n, index |
				var theindex;
				n.listener.items = selections.edited;
				theindex = n.listener.items indexOf: selections.currentSelectionIndex;
				theindex !?  { n.listener.value = theindex };
			})
		)
	}

	sendSelectionToServer {
		this.startFrame.perform('@>', \startframe, this.name);
		this.endFrame.perform('@>', \endframe, this.name);
	}

	zeroSelection {
		sfv.setSelection(sfv.currentSelection, [0, 0]);
		this.changed(\selection);
	}
	selectionStart {
		^this.startFrame / buffer.sampleRate;
	}

	selectionEnd {
		^this.endFrame / this.sampleRate;
	}

	selectionDur {
		^this.numFrames / this.sampleRate;
	}

	startFrame { ^selections.startFrame; }
	endFrame { ^selections.endFrame; }
	numFrames { ^selections.numFrames }
	sampleRate { ^buffer.sampleRate }

	selectionFrac {
		selections.currentSelection;
		^(selections.currentSelection[1] / buffer.numFrames);
	}

	selectionBegFrac {
		selections.currentSelection;
		^(selections.currentSelection[0] / buffer.numFrames);
	}

	sfView {
		var mouseButton, sfZoom, sfv;
		sfv = SoundFileView()
		// .gridOn_(false)
		.background_(Color(0.25, 0.1, 0.1))
		.soundfile_(SoundFile(buffer.path))
		.readWithTask(0, buffer.numFrames, { this.setSelection(0) })
		.timeCursorOn_(true)
		.keyDownAction_({ | me, char ... args |
			switch(char,
				$t, { // experimental
					"testing".postln;
					sfv.currentSelection;
				},
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
		.mouseDownAction_({ |view, x, y, mod, buttonNumber|
			mouseButton = buttonNumber; // not used for now
		})
		.mouseUpAction_({ |view, x, y, mod| //
			// divert non-drag clicks to last selection.
			this.sendSelectionToServer;
			// sfv.currentSelection = 63;
			this.divertSelection;
			// this.changed(\selection);
		})
		.action_({ | me | // Runs on mouseclick
			// do not change current selection on mouse click.
			// only change selection when drag-clicking on trackpad.
			if (sfv.selectionSize(sfv.currentSelection) < 100) {
				sfv.currentSelection = 63; // divert to last selection
			}{  // use current selection when range dragged is > 100
				sfv.currentSelection = selections.currentSelectionIndex;
				this.changed(\selection);
			};
		})
		.addNotifier(this, \focusSoundFileView, { | n |
			"Sound File View will focus!".postln;
			n.listener focus: true;
		});
		colors do: { | c, i | sfv.setSelectionColor(i, c) };
		^sfv;
	}

	focusRangeSlider {
		this.changed(\focusRangeSlider)
	}

	edit {
		// "edit not yet implemented".postln;
		BufCode(this).makeDoc;
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

	selectAll {
		var restore;
		restore = this.currentSelection;
		sfv.setSelection(selections.currentSelectionIndex, [0, buffer.numFrames]);
		this.changed(\selection);
		sfv.currentSelection = restore;
		this.changed(\selection);
	}

	clearSelection { // TODO: Rework and check this.
		var restore;
		restore = this.currentSelection;
		sfv.setSelection(selections.currentSelectionIndex, [0, 0]);
		this.changed(\selection);
		sfv.currentSelection = restore;
		this.changed(\selection);
	}

	setSelection { | beg, duration | // TODO: implement this
		// set frames of current selection to beginning and end
		// test version:
		selections.setCurrentSelectionValues(10000, 40000);
		this.getSelection;
	}

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

	divertSelection { sfv.currentSelection = 63; }

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
			this.sendSelectionToServer;
			this.divertSelection;
		})
		.focusColor_(Color.red)
		.keyDownAction_({ | me, char |
			switch(char,
				// // // $z, { //  zoom to current selection11
				// // 	"NOT DEBUGGED!".postln;
				// // 	if (this.isZoomedOut) {
				// // 	this.restoreSelectionIndex;
				// // 	me.lo = selections.currentSelectionValues[0] / buffer.numFrames;
				// // 	me.hi = selections.currentSelectionValues[0]
				// // 	+ selections.currentSelectionValues[1] / buffer.numFrames;
				// // 	me.doAction;
				// // 	}{
				// // 		this.zoomOut;
				// // 	}
				// },
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
				$Z, { this.focusSoundFileView }
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

	posDisplay { // | sfv |
		^HLayout(
			StaticText().string_("selection")
			.addNotifier(this, \selectionIndex, { | n, index |
				n.listener.background = colors[index];
				n.listener.stringColor = colors[index].complement;
			}),
			NumberBox()
			.maxWidth_(40)
			.decimals_(0)
			.clipHi_(63)
			.clipLo_(0)
			.action_({ | me |
				sfv.currentSelection = me.value.asInteger;
				this.changed(\selection);
				this.changed(\selectionIndex, me.value.asInteger);
			})
			.addNotifier(this, \selectionIndex, { | n, index |
				n.listener.value = index;
				sfv.currentSelection = n.listener.value.asInteger;
				this.changed(\selection);
			}),
			StaticText().string_("begin"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n |
				n.listener.value = this.selectionStart;
			}),
			StaticText().string_("end"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n, index, start, size |
				n.listener.value = this.selectionEnd;
			}),
			StaticText().string_("dur"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n |
				n.listener.value = this.selectionDur;
			}),
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
					f.postln; playfunc = f.asSymbol
				})})
			).front }),
			Button()
			.states_([["test"]])
			.action_({ this.test })
		)
	}

	openParameterGui { selections.openParameterGui; }

	test {
	}

	bufName { ^buffer.name }
	envir {
		^buffer.name.envir;
	}
}