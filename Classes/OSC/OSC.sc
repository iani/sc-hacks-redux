/* 14 Feb 2022 11:17
Alternative scheme for OSC reception

OscGroupClient 139.162.184.97 22242 22246 22244 22245 <USERNAME> <USER_PASSWORD> checkGroup test

*/

OSC {
	classvar >reportingFunc;

	*add { | message, function, key |
		// message is the osc message to which the function is bound.
		// One can use different keys to add more than one function to one message.
		// Each key - message pair creates a new Notification, with
		// key as the listener and message as the message.
		// Convert message to standard osc message format by prepending / if needed
		(key ? message).addNotifier(this, message.asOscMessage, function);
	}

	*addRaw { | message, function, key |
		// Like add, without prepending / to message.
		// For use with SendReply.
		(key ? message).addNotifier(this, message, function);
	}
	*remove { | message, key |
		(key ? message).removeNotifier(this, message.asOscMessage);
	}

	*list {
		var controllers;
		controllers = this.dependants.select({ | d |
			d isKindOf: NotificationController
		});
		if (controllers.size == 0) {
			^"===== There are no actions in OSC currently =====".postln;
		};
		postln("======= Listing" + controllers.size + "controllers: ======= ");
		controllers do: { | d |
			d.actions keysValuesDo: { | key, notifications |
				postf("  Actions at % :\n", key);
				notifications do: { | n |
					postf("    notifier: %, listener: %, message: %, action: %\n",
						n.notifier, n.listener, n.message, n.action)
				}
			};
		}
	}

	*clear {
		// The following cleans up the Notification data:
		Notification.removeListenersOf(this);
		this.releaseDependants; // Release non-Notification dependants
	}

	*actionsAt { | message | // all keys responding to this message
		"OSC actionsAt has not been implemented.".postln;
	}

	*listensTo { | message, key |
		^Notification.matches(OSC, key ? message, message.asOscMessage);
	}

	*activeMessages {
		// Return all messages that OSC intercepts
		^this.dependants.asArray.select({ | n | n isKindOf: NotificationController })
		.collect({ | nc | nc.actions.keys.asArray })
		.flat
	}
	*activeMessagePairs {
		// Return all messages that OSC intercepts
		^this.dependants.asArray.select({ | n | n isKindOf: NotificationController })
		.collect({ | nc | nc.notifications.collect({|n| [n.message, n.listener]}) })
		// .flat
	}

	*verbose { this addDependant: this.reportingFunc; }
	*silent { this removeDependant: this.reportingFunc; }
	*reportingFunc {
		^reportingFunc ?? { reportingFunc = { | ... args | args.postln; } };
	}

	*sendLocal { | message ... args | // for testing
		NetAddr.localAddr.sendMsg(message.asOscMessage, *args);
	}

	*traceMessages { | ... messages |
		messages do: { | message | this trace1: message }
	}

	*untraceMessages { | ... messages |
		messages do: { | message | this untrace1: message }
	}
	*trace1 { | message |
		Trace.addNotifier(this, message.asOscMessage, { | msg ... args | args.postln; })
	}

	*untrace1 { | message |
		Trace.removeNotifier(this, message.asOscMessage)
	}
}