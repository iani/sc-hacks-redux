// 木 12  6 2025 09:21

AnimationGui : NamedSingleton2 {
	var <animation;
	var <window, sfview, soundFile, numdata;

	init {
		animation = Animation.sessions.at(name);
		postln("Initing" + this);
		this.gui;
	}

	*gui {
	// list loaded Animations.
	// Add new animation by reading data.
	// Start/stop a selected animation from the list.
		var sessionList, clipList, playButton, viewButton, window;
		var sessionButton, sessionPlayButton;
		window = this.hlayout(
			VLayout(
				sessionList = ListView().items_(Animation.sessionFolders)
				.minWidth_(300),
				HLayout(
				sessionButton = Button().states_([["session gui"]])
				.action_({ | me |
					AnimationGui(sessionList.item.asSymbol);
				}),
				sessionPlayButton = Button().states_([["play session"]])
				.action_({ | me |
					;
				})
				)
			),
			VLayout(
				clipList = ListView(),
				playButton = Button().states_([["play clip"]]),
				viewButton = Button().states_([["clip gui"]])
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
		postln("The animation is:" + animation);
		if (window.isNil) { this.makeWindow } { window.front };
	}

	makeWindow {
		window = Window(name.asString, Rect(0, 0, 1400, 900));
		animation.soundFilePath.postln;
		numdata = animation.converter.numdata;
		window.view.layout = HLayout(
			sfview = SoundFileView();
		);
		{ sfview.setData(numdata.lace, 16, channels: numdata.first.size) }.defer(0.1);
		window.front;
		window.onClose = { window = nil };
	}
}
