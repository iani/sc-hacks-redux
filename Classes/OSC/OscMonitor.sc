/*  2 Nov 2022 10:52
Track incoming OSC messages.
Open windows for viewing input arriving at any selected message.
*/

OscMonitor {
	classvar <messages;
	classvar <oscdataplayer; // EnventStreamInstance;
	*enable {
		messages ?? { messages = Set() };
		OSC addDependant: this;
	}
	*disable { OSC removeDependant: this; }
	*update { | sender, message |
		var size;
		size = messages.size;
		messages add: message;
		if (messages.size > size) { this changed: \messages }
	}

	*playOsc {
		if (this.hasPlayer) {
			if (oscdataplayer.isPlaying) {
				"Osc data player is already playing".postln;
			}{
				oscdataplayer.start;
			}
		}{
			oscdataplayer = this.makePlayer;
		}
	}

	*stopPlayingOsc { oscdataplayer.stop }

	*hasPlayer { ^oscdataplayer.notNil }
	*isPlaying { ^oscdataplayer.isPlaying }
	*makePlayer {
		var player;
		if (OscDataReader.hasData) {
			if (oscdataplayer.notNil) {
				this.removeNotifier(oscdataplayer, \osclplayerstopped);
			};
			player = OscDataReader.makePlayer.play;
			this.addNotifier(player, \stopped, { this changed: \oscplayerstopped; });
			^player;
		}{
			"OscDataReader is empty".postln;
			"Please run Load OscFiles or Reload to get osc data".postln;
			{ this changed: \oscplayerstopped; }.defer(0.1);
			^nil;
		}
	}

	*gui {
		this.enable;
		this.tl_(400, 380).vlayout(
			HLayout(
				Button()
				.states_([["Record OSC"], ["Stop Recording OSC"]])
				.action_({ | me |
					OSCRecorder3.perform([\disable, \enable][me.value]);
				})
				.addNotifier(OSCRecorder3, \enabled_p, { | n, enabled_p |
					postln("OSC Recording status in now: " + enabled_p);
					if(enabled_p) { n.listener.value = 1 } { n.listener.value = 0 }
				}),
				Button()
				.states_([["Record OSC+Sound"], ["Stop Recording OSC+Sound"]])
				.action_(({ | me |
					[
						{ this.stopOscAndAudioRecording },
						{ this.startOscAndAudioRecording }
					][me.value].value;
				}))
				.addNotifier(Server.default, \recording, { | n |
					this.doublecheckRecordingStatus(n.listener);
				})
				.addNotifier(OSCRecorder3, \enabled_p, { | n |
					this.doublecheckRecordingStatus(n.listener);
				}),
				Button()
				.states_([["Record Sound"], ["Stop Recording Sound"]])
				.action_(({ | me |
					[
						{ Server.default.stopRecording },
						{ Server.default.record }
					][me.value].value;
				}))
				.addNotifier(Server.default, \recording, { | n, status |
					 n.listener.value = status.binaryValue;
				}),
				NumberBox().maxWidth_(51).minWidth_(50)
				.addNotifier(Server.default, \recordingDuration, { | n, d |
					n.listener.value = d.asInteger;
				})
			),
			HLayout(
				HLayout(
					CheckBox().string_("OscGroups")
					.backColor_(Color(0.7, 0.9, 0.8))
					.value_(OscGroups.isEnabled)
					.action_({ | me |
						if (me.value) { OscGroups.enable } { OscGroups.disable }
					})
					.addNotifier(OscGroups, \status, { | n |
						// postln("OscGroups enabled status is now:" + OscGroups.isEnabled)
						n.listener.value = OscGroups.isEnabled;
					}),
					PscoreMenu.scores1view,
					PscoreMenu.scores2view,
					PscoreMenu.scores3view,
					Button().states_([["browse", Color.red]]).maxWidth_(60)
					.menuActions([
						// FileNavigator(\oscscores, PathName(Scores.parentPath)).gui
						["browse folder", { FileNavigator.browseHacksScores }],
						["open sets", { this.pathListView }]
					]),
					// Button().states_([["browse sets"]]).action_({ this.pathListView })
					// Button().states_([["folders"]]).maxWidth_(45).action_({
					// 	// FileNavigator(\oscscores, PathName(Scores.parentPath)).gui
					// 	PscoreMenu.scorefoldersmenu;
					// })
				),
				// Button().states_([["Osc File Lists"]])
				// .action_({ OscDataGui.gui }),

				// Button().states_([["Load OscFiles"]])
				// .action_({ OscDataReader.openDialog }),
				// Button().states_([["Reload"]])
				// .action_({ OscDataReader.reRead }),
				// CheckBox()
				// .addNotifier(this, \oscplayerstopped, { | n | n.listener.value = false })
				// // .addΝοtifier(this, \oscplayerstopped, { | n | n.listener.value = false })
				// .string_("play osc")
				// .action_({ | me |
				// 	if (me.value) {
				// 		this.playOsc;
				// 	}{
				// 		this.stopPlayingOsc;
				// 	}
				// })
			),
			HLayout(
				CheckBox()
				.string_("OSC trace")
				.action_({ | me | if (me.value) { OSC.trace } { OSC.untrace }; }),
				CheckBox()
				.string_("Filter Server Messages")
				.value_(Trace.excludeServerMessages_p)
				.action_({ | me | OSC.filterServerMessages(me.value); }),
				CheckBox()
				.string_("OSCFunc trace")
				.action_({ | me | OSCFunc.trace(me.value); })
			),
			HLayout(
				CheckBox()
				.maxWidth_(70)
				.string_("Minibee")
				.backColor_(Color(0.9, 0.8, 0.7))
				.action_({ | me |
					if (me.value) { Minibee.enable.gui }{ Minibee.disable }
				})
				.addNotifier(Minibee, \status, { | n |
					if (n.notifier.enabled) {
						n.listener.value = true;
					}{
						n.listener.value = false;
					}
				}),
				CheckBox()
				.maxWidth_(65)
				.string_("smooth")
				.action_({ | me |
					if (me.value) { Minibee.enableSmoothing } { Minibee.disableSmoothing }
				})
				.value_(Minibee.smoothEnabled),
				Button().maxWidth_(40).states_([["gui"]]).action_({ Minibee.gui }),
				Button().states_([["BufferGui"]]).action_({ SoundBufferGui.gui }),
				Button().states_([["Meters"]]).action_({
					Server.default.meter;
					Stethoscope().window.bounds = Rect(0, 0, 240, 200);
				}),
				Button().states_([["Presets"]]).action_({ PresetList.presetSelectionGui; })
			),
			ListView()
			.palette_(QPalette.dark
				.highlight_(Color(0.1, 0.1, 0.7))
				.highlightText_(Color(0.9, 0.8, 0.7))
			)
			.addNotifier(this, \messages, { | n |
				n.listener.items = messages.asArray.sort;
			})
			.enterKeyAction_({ | me |
				me.item.asSymbol.watch;
			}),
		);
		{
			this changed: \messages;
			Minibee changed: \status;
		} defer: 1.0;
	}

	*startOscAndAudioRecording {
		OSCRecorder3.enable;
		Server.default.record;
	}

	*stopOscAndAudioRecording {
		OSCRecorder3.disable;
		Server.default.stopRecording;
	}

	*doublecheckRecordingStatus { | view |
		if (Server.default.isRecording and: { OSCRecorder3.isRecording }) {
			view.value = 1;
		}{
			view.value = 0;
		}
	}

	*pathListView {
		PathList.window(\oscMonitor,
			Button().states_([["multiplay"]]),
			{ | plv | OscData multiPlay: plv.getSelection; }
		);
	}
}