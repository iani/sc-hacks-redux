/*  7 Jun 2023 15:45
Simple specialized class for handling input from Minibee sensors.

~spec = [0.44, 0.56].asSpec;

~spec.unmap(0.55)

*/

Minibee {
	classvar <>sensormsg = '/minibee/data';
	classvar <>numSensors = 12; // three sets of sense-stage, connected via osc groups
	classvar <all;
	classvar <values;
	classvar <>min = 0.44;
	classvar <>max = 0.56;
	var <id = 1;
	var <busses;

	*initClass { ServerBoot add: this; }
	*doOnServerBoot { | server |
		// "Sensors do on server boot".postln;
		// workaround for a bug: make sure the server is booted:
		server doWhenBooted: { // remake busses
			this.init;
		}
	}

	*init {
		all = { | i | this.new(i + 1) } ! numSensors; // 1-12
		this.getValues;
	}

	*getValues {
		values = 0.dup(all.size * 3);
		all do: _.getBusValues;
	}

	*new { | id = 1 |
		^this.newCopyArgs(id).init;
	}

	getBusValues {
		var offset;
		offset = id - 1 * 3;
		busses do: { | b, i |
			b.get({ | val | values[offset + i] = val })
		};
	}

	init {
		this.makeBusses;
	}

	makeBusses {
		busses = [\x, \y, \z] collect: { | s |
			format("%%", s, id).asSymbol.sensorbus;
		}
	}

	*busses { ^all collect: _.busses }

	*enable { OSC addDependant: this; this.changed(\status) }

	*disable { OSC removeDependant: this; this.changed(\status) }

	*enabled { ^OSC.dependants includes: this }

	*update { | sender, cmd, msg |
		var index;
		if (cmd === sensormsg) {
			index = msg[1];
			this.changed(\values, index, all[index - 1].input(msg[2..]));
		}
	}

	input { | xyz |
		^xyz.linlin(min, max, 0.0, 1.0) do: { | val, i |
			values[id - 1 * 3 + i] = val;
			busses[i].set(val);
		}
	}

	*gui {
		this.tr_.vlayout(
			MultiSliderView()
			.thumbSize_(5)
			.size_(3 * 25)
			.addNotifier(this, \values, { | n |
				n.listener.value_(values);
			})
		)
	}
}
