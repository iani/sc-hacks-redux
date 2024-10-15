/*  1 Jul 2023 13:17

*/
//:
f = SoundFile.openRead(\crickets.buf.path);
f.readData(d = Signal.newClear(f.numFrames * f.numChannels));
f.close;
​
//:
var w = Window("test", Rect(700, 200, 600, 300)),
sfv, sfZoom, mouseButton,
dur = d.size / f.sampleRate / f.numChannels;
​
w.layout = VLayout(
    sfv = SoundFileView(),
    sfZoom = RangeSlider().orientation_(\horizontal)
);
​
sfZoom.lo_(0).range_(1)
.action_({ | view |
    var divisor, rangeStart;
    rangeStart = view.lo;
    divisor = 1 - sfZoom.range;
    if(divisor < 0.0001) {
        rangeStart = 0;
        divisor = 1;
    };
	// postln("zoom" + (sfZoom.range * dur) + "scroll" + (rangeStart / divisor));
	postln("view lo" + (view.lo * dur) + "view hi" + (view.hi * dur));
    sfv.xZoom_(sfZoom.range * dur)
    .scrollTo(rangeStart / divisor)
});
​
sfv.setData(d, startFrame: 0, channels: f.numChannels, samplerate: f.sampleRate);
​
sfv.mouseDownAction_({ |view, x, y, mod, buttonNumber|
    mouseButton = buttonNumber;
})
.mouseUpAction_({ |view, x, y, mod|
    mouseButton = nil;
})
.mouseMoveAction_({ |view, x, y, mod|
    var rangeSize, rangeStart;
	// mouseButton.postln;
    if(mouseButton == 1) {
        rangeSize = view.xZoom / dur;
        rangeStart = view.scrollPos * (1 - rangeSize);
        sfZoom.lo_(rangeStart).range_(rangeSize);
		postln("rangeStart" + (rangeStart * dur) + "rangeSize" + (dur * rangeSize)
			+ "rangeEnd" + (rangeStart + rangeSize * dur)
		);
    }{
		view.selections.postln;
		view.setSelection(1, [10000, 80000]);
		rangeSize = view.xZoom / dur;
        rangeStart = view.scrollPos * (1 - rangeSize);
		postln("rangeStart" + (rangeStart * dur) + "rangeSize" + (dur * rangeSize)
			+ "rangeEnd" + (rangeStart + rangeSize * dur)
		);
	};
});
​
w.front;
//:
