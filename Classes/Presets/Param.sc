/* 13 Jul 2023 10:20



*/

Param {
	var <model, <spec, <name, <value, <code;
	// var <ctl; // handles code for kr control from sensor or other
	var <player;
	var checkbox;
	// model: a SoundParams
	*new { | model, spec, dict |
		^this.newCopyArgs(model, spec).init(dict);
	}

	init { | dict |
		// var valcode;
		name = spec.units.asSymbol;
		// valcode = dict[name] ?? { [spec.default, ""] };
		// valcode = valcode.asArray ++ [""];
		// valcode = [spec.default, ""];
		// Compatibility with previous dict format:
		#value, code = (dict[name] ?? {[spec.default, ""]}).asArray ++ [""];
		player = model.player;
	}
	player_ { | argPlayer |
		player = argPlayer;
	}
	gui {
		// postln("Param gui. model:" + model + "value" + value);
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
			.addNotifier(model, \dict, { | n |
				n.listener.value = (model valueAt: name) ? 0;
			})
			.addNotifier(this, \value, { | n |
				n.listener.value = value;
				this.updateModel;
			})
			.addNotifier(model, \gui, { | n |
				n.listener.value = model.valueAt(name) ? 0;
			})
			.action_({ | me |
				value = me.value;
			}),
			Slider().maxWidth_(300).orientation_(\horizontal)
			.addNotifier(model, \gui, { | n |
				n.listener.value = spec unmap: value;
			})
			.addNotifier(model, \dict, { | n |
				n.listener.value = spec unmap: value;
				// TODO: Send this to model!!!!!!!!!
				// this.updateModel;
			})
			.addNotifier(this, \value, { | n |
				n.listener.value = spec.unmap(value);
			})
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
	checkbox { ^checkbox ?? { this.makeCheckbox } }
	makeCheckbox {
		^checkbox = CheckBox().maxWidth_(10).action_({ | me |
			if (me.value) { this.start; } {  this.stop; }
 		})
	}
	textfield {
		^TextField().maxWidth_(300).string_(code ? "").action_({ | me |
			code = me.value;
			postln("The Ctl for" + this.name + "now contains this code:\n" ++ code);
			if (checkbox.value) {
				this.start;
			}
		})
	}
	start {
		postln("Starting param ctl for" + this.name);
	}
	stop {
		postln("Stopping param ctl for" + this.name);
	}
	updateModel { model.setParam(name, value); }
	bufName { ^model.bufName; }
}