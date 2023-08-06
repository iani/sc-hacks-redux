/* 13 Jul 2023 10:20

Model will be an instance of SoundParams
*/

Param {
	var <model, <spec, <name, <value;
	var <sensor, <sensorlo, <sensorhi;
	var <player;
	// model: a SoundParams
	*new { | model, spec, dict |
		^this.newCopyArgs(model, spec).init(dict);
	}

	init { | dict |
		name = spec.units.asSymbol;
		// spec = spec.asSpec;
		value = spec.default;
		sensorlo = spec.minval;
		sensorhi = spec.maxval;
		player = model.player;
		if (dict.isNil) {
		sensor = SensorCtl(player, name, 1, \off, sensorlo, sensorhi, \lin);
		}{
			sensor = SensorCtl(*dict[name]);
		}
	}
	player_ { | argPlayer |
		player = argPlayer;
		sensor.player = player;
	}
	gui {
		postln("param name:" + name + "value" + value + "spec" + spec);
		postln("param sensor:" + sensor + "sensorlo" + sensorlo + "sensorhi" + sensorhi);
		^HLayout(
			StaticText().minWidth_(100)
			.minWidth_(100).string_(format("%(%-%)", name, spec.minval, spec.maxval)),
			Button().maxWidth_(55).states_([["custom:"]])
			.action_({ sensor.customize; }),
			Button().maxWidth_(30) // sensor source type
			.states_([["-"]])
			.addNotifier(model, \gui, { | n |
				n.listener.states = [[sensor.ctl.asString]];
			})
			.action_({ | me | Menu(
				*['off', \x, \z, \cx, \cz, \c3].collect({ | f |
					MenuAction(f.asString, {
						me.states_([[f.asString]]);
						sensor.ctl = f.asSymbol;
					})})
			).front }),
			Button().maxWidth_(20) // sensor id
			.states_([["1"]])
			.addNotifier(model, \gui, { | n |
				n.listener.states = [[sensor.id.asString]];
			})
			.action_({ | me | Menu(
				*(1..12).collect({ | f | MenuAction(f.asString, { //c| me |
					me.states_([[f.asString]]);
					sensor.id = f;
				})})
			).front }),
			NumberBox().maxWidth_(55)
			.background_(Color.gray(0.7))
			.clipLo_(spec.clipLo)
			.clipHi_(spec.clipHi)
			.decimals_(5)
			.value_(spec.minval)
			.addNotifier(this, \value, { | n |
				n.listener.value = sensor.lo;
			})
			.action_({ | me | this.setSensorLo(me.value) }),
			NumberBox().maxWidth_(55)
			.background_(Color.gray(0.7))
			.clipLo_(spec.clipLo)
			.clipHi_(spec.clipHi)
			.decimals_(5)
			.value_(spec.maxval)
			.addNotifier(this, \value, { | n |
				n.listener.value = sensor.hi;
			})
			.action_({ | me | this.setSensorHi(me.value) }),
			NumberBox().maxWidth_(80)
			.normalColor_(Color.red(0.5))
			.clipLo_(spec.clipLo)
			.clipHi_(spec.clipHi)
			.decimals_(5)
			.value_(model valueAt: name)
			.addNotifier(model, \dict, { | n |
				n.listener.value = model valueAt: name;
			})
			.addNotifier(this, \value, { | n |
				n.listener.value = value;
				this.updateModel;
			})
			.addNotifier(model, \gui, { | n |
				n.listener.value = model.valueAt(name);
			})
			.action_({ | me |
				value = me.value;
				// model.changed(name);
			}),
			Slider().orientation_(\horizontal)
			.addNotifier(model, \gui, { | n |
				n.listener.value = spec unmap: model.valueAt(name);
			})
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

	setSensorLo { | arglo |
		sensorlo = arglo;
		sensor.lo = sensorlo;
	}

	setSensorHi { | arghi |
		sensorhi = arghi;
		sensor.hi = arghi;
	}
	// player { ^model.player }
	start { sensor.start }
	stop { sensor.stop }
	updateModel { model.setParam(name, value); }
	bufName { ^model.bufName; }
}