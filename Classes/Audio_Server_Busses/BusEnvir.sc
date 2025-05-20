//:map control inputs of a synth made by a function to busses,
// play synthfuncs into these buses by name.
//
// Function:play arguments:
// target, outbus: 0, fadeTime: 0.02, addAction: 'addToHead', args

BusEnvir {
	var <synthFunc, <target, <outbus, <fadeTime, <addAction, <args;
	var <synthDef, <controlNames;
	var <synth, <buses, <controlSynths;

	*new { | synthFunc, target, outbus = 0, fadeTime = 0.02, addAction = \addToHead, args |
		^this.newCopyArgs(synthFunc, target, outbus, fadeTime, addAction, args).init;
	}

	init {
		fork { // fork to permit sync making buses and synth
			this.makeControls;
			this.makeBuses;
			this.makeControlSynths;
			this.makeSynth;
		}
	}

	makeControls {
		synthDef = synthFunc.asSynthDef;
		controlNames = synthDef.allControlNames;
		args.keysValuesDo { | key, value |
			var c;
			c = controlNames detect: { | cn | cn.name === key; };
			c.isNil.if {
				postln("Error: No control for arg named" + key);
			}{
				c.defaultValue = value;
			}
		}
	}

	makeBuses { // rusn inside a fork to permit sync
		buses = IdentityDictionary();
		controlNames do: { | c |
			var b;
			b = Bus.control;
			Server.default.sync;
			b.set(c.defaultValue);
			buses[c.name] = b;
		}
	}

	makeControlSynths { controlSynths = IdentityDictionary() }

	makeSynth {
		Server.default.sync;
		synth = synthDef.play(target, args, addAction);
		synth.register;
		synth.onStart({
			// "Synth started. Mapping controls".postln;
			buses keysValuesDo: { | key, value |
				synth.map(key, value.index);
			}
		});
	}

	// play a function into a bus
	// better synonym
	map { | key, func |
		this.addctl(key, func);
	}

	addctl { | key, func |
		var prev;
		buses[key] ?? {
			^postln("Error: synth" + synth + "has no control named" + key);
		};
		prev = controlSynths[key];
		prev.isPlaying.if { prev.release };
		controlSynths[key] = buses[key].ff(func);
		// remap in case map was unsed throug ha set comman
		synth.map(key, buses[key].index);
	}

	remap { | key | // reconnect to an existing control synth
		var ctl;
		ctl = controlSynths[key];
		if (ctl.isPlaying and: { synth.isPlaying }) { synth.map(key, ctl) }
	}

	unmap { | key |
		var ctl;
		ctl = controlSynths[key];
		if (ctl.isPlaying) {
			ctl.release;
			controlSynths[key] = nil;
		}
	}
	// set a bus to a value
	set { | ... args |
		synth.isPlaying.if { synth.set(*args) }
	}

	free { synth.free }
	release { | dur |  synth.release(dur) }
}