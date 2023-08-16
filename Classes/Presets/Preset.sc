/* 29 Jul 2023 15:24
Prototype for saving and loading presets as dictionaries (events).



*/

Preset {
	var <presetList, <index = 0, <>code, <dict;
	var <playfunc;
	var <selectionNum;
	var <paramSpecs, <params;
	var <ampctl;
	var <template; // subclass of SynthTemplate. creates the specs
	//	var <player; // obtain from presetList! ???

	// EXPERIMENTAL
	*newWithDict { | list, index, source, dict |
		^this.newCopyArgs(list, index, source).importDict(dict)
	}

	*fromSnippet { | snippet |
		^this.fromDict(snippet.interpret).code_(snippet);
	}

	*fromDict { | dict |
		^this.newCopyArgs((player: dict[\player] ? \default), 0).importDict(dict);
	}

	*new { | list, index, source |
		^this.newCopyArgs(list, index, source).init;
	}

	init {
		this importDict: code.interpret;
	}

	importDict { | adict | // create all contents from dict
		playfunc = adict[\playfunc];
		selectionNum = adict[\selectionNum]; // possibly redundant
		this.makeParams(adict[\paramctl]);
		dict = adict;
		ampctl = SensorCtl(*dict[\ampctl]);
	}
	makeParams { | adict |
		// TODO: use also PlainSynths!  Call SynthTemplate.getTemplate instead:
		// paramSpecs = BufferSynths.getTemplate(playfunc).specs;
		template = BufferSynths.getTemplate(playfunc);
		paramSpecs = template.specs;
		// postln("sound params found specs is:" + specs);
		params = paramSpecs.flat collect: { | p | Param(this, p, adict) };
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
				ampctl.start;
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
	valueAt { | param | ^dict[param.asSymbol] }
	startFrame { ^dict[\startframe] ? 0 }
	endFrame { ^dict[\endframe] ? 0 }
	numFrames { ^this.endFrame - this.startFrame }
	sampleRate { ^dict[\buf].buf.sampleRate }
	isPlaying { ^this.player.envir[this.player].notNil; }

	setParam { | param, value |
		dict[param] = value;
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
				this.playView2(view),
				this.paramView
			)
		);
		^view;
	}
	// playcheckbox, presetnum, playfuncmenu, bufferbutton, startframe,
	// endframe, dur, previewbutton,
	//
	//
	playView2 { | view | // new version: Wed 16 Aug 2023 09:33
		selectionNum = dict[\selectionNum] ? 0;
		^HLayout(
			Button().states_([["remove (test)"]]).action_({ this confirmRemove: view }),
			CheckBox().string_("play").maxWidth_(50)
			.action_({ | me |
				if (me.value) { this.play }{ this.stop }
			})
			.addNotifier(presetList, \stopped, { | n, who |
				// postln("checking playView checkbox stopped. who?" + who +
				// 	"is it me?"  ++ (who === this);
				// );
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
			StaticText().maxWidth_(20).string_(index.asString),
			StaticText().maxWidth_(80).string_(playfunc.asString),
			StaticText().maxWidth_(60).string_("selection:")
			.background_(SoundBufferGui.colors[selectionNum]),
			NumberBox().maxWidth_(25).value_(selectionNum).enabled_(false),
		)
	}

	confirmRemove { | argView |  // TODO: also remove self from list!
		{
			postln("will now remove preset" + index + "from the preset list");
			argView !? { argView.remove }
		}.confirm("Do you really want to remove preset no." + index + "?");
	}

	playView {
		selectionNum = dict[\selectionNum] ? 0;
		^HLayout(
			StaticText().maxWidth_(20).string_(index.asString),
			StaticText().maxWidth_(80).string_(playfunc.asString),
			StaticText().maxWidth_(60).string_("selection:")
			.background_(SoundBufferGui.colors[selectionNum]),
			NumberBox().maxWidth_(25).value_(selectionNum).enabled_(false),
			CheckBox().string_("play").maxWidth_(50)
			.action_({ | me |
				if (me.value) { this.play }{ this.stop }
			})
			.addNotifier(presetList, \stopped, { | n, who |
				// postln("checking playView checkbox stopped. who?" + who +
				// 	"is it me?"  ++ (who === this);
				// );
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
			// Button().maxWidth_(70)
			// .states_([[this.player, Color.green(0.5)]])
			// .action_({ | me | Menu(
			// 	*PresetList.players.collect({ | f | MenuAction(f.asString, {
			// 		me.states_([[f.asString, Color.green(0.5), Color.white]]);
			// 		this.player = f.asSymbol.postln;
			// 	})})
			// ).front }),
			Button().maxWidth_(55).states_([["amp ctl:"]])
			.action_({ ampctl.customize; }),
			Button().maxWidth_(30)
			.states_([["off"]])
			.addNotifier(this, \gui, { | n |
				n.listener.states_([[ampctl.ctl.asString]])
			})
			.action_({ | me | Menu(
				*['off', 'xyz', 'lx', 'lz', 'cx', 'cz', 'c3'].collect({ | f |
					MenuAction(f.asString, {
						me.states_([[f.asString]]);
						ampctl.ctl_(f);
					})})
			).front }),
			Button().maxWidth_(20)
			.states_([["1"]])
			.addNotifier(this, \gui, { | n |
				n.listener.states_([[ampctl.id.asString]])
			})
			.action_({ | me | Menu(
				*(1..12).collect({ | f | MenuAction(f, {
					me.states_([[f.asString]]);
					ampctl.id_(f);
				})})
			).front }),
			StaticText().maxWidth_(60).string_("frames:"),
			RangeSlider().maxWidth_(100).orientation_(\horizontal),
			Button().maxWidth_(10).states_([["x"]])
			.action_({ CmdPeriod.run }),
			Button().maxWidth_(10).states_([["x", Color.yellow, Color.red]])
			.action_({ "CmdPeriod.run".share })
		)
	}

	paramView {
		^VLayout(*params.collect({ | p | p.gui }))
	}


	viewSimplePrototype {
		^View().background_(Color.rand).layout_(
			HLayout(
				TextField().string_( ("This is entry number " + index + playfunc) ),
				Button().states_([["Delete"]]).action_({ this.remove; })
			)
		)
	}

	ampCtlView { // to be replaced!
		var ampctl;
		^[
		Button().maxWidth_(55).states_([["amp ctl:"]])
		.action_({ ampctl.customize; }),
		Button().maxWidth_(30)
		.states_([["off"]])
		.addNotifier(this, \gui, { | n |
			n.listener.states_([[ampctl.ctl.asString]])
		})
		.action_({ | me | Menu(
			*['off', 'xyz', 'lx', 'lz', 'cx', 'cz', 'c3'].collect({ | f |
				MenuAction(f.asString, {
					me.states_([[f.asString]]);
					ampctl.ctl_(f);
				})})
		).front }),
		Button().maxWidth_(20)
		.states_([["1"]])
		.addNotifier(this, \gui, { | n |
			n.listener.states_([[ampctl.id.asString]])
		})
		.action_({ | me | Menu(
			*(1..12).collect({ | f | MenuAction(f, {
				me.states_([[f.asString]]);
				ampctl.id_(f);
			})})
		).front })
		]
	}
}