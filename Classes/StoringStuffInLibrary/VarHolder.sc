// 日 18  5 2025 21:20
// Use Library for storing values
// as key-value pairs, unique to an object.
// To be used for storing buses mapped to a Synth.
// Or for other stuff.

VarHolder {
	*new { | object |
	}

	*getVar { | object, key |
		^this.getVarDict(object)[key];
	}

	*putVar { | object, key, value |
		this.getVarDict(object)[key] = value;
	}

	*removeVar { | object, key |
		var dict;
		dict = this getVarDict: object;
		dict[key] = nil;
		(dict.size == 0).if { Library.put(object, \vars, nil) };
	}

	*removeVarDict { | object | Library.put(object, \vars, nil); }

	*getVarDict { | object |
		var dict;
		dict = Library.at(object, \vars);
		dict ?? {
			dict = IdentityDictionary();
			Library.put(object, \vars, dict);
		};
		^dict;
	}
}

+ Object {
	getVar { | key | ^VarHolder.getVar(this, key) }
	putVar { | key, value | VarHolder.putVar(this, key, value) }
	removeVar { | key | VarHolder.removeVar(this, key) }
	removeVarDict { VarHolder.removeVarDict(this) }
	vars { ^VarHolder.getVarDict(this) }
}