/*  1 Jul 2023 12:02

*/

SoundBufferGui {
	var buffer, <sfv, colors;
	var selection;
	var <>selections; // remember selections because sfv seems to forget them.
	*gui { | buffer |
		^this.newCopyArgs(buffer).gui;
	}

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

	zeroSelection {
		sfv.setSelection(sfv.currentSelection, [0, 0]);
		this.changed(\selection);
	}
	selectionStart {
		^(sfv.selectionStart(sfv.currentSelection) / buffer.sampleRate);
	}

	selectionEnd {
		^sfv.selectionStart(sfv.currentSelection) + sfv.selectionSize(sfv.currentSelection)
		/ buffer.sampleRate;
	}

	selectionDur {
		postln(
			"selection.index" + selections.currentSelectionIndex +
			"selection size" + sfv.selectionSize(selections.currentSelectionIndex) +
			"selection dur" + (sfv.selectionSize(selections.currentSelectionIndex) / buffer.sampleRate)
		);
		^sfv.selectionSize(selections.currentSelectionIndex) / buffer.sampleRate;
	}

	selectionFrac {
		selections.currentSelection.postln;
		^(selections.currentSelection[1] / buffer.numFrames.postln);
	}

	selectionBegFrac {
		selections.currentSelection;
		^(selections.currentSelection[0] / buffer.numFrames);
	}

	sfView {
		var mouseButton, sfZoom, sfv;
		sfv = SoundFileView()
		.soundfile_(SoundFile(buffer.path))
		.readWithTask(0, buffer.numFrames, { this.setSelection(0) })
		.timeCursorOn_(true)
		.keyDownAction_({ | me, char ... args |
			switch(char,
				$x, { // experimental
					"testing".postln;
					sfv.currentSelection;
				},
				$z, { this.toggleSelectionZoom },
				$p, { this.play },
				$., { this.stop },
				$1, {
					sfv.zoomToFrac(1);
				},
				$2, { sfv.zoomToFrac(1/2) },
				$3, { sfv.zoomToFrac(1/3) },
				$4, { sfv.zoomToFrac(1/4) },
				$5, { sfv.zoomToFrac(1/5) },
				$6, { sfv.zoomToFrac(1/6) },
				$7, { sfv.zoomToFrac(1/7) },
				$8, { sfv.zoomToFrac(1/8) },
				$9, { sfv.zoomToFrac(1/9) },
				$(, { sfv.scrollTo(0); this.changed(\zoom) },
				$>, { sfv.scroll(1/10);  this.changed(\zoom) },
				$<, { sfv.scroll(-1/10);  this.changed(\zoom) }
			);
		})
		.mouseDownAction_({ |view, x, y, mod, buttonNumber|
			mouseButton = buttonNumber; // not used for now
		})
		.mouseUpAction_({ |view, x, y, mod| //
			// divert non-drag clicks to last selection.
			sfv.currentSelection = 63;
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
		});
		colors do: { | c, i | sfv.setSelectionColor(i, c) };
		^sfv;
	}

	play {
		buffer.name.perform(
			'**',
			(
				startpos: this.selectionStart,
				dur: this.selectionDur.postln
			),
			buffer.name);
	}
	stop {
		buffer.name.envir.stopSynths;
	}
	toggleSelectionZoom {
		if (this.isZoomedOut) {
			this.zoomSelection;
		}{
			sfv.zoomToFrac(1);
			sfv.setSelection(selections.currentSelectionIndex, selections.currentSelection);
		}
	}

	isZoomedOut {
		^sfv.xZoom.round(0.00001) == buffer.dur.round(0.00001)
	}

	zoomSelection {
		{
			sfv.zoomSelection(selections.currentSelectionIndex);
		}.fork(AppClock);
	}

	rangeSlider {
		^RangeSlider().orientation_(\horizontal)
	}

	posDisplay { | sfv |
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
			})

		)
	}
}