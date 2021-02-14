/* 14 Feb 2021 14:18
Experimental: 
For efficiency, use SimpleController instead of Notifier.
*/

+ Object {
	addNotifier2 { | notifier, message, action |
		Notifier2(notifier, message, this, action).add;
	}

	removeNotifier2 { | notifier, message |
		notifier.removeDependant(Notifier2.get(notifier, message, this));
		Notifier2.remove(notifier, message, this);
	}

	addNotifierOneShot2 { | notifier, message, action |
		this.addNotifier2(notifier, message, { | notification ... args |
			action.(notification, *args);
			this.removeNotifier2(notifier, message);
		})
	}

	listeners2 { ^Notifier2.listenersOf(this) }
	notifiers2 { ^Notifier2.notifiersOf(this) }
	removeListeners2 { this.listeners2 do: _.remove; } // remove all listeners
	removeNotifiers2 { this.notifiers2 do: _.remove; } // remove all notifiers
	removeListenersAt2 { | message |
		
	}
	removeNotifiersAt2 { | message |

	}
	objectClosed2 {
		this.changed(\objectClosed);
		this.removeListeners2;
		this.removeNotifiers2;
		this.releaseDependants2;
	}

	onObjectClosed2 { | listener, action |
		listener.addNotifier2(this, \objectClosed, action);
		if (this respondsTo: \onClose_) {
			this.onClose = { this.objectClosed2 };
		}
	}
}