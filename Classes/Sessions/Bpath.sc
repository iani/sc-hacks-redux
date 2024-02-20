//Wed 21 Feb 2024 01:57
//debug adding equal elements to a set
//Note: If variable name is removed, then Set does not add duplicate
//Bpath instances.

Bpath {
	var <path, <name;

	*new { | path | ^this.newCopyArgs(path); }

	== { arg that; ^path == that.path }
	// also tested with this, modeled after NetAddr:
	// == { arg that; ^this.compareObject(that, #[\path]) }
}