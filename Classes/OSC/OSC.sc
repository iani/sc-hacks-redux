/* 14 Feb 2022 11:17
Alternative scheme for OSC reception

OscGroupClient 139.162.184.97 22242 22246 22244 22245 <USERNAME> <USER_PASSWORD> checkGroup test

*/

OSC {
	// classvar >settings;
	// *settings { ^settings ?? { settings = IdentityDictionary(); } }
	// *getSetting { | key | ^this.settings[setting] }
	// *putSetting { | key, value | this.settings[setting] = value }

	*add { | receiver, message, function |
		receiver.addNotifier(this, message, function);
	}

	*remove { | receiver, message |
		receiver.removeNotifier(this, message);
	}

	*clear {
		this.releaseDependants;
	}

	*activeMessages {
		// Return all messages that OSC intercepts
		^this.dependants.asArray.select({ | n | n isKindOf: NotificationController })
		.collect({ | nc | nc.actions.keys.asArray })
		.flat
	}

}