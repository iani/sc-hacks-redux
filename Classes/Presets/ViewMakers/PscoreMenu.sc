/* 20 Aug 2023 19:51

*/

PscoreMenu : PresetViewTemplate {
	classvar pscoremenu; // cache
	classvar oscdatamenu;
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

	*scores1view {
		^Button().states_([["scores1"]]) // .maxWidth_(100)
		.menuActions(this.oscdatamenu1)
	}

		*scores2view {
		^Button().states_([["scores2"]]) // .maxWidth_(100)
		.menuActions(this.oscdatamenu2)
	}
		*scores3view {
		^Button().states_([["scores3"]]) // .maxWidth_(100)
		.menuActions(this.oscdatamenu3)
	}

	*oscdatamenu1 {
		// ([["Select score file from disc", { OscData.fromPathDialog }]]
		// ++ Scores.scores.collect({ | p | [p.name, { OscData.fromPath(p).gui }] })).postln;

		^[["Select score file from disc", { OscData.fromPathDialog }]]
		++ Scores.scores1.collect({ | p | [p.name, { OscData.fromPath(p).gui }] })
	}

	*oscdatamenu2 {
		// ([["Select score file from disc", { OscData.fromPathDialog }]]
		// ++ Scores.scores.collect({ | p | [p.name, { OscData.fromPath(p).gui }] })).postln;

		^[["Select score file from disc", { OscData.fromPathDialog }]]
		++ Scores.scores2.collect({ | p | [p.name, { OscData.fromPath(p).gui }] })
	}

	*oscdatamenu3 {
		// ([["Select score file from disc", { OscData.fromPathDialog }]]
		// ++ Scores.scores.collect({ | p | [p.name, { OscData.fromPath(p).gui }] })).postln;

		^[["Select score file from disc", { OscData.fromPathDialog }]]
		++ Scores.scores3.collect({ | p | [p.name, { OscData.fromPath(p).gui }] })
	}

}