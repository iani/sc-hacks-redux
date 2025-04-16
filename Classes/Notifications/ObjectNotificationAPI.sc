/* 30 Jan 2021 12:25
Transferred from sc-hacks.
*/

+ Object {
	addNotifier { | notifier, message, action |
		Notification(notifier, message, this, action).add;
	}

	removeNotifier { | notifier, message |
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

	addNodeActions { | node, onStart, onEnd |
		node.onStart({ onStart.(this, node) }, this);
		node.onEnd({ onEnd.(this, node) }, this);
	}
	// TODO:
	// removeListenersAt { | message | }
	// removeNotifiersAt { | message | }

}

+ Node {
    /* always release notified nodes when they are freed
        Note: any objects that want to be notified of the node's end, 
        can listen to it notifying 'n_end', which is triggered through NodeWatcher
        and which is the same message that makes the Node remove all its Notifications.
    */
	// Fixed  6 Dec 2022 17:55 - still needs watching and more testing
    addNotifier { | notifier, message, action |
        super.addNotifier(notifier, message, action);
        NodeWatcher.register(this);
		this.addDependant({ | me, message |
			// args.postln;
			// "somethingChanged".postln;
			if (message == 'n_end') {
				// "I ended".postln;
				{ this.objectClosed; }.defer(0.1);
			}
		});
		//     this.addNotifierOneShot(this, 'n_end', {
		// 		// remove notifiers only after all notifications have been issued!
		// 		// { this.objectClosed; }.defer(0.1);
		// 		// "debuggging node addNotifier".postln;
		// });
    }

	onStart { | action, listener |
		if (this.isPlaying) { ^action.(this) };
		listener = listener ? { this }; // DO NOT CHANGE THIS!
		NodeWatcher.register(this);
		listener.addNotifierOneShot(this, \n_go, {
			this.isPlaying = true;
			action.(this);
			// this.changed(\started);
		});
	}

	onEnd { | action, listener |
		listener = listener ? { this }; // DO NOT CHANGE THIS!
		NodeWatcher.register(this);
		listener.addNotifierOneShot(this, \n_end, { action.(this) });
	}	
}
