/* 13 Jul 2023 10:20

Model will be an instance of SoundParams
*/

Param {
	var <model, <spec, <name, <value;
	var <sensor, <sensorlo, <sensorhi;
	// model: a SoundParams
	*new { | model, spec |
		^this.newCopyArgs(model, spec).init;
	}

	init {
		name = spec.units.asSymbol;
		// spec = spec.asSpec;
		value = spec.default;
	}
	gui {
		^HLayout(
			StaticText().minWidth_(100)
			.minWidth_(100).string_(name),
			Button().maxWidth_(30)
			.states_([["-"]])
			.action_({ | me | Menu(
				*['-', \x, \y, \z, 'x>', 'x<', 'z>', 'z<', 'xyz'].collect({ | f |
					MenuAction(f.asString, {
					me.states_([[f.asString]]);
					// player = f.asSymbol;
				})})
			).front }),
			Button().maxWidth_(20)
			.states_([["0"]])
			.action_({ | me | Menu(
				*(0..12).collect({ | f | MenuAction(f.asString, { //c| me |
					me.states_([[f.asString]]);
					// player = f.asSymbol;
				})})
			).front }),
			NumberBox().maxWidth_(80)
			.clipLo_(spec.clipLo)
			.clipHi_(spec.clipHi)
			.decimals_(5)
			// .value_(model valueAt: name)
			.addNotifier(model, \dict, { | n |
				n.listener.value = model valueAt: name;
				// postln("set number value to" + (model valueAt: name));
				// this.updateModel;
			})
			.addNotifier(this, \value, { | n |
				n.listener.value = value;
				this.updateModel;
			})
			.value_(spec.default)
			.action_({ | me |
				value = me.value;
				// model.changed(name);
			}),
			Slider().orientation_(\horizontal)
			.addNotifier(model, \dict, { | n |
				n.listener.value = spec.unmap (model valueAt: name);
				// postln("set slider value to" + (spec.unmap (model valueAt: name)));
				// this.updateModel;
			})
			.addNotifier(this, \value, { | n |
				n.listener.value = spec.unmap(value);
			})
			.palette_(QPalette.light)
			.background_(Color.grey(0.65))
			// .value_(spec.unmap(model valueAt: name))
			.action_{ | me |
				value = spec.map(me.value);
				this.changed(\value);
			},
		)
	}

	player { ^model.player }

	updateModel { model.setParam(name, value); }
	bufName { ^model.bufName; }
}