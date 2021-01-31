/* 31 Jan 2021 14:30
Adding things to do when a Node or Synth starts or ends
*/
+ Node {
	onStart { | action, listener |
		listener = listener ? { this };
		NodeWatcher.register(this);
		listener.addNotifierOneShot(this, \n_go, action);
	}

	onEnd { | action, listener |
		listener = listener ? { this };
		NodeWatcher.register(this);
		listener.addNotifierOneShot(this, \n_end, action);
	}
}
