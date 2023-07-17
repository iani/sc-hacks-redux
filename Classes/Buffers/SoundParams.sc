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
	var <>playfunc;
	var <paramSpecs, <params;
	var <dict; // used for starting the synth.
	*new { | selection, playfunc |
		^this.newCopyArgs(selection, playfunc).init;
	}

	init {
		this.makeParams;
		this.initDict;
	}

	makeParams {
		// postln("making sound params. playfunc is:" + playfunc);
		paramSpecs = SynthTemplate.getTemplate(playfunc).specs;
		// postln("sound params found specs is:" + specs);
		params = paramSpecs.flat collect: { | p | Param(this, p) };
	}

	addParams { | argDict | // add parameters without sending to synth
		// used by selection to add buffer and frame parameters at instance creation time
		argDict keysValuesDo: { | key, value | dict[key] = value };
	}

	initDict {
		dict = ();
		// transfer values from template
		params do: { | param | dict[param.name] = param.value; };
		// transfer values from selections
		dict[\buf] = model.bufName;
		dict[\playfunc] = model.playfunc; // this may already be in template!!!
		this.setFrame(model.currentSelection);
	}

	// merge parameters from new template into existing dictionary values
	// keep exisiting values set by user.
	mergeParams2Dict {
		// Create all params in the right order from tne new template.
		// Restore the values of previously existing params in the dictionary.
		var restore;
		restore = dict;
		this.init;
		// only set those values belonging to the new playfunc's template!
		restore keysValuesDo: { | key, value |
			dict[key] !? { dict[key] = value; } // overwrite existing keys only!
		}
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
		var clumped, height;
		clumped = params.flat.clump(12);
		height = clumped.collect(_.size).maxItem * 20 + 20;
		this.bounds_(Rect(400, 0, 700, height))
		.vlayout(
			HLayout(
				CheckBox().string_("play")
				.action_({ | me |
					if (me.value) { this.play }{ this.stop }
				})
			),
			HLayout(
			*clumped.collect({ | ps |
				VLayout(*ps.collect({ | p | p.gui}))
			}))
		)
		.addNotifier(this, \close, { | n | n.listener.close })
		.name_(format("%:%", dict[\buf], dict[\playfunc]));

		{ this.changed(\dict) }.defer(0.1);
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
		// dict[buf].perform('@@', dict, event[\playfunc]);
		if (this.dur <= 0.0) {
			"Cannot play settings with duration 0".postln;
		}{
			dict[\buf].envir.play(dict);
		}
	}
	stop { // stop all synths - both sound + controls
		dict[\buf].stopSynths;
	}

	dur {
		^this.numFrames / this.sampleRate;
	}

	valueAt { | param | ^dict[param.asSymbol] }
	startFrame { ^dict[\startframe] ? 0 }
	endFrame { ^dict[\endframe] ? 0 }
	numFrames { ^this.endFrame - this.startFrame }
	sampleRate { ^dict[\buf].buf.sampleRate }

	/*
	sendFramesToServer {
		this.startFrame.perform('@>', \startframe, this.name);
		this.endFrame.perform('@>', \endframe, this.name);
	}
	*/
}