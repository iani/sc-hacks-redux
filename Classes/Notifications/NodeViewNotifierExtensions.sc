/* 30 Jan 2021 12:25
Transferred from sc-hacks.
*/

+ Node {
    /* always release notified nodes when they are freed
        Note: any objects that want to be notified of the node's end, 
        can listen to it notifying 'n_end', which is triggered through NodeWatcher
        and which is the same message that makes the Node remove all its Notifications.
    */
    addNotifier { | notifier, message, action |
        super.addNotifier(notifier, message, action);
        NodeWatcher.register(this);
        this.addNotifierOneShot(this, 'n_end', {
			// remove notifiers only after all notifications have been issued!
			{ this.objectClosed; }.defer(0.1);
		});
    }

	
}

+ View {
	addNotifier { | notifier, message, action |
		super.addNotifier(notifier, message, action);
		// release view when closed
		this.onClose = { this.objectClosed };
    }

	addServerNotifier { | server, on = 1, off = 0 |
		// Shortcut for server monitoring
		server = server ?? { Server.default };
		this.addNotifier(server, \counts, { | n |
			n.listener.value = on;
		});
		this.addNotifier(server, \didQuit, { | n |
			n.listener.value = off;
		})
	}

	onServerCounts { | action, server |
		server = server ?? { Server.default };
		this.addNotifier(server, \counts, { | n |
			action.(n.listener, server);
		})
	}
	
	onServerQuit { | action, server |
		server = server ?? { Server.default };
				this.addNotifier(server, \quit, { | n |
			action.(n.listener, server);
		})
	}
}
