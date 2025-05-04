/*
WARNING: Extension in '/Users/iani/Library/Application Support/SuperCollider/Extensions/sc-hacks-redux/Classes/OperatorsAndShortcuts/SymbolOperators.sc' overwrites Nil:pop in main class library.
WARNING: Extension in '/Users/iani/Library/Application Support/SuperCollider/Extensions/sc-hacks-redux/Classes/Utilities/PathnameUtilities/plusStringPathNameMethods.sc' overwrites String:asRelativePath in main class library.
WARNING: Extension in '/Users/iani/Library/Application Support/SuperCollider/Extensions/sc-hacks-redux/Classes/Utilities/PathnameUtilities/plusStringPathNameMethods.sc' overwrites String:absolutePath in main class library.
WARNING: Extension in '/Users/iani/Library/Application Support/SuperCollider/Extensions/sc-hacks-redux/Classes/Notifications/ObjectNotificationAPI.sc' overwrites Node:register in main class library.

日  4  5 2025 12:24
*/

+ Nil {
	pop { ^User.localId.pop }
}

+ String {
	asRelativePath { | relativeTo | ^PathName(this).asRelativePath(relativeTo) }
	absolutePath { ^PathName(this).absolutePath }
}

+ Node {
	register { | listener = \nodeWatcher |
		NodeWatcher.register(this);
		listener.addNotifierOneShot(this, \n_go, {
			this.isPlaying = true;
			listener.changed(\started);
		});
		listener.addNotifierOneShot(this, \n_end, {
			this.isPlaying = false;
			listener.changed(\stopped);
		});
	}
}

+ Symbol {
		// Symbol:stop must be overwritten in SystemOverWrites folder
		// Otherwise the compiler will not overwrite this method
	stop { currentEnvironment[this].stop; }
	clear {
		postln("clear: stored an empty stream on" + this);
		this.stop; // first stop previous contents to prevent runaway orphans
		this storeEventStream: ();
	}
}