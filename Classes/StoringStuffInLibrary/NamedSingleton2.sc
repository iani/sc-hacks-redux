// 火  6  5 2025 08:24
// Enable use of "new" for instantiation.
// Simpler implementation.

NamedSingleton2 {
	var <name;
	*doesNotUnderstand { | selector ... args |
		^this.default.perform(selector, *args);
	}

	*default { | ... args | ^this.new(\default, *args);}

	*new { | key ... args |
		var new;
		new = Library.global.at(this, key);
		new ?? {
			new = this.newCopyArgs(key);
			Library.global.put(this, key, new);
			this.changed(\fromLib, key, *args);
		};
		^new.init(*args);
	}

	*all { ^Registry.allAt(this) }

	init { | name ... args |
		// use init to customize state in your subclass
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << name.asString;
		stream << ">" ;
	}
}