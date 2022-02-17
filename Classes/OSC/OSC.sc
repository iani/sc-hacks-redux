/* 14 Feb 2022 11:17
Alternative scheme for OSC reception

OscGroupClient 139.162.184.97 22242 22246 22244 22245 <USERNAME> <USER_PASSWORD> checkGroup test

*/

OSC {
	// classvar >settings;
	// *settings { ^settings ?? { settings = IdentityDictionary(); } }
	// *getSetting { | key | ^this.settings[setting] }
	// *putSetting { | key, value | this.settings[setting] = value }

	classvar >reportingFunc;

	*add { | key, message, function |
		// message is the osc message to which the function is bound.
		// One can use different keys to add more than one function to one message.
		// Each key - message pair creates a new Notification, with
		// key as the listener and message as the message.
		// Convert message to standard osc message format by prepending / if needed
		key.addNotifier(this, message.asOscMessage, function);
	}

	*remove { | key, message |
		Notification.remove(this, message.asOscMessage, key);
	}

	*list {
		var controllers;
		controllers = this.dependants.select({ | d |
			d isKindOf: NotificationController
		});
		if (controllers.size == 0) {
			"===== There are no actions in OSC currently =====".postln;
		};
		controllers do: { | d |
			d.actions keysValuesDo: { | key, notifications |
				postf("  Actions at % :\n", key);
				notifications do: { | n |
					postf("    key: %, action: %\n", n.listener, n.action)
				}
			};
		}
	}

	*clear {
		// this.releaseDependants; // this does not remove Notifications!
		// The following cleans up the Notification data:
		Notification.removeListenersOf(this);
	}

	*activeMessages {
		// Return all messages that OSC intercepts
		^this.dependants.asArray.select({ | n | n isKindOf: NotificationController })
		.collect({ | nc | nc.actions.keys.asArray })
		.flat
	}

	*verbose { this addDependant: this.reportingFunc; }
	*silent { this removeDependant: this.reportingFunc; }
	*reportingFunc {
		^reportingFunc ?? { reportingFunc = { | ... args | args.postln; } };
	}

	*sendLocal { | message ... args | // for testing
		// asOscMessage: prepend / to message if needed.
		NetAddr.localAddr.sendMsg(message.asOscMessage, *args);
	}
}