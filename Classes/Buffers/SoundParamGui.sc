/* 12 Jul 2023 23:52
pop up window created by SoundBufferGui
create slider + numbox pairs for each parameter, based on the specs of the sound ugen functions
(see: UGenFunc).

Auto-update (close and reopen) when a different UGenFunc is chosen from the menu in SoundBUffeerGui.

Save the resulting config in a file at folder ???? in sc-projects.

*/
//:

SoundParamGui {
	var sbg; // SoundBufferGui
	var paramSpecs, params;
	*new { | sbg |
		^this.newCopyArgs(sbg ?? { SoundBufferGui.default; }).init;
	}

	init {
		// this.makeParamSpecs;
		this.makeParams;
		this.bounds_(Rect(400, 0, 700, 400))
		.hlayout(
			*params.clump(12).collect({ | ps |
				VLayout(*ps.collect({ | p | p.gui}))
			})
		)
	}
	makeParams {
		params = (1..24) collect: { | i |
			("label" + i).asSymbol.ps;
		};
		params[0] = 'rate'.ps(0.05, 20, 1);
		params = params collect: { | p | Param(this, p) };
	}

	pane { |  ps |
		^VLayout(*(ps.collect({ | p | p.gui})))
	}

	envir {
		^sbg.name.envir;
	}

	bufName {
		^sbg.bufName;
	}
}