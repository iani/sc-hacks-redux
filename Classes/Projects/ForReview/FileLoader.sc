/* 26 Jan 2023 01:29
Prototype of abstract class for loading files in different ways,
when the server boots.
Subclasses should provide specific ways of loading files, such as
evaluating them (load), or loading audio buffers, etc.

Should implement generic functionality hitherto found in AudioFolders.

*/

FileLoader : PreferenceSet {
	classvar enabled; // enabled instances load on server boot.
	var items; // items to be loaded
	// subclasses treat items as names of folders or as pathnames of files
	// or as either.

	*activate {
		ServerBoot.add(this);
		ServerQuit.add(this);
	}

	*doOnServerBoot { | server |
		// workaround for a bug: make sure the server is booted:
		server doWhenBooted: { this.enabled do: _.load;};
	}

	*doOnServerQuit { this.clearItems }

	// remove loaded items from memory
	*clearItems { }
	// add to enabled set
	enable { }

	// remove from enabled set
	dissable {  }

	load {  } // load files

	add {  }

	remove {  }

	// add and load immediately
	require {  }
}