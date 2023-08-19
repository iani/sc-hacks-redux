/* 29 Jul 2023 15:24
Saving and loading presets as dictionaries (events), and in lists - with guis.

*/

Preset {
	classvar pfuncmenu; // cache for use by all preset guis!
	var <>presetList, <index = 0, <>code, <dict;
	var <playfunc;
	var <selectionNum;
	var <paramSpecs, <params;
	// var <ampctl;
	var <template; // subclass of SynthTemplate. creates the specs - and other customized stuff?
	//	var <player; // obtain from presetList! ???

	index_ { | i | index = i; this.changed(\index)	}
	pfuncmenu {
		^pfuncmenu ?? {
			pfuncmenu = SynthTemplate.templateNames collect: { | p | [p, { this.addPreset(p) }] };
		}
	}
	// EXPERIMENTAL
	*newWithDict { | list, index, source, dict |
		^this.newCopyArgs(list, index, source).importDict(dict)
	}

	*fromSnippet { | snippet |
		^this.fromDict(snippet.interpret).code_(snippet);
	}

	*fromPlayfunc { | playfunc |
		^this.fromDict(SynthTemplate.getTemplate(playfunc).dict);
	}

	*fromDict { | dict | // import from dict from PresetList script
		^this.newCopyArgs((player: dict[\player] ? \default), 0).importDict(dict)
		.code_(dict.pp);
	}

	*new { | list, index, source |
		^this.newCopyArgs(list, index, source).init;
	}

	init {
		this importDict: code.interpret;
	}

	importDict { | adict | // create all contents from dict
		playfunc = adict[\playfunc];
		selectionNum = adict[\selectionNum] ? 0; // possibly redundant
		dict = adict;
		this.makeParams(dict);
	}
	makeParams { | adict |
		template = SynthTemplate.getTemplate(playfunc);
		paramSpecs = template.specs;
		params = paramSpecs collect: { | p | Param(this, p, adict) };
	}

	player { ^presetList.player }

	play {
		{
			// this.player.envir.stopSynths;
			this.stop;
			postln("\n============== playing" + this.player +
				"list" + presetList.name
				+ "preset"
				+ index  + "=========\n");
			format("%.envir play: %", this.player.asCompileString, dict.asCompileString).postln.share;
			this.addNotifier(Mediator, \ended, { | n, playername, synthname |
				if (playername == this.player and: { synthname == this.player }) {
					// TODO: Check this changed vs. presetlist stopped
					this.changed(\stopped, this); // does this confuse control synths???????
				}
			});
			// 0.01.wait;  // must wait for synths to stop!!! ????????
			params do: _.start;
		}.fork; /// fork needed?
	}

	// stop { this.envir.stopSynths; }
	stop {
		format("%.envir.stopSynths", this.player.slash).share;
		// TODO: check this:
		// checkboxes of all presets must update here.
		// Only the checkbox of a preset that just stared stays on! How?????
		presetList.changed(\stopped, this);
	}

	envir { ^this.player.envir }
	// may be different if not Buffer synth!
	dur {^this.numFrames / this.sampleRate;}
	valueAt { | param | ^(dict[param.asSymbol] ? 0).asArray.first }
	startFrame { ^dict[\startframe] ? 0 }
	endFrame { ^dict[\endframe] ? 0 }
	numFrames { ^this.endFrame - this.startFrame }
	sampleRate { ^dict[\buf].buf.sampleRate }
	isPlaying { ^this.player.envir[this.player].notNil; }

	setParam { | param, value, code, ctl |
		dict[param] = [value, code, ctl];
		// postln("debug setParam. this.isPlaying? " + this.isPlaying);
		if (this.isPlaying) { this.sendParam2Synth(param, value); };
		// this.sendParam2Synth(param, value);
	}

	sendParam2Synth { | param, value |
		format("% @>.% %%", value, this.player, "\\", param).share;
	}

	gui {
		^this.window({ | w |
			w.view.layout = VLayout(this.view);
		});
	}

	view {
		var view;
		{ this.changed(\gui) }.defer(0.1);
		view = View().background_(Color.rand);
		view.layout_(
			VLayout(
				template.playView(view, this),
				this.paramView
			)
		);
		view.addNotifier(presetList, \reload, { view.remove });
		^view;
	}

	bufname { ^dict[\buf] ? '----' }

	addPreset { | p | //  create a new preset and add it to the list
		var newPreset;
		newPreset = SynthTemplate.makePreset(p.asSymbol, presetList, presetList.currentPreset.index);
		presetList.insert(newPreset, presetList.currentPreset.index);
	}

	confirmRemove { | argView |  // TODO: also remove self from list!
		{
			postln("will now remove preset" + index + "from the preset list");
			argView !? { argView.remove };
			presetList remove: this;
		}.confirm("Do you really want to remove preset no." + index + "?");
	}

	paramView { ^VLayout(*params.collect({ | p | p.gui })) }

	viewSimplePrototype {
		^View().background_(Color.rand).layout_(
			HLayout(
				TextField().string_( ("This is entry number " + index + playfunc) ),
				Button().states_([["Delete"]]).action_({ this.remove; })
			)
		)
	}

	asScript {
		this.updateDictFromParams;
		^"\n//:" + format("(%)", index) + this.player + playfunc + dict[\buf] ++ "\n" ++ dict.pp;
	}

	updateDictFromParams { params do: { | p | dict[p.name] = [p.value, p.code, p.ctl]; } }

	clean { // remove legacy keys
		dict[\ampctl] = nil;
		dict[\player] = nil;
		dict[\dur] = nil;
		dict[\selectionNum] = nil;
		dict[\paramctl] = nil;
	}

	makeCurrent {
		postln("Preset makeCurrent index:" + index);
		presetList.currentPreset = this;
	}

	templateMenu {
		^Button().states_([["+"]]).maxWidth_(15)
		.mouseDownAction_({ this.makeCurrent; })
		.menuActions(this.pfuncmenu)
	}

	scoreMenu {
		^Button().states_([["*"]]).maxWidth_(15)
		.mouseDownAction_({ this.makeCurrent; })
		.menuActions(presetList.scoremenu)
	}

}