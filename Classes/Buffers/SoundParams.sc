/* 12 Jul 2023 23:52
pop up window created by SoundBufferGui
create slider + numbox pairs for each parameter, based on the specs of the sound ugen functions
(see: UGenFunc).

Auto-update (close and reopen) when a different UGenFunc is chosen from the menu in SoundBUffeerGui.

Save the resulting config in a file at folder ???? in sc-projects.

!: Store all current parameters for playing the synth of the current selection in a dict.
*/
//:

SoundParams {
	var model; // SfSelections;
	var paramSpecs, params, envirname;
	var <dict; // used for starting the synth.
	*new { | selection | // or selection?
		^this.newCopyArgs(selection).init;
	}

	init {
		this.makeEnvirName;
		this.makeParams;
	}

	makeEnvirName {
		model !? { envirname = model.sbgui.name };
	}

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
		this.resetParams; // fill dict with defaults from params
	}

	resetParams {
		// not yet implemented
	}

	setParam { | param, value |
		dict[param] = value;
		if (this.isPlaying) { this.sendParam2Synth(param, value); };
	}

	isPlaying { ^false } // TODO: implement this properly

	sendParam2Synth { | param, value |
		value.perform('@>', param, envirname);
	}

	// ======================= GUI ========================
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

	/*

	sendSelectionToServer {
		this.startFrame.perform('@>', \startframe, this.name);
		this.endFrame.perform('@>', \endframe, this.name);
	}
	*/
}