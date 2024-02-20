//Sun 18 Feb 2024 17:12

Bookmark {
	classvar all; // array of strings. duplicate paths not allowed!
	var <path, <name;

	*new { | path |
		^this.newCopyArgs(path).init;
	}

	init {
		this.makeName;
	}

	makeName { name = path.basicName }

	all {
		^all ?? { all = Set() }
	}

	// == { | b | ^path == b.path }

	== { arg that;
		^this.compareObject(that, #[\path])
	}
}