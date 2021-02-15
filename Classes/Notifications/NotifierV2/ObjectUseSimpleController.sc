/* 14 Feb 2021 14:18
Experimental: 
For efficiency, use SimpleController instead of Notifier.
*/

+ Object {
	addNotifier { | notifier, message, action |
		Notification(notifier, message, this, action).add;
	}

	removeNotifier { | notifier, message |
		//		notifier.removeDependant(Notifier2.get(notifier, message, this));
		Notification.remove(notifier, message, this);
	}

	addNotifierOneShot { | notifier, message, action |
		this.addNotifier(notifier, message, { | notification ... args |
			action.(notification, *args);
			this.removeNotifier(notifier, message);
		})
	}

	listeners { ^Notification.listenersOf(this) }
	notifiers { ^Notification.notifiersOf(this) }

	objectClosed {
		this.changed(\objectClosed);
		this.removeListeners;
		this.removeNotifiers;
		this.releaseDependants;
	}

	removeListeners { Notification removeListenersOf: this }
	removeNotifiers { Notification removeNotifiersOf: this }

	onObjectClosed { | listener, action |
		listener.addNotifier(this, \objectClosed, action);
		if (this respondsTo: \onClose_) {
			this.onClose = { this.objectClosed };
		}
	}

	// TODO:
	// removeListenersAt { | message | }
	// removeNotifiersAt { | message | }

}