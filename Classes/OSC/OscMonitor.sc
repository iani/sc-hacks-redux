/*  2 Nov 2022 10:52
Track incoming OSC messages.
Open windows for viewing input arriving at any selected message.
*/

OscMonitor {
	classvar <messages;
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

	*gui {
		this.enable;
		this.tl_.vlayout(
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
			HLayout(
				CheckBox()
				.string_("Post OSC Input")
				.action_({ | me | if (me.value) { OSC.trace } { OSC.untrace }; }),
				CheckBox()
				.string_("Filter Server Messages")
				.value_(Trace.excludeServerMessages_p)
				.action_({ | me | OSC.filterServerMessages(me.value); })
			),
			HLayout(
				CheckBox()
				.string_("OSCFunc trace")
				.action_({ | me | OSCFunc.trace(me.value); })
			)
		);
		{ this changed: \messages } defer: 1.0;
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
}