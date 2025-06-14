// 木 12  6 2025 09:21

AnimationGui {
	*gui {
	// list loaded Animations.
	// Add new animation by reading data.
	// Start/stop a selected animation from the list.
		var sessionList, clipList, playButton, viewButton, window;
		window = this.hlayout(
			sessionList = ListView().items_(Animation.sessionFolders)
			.minWidth_(300),
			VLayout(
				clipList = ListView(),
				playButton = Button().states_([["play"]]),
				viewButton = Button().states_([["gui"]])
			)
		);
		window.bounds = Rect(0, 0, 550, 250);
		sessionList.action = { | me |
			postln("selecting nr" + me.value + "which is" + me.item);
			me.item.class.postln;
			Animation.sessions[me.item].postln;
		};
		sessionList.valueAction = 0;
	}

	gui {
		//
	}
}
