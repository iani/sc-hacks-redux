/* 22 Aug 2023 18:41
Load abd start playubg a score, and then start the preset.
*/

PplayScoreMenu : PresetViewTemplate {
	classvar pscoremenu; // cache
	*scoresInLib { ^PathName(this.parentPath +/+ "Scores" +/+ "*.scd").pathMatch;}
	*scoreNamesInLib { ^this.scoresInLib collect: _.name; }

	view {
		^Button().states_([["*", Color.red]]).maxWidth_(15)
		.mouseDownAction_({ preset.makeCurrent; })
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