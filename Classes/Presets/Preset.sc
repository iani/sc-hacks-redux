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
}