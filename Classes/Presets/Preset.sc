/* 29 Jul 2023 15:24
Prototype for saving and loading presets as dictionaries (events).



*/

Preset {
	var <presetList, <index = 0, <>code, <dict;
	var <playfunc;
	var <selectionNum;
	var <paramSpecs, <params;
	// var <ampctl;
	var <template; // subclass of SynthTemplate. creates the specs - and other customized stuff?
	//	var <player; // obtain from presetList! ???

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
		template = this.getTemplate(playfunc);
		paramSpecs = template.specs;
		params = paramSpecs collect: { | p | Param(this, p, adict) };
	}

	player { ^presetList.player }

	play {
		if (this.dur <= 0.0) { // this may be different depending on type of preset
			"Cannot play settings with duration 0".postln;
		}{
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
			w.view = this.view;
		});
	}

	view {
		var view;
		{ this.changed(\gui) }.defer(0.1);
		view = View().background_(Color.rand);
		view.layout_(
			VLayout(
				this.playView(view),
				this.paramView
			)
		);
		^view;
	}
	// playcheckbox, presetnum, playfuncmenu, bufferbutton, startframe,
	// endframe, dur, previewbutton,
	playView { | view | // new version: Wed 16 Aug 2023 09:33
		var pfuncmenu, buffermenu;
		pfuncmenu = SynthTemplate.templateNames collect: { | p | [p, { this.addPreset(p) }] };
		buffermenu = Buffer.all collect: { | p | [p, { this.switchBuffer(p) }] };
		^HLayout(
			StaticText().maxWidth_(20).string_(index.asString),
			StaticText().maxWidth_(100).string_(playfunc.asString),
			CheckBox().string_("play").maxWidth_(50)
			.action_({ | me |
				if (me.value) { this.play }{ this.stop }
			})
			.addNotifier(presetList, \stopped, { | n, who |
				if (who !== this) { n.listener.value = false };
			})
			// TODO: FIX THIS!!!!!:
			.addNotifier(this.envir, this.player, { | n |
				// "Received notification from envir".postln;
				if (envir(this.player).isPlaying) {
					n.listener.value = false;
					n.listener.focus(true);
				}
			}),
			Button().maxWidth_(150).states_([[this.bufname]]).menuActions(buffermenu),
			StaticText().maxWidth_(35).string_("startf"),
			NumberBox().maxWidth_(80),
			StaticText().maxWidth_(30).string_("endf"),
			NumberBox().maxWidth_(80),
			StaticText().maxWidth_(30).string_("dur"),
			NumberBox().maxWidth_(50),
			Button().states_([["+"]]).maxWidth_(15).menuActions(pfuncmenu),
			Button().states_([["-"]]).maxWidth_(15).action_({ this confirmRemove: view })
		)
	}

	bufname { ^dict[\buf] ? '----' }

	addPreset { | p | // TODO: create a new preset and add it to the list???
		p.postln;
	}
	confirmRemove { | argView |  // TODO: also remove self from list!
		{
			postln("will now remove preset" + index + "from the preset list");
			argView !? { argView.remove }
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

	clean {
		dict[\ampctl] = nil;
		dict[\player] = nil;
		dict[\dur] = nil;
		dict[\selectionNum] = nil;
		dict[\paramctl] = nil;
	}
}