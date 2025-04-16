/* 13 Jul 2023 10:20

*/

Param {
	// model: a SoundParams
	var <model, <spec, <name, <value, <code;
	// var <player;
	var <ctl = \off;

	saveParams {
		^[name, value, code, ctl];
	}
	*new { | model, spec, dict |
		^this.newCopyArgs(model, spec).init(dict);
	}

	init { | dict |
		name = spec.units.asSymbol;
		#value, code, ctl = ParamCode(name, *dict[name]).vcc;
	}

	player { ^model.player }
	gui {
		^HLayout(
			this.label,
			this.checkbox,
			this.textfield,
			NumberBox().maxWidth_(80)
			.normalColor_(Color.red(0.5))
			.clipLo_(spec.clipLo)
			.clipHi_(spec.clipHi)
			.decimals_(5)
			.value_(value)
			.addNotifier(model, \dict, { | n | n.listener.value = (model valueAt: name) ? 0; })
			.addNotifier(this, \value, { | n |
				n.listener.value = value;
				this.updateModel; // TODO: Check this!
			})
			.keyDownAction_({ | me, char, mod, key ... args | // enter key ...
				me.defaultKeyDownAction(me, char, mod, key, *args);
				if (key == 13) {
					value = me.value;
					this.changed(\value);
				};
			})
			.addNotifier(model, \gui, { | n |
					n.listener.value = model.valueAt(name) ? 0;
			})
			.action_({ | me | value = me.value; this.changed(\value); }),
			Slider().maxWidth_(300).orientation_(\horizontal)
			.addNotifier(model, \gui, { | n |
					n.listener.value = spec unmap: (model.valueAt(name) ? 0);
			})
			.addNotifier(model, \dict, { | n | n.listener.value = spec unmap: value; })
			.addNotifier(this, \value, { | n | n.listener.value = spec unmap: value; })
			.palette_(QPalette.light)
			.background_(Color.grey(0.65))
			.action_{ | me |
				value = spec.map(me.value);
				this.changed(\value);
			}
		)
	}

	label {
		^StaticText().minWidth_(100)
		.minWidth_(100).string_(format("%(%-%)", name, spec.minval, spec.maxval))
	}
	// checkbox { ^checkbox ?? { this.makeCheckbox } }
	checkbox {
		^CheckBox().maxWidth_(10).action_({ | me |
			if (me.value) { ctl = \on; this.start; } { ctl = \off; this.stop; };
			this.updateModel;
 		}).value_(if (ctl == \on) { true } { false })
	}
	textfield {
		^TextField().maxWidth_(300).string_(code ? "").action_({ | me |
			code = me.value;
			postln("player: " + model.player + "param:" + name + "code:" + code);
			this.updateModel;
			if (this.isOn) { this.start; }
		})
	}

	isOn { ^ctl == \on and: { code.size > 0 } }
	start {
		if (this.isOn) { // check when starting from Preset
			format("{%}@>.% %", code, this.player, name.slash).postln.share; // TODO: share code ...
		}
	}
	stop {
		postln("Stopping param ctl for" + this.name);
		// TODO: share code ...
		format("nil @>.% %", this.player, name.slash).postln.share;
	}
	updateModel { model.setParam(name, value, code, ctl); }
	bufName { ^model.bufName; }
}