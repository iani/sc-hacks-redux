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
		var sessionButton, sessionPlayButton, verboseButton, freezeButton;
		window = this.hlayout(
			VLayout(
				sessionList = ListView().items_(Animation.sessionFolders)
				.minWidth_(300)
				.hiliteColor_(Color(0.7, 0.8, 0.9))
				.selectedStringColor_(Color.red),
				HLayout(
					sessionButton = Button().states_([["session gui"]])
					.action_({ | me |
						AnimationGui(sessionList.item.asSymbol);
					}),
					sessionPlayButton = Button().states_(
						[["play"], ["stop"]])
					.action_({ | me |
						var chosenSession;
						chosenSession = Animation.sessions.at(sessionList.item.asSymbol);
						chosenSession.postln;
						if (me.value > 0) {
							chosenSession.play;
							freezeButton.value = 0;
						}{ // stop all - in case previous sessions left
							Animation.stopAll;
						}
					})
					.addNotifier(this, \cmdperiod, { | n |
						// { "I should change".postln; } ! 10;
						// postln("Setting to 0" + n.listener);
						n.listener.value = 0;
					}),
					freezeButton = Button().states_(
						[["freeze"], ["move"]])
					.action_({ | me |
						var animation;
						animation = Animation.sessions.at(sessionList.item.asSymbol);
						if (me.value > 0) {
							animation.freeze;
						}{
							animation.move;
						}
					}),
					verboseButton = Button().states_(
						[["post"], ["mute"]])
					.action_({ | me |
						if (me.value > 0) {
							AnimationController.verbose = true;
						}{
							AnimationController.verbose = false;
						}
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
		CmdPeriod add: this;
	}

	*doOnCmdPeriod {
		// { "CMDPERIOD".postln; } ! 10;
		this.changed(\cmdperiod);
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
