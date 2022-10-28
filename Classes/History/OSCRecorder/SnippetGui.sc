/* 28 Oct 2022 09:35

*/

SnippetGui {
	var <source, <snippets, <snippetPositions;
	var <followProjectGui = true;

	*read { | path | ^this.new.read(path); }
	init {} // enable fromLib;
	readAndUpdate { | path |
		if (path.isFolder) { ^this };
		this.read(path);
		this.makeSnippets;
		this.changed(\snippets);
	}
	read { | path |
		source = File.readAllString(path);
		// postln("source now is:" + source);
	}

	*gui { | path |
		^this.fromLib(\default).gui;
	}

	gui {
		this.vlayout(
			[ListView()
				// .items_(snippets collect: _.header)
			.action_({ | me | this.changed(\snippet, me.value) })
			.addNotifier(Project, \selectedProjectItem, { | n |
				if (Project.selectedProjectItem.isFolder) {}
				{
					this.readAndUpdate(Project.selectedProjectItem.fullPath);
					n.listener.items = snippets collect: _.header;
					this.changed(\snippet, n.listener.value);
				};
				// if (followProjectGui) {
				// 	this.readAndUpdate(Project.selectedProjectPath.fullPath);
				// 	n.listener.items_(snippets collect: _.header);
				// 	this.changed(\snippet, snippets[0]);
				// }
			}), stretch: 1],
			[TextView()
				.palette_(QPalette.dark.highlight_(Color(0.9, 0.9, 0.7)))
				.addNotifier(this, \snippet, { | n, snippet |
					var start, length;
					#start, length = [1, -1] + snippetPositions[snippet];
					n.listener.string = source;
					// n.listener.setSelectionColor(Color.red);
					n.listener.select(start, length);
					// n.listener.set(Color.red, 0, source.size - 1);
				// n.listener.string = snippets[snippet]
			}), stretch: 4]
		)
	}

	/*
	gui {
		this.hlayout(
			[VLayout(
			ListView()
			.items_(snippets collect: _.header)
			.addNotifier(Project, \selectedProjectItem, { | n |
				if (followProjectGui) {
					this.readAndUpdate;
					n.listener.items_(snippets collect: _.header);
					this.changed(\snippet, snippets[0]);
				}
			}),
			TextView()
			.addNotifier(this, \snippet, {
				if (followProjectGui) {

				}
			})
			), stretch: 1],
			[TextView(), stretch: 4]
		)
	}
	*/

	makeSnippets {
		#snippets, snippetPositions = SnippetParser(source).parse;
	}

	headers {

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