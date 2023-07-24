/* 13 Jul 2023 10:20

Model will be an instance of SoundParams
*/

Param {
	var <model, <spec, <name, <value;
	var <sensor, <sensorlo, <sensorhi;
	var <player;
	// model: a SoundParams
	*new { | model, spec |
		^this.newCopyArgs(model, spec).init;
	}

	init {
		name = spec.units.asSymbol;
		// spec = spec.asSpec;
		value = spec.default;
		sensorlo = spec.minval;
		sensorhi = spec.maxval;
		player = model.player;
		sensor = SensorCtl(player, name, 1, \off, sensorlo, sensorhi, \lin);
	}
	player_ { | argPlayer |
		player = argPlayer;
		sensor.player = player;
	}
	gui {
		^HLayout(
			StaticText().minWidth_(100)
			.minWidth_(100).string_(format("%(%-%)", name, spec.minval, spec.maxval)),
			Button().maxWidth_(30)
			.states_([["-"]])
			.action_({ | me | Menu(
				*['off', \x, \z].collect({ | f |
					MenuAction(f.asString, {
						me.states_([[f.asString]]);
						this.setSensor(f);
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
			NumberBox().maxWidth_(55)
			.background_(Color.gray(0.7))
			.clipLo_(spec.clipLo)
			.clipHi_(spec.clipHi)
			.decimals_(5)
			.value_(spec.minval)
			.action_({ | me | this.setSensorLo(me.value) }),
			NumberBox().maxWidth_(55)
			.background_(Color.gray(0.7))
			.clipLo_(spec.clipLo)
			.clipHi_(spec.clipHi)
			.decimals_(5)
			.value_(spec.maxval)
			.action_({ | me | this.setSensorHi(me.value) }),
			NumberBox().maxWidth_(80)
			.normalColor_(Color.red(0.5))
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

	setSensor { | argSensor |
		postln("setting sensor" + sensor + "to" + argSensor);
		sensor.ctl = argSensor;
	}
	setSensorLo { | arglo |
		sensorlo = arglo;
		sensor.lo = sensorlo;
	}

	setSensorHi { | arghi |
		sensorhi = arghi;
		sensor.hi = arghi;
	}
	// player { ^model.player }

	updateModel { model.setParam(name, value); }
	bufName { ^model.bufName; }
}