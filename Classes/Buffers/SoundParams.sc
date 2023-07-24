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
	classvar <>players;

	var model; // SfSelections;
	var <>playfunc;
	var <paramSpecs, <params;
	var <dict; // param values for starting the synth.
	var <switch; // controls on-off audibility
	var <player;
	*new { | selection, playfunc |
		^this.newCopyArgs(selection, playfunc).init;
	}

	init {
		players = this.loadFromLib("playernames");
		player = players.first;
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
		.addNotifier(this.soundfileview, \buffer, { | n |
			// must stop because sf gui controls range of different buffer
			this.stop;
			"closing because of Buffer".postln;
			n.listener.close;
			// n.listener.name = this.header;
		})
		.addNotifier(this, \player, { | n | n.listener.name = this.header; })
		.name_(this.header);

		{ this.changed(\dict) }.defer(0.1);
	}
	header { ^format("% -- % : %", this.player, dict[\buf], dict[\playfunc]) }
	pane { |  ps |
		^VLayout(*(ps.collect({ | p | p.gui})))
	}

	playView {
		^HLayout(
			CheckBox().string_("play")
			.action_({ | me |
				if (me.value) { this.play }{ this.stop }
			})
			.addNotifier(this, \stopped, { | n |
				n.listener.value = false;
				n.listener.focus(true);
			}),
			StaticText().maxWidth_(50).string_("sensor"),
			Button().maxWidth_(20)
			.states_([["1"]])
			.action_({ | me | Menu(
				*(1..12).collect({ | f | MenuAction(f, {
					me.states_([[f.asString]]);
					switch.sensorNum_(f);
				})})
			).front }),
			StaticText().maxWidth_(70).string_("on-off ctl:"),
			Button().maxWidth_(30)
			.states_([["off"]])
			.action_({ | me | Menu(
				*['off', 'lx', 'lz', 'gx', 'gz', \xyz].collect({ | f |
					MenuAction(f.asString, {
						me.states_([[f.asString]]);
						switch.ctl_(f);
					})})
			).front }),
			StaticText().maxWidth_(40).string_("player:"),
			Button().maxWidth_(70)
			.states_([[player, Color.green(0.5)]])
			.action_({ | me | Menu(
				*players.collect({ | f | MenuAction(f.asString, {
					me.states_([[f.asString, Color.green(0.5), Color.white]]);
					if (player != f.asSymbol) { this.stop };
					player = f.asSymbol;
					this.changed(\player);
				})})
			).front }),
			Button().maxWidth_(10).states_([["x"]])
			.action_({ CmdPeriod.run }),
			Button().maxWidth_(10).states_([["x", Color.yellow, Color.red]])
			.action_({ "CmdPeriod.run".share })

		)
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
			this.addNotifier(Mediator, \ended, { | n, playername, synthname |
				if (playername == this.player and: { synthname == this.player }) {
					this.changed(\stopped);
				}
			});
			// Share the action locally and via oscgroups
			// this.player.envir.play(dict); // this is the plain not-shared version
		}
	}
	stop { // stop all synths - both sound + controls
		format("%%.stopSynths", "\\", this.player).share;
		this.changed(\stopped); // notify even when player has changed!
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
	// player { ^model.player }
	soundfileview { ^model.soundfileview }
	/*
	sendFramesToServer {
		this.startFrame.perform('@>', \startframe, this.name);
		this.endFrame.perform('@>', \endframe, this.name);
	}
	*/
}