/* 13 Jul 2023 10:20

*/

Param {
	var <model, <name, <spec, <value;

	*new { | model, name, spec |
		^this.newCopyArgs(model, name, spec).init;
	}

	init {
		spec = spec.asSpec;
		spec.postln;
		value = spec.default;
	}
	gui {
		^HLayout(
			StaticText().minWidth_(100)
			.minWidth_(100).string_(name),
			NumberBox().maxWidth_(80)
			.clipLo_(spec.clipLo)
			.clipHi_(spec.clipHi)
			.addNotifier(this, name, { | n |
				n.listener.value = value;
			})
			.action_{ | me |
				value = me.value;
				this.changed(name);
			},
			Slider().orientation_(\horizontal)
			.addNotifier(this, name, { | n |
				n.listener.value = spec.unmap(value);
			})
			.action_{ | me |
				value = spec.map(me.value);
				this.changed(name);
			},
		)
	}
}