/* 25 Oct 2018 03:03
*/

+ Rect {
	// Note: Cannot defer these because their values are immediately needed.
	*tl { | width = 200, height = 200 |
		^this.new(
			0, Window.availableBounds.height - height - 25,
			width, height - 25
		)
	}
	*tr { | width = 200, height = 200 |
		var availableBounds = Window.availableBounds;
		^this.new(
			availableBounds.width - width,
			availableBounds.height - height - 25,
			width, height - 25
		)
	}

	*bl { | width = 200, height = 200 |
		^this.new(0, height + 25, width, height)
	}

	*br { | width = 200, height = 200 |
		^this.new(Window.availableBounds.width - width,
			0, width, height
		)
	}
}