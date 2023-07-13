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
		^this.newCopyArgs(sbg).init;
	}

	init {
		this.makeParamSpecs;
		this.makeParams;
		this.bounds_(Rect(400, 0, 700, 400))
		.hlayout(
			this.pane(0, 11),
			this.pane(12, 23)
		).name()
	}
	makeParamSpecs {
		paramSpecs = (1..24) collect: { | i |
			[("label" + i).asSymbol, \freq.asSpec]
		};
	}

	makeParams {
		params = paramSpecs collect: { | p |
			Param(this, p[0], p[1])
		}
	}

	pane { | lo, hi |
		^VLayout(*(params.copyRange(lo, hi).collect({ | p | p.gui})))
	}
}