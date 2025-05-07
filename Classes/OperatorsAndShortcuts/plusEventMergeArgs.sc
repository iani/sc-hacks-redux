// Used by NodeTemplate and ActionStack to accumulate args
// when repeating set commands on the same instance.

+ Event {
	mergeArgs { | args |
		// add all key-value pairs in args to self.
		(args ? []) keysValuesDo: { | key, value | this[key] = value };
		// return new args array for use by NodeTemplate etc.
		^this.keys.asArray.sort.collect({ | key | [key, this[key]] }).flat;
	}
}