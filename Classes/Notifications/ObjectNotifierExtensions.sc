/* 30 Jan 2021 12:29

*/

+ Object {
	addNotifier { | notifier, message, action |
		notifier.removeDependant(Notifier.get(notifier, message, this));
		notifier.addDependant(Notifier(notifier, message, this, action).store);
	}

	removeNotifier { | notifier, message |
		notifier.removeDependant(Notifier.get(notifier, message, this));
		Notifier.remove(notifier, message, this);
	}

	addNotifierOneShot { | notifier, message, action |
		this.addNotifier(notifier, message, { | notification ... args |
			action.(notification, *args);
			this.removeNotifier(notifier, message);
		})
	}

	listeners { ^Notifier.listenersOf(this) }
	notifiers { ^Notifier.notifiersOf(this) }
	removeListeners { this.listeners do: _.remove; } // remove all listeners
	removeNotifiers { this.notifiers do: _.remove; } // remove all notifiers
	removeListenersAt { | message |
		
	}
	removeNotifiersAt { | message |

	}
	objectClosed {
		this.changed(\objectClosed);
		this.removeListeners;
		this.removeNotifiers;
		this.releaseDependants;
	}

	onObjectClosed { | listener, action |
		listener.addNotifier(this, \objectClosed, action);
		if (this respondsTo: \onClose_) {
			this.onClose = { this.objectClosed };
		}
	}
}