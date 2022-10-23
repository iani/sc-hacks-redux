/* 14 Oct 2022 15:50

EventStream history cannot be recorded.
Synth history is recorded.

However, both Synhs and EvenStreams are managed by this gui.
EventStreams can be restarted without using history.

*/

PlayerHistory : MultiLevelIdentityDictionary {
	classvar default;

	// currently chosen environment and player:
	// Used by gui to obtain list items.
	var <envir, <player;

	*initClass {
		StartUp add: { this.enable; }
	}

	*enable {
		this.addNotifier(Function, \player, { | n, event, player, time, code, controls |
			this.add(event, player, time, code, controls);
		});
	}

	*disable {
		this.removeNotifier(Function, \player);
	}
	*default { ^default ?? { default = this.fromLib(\default) } }

	*add { | event, player, time, code, controls |
		var all, thisOne;
		player !? {
			// only add if played for player (with playInEnvir)
		all = this.default;
		event ?? { event = currentEnvironment };
		thisOne = all.at(event, player);
		thisOne = thisOne add: [time, code, controls];
		all.put(event, player, thisOne);
		}
	}

	*at { | event, player |
		^this.default.at(event, player);
	}

	*gui { this.default.gui }
	gui {
		this.vlayout(
			HLayout(
				ListView()
				.items_(Mediator.envirNames.sort)
				.action_({ | me |
					postln("")
				}),
				ListView()
				.items_(Mediator.playerNames(
					\default
					// Mediator.envirNames.sort.first).sort
				).sort),
				ListView()
			),
			TextView()
		).name_("Player History")
	}
}