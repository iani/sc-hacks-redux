// Used by NodeTemplate and ActionStack to accumulate args
// when repeating set commands on the same instance.

+ Event {
	mergeArgs { | args |
		// add all key-value pairs in args to self.
		// postln("debugging merge args");
		// postln("here I am before getting the new args" + this);
		// "now I start the merging".postln;
		// postln("the args to be merged are" + (args ? []));
		(args ? []) keysValuesDo: { | key, value |
			// postln("adding to key" + key + "the value" + value);
			this[key] = value;
			// postln("after adding I am" + this);
		};
		// postln("the merged args are now" + this);
		// return new args array for use by NodeTemplate etc.
		^this.keys.asArray.sort.collect({ | key | [key, this[key]] }).flat;
	}
}