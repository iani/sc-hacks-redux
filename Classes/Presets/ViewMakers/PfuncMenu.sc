/* 20 Aug 2023 19:32
Create a pfunc menu for any object;
*/

PfuncMenu : PresetViewTemplate {
	var pfuncmenu; // cache

	view {
	^Button().states_([["+"]]).maxWidth_(15)
		.mouseDownAction_({ preset.makeCurrent; })
		.menuActions(this.pfuncmenu)
	}

	pfuncmenu {
		^pfuncmenu ?? {
			pfuncmenu = SynthTemplate.templateNames collect: { | p | [p, {
				preset.presetList.addPreset(p)
			}] };
		}

	}
}