/* 14 Oct 2022 15:50

EventStream history cannot be recorded.
Synth history is recorded.

However, both Synhs and EvenStreams are managed by this gui.
EventStreams can be restarted without using history.

Under development 23 Oct 2022 21:34

*/

PlayerHistory : MultiLevelIdentityDictionary {
	classvar default;

	// currently chosen environment and player:
	// Used by gui to obtain list items.
	var <envir, <player, <control;

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
				ListView() // envir list
				.items_(Mediator.envirNames.sort)
				.action_({ | me |
					// postln("You chose this item: " + me.item + me.item.class);
					envir = me.item;
					this.changed(\envir, envir);
				})
				.addNotifier(Mediator, \fromLib, { | n |
					var envirs, index;
					// "PlayerHistory: a new envir was created !!!!!!!!!!!".postln;
					envirs = Mediator.envirNames.sort;
					n.listener.items = envirs;
					n.listener.value = envirs indexOf: (envir ? \default);
				}),
				ListView()
				.items_(Mediator.playerNames(
					\default
				).sort),
				ListView()
			),
			TextView(), //
		).name_("Player History")
	}
}