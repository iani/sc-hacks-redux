// Sat 22 Feb 2025 19:11
// Map an incoming arithmetic value to a new one. Set synth control if needed.
// Experimental. May not be used

ValueMapper {
	var <function, <value = 0;

	*new { | function, value = 0 |
		^newCopyArgs(function, value);
	}

	// instead of handleReplacement?
	// TODO: write handleReplacement method
	inputValue { | argValue = 0 |
		value = function.(argValue);
		// this.changed(\value);
	}
}
