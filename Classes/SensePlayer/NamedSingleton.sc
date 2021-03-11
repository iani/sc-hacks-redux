// Superclass for SenseServer and its dependants
NamedSingleton : Singleton {
	var <name;

	*new { | name |
		postf("NamedSingleton NEW. name is: %\n", name);
		^this.newCopyArgs(name);
	}
	
	init { | ... args |
		this.prInit(*args); // subclasses add more init here if needed
	}

	prInit { /* subclasses add stuff if needed */ }

	// we don't know if this can be inherited yet.
	// testing!:::::
	*all {
		//postf("testing 'all' method. my class is: %\n", this);
		^this.libTree;
	}
}
