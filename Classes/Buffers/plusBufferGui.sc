/*  1 Jul 2023 12:02

*/

+ Buffer {
	gui {
		// "GUI".postln;
		this.postln;
		this.path.postln;
		this.bl_(600, 400).vlayout(
			this.sfView,
			this.rangeSlider,
			this.posDisplay
		)
	}

	sfView {
		^SoundFileView()
		.soundfile_(SoundFile(this.path))
		.readWithTask(0, this.numFrames)
	}

	rangeSlider {
		^RangeSlider().orientation_(\horizontal)
	}

	posDisplay {
		^HLayout(
			StaticText().string_("begin"),
			NumberBox()
			.addNotifier(this, \zoom, { | n, begin, end, dur |
				n.listener.value = begin
			}),
			StaticText().string_("end"),
			NumberBox()
			.addNotifier(this, \zoom, { | n, begin, end, dur |
				n.listener.value = end
			}),
			StaticText().string_("dur"),
			NumberBox()
			.addNotifier(this, \zoom, { | n, begin, end, dur |
				n.listener.value = dur
			})

		)
	}
}