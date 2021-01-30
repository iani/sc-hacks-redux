/* 29 Jan 2021 11:49
Replacing Notification class.
*/

Notifier {
	classvar <notifiers;
	var <notifier, <message, <listener, <action;

	*initClass {
		StartUp add: { notifiers = MultiLevelIdentityDictionary() }
		
	}
	
	*new { | notifier, message, listener, action |
		^super.newCopyArgs(notifier, message, listener, action);
	}

	// respond to changed messages. Note: 'this' contains notifier and listener
	update { | sender, message ... args | action.(this, *args); }

	// store self in library
	store { notifiers.put(notifier, message, listener, this) }

	*get { | notifier, message, listener |
		^notifiers.at(notifier, message, listener)
	}

	remove { this.class.remove(notifier, message, listener); }

	*remove { | notifier, message, listener |		
		notifiers.removeAtPath([notifier, message, listener]);
	}

	*notifiersOf { | object |
		// a notifier of object is a Notifier containing object as listener!
		^notifiers.leaves.flat.select({ | n | n.listener === object })
	}

	*listenersOf { | object |
		// a listener of object is a Notifier containing object as notifier!
		^notifiers.leaves.flat.select({ | n | n.notifier === object })
	}
	
	*removeNotifiersOf { | object | this.notifiersOf(object) do: _.remove; }

	*removeListenersOf { | object | this.listenersOf(object) do: _.remove; }
}
