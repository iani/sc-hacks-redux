/* 22 Aug 2023 18:41
Load abd start playubg a score, and then start the preset.
*/

PplayScoreMenu : PresetViewTemplate {
	var pscoremenu; // private menu with own preset + index
	*scoresInLib { ^PathName(this.parentPath +/+ "Scores" +/+ "*.scd").pathMatch;}
	*scoreNamesInLib { ^this.scoresInLib collect: _.name; }

	view {
		^Button().states_([["*", Color.red]]).maxWidth_(15)
		.mouseDownAction_({
			postln("Debugging pplayScoreMenu mouseDown. index:" + preset.index);
			preset.makeCurrent;
		})
		.menuActions(this.pplayscoremenu)
	}

	pplayscoremenu {
		^pscoremenu ?? {
			pscoremenu = Scores.scoreNames collect: { | p | [p, {
				postln("starting with score. preset:" + preset);
				preset.startWithScore(p) }] };
		}
	}
}