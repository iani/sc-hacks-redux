/* 12 Jul 2023 23:52
pop up window created by SoundBufferGui
create slider + numbox pairs for each parameter, based on the specs of the sound ugen functions
(see: UGenFunc).

Auto-update (close and reopen) when a different UGenFunc is chosen from the menu in SoundBUffeerGui.

Save the resulting config in a file at folder ???? in sc-projects.

*/
//:

SoundParams {
	var model; // SfSelections;
	var paramSpecs, params;
	var <dict; // used for starting the synth.
	*new { | sbg | // or selection?
		^this.newCopyArgs(sbg ?? { SoundBufferGui.default; }).init;
	}

	init { this.makeParams; }
	makeParams {
		dict = ();
		params = (1..24) collect: { | i |
			("label" + i).asSymbol.ps;
		};
		params[0] = 'rate'.ps(0.05, 20, 1);
		params = params collect: { | p | Param(this, p) };
		params do: { | param |
			dict[param.name] = param.value;
		};
		// must review this!
		// selection updates my dict at every start or duration frames change
		// should selections store the current param separately?
		model.updateParams(this); // write current selection values to dict;
	}

	gui {
		this.bounds_(Rect(400, 0, 700, 400))
		.hlayout(
			*params.clump(12).collect({ | ps |
				VLayout(*ps.collect({ | p | p.gui}))
			})
		)
	}
	pane { |  ps |
		^VLayout(*(ps.collect({ | p | p.gui})))
	}

	// Review this?
	envir { // the environment Mediator where I am playing
		^model.name.envir;
	}

	// ????
	bufName {
		/// ^sbg.bufName;
	}

}