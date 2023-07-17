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
	var playfunc;
	var paramSpecs, params;
	var <dict; // used for starting the synth.
	*new { | selection, playfunc |
		^this.newCopyArgs(selection).init;
	}

	init {
		this.makeParams;
	}

	makeParams {
		// This is a dummy to be replaced by specs from a template
		params = (1..24) collect: { | i | ("label" + i).asSymbol.ps; };
		params[0] = 'rate'.ps(0.05, 20, 1);
		// this is the right way to create the params:
		params =
		params = params collect: { | p | Param(this, p) };
		this.initDict; // fill dict with defaults from params + model
	}

	addParams { | argDict | // add parameters without sending to synth
		// used by selection to add buffer and frame parameters at instance creation time
		argDict keysValuesDo: { | key, value | dict[key] = value };
	}

	/*
	makeParamsOLD { // placeholder.
		params = (1..24) collect: { | i |
			("label" + i).asSymbol.ps;
		};
		params[0] = 'rate'.ps(0.05, 20, 1);
		params = params collect: { | p | Param(this, p) };
		params do: { | param |
			dict[param.name] = param.value;
		};
		this.initDict; // fill dict with defaults from params
	}
	*/

	initDict {
		dict = ();
		// transfer values from template
		params do: { | param | dict[param.name] = param.value; };
		// transfer values from selections
		dict[\buf] = model.bufName;
		dict[\playbuf] = model.playfunc; // this may already be in template!!!
		this.setFrame(model.currentSelection);
	}

	setFrame { | frame | // set startframe and endframe as received from selections
		this.setParam('startframe', frame[0]);
		this.setParam('endframe', frame.sum);
	}

	setParam { | param, value |
		dict[param] = value;
		// if (this.isPlaying) { this.sendParam2Synth(param, value); };
		this.sendParam2Synth(param, value);
	}

	isPlaying { ^false } // TODO: implement this properly

	sendParam2Synth { | param, value | value.perform('@>', param, dict[\buf]); }

	// ======================= GUI ========================
	gui {
		this.bounds_(Rect(400, 0, 700, 400))
		.hlayout(
			*params.clump(12).collect({ | ps |
				VLayout(*ps.collect({ | p | p.gui}))
			})
		)
		.addNotifier(this, \close, { | n | n.listener.close })
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



	close { // stop synths and close gui
		this.stop; // stop synth
		this.changed(\close); // notify gui window to close
	}

	play {
		// equivalent:
		// dict[buf].perform('@@', dict, event[\playfunc])
		dict[\buf].envir.play(dict);
	}
	stop { // stop all synths - both sound + controls
		dict[\buf].stopSynths;
	}



	/*

	sendSelectionToServer {
		this.startFrame.perform('@>', \startframe, this.name);
		this.endFrame.perform('@>', \endframe, this.name);
	}
	*/
}