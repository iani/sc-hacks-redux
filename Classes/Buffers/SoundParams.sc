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
	var <dict; // param values for starting the synth.
	var <switch; // controls on-off audibility
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
		// set frame silently - do not zero all selections at init time:
		// this.setFrameFromSelection(model.currentSelection);
	}

	// set frame silently at init time:
	// setFrameFromSelection { | frame | // set startframe and endframe as received from selections
	// 	this.dict['startframe'] = frame[0];
	// 	this.dict['endframe'] = frame.sum;
	// }

	// Not using this????
	setFrame { | frame | // set startframe and endframe as received from selections
		this.setParam('startframe', frame[0]);
		this.setParam('endframe', frame.sum);
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


	setParam { | param, value |
		dict[param] = value;
		if (this.isPlaying) { this.sendParam2Synth(param, value); };
		// this.sendParam2Synth(param, value);
	}

	isPlaying {
		^this.player.envir[this.player].notNil;
 }

	sendParam2Synth { | param, value |
		// new version: run locally + share over Osc
		format("% @>.% %%", value, this.player, "\\", param).share;
		// Old version
		// value.perform('@>', param, dict[\buf]);
	}

	// ======================= GUI ========================
	// , 'x>', 'x<', 'z>', 'z<', 'xyz'
	gui {
		var clumped, height;
		clumped = params.flat.clump(12);
		height = clumped.collect(_.size).maxItem * 20 + 20;
		this.bounds_(Rect(400, 0, 700, height))
		.vlayout(
			this.playView,
			this.paramView(clumped)
		)
		.addNotifier(this, \close, { | n | n.listener.close })
		.name_(format("%:%", dict[\buf], dict[\playfunc]));

		{ this.changed(\dict) }.defer(0.1);
	}
	pane { |  ps |
		^VLayout(*(ps.collect({ | p | p.gui})))
	}

	playView {
		^HLayout(
				CheckBox().string_("play")
				.action_({ | me |
					if (me.value) { this.play }{ this.stop }
				})
			)
	}
	paramView { | clumped |
		^HLayout(
			*clumped.collect({ | ps |
				VLayout(*ps.collect({ | p | p.gui}))
			}))
	}
	// Review this?
	envir { // the environment Mediator where I am playing
		^this.player.envir;
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
			format("%.envir play: %", this.player.asCompileString, dict.asCompileString).share;
			// Share the action locally and via oscgroups
			// this.player.envir.play(dict); // this is the plain not-shared version
		}
	}
	stop { // stop all synths - both sound + controls
		format("%%.stopSynths", "\\", this.player).share;
		// dict[\buf].stopSynths;
	}

	dur {
		^this.numFrames / this.sampleRate;
	}

	valueAt { | param | ^dict[param.asSymbol] }
	startFrame { ^dict[\startframe] ? 0 }
	endFrame { ^dict[\endframe] ? 0 }
	numFrames { ^this.endFrame - this.startFrame }
	sampleRate { ^dict[\buf].buf.sampleRate }
	player { ^model.player }
	/*
	sendFramesToServer {
		this.startFrame.perform('@>', \startframe, this.name);
		this.endFrame.perform('@>', \endframe, this.name);
	}
	*/
}