// Sat 22 Feb 2025 17:53
// Objects stored in Mediator update synth in different ways when they
// change.

+ Object {
	updateSynth { | key, synth |
		// postln("object" + this + "changed at key" + key + "skipping update of synth" + synth);
	}
}

+ SimpleNumber {
	updateSynth { | key, synth |
		// postln("Number" + this + "changed at key" + key + ". Setting control of synth" + synth);
		synth.set(key, this);
	}
}

+ Bus {
	updateSynth { | key, synth |
		// postln("Bus" + this + "changed at key" + key + ". Mapping control of synth" + synth);
		synth.map(key, this);
	}
}

+ ValueMapper {
	updateSynth { | key, synth |
		postln("Bus" + this + "changed at key" + key + ". Mapping control of synth" + synth);
	}
}