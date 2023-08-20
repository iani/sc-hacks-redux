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
	var <list, >index = 0, <name, <path, <score;

	index { ^index ?? { index = 0 } }

	*new { | list, index = 0, name |
		^this.newCopyArgs(list, index, name).init;
	}

	presetList_ { | argList | list = argList }

	init { this.readScore }

	readScore {
		path = this.makePath;
		postln("ScorePlayer:readScore. name:" + name);
		postln("Path:" + path);
		postln("File exists?" + (File exists: path));
		if (File.exists(path).not) {
			postln("Could not find path:\n" ++ path);
			"Using default score path instead".postln;
			path = this.defaultPath;
		};
		// postln("DOUBLECHECK PATH\n" ++ path);
		// postln("DOUBLECHECK File exists?" + (File exists: path))
		score = OscData.fromPath(path);
	}

	makePath { | argName |
		// postln("Testing ScorePlayer:makePath");
		^Scores.scorePath(argName ? name).postln;
	}

	defaultPath { ^this.makePath("default") }
	gui { score.gui }
	view {
		var view;
		view = View();
		^view.background_(Color(*Array.rand(3, 0.7, 1.0))).layout_(
			VLayout(this.playView(view))
		)
	}

	playView { | view |
		^HLayout(
			StaticText().string_(
			"Score:" + name ++ "."
			+ score.unparsedEntries.size + "snippets. Duration:"
			+ score.timeline.totalDuration + "sec."
			),
			CheckBox().maxWidth_(60).string_("trigger")
			.action_({ | me |
				if (me.value) {
					postln("Activated triggering by" + list.player);
					score addTrigger: list.player;
				}{
					score removeTrigger:  list.player;
					"Trigger switched off!".postln;
				}
			}),
			Button().maxWidth_(60).states_([["gui"]])
			.action_({ score.gui }),
			Button().maxWidth_(60).states_([["reset"]])
			.action_({ score.makeStreamEvent }),
			PfuncMenu(this).view,
			PscoreMenu(this).view,
			PdeleteButton(this, view).view
		);
	}

	scoreMenu {
		^Button().states_([["*"]]).maxWidth_(15)
		.mouseDownAction_({ this.makeCurrent; })
		.menuActions(list.scoremenu)
	}

	paramView {
		^StaticText().string_("this is another test");
	}

	makeCurrent {
		postln("Score makeCurrent index:" + index);
		list.currentPreset = this;
	}
}