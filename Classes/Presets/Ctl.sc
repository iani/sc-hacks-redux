/* 17 Aug 2023 16:01

THIS WILL BE MERGED INTO PARAM!

Create kr functions for playing into busses that are control inputs to Synths created by Preset.

(Previously SensorCtl.)

model is a Param.

*/

Ctl {
	var <model, <default, <code, checkbox;

	*new { | model, default, code |
		^this.newCopyArgs(model, default, code);
	}

	checkbox { ^checkbox ?? { this.makeCheckbox } }
	makeCheckbox {
		^checkbox = CheckBox().maxWidth_(10).action_({ | me |
			if (me.value) {
				this.start;
			}{  this.stop;
			}
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
		postln("Starting ctl for" + this.name);
	}
	stop {
		postln("Stopping ctl for" + this.name);
	}

	name { ^model.name }
}