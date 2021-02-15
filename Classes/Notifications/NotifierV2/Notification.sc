/* 14 Feb 2021 22:31
New version of Notification,
using SimpleController to optimize speed of lookup.
The actual performance gain may be negligible.
This is just an exercise to prove the feasibility of the concept.

Interface and steps for implementation: 

listener.addNotifier(notifier, message, action):

	-> Notification(notifier, message, listener, action) [NEW INSTANCE !!!!!]
	-> Add the instance to the corresponding controller in controllers
	-> If a previous instance exists, replace it (this just replaces the action)
	-> Add the corresponding controller as dependant to notifier

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
	
	*removeNotifiersOf { | object | this.notifiersOf(object) do: _.remove; }

	*removeListenersOf { | object | this.listenersOf(object) do: _.remove; }
}