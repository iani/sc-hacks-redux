/* 29 Jul 2023 15:24
Prototype for saving and loading presets as dictionaries (events).



*/

Preset {
	var <presetList, <index, <code, <dict;
	var <playfunc;
	var <selectionNum;
	var <paramSpecs, <params;
	var <ampctl;
	//	var <player; // obtain from presetList! ???


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
		// player = dict[\player];
	}
	makeParams { | adict |
		// TODO: use also PlainSynths!  Call SynthTemplate.getTemplate instead:
		paramSpecs = BufferSynths.getTemplate(playfunc).specs;
		// postln("sound params found specs is:" + specs);
		params = paramSpecs.flat collect: { | p | Param(this, p, adict) };
	}

	player { ^presetList.player }

	play {
		if (this.dur <= 0.0) { // this may be different depending on type of preset
			"Cannot play settings with duration 0".postln;
		}{
			{
				this.player.envir.stopSynths;
				format("%.envir play: %", this.player.asCompileString, dict.asCompileString).postln.share;
				this.addNotifier(Mediator, \ended, { | n, playername, synthname |
					if (playername == this.player and: { synthname == this.player }) {
						this.changed(\stopped); // does this confuse control synths???????
					}
				});
				// 0.01.wait;  // must wait for synths to stop!!! ????????
				ampctl.start;
				params do: _.start;
			}.fork; /// fork needed?
		}
	}

	// may be different if not Buffer synth!
	dur {^this.numFrames / this.sampleRate;}
	valueAt { | param | ^dict[param.asSymbol] }
	startFrame { ^dict[\startframe] ? 0 }
	endFrame { ^dict[\endframe] ? 0 }
	numFrames { ^this.endFrame - this.startFrame }
	sampleRate { ^dict[\buf].buf.sampleRate }

	view {
		^View().background_(Color.rand).layout_(
			VLayout(
				this.playView,
				this.paramView
			)
		)
	}

	playView {
		selectionNum = dict[\selectionNum];
		^HLayout(
			StaticText().maxWidth_(70).string_("selection:")
			.background_(SoundBufferGui.colors[selectionNum]),
			NumberBox().maxWidth_(40).value_(selectionNum).enabled_(false),
			CheckBox().string_("play").maxWidth_(50)
			.action_({ | me |
				if (me.value) { this.play }{ this.stop }
			})
			.addNotifier(this, \stopped, { | n |
				n.listener.value = false;
				n.listener.focus(true);
			}),
			StaticText().maxWidth_(40).string_("player:"),
			Button().maxWidth_(70)
			.states_([[this.player, Color.green(0.5)]])
			.action_({ | me | Menu(
				*PresetList.players.collect({ | f | MenuAction(f.asString, {
					me.states_([[f.asString, Color.green(0.5), Color.white]]);
					this.player = f.asSymbol.postln;
				})})
			).front }),
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
			StaticText().maxWidth_(80).string_("frame range:"),
			RangeSlider().maxWidth_(120).orientation_(\horizontal),
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
}