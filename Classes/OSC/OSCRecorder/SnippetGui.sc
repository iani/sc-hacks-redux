/* 28 Oct 2022 09:35

*/

SnippetGui {
	var <source, <snippets, <snippetPositions;
	var <followProjectGui = true;
	var <snippetindex = 0, sourcePath, isEdited = false;
	var <>repeats = inf;
	// var <editedSource;

	*read { | path | ^this.new.read(path); }
	*withScript { | source | // for playing scripts remotely...
		// TODO: develop and test code to do this remotely.
		^this.newCopyArgs(source);
	}
	init {} // enable fromLib;
	readAndUpdate { | path |
		path ?? { path = sourcePath };
		if (path.isFolder) { ^this };
		this.read(path);
		this.updateSnippets;
	}

	updateSnippets {
		this.makeSnippets;
		this.changed(\snippets);
		this.changed(\isEdited, isEdited = false);
	}

	read { | path |
		source = File.readAllString(path);
		sourcePath = path;
		// postln("source now is:" + source);
	}

	*gui { | path |
		^this.fromLib(\default).gui;
	}

	gui {
		var window;
		window = this.br_(600, 400).vlayout(
			HLayout(
				Button().maxWidth_(50).states_([["play"]])
				.action_({
					this.playScript;
				}),
				NumberBox().minWidth_(50).maxWidth_(55)
				.clipLo_(0).maxDecimals_(0)
				.action_({ | me |
					repeats = me.value.round(1).asInteger;
					if (repeats == 0) { repeats = inf };
				}),
				Button().maxWidth_(50).states_([["stop"]])
				.action_({
					this.stopScript;
				}),
				Button().states_([["eval snippet globally"]])
				.action_({ | me |
					snippets[snippetindex].interpretAndShare;
				}),
				Button().states_([["eval snippet locally"]])
				.action_({ | me |
					// OscGroups.disableCodeForwarding;
					snippets[snippetindex].interpret;
					// OscGroups.enableCodeForwarding;
				}),
				Button().maxWidth_(50).states_([["Cmd-."]])
				.action_({
					OscGroups.disableCmdPeriod;
					CmdPeriod.run;
					OscGroups.enableCmdPeriod;
				}),
				Button().maxWidth_(50).states_([["Cmd-.*", Color.red]])
				.action_({ CmdPeriod.run; }),
				Button().maxWidth_(50).states_([["open"]])
				.action_({
					this.openScript.postln;
				}),
				Button().maxWidth_(50).states_([["save", Color.black], ["save", Color.red]])
				.action_({ this.changed(\save); })
				.addNotifier(this, \edited, { | n, edited |
					if (edited) { n.listener.value = 1 }
				}),
				Button().maxWidth_(50).states_([["detach"], ["attach"]])
				.action_({ | me |
					followProjectGui = [true, false][me.value];
					if (followProjectGui) {
						this.readAndUpdate(Project.selectedProjectItem.fullPath)
					}
				})
			),
			[ListView()
				// .items_(snippets collect: _.header)
				.palette_(QPalette.dark
					.highlight_(Color(0.1, 0.1, 0.7))
					.highlightText_(Color(0.9, 0.8, 0.7))
				)
				.action_({ | me |
					snippetindex = me.value;
					this.changed(\snippet, me.value);
				})
				.addNotifier(Project, \selectedProjectItem, { | n |
					this.doIfScript(n.listener, {
						this.readAndUpdate(Project.selectedProjectItem.fullPath);
						this.updateSnippetListView(n.listener);
					});
				})
				.addNotifier(this, \snippets, { | n |
					this.updateSnippetListView(n.listener)
				}), stretch: 1],
			[TextView()
				.palette_(QPalette.dark
					.highlight_(Color(0.1, 0.1, 0.7))
					.highlightText_(Color(0.9, 0.8, 0.7))
				)
				.string_("NO SCRIPT SELECTED.\nSELECT A SCRIPT FILE ON PROJECT GUI.")
				.keyDownAction_({ | me ... args |
					this.changed(\edited, isEdited = true);
					me.defaultKeyDownAction(me, *args);
				})
				.addNotifier(this, \snippets, { | n |
					this.updateSnippetView(n.listener, 0);
				})
				.addNotifier(this, \snippet, { | n, snippet |
					this.updateSnippetView(n.listener, snippet);
				})
				.addNotifier(this, \save, { | n |
					"debugging save".postln;
					postln("view is: " + n.listener);
					postln("its contents are:" + n.listener.string);
					this.saveScript(n.listener.string.postln);
				}),
				stretch: 3]
		);
		window.addNotifier(Project, \selectedProjectItem, { | n |
			// "Debugging window renaming".postln;
			this.doIfScript(n.listener, { | me |
				// me.postln;
				me.name = Project.selectedProjectItem.folderName + ":" +
				Project.selectedProjectItem.fileNameWithoutExtension;
			})
		})
		.view.mouseEnterAction = { this.rereadIfNeeded; };
		Project.notifyProjectItem;
		^window;
	}

	updateSnippetListView { | view |
		view.items = snippets collect: _.header;
		this.changed(\snippet, view.value);
	}

	saveScript { | editedSource |
		var file;
		postln("debuggging save script. edited source is:\n\n");
		editedSource.postln;
		if (isEdited) {
			source = editedSource;
			file = File(sourcePath.postln, "w");
			file.write(editedSource);
			file.close;
			this.updateSnippets;
		}{
			"There are no changes to save.".postln;
			this.changed(\edited, isEdited);
		};
	}

	playScript {
		var envir;
		envir = this.envirName.envir;
		if (snippets.size == 1) {
			source.interpret;
		}{
			(
				dur: this.collectTimes.pseq(repeats),
				snippet: snippets.pseq(repeats),
				play: { var snippet;
					snippet = ~snippet;
					envir use: {  snippet.interpretAndShare }
				}
			).playInEnvir(this.playerName, this.envirName);
		}
	}

	stopScript {
		// TODO: keep a history of started scripts.
		// Offer list to stop any of them.
		this.envirName.envir do: _.stop;
	}

	collectTimes {
		^snippets.collect(_.header).collect({ | h |
			var time;
			time = h.findRegexp("\\[.*?]");
			if (time.size < 1) {
				time = 1
			}{
				time = (time[0][1].interpret ?? { [1] })[0] ?? { 1 };
			};
			time;
		})
	}

	envirName {
		// return name of envir to play current script with.
		// Envir has unique name made from script's path's folders and file name.
		var pathname, folders, envirName;
		pathname = PathName(sourcePath);
		folders = pathname.allFolders collect: _.asSymbol;
		folders = folders[folders.indexOf('sc-projects') + 1..] add: pathname.fileNameWithoutExtension;
		^folders.catList("_").asSymbol;
	}

	playerName {
		^PathName(sourcePath).fileNameWithoutExtension.asSymbol;
	}

	doIfScript { | view, action |
		if (followProjectGui and: { Project.selectedProjectItem.isFolder.not }) {
			action.(view);
		}
	}

	updateSnippetView { | view, snippet = 0 |
		var start, length;
		if (snippets.size == 1) {
			start = 0; length = snippet.size - 1
		}{
			#start, length = [1, -1] + snippetPositions[snippet];
		};
		view.string = source;
		view.select(start, length);
	}

	rereadIfNeeded {
		if (isEdited) {
			'Snippet Gui:'.vlayout(
				StaticText().string_("Discard edits and reread from file?"),
				Button().states_([["yes"]])
				.action_({ | me |
					this.readAndUpdate;
					me.parents.last.close;
				}),
				Button().states_([["no"]])
				.action_({ | me |
					me.parents.last.close;
				})
			)
		}
	}

	openScript {
		sourcePath ?? { ^"Please select a script to open first".postln; };
		if (PathName(sourcePath).isFolder) { ^"Cannot open a folder. Please select a file".postln; };
		Project.openFile(PathName(sourcePath));
	}
	makeSnippets {
		#snippets, snippetPositions = SnippetParser(source).parse;
	}

}

SnippetParser {
	var <string;
	*new { | string | ^this.newCopyArgs(string) }
	parse { | separator = "\n//:" |
		var delimiters, data, entry, positions;
		// string.findRegexp("^//:").postln;
		// delimiters = string findAll: separator;string.findRegexp("^//:")
		delimiters = string.findRegexp("^//:").collect({ | d | d[0] });
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = string.copyRange(b, end - 1)
			}{
				entry = string.copyRange(b, string.size - 1)
			};
			data = data add: entry;
			positions = positions add: [delimiters[i], entry.size];
		};
		if (data.size == 0) {
			data = [string];
			positions = [[0, string.size-1]];
		};
		postln("... read " + data.size + "entries.");
		^[data, positions];
	}
}
