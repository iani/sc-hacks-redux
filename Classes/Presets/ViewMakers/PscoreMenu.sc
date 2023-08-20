/* 20 Aug 2023 19:51

*/

PscoreMenu : PresetViewTemplate {
	classvar pscoremenu; // cache
	*scoresInLib { ^PathName(this.parentPath +/+ "Scores" +/+ "*.scd").pathMatch;}
	*scoreNamesInLib { ^this.scoresInLib collect: _.name; }

	view {
		^Button().states_([["*"]]).maxWidth_(15)
		.mouseDownAction_({ preset.makeCurrent; })
		.menuActions(this.pscoremenu)
	}

	pscoremenu {
		^pscoremenu ?? {
			pscoremenu = Scores.scoreNames collect: { | p | [p, { preset.list.addScore(p) }] };
		}
	}
}