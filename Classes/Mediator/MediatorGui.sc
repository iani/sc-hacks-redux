/*  7 Mar 2022 09:45
List all keys and values of an environment.
*/

MediatorGui {
	var <mediator;

	*new { | mediator |
		mediator = mediator ?? { Mediator.default; };
		^Registry(this, mediator, {
			var instance;
			instance = this.newCopyArgs(mediator).init;
			instance.bounds_(Rect(0, 400, 500, 400));
		}).makeWindow;
	}

	init {
		mediator addDependant: this;
	}

	makeWindow {
		// postln("Mediator" + this + "WILL MAKE A WINDOW");
		this.window({ | w |
			w.name_(format("Environment: %", mediator.name));
			w.layout = HLayout(
				ListView()
				.items_(mediator.envir.keys.asArray.sort)
				/*
				.addNotifier(this, \key, { | n ... args |
					var value;
					value = n.listener.value;
					value !? {
						n.listener.items[n.listener.value].postln;
						n.listener.items[n.listener.value].class.postln;
					};
					postln("current index ?????" + n.listener.value);
					n.listener.items = n.notifier.mediator.keys.asArray.sort;
					value !? {
						n.listener.value = n.listener.items indexOf: n.listen
					};
				})
				*/
				.action_({ | me |
				}),
				TextView()
				.string_("TESTING")
			)
		})
	}

	update { | mediator, key, value |
		this.changed(\key, key, value);
	}

}