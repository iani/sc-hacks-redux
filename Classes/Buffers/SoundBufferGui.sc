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
			]
		}).flat add: (Color(0.3, 0.2, 0.9))).reverse;
		this.bl_(1400, 400).hlayout(
			VLayout(
			sfv = this.sfView,
			this.rangeSlider,
				this.posDisplay(sfv)
			),
			ListView().maxWidth_(40)
			.items_((0..63))
			.colors_(colors)
			.action_({ | me |
				me.hiliteColor = colors[me.value];
				me.selectedStringColor_(colors[me.value].complement);
				this.changed(\selectionIndex, me.value);
			})
		).name_(PathName(buffer.path).fileNameWithoutExtension);
	}

	selectionStart {
		^sfv.selectionStart(sfv.currentSelection) / buffer.sampleRate;
	}

	selectionEnd {
		^(sfv.selectionStart(sfv.currentSelection) + sfv.selectionSize(sfv.currentSelection))
		/ buffer.sampleRate;
	}

	selectionDur {
		^sfv.selectionSize(sfv.currentSelection) / buffer.sampleRate;
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
					sfv.setSelectionStart(0, 0);
					sfv.setSelectionSize(0, 100000);
				},
				$z, {
					// postln("numFrames" + sfv.numFrames + "viewFrames" + sfv.numFrames);
					postln("before zoom selection" + sfv.selection(sfv.currentSelection)
						+ "index" + sfv.currentSelection);
					sfv.zoomSelection(sfv.currentSelection);
					postln("after zoom selection" + sfv.selection(sfv.currentSelection)
						+ "index" + sfv.currentSelection);
					postln("own cache:" + selections.currentSelection);
				},
				$1, {
					postln("before zoom selection" + sfv.selection(sfv.currentSelection)
						+ "index" + sfv.currentSelection);
					sfv.zoomToFrac(1);
					postln("after zoom selection" + sfv.selection(sfv.currentSelection)
						+ "index" + sfv.currentSelection);
					postln("own cache:" + selections.currentSelection);
				},
				$2, { sfv.zoomToFrac(1/2) },
				$3, { sfv.zoomToFrac(1/3) },
				$4, { sfv.zoomToFrac(1/4) },
				$5, { sfv.zoomToFrac(1/5) },
				$6, { sfv.zoomToFrac(1/6) }
			);
		})
		.mouseDownAction_({ |view, x, y, mod, buttonNumber|
			mouseButton = buttonNumber;
			// mouseButton.postln;
		})
		.mouseUpAction_({ |view, x, y, mod|
			// mouseButton = nil;
		})
		.action_({ | me | // Runs on mouseclick!
			this.changed(\selection);
		});
		colors do: { | c, i | sfv.setSelectionColor(i, c) };
		^sfv;
	}

	rangeSlider {
		^RangeSlider().orientation_(\horizontal)
	}

	posDisplay { | sfv |
		^HLayout(
			StaticText().string_("selection"),
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