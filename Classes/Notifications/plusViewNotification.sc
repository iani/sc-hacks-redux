/*  8 Nov 2022 21:44
Enable disabling of file through yaml config.
By placing this extension in separate file.

*/

+ Window {
	addNotifier { | notifier, message, action |
		super.addNotifier(notifier, message, { | ... args |
		// defer needed when called from OSC or other SystemClock based process
			{ action.value(*args) }.defer
		});
		// release view when closed
		this.onClose = { this.objectClosed };
    }
}

+ View {
	addNotifier { | notifier, message, action |
		super.addNotifier(notifier, message, { | ... args |
		// defer needed when called from OSC or other SystemClock based process
			{ action.value(*args) }.defer
		});
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