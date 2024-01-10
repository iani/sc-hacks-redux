/* 18 Aug 2023 09:09
For compatibility with simple specs in PlainSynth templates.
*/

+ Object {
	specs { ^this.asSpec2 } // used by SynthTemplate, UGenFunc and their subclasses
	asSpec2 { ^this.asSpec } // symbol overwrites this to name the unit
}

+ Symbol { // convert nil specs to a spec + store self to units
	asSpec2 { ^this.asSpec.asSpec.units_(this) }
}