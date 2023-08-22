/* 14 Aug 2023 22:16

Can be part of a PresetList, just as a Preset.
Loads an OscData code from a snippet list, and plays
it either with a routine or by stepping through its snippets
triggered by an osc message.

a = ScorePlayer(nil, nil, "testscore");
a.score.gui;

ScorePlayer(nil, nil, "testscore-negative");
*/

ScorePlayer {
	var <>presetList, >index = 0, <name, <path, score, <>player;

	index { ^index ?? { index = 0 } }

	*new { | list, index = 0, name |
		^this.newCopyArgs(list, index, name).init;
	}

	edit { score.edit }
	asScript {
		^"\n//:" + format("(%)", index) + this.player ++ "\n" ++ name.asString.asCompileString;
	}

	//	preset
	// presetList_ { | argList | list = argList }

	init { this.prepare }

	start { this.score.start; }
	prepare { // delay reading score until displaying.
		path = this.makePath;
		postln("ScorePlayer:readScore. name:" + name);
		postln("Path:" + path);
		postln("File exists?" + (File exists: path));
		if (File.exists(path).not) {
			postln("Could not find path:\n" ++ path);
			"Using default score path instead".postln;
			path = this.defaultPath;
		};
		// this.score; // doublechecking maybe needed here
		// Mon 21 Aug 2023 16:41 score adding worked without loading here
		// postln("DOUBLECHECK PATH\n" ++ path);
		// postln("DOUBLECHECK File exists?" + (File exists: path))

		player = presetList.player;
	}

	makePath { | argName |
		// postln("Testing ScorePlayer:makePath");
		^Scores.scorePath(argName ? name).postln;
	}

	defaultPath { ^this.makePath("default") }

	score {
		if (score.isNil) {
			score = OscData.fromPath(path); // score notifies when load is complete
			this.notifyLoaded;
		}{ // if score already loaded, then notify that load is already complete:
			this.notifyLoaded;
		};
		^score;
	}

	notifyLoaded { this.changed(\scoreLoaded); }

	gui { this.score.gui } // read score only when needed.
	view {
		var view;
		this.score; // make score now at the latest;
		view = View().maxHeight_(150);
		^view.background_(Color(*Array.rand(3, 0.7, 1.0))).layout_(
			VLayout(this.playView(view))
		)
	}

	playView { | view |
		^VLayout(
			this.mainView(view),
			this.commentView
		);
	}

	mainView { | view |
		^HLayout(
				StaticText().string_(
					name.asString + this.score.unparsedEntries.size + "entries"
					+ this.score.timeline.totalDuration.round(0.001) + "sec."
				),
				CheckBox().maxWidth_(60).string_("trigger")
				.action_({ | me |
					if (me.value) {
						postln("Activated triggering by" + presetList.player);
						this.score addTrigger: presetList.player;
					}{
						this.score removeTrigger:  presetList.player;
						"Trigger switched off!".postln;
					}
				}),
				Button().maxWidth_(60).states_([["gui"]]).action_({ score.gui }),
				Button().maxWidth_(60).states_([["reset"]]).action_({ score.makeStreamEvent }),
				Button().maxWidth_(60).states_([["edit"]]).action_({ score.edit }),
				PfuncMenu(this).view,
				PscoreMenu(this).view,
				PdeleteButton(this, view).view
			)
	}

	commentView {
		^TextView().maxHeight_(50).string_(score.header);
	}
	scoreMenu {
		^Button().states_([["*"]]).maxWidth_(15)
		.mouseDownAction_({ this.makeCurrent; })
		.menuActions(presetList.scoremenu)
	}

	paramView {
		^StaticText().string_("ScorePlayer: paramView - this is another test");
	}

	makeCurrent {
		postln("Score makeCurrent index:" + index);
		presetList.currentPreset = this;
	}
}