
/* 29 Jul 2023 15:24
Saving and loading presets as dictionaries (events), and in lists - with guis.

*/

Preset {
	classvar pfuncmenu; // cache for use by all preset guis!
	var <>presetList, <index = 0, <>code, <dict;
	var <playfunc;
	var <selectionNum;
	var <paramSpecs, <params;
	var <template; // subclass of SynthTemplate. creates the specs - and other customized stuff?

	// compatibiity with score. TODO: change varname presetList to list?
	list { ^presetList }
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

	stop {
		format("%.envir.stopSynths", this.player.slash).share;
		presetList.changed(\stopped, this);
	}

	envir { ^this.player.envir }
	dur {^this.numFrames / this.sampleRate;}
	valueAt { | param | ^(dict[param.asSymbol] ? 0).asArray.first }
	startFrame { ^dict[\startframe].asArray.first ? 0 }
	endFrame { ^dict[\endframe].asArray.first ? 0 }
	numFrames { ^this.endFrame.asArray.first - this.startFrame.asArray.first }
	sampleRate { ^this.buffer.sampleRate }
	buffer { ^(dict[\buf] ? \default).asArray.first.buf }
	isPlaying { ^this.player.envir[this.player].notNil; }

	switchBuffer { | b |
		b ?? { b = dict[\buf] };
		postln("Preset switchbuffer:" + b);
		this.makeCurrent;
		this.setBuf(b);
		presetList.bufferWindow(b);
	}

	setBuf { | bufname |
		dict[\buf] = bufname;
		this.sendToSynth(\buf, bufname.bufnum);
	}

	setBufSelection { | start, end |
		this.setParam(\startframe, start);
		this.setParam(\endframe, end);
		this.changed(\frames, start, end, this.dur);
	}

	setParam { | param, value, code, ctl |
		dict[param] = [value, code, ctl];
		this.sendToSynth(param, value);
	}

	sendToSynth { | param, value |
		if (this.isPlaying) { this.sendParam2Synth(param, value); };
	}

	sendParam2Synth { | param, value |
		format("% @>.% %%", value, this.player, "\\", param).share;
	}

	gui {
		^this.window({ | w |
			w.view.layout = VLayout(this.view);
		});
	}

	view { ^PresetView(this).view }

	bufname { ^dict[\buf].asArray.first ? '----' }

	asScript {
		^"\n//:" + format("(%)", index) + this.player + playfunc + dict[\buf] ++ "\n" ++ dict.pp;
	}

	updateDictFromParams {
		params do: { | p |
			dict[p.name] = [p.value, p.code, p.ctl];
		}
	}

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

	comments {
		// ^(code ?? { ^"" }).findRegexp("^//[^\n]*");
		^(code ?? { "" }).comments;
	}
}