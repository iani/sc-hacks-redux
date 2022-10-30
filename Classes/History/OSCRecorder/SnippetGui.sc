/* 28 Oct 2022 09:35

*/

SnippetGui {
	var <source, <snippets, <snippetPositions;
	var <followProjectGui = true;
	var <snippetindex = 0, sourcePath, isEdited = false;

	*read { | path | ^this.new.read(path); }
	init {} // enable fromLib;
	readAndUpdate { | path |
		path ?? { path = sourcePath };
		if (path.isFolder) { ^this };
		this.read(path);
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
		window = this.tr_(600, 400).vlayout(
			HLayout(
				Button().states_([["detach"], ["attach"]])
				.action_({ | me |
					followProjectGui = [true, false][me.value];
					if (followProjectGui) {
						this.readAndUpdate(Project.selectedProjectItem.fullPath)
					}
				}),
				Button().states_([["eval snippet globally"]])
				.action_({ | me |
					snippets[snippetindex].interpret;
				}),
				Button().states_([["eval snippet locally"]])
				.action_({ | me |
					OscGroups.disableCodeForwarding;
					snippets[snippetindex].interpret;
					OscGroups.enableCodeForwarding;
				}),
				Button().states_([["play script"]])
				.action_({
					this.collectTimes.postln;
				}),
				Button().states_([["open"]])
				.action_({
					this.openScript.postln;
				}),
				Button().states_([["save", Color.black], ["save", Color.red]])
				.action_({ this.saveScript; })
				.addNotifier(this, \edited, { | n, edited |
					if (edited) { n.listener.value = 1 }
				})
			),
			[ListView()
				// .items_(snippets collect: _.header)
				.action_({ | me |
					this.changed(\snippet, me.value);

				})
			.addNotifier(Project, \selectedProjectItem, { | n |
				if (followProjectGui and: { Project.selectedProjectItem.isFolder.not }) {
					this.readAndUpdate(Project.selectedProjectItem.fullPath);
					n.listener.items = snippets collect: _.header;
					this.changed(\snippet, n.listener.value);
				};
			}), stretch: 1],
			[TextView()
				.palette_(QPalette.dark.highlight_(Color(0.9, 0.9, 0.7)))
				.keyDownAction_({ | me ... args |
					this.changed(\edited, isEdited = true);
					me.defaultKeyDownAction(me, *args);
				})
				.addNotifier(this, \snippets, { | n |
					this.updateSnippetView(n.listener, 0);
				})
				.addNotifier(this, \snippet, { | n, snippet |
					this.updateSnippetView(n.listener, snippet);
				}),
				stretch: 4]
		);
		window.view.mouseEnterAction = { this.rereadIfNeeded; };
		Project.notifyProjectItem;
		^window;
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

	saveScript {
		if (isEdited) {
			"TODO: put some code here!!";
		};
		"save not implemented".postln;
		this.changed(\edited, isEdited);
	}

	openScript {
		sourcePath ?? { ^"Please select a script to open first".postln; };
		if (PathName(sourcePath).isFolder) { ^"Cannot open a folder. Please select a file".postln; };
		Project.openFile(PathName(sourcePath));
	}
	makeSnippets {
		#snippets, snippetPositions = SnippetParser(source).parse;
	}

	collectTimes {
		^snippets.collect(_.header).collect({ | h |
			var time;
			time = h.findRegexp("\\[.*?]");
			if (time.isNil) {
				time = 1
			}{
				time = time[0][1];
				time[1..time.size-2].postln;
			};
			time;
		})
	}
}

SnippetParser {
	var <string;
	*new { | string | ^this.newCopyArgs(string) }
	parse { | separator = "\n//:" |
		var delimiters, data, entry, positions;
		delimiters = string findAll: separator;
		delimiters do: { | b, i |
			var end;
			end = delimiters[i + 1];
			if (end.notNil) {
				entry = string.copyRange(b, end)
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