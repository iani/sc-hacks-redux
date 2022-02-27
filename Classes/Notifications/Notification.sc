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

	remove { this.class.remove(notifier, message, listener); }

	*remove { | argNotifier, message, listener |
		controllers[argNotifier].remove(message, listener);
	}

	*notifications { ^controllers.values.collect({|nc|nc.actions.values}).flat; }

	*listeningto { | notifier |
		^this.notifications.select({ | n | n.notifier === notifier })
	}

	*notifying { | listener |
		^this.notifications.select({ | n | n.listener === listener })
	}

	*removeNotifiersOf { | listener |
		this.notifying(listener) do: _.remove;
	}

	*removeListenersOf { | notifier |
		this.listeningto(notifier) do: _.remove;
	}

	*notifiers {
		^Set.newFrom(this.notifications.collect({|n| n.notifier})); //.asArray;
	}
	*listeners {
		^Set.newFrom(this.notifications.collect({|n| n.listener})); //.asArray;
	}
	// *listeners { ^controllers.collect({ | c | c.listeners }).asArray.flat; }

	*matches { | notifier, listener, message |
		^this.notifications.detect({|n| n.matches(notifier, listener, message)}).notNil
	}

	matches { | argNotifier, argListener, argMessage |
		^notifier == argNotifier and:
		{ listener == argListener } and:
		{ message == argMessage }
	}

	// ---- rarely used access methods ----
	*notifiersOf { | listener |
		^Set newFrom:
		(this.notifications.select({|n| n.listener === listener }) ?? [])
		.collect(_.notifier);
	}

	*messagesOf { | notifier |
		var controller;
		controller = controllers[notifier];
		if (controller.isNil) { ^nil } { ^controller.messages };
	}

	*listenersOf { | notifier |
		^Set newFrom:
		(this.notifications.select({|n| n.notifier === notifier }) ?? [])
		.collect(_.listener);
	}
}