/* 12 Jul 2023 23:52
pop up window created by SoundBufferGui
create slider + numbox pairs for each parameter, based on the specs of the sound ugen functions
(see: UGenFunc).

Auto-update (close and reopen) when a different UGenFunc is chosen from the menu in SoundBUffeerGui.

Save the resulting config in a file at folder ???? in sc-projects.

!: Store all current parameters for playing the synth of the current selection in a dict.

*/
//:

BufSoundParams {
	classvar <>players;

	var model; // SfSelections;
	var <>playfunc;
	var <selectionNum;
	var <paramSpecs, <params;
	var <dict; // param values for starting the synth.
	var <ampctl; // controls on-off audibility
	var <player;
	var <guibounds;
	*new { | selection, playfunc, selectionNum |
		^this.newCopyArgs(selection, playfunc, selectionNum).init;
	}

	init {
		players ?? { players = this.loadFromLib("playernames"); };
		player = players.first;
		ampctl = SensorCtl(player, \amp, 1, \off, 0, 1, \lin);
		this.makeParams;
		this.initDict;
	}

	makeParams { | adict |
		// postln("making sound params. playfunc is:" + playfunc);
		paramSpecs = BufferSynth.getTemplate(playfunc).specs;
		// postln("sound params found specs is:" + specs);
		params = paramSpecs.flat collect: { | p | Param(this, p, adict) };
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
		dict[\selectionNum] = selectionNum;
 		this.setFrame(model.currentSelection);
	}

	// Not using this????
	setFrame { | frame | // set startframe and endframe as received from selections
		this.setParam('startframe', frame[0]);
		this.setParam('endframe', frame.sum);
	}

	setPlayfunc { | argPlayfunc |
		// postln("guibounds before close" + guibounds);
		this.close; // stop synth, close gui;
		// postln("guibounds after close" + guibounds);
		playfunc = argPlayfunc;
		this.setParam(\playfunc, argPlayfunc);
		// Experimental: merge parameters from new playfunc template!
		this.makeParams;
		this.mergeParams2Dict; // transfer previously set param values to new dict
		// { this.gui; }.defer(0.5);
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

	isPlaying { ^this.player.envir[this.player].notNil; }

	sendParam2Synth { | param, value |
		// new version: run locally + share over Osc
		format("% @>.% %%", value, this.player, "\\", param).share;
		// Old version
		// value.perform('@>', param, dict[\buf]);
	}

	// ======================= GUI ========================
	// , 'x>', 'x<', 'z>', 'z<', 'xyz'
	gui {
		var clumped, height, prevpos, left = 400, bottom = 0, bounds;
		clumped = params.flat.clump(12);
		height = clumped.collect(_.size).maxItem * 20 + 20;
		prevpos = this.getBounds;
		if (prevpos.notNil) {
			left = prevpos.left;
			bottom = prevpos.bottom;
		};
		this.bounds_(Rect(left, bottom, 700, 10 /* height */));
		this.vlayout(
			this.playView,
			this.paramView(clumped)
		).name_(this.header);
		{ this.changed(\gui) }.defer(0.1);
	}
	header { ^format("% -- % [%] : %", this.player, dict[\buf], selectionNum, dict[\playfunc]) }
	pane { |  ps |
		^VLayout(*(ps.collect({ | p | p.gui})))
	}

	playView {
		^HLayout(
			StaticText().maxWidth_(70).string_("selection:")
			.background_(SoundBufferGui.colors[selectionNum]),
			NumberBox().maxWidth_(40).value_(selectionNum).enabled_(false),
			CheckBox().string_("play").maxWidth_(50)
			.action_({ | me |
				if (me.value) { this.play }{ this.stop }
			})
			.addNotifier(this, \stopped, { | n |
				// this.player; // REVIEW THIS??????
				n.listener.value = false;
				n.listener.focus(true);
			}),
			StaticText().maxWidth_(40).string_("player:"),
			Button().maxWidth_(70)
			.states_([[player, Color.green(0.5)]])
			.action_({ | me | Menu(
				*players.collect({ | f | MenuAction(f.asString, {
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

	player_ { | argPlayer |
		argPlayer = argPlayer.asSymbol;
		if (player != argPlayer) { this.stop };
		player = argPlayer;
		ampctl.player = player;
		params do: { | p | p.player = player };
		this.changed(\player);
	}

	setSensor { | argId = 1 |
		// sensor = argId;
		// if (controlType.notNil and: { this.isPl })
	}

	setControl { | controlType |

	}
	paramView { | clumped |
		^HLayout(
			*clumped.collect({ | ps |
				VLayout(*ps.collect({ | p | p.gui}))
			}))
	}
	// Review this?
	envir {^this.player.envir;}  // the environment Mediator where I am playing
	bufName { ^model.bufName; }

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
	stop { // stop all synths - both sound + controls
		// ampctl.stop; // add this because stopSynths does not work
		// params do: _.stop; // add this because stopSynths does not work
		// TODO: this needs debugging: not all synths are stopped by stopSynths!
		// format("nil +>.% %", this.player, this.player.slash).share;
		// format("%%.stopSynths", "\\", this.player).share;
		// dict[\buf].stopSynths;
		// postln("Debugging BufSoundParams stop. Player is:" + this.player);
		// postln("The mediator for this synth is:" + this.player.envir);
		// postln("Will now stop all synths in this mediator. The command is:");
		// postln("Debug BufSoundParams stop. Synth Report:");
		// this.envir.synthReport;
		format("%.envir.stopSynths", this.player.slash).share;
		// { postln("Synth report after the stop"); this.envir.synthReport; } defer: 0.5;
		this.changed(\stopped); // notify even when player has changed!
	}

	dur {
		^this.numFrames / this.sampleRate;
	}

	valueAt { | param | ^dict[param.asSymbol] }
	startFrame { ^dict[\startframe] ? 0 }
	endFrame { ^dict[\endframe] ? 0 }
	numFrames { ^this.endFrame - this.startFrame }
	sampleRate { ^dict[\buf].buf.sampleRate }
	// player { ^model.player }
	soundfileview { ^model.soundfileview }
	/*
	sendFramesToServer {
		this.startFrame.perform('@>', \startframe, this.name);
		this.endFrame.perform('@>', \endframe, this.name);
	}
	*/
	// ====================== Saving and loading ===================
	asDict {
		dict[\ampctl] = ampctl.saveParams;
		dict[\paramctl] = this.paramCtl;
		dict[\player] = player;
		^dict;
	}

	paramCtl {
		var paramctl;
		paramctl = ();
		params do: { | p |
			paramctl[p.name] = p.saveParams;
		};
		^paramctl;
	}

	importDict { | adict | // create all contents from dict
		playfunc = adict[\playfunc];
		selectionNum = adict[\selectionNum]; // possibly redundant
		this.makeParams(adict[\paramctl]);
		dict = adict;
		ampctl = SensorCtl(*dict[\ampctl]);
		player = dict[\player];
	}

	info {
		^dict[\buf] ++ ":" ++ playfunc ++ [dict[\startframe], dict[\endframe]];
	}
}