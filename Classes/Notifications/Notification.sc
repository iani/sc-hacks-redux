/* 14 Feb 2021 22:31
New version of Notification,
using SimpleController to optimize speed of lookup.
The actual performance gain may be negligible, but here it is anyway.
*/

Notification {
	classvar <controllers;

	var <notifier, <message, <listener, <action;

	*initClass { controllers = IdentityDictionary(); }

	*new { | notifier, message, listener, action |
		^super.newCopyArgs(notifier, message, listener, action);
	}

	update { | sender, argMessage ... args |
		 action.valueArray(this, *args) 
	}

	add {
		var controller;
		controller = controllers[notifier];
		controller ?? {
			controller = NotificationController(notifier);
			controllers[notifier] = controller;
		};
		controller.add(message, listener, this);
	}

	*get { | notifier, message, listener |
		^controllers.at(notifier).at(message, listener);
	}

	*remove { | argNotifier, message, listener |
		controllers[argNotifier].remove(message, listener);
	}

	*notifications { ^controllers.values; }
	*notifiers { ^controllers collect: _.model; }
	*listeners { ^controllers.collect({ | c | c.listeners }).flat; }

	*notifiersOf { | listener |
		^this.notifications.select({ | n |
			n.listeners includes: listener
		}).collect({ | n | n.model })
	}

	*listenersOf { | notifier |
		var controller;
		controller = controllers[notifier];
		^if (controller.isNil) { ^nil } { ^controller.listeners };
	}
	
	*removeNotifiersOf { | listener |
		this.notifiersOf(listener) do: { | notifier |
			controllers[notifier].removeListener(listener)
		};
	}

	*removeListenersOf { | notifier |
		controllers[notifier].free;
		controllers.removeAt(notifier);
	}
}