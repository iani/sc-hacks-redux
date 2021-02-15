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
		this.addNotifier2(notifier, message, { | notification ... args |
			action.(notification, *args);
			this.removeNotifier(notifier, message);
		})
	}

	listeners { ^Notification.listenersOf(this) }
	notifiers { ^Notification.notifiersOf(this) }
	removeListeners2 { this.listeners2 do: _.remove; } // remove all listeners
	removeNotifiers2 { this.notifiers2 do: _.remove; } // remove all notifiers
	removeListenersAt2 { | message |
		
	}
	removeNotifiersAt2 { | message |

	}
	objectClosed {
		this.changed(\objectClosed);
		this.removeListeners2;
		this.removeNotifiers2;
		this.releaseDependants;
	}

	onObjectClosed { | listener, action |
		listener.addNotifier(this, \objectClosed, action);
		if (this respondsTo: \onClose_) {
			this.onClose = { this.objectClosed };
		}
	}
}