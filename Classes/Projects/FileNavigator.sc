/*  3 Jan 2023 23:44
Navigate in a File Directory using 2 panes.
Saving / reloading the current position on disc, with bookmarks (TODO).

Creates 2 ListViews (inner / outer) that can be built into other guis.

*/

FileNavigator {
	var <>key = \default;
	var >homeDir;  // top folder holding all items.
	var >currentRoot; // path for getting outerList items
	var <>outerList, <>outerItem, <outerIndex = 0;
	var <>innerList, <>innerItem, <innerIndex = 0;
	var <selection; // currently selected indices of inner list (for export/browsing)
	var bookmarks; // TODO: Impement methods to use this.

	homeDir { ^homeDir ?? { homeDir = PathName(this.class.defaultPath); } }
	// homeDir { ^homeDir ?? { homeDir = this.class.defaultHomeDir; } }
	*defaultHomeDir { ^(PathName(Platform.userHomeDir) +/+ "sc-projects").asDir; }
	*defaultPath { ^(PathName(Platform.userHomeDir) +/+ "sc-projects").asDir.fullPath; }
	*new { | key = \default | ^this.newCopyArgs(key) /* .getOuterItems */ }

	save {
		postln("Saving preferences for:" + this.class.name + "at key" + key + "prefs" + this.prefs);
		Preferences.put(this.class.name, key, this.prefs);
	}

	bookmarks { ^bookmarks ?? { bookmarks = Bookmarks() } }
	prefs {
		// save info needed to regenerate state,
		// plus human-readable relevant info.
		^(
			homeDir: homeDir,
			currentRoot: currentRoot,
			outerItem: outerItem, // human readable
			outerIndex: outerIndex,
			innerItem: innerItem, // human readable
			innerIndex: innerIndex,
			bookmarks: this.bookmarks
		)
	}

	help {
		"Keyboard shortcuts on list views:".postln;
		". : save bookmark".postln;
		", : go to bookmark".postln;
		"/ : set project home file dialog".postln;
		"! : go home (home root folder)".postln;
		"> : zoom in".postln;
		"< : zoom out".postln;
		"^ : zoom out".postln;
		"? : help".postln;
	}

	// Load and go to last saved bookmark position from preferences.
	load {
		var prefs;
		prefs = Preferences.get(this.class.name, key);
		prefs ?? {
			^postln("Could not get preferences for" + this.class.name + "at" + key);
		};
		homeDir = prefs[\homeDir];
		currentRoot = prefs[\currentRoot];
		outerIndex = prefs[\outerIndex];
		innerIndex = prefs[\innerIndex];
		bookmarks = prefs[\bookmarks] ?? { Bookmarks(); }; // TODO: implement methods to use this.
		this.changed(\setOuterListAndIndex,
			currentRoot.folders,
			outerIndex
		);
	}

	goHome {
		currentRoot = this.homeDir;
		this.getOuterItems;
	}

	setProjectHomeDialog {
		FileDialog({ | path |
			homeDir = PathName(path[0]).asDir;
			this.goHome;
		}, {}, 2)
	}
	getOuterItems {
		outerList = this.getOuterItemsList;
		if (outerList.size == 0) {
			// this.setProjectHomeDialog;
			// FileDialog({ | path |
			// 	"You selected:".postln;
			// 	path[0].postln;
			// });
			postln("No items found in" + currentRoot.fullPath);
			this.goHome;
		};
		outerIndex = 0;
		outerItem = outerList[outerIndex];
		this.changed(\outerItems);
		// when updating outer items, always update inner items too
		innerIndex = 0;
		this.getInnerItems;
	}

	// update contents of inner list when selecting new outer item
	getInnerItems {
		innerList = this.getInnerItemsList;
		innerItem = innerList[innerIndex];
		this.changed(\innerItems);
	}

	// subclasses may overwrite this:
	getOuterItemsList {
		// postln("Current root is:" + this.currentRoot);
		// postln("Folders are:");
		// this.currentRoot.folders do: _.postln;
		^this.currentRoot.folders;
	}
	getInnerItemsList {
		^this entries: outerItem;
	}
	// provide defaults for homeDir and currentRoot
	currentRoot { ^currentRoot ?? { currentRoot = this.homeDir } }
	// homeDir { ^homeDir ?? { homeDir = this.class.defaultHomeDir; } }

	// make outerItem the currentRoot
	zoomIn {
		// outerItem.postln;
		// outerItem.asString.ok;
		if (outerItem.folders.size == 0) {
			postln("Cannot zoom into this folder");
			postln(outerItem);
			"Because it has no subfolders".postln;
			("Cannot zoom into this folder" + outerItem.fullPath
				+ "Because it has no subfolders").ok;
			^nil;
		};
		currentRoot = outerItem;
		this.setOuterListAndIndex(innerList, innerIndex);
	}

	setOuterListAndIndex { | argList, argIndex |
		 this.changed(\setOuterListAndIndex, argList, argIndex)
	}

	// make currentRoot's superfolder the currentRoot;
	zoomOut {
		var newOuterItemPaths, newOuterItemPath;
		if (this.currentRoot.asDir.fullPath == this.homeDir.asDir.fullPath) {
			^postln("Cannot go above the homeDir: " + homeDir)
		};
		innerList = outerList;
		innerIndex = outerIndex;
		innerItem = outerItem;
		currentRoot = currentRoot.up;
		outerList = this entries: currentRoot;
		newOuterItemPaths = outerList collect: _.fullPath;
		newOuterItemPath = outerItem.up.fullPath;
		outerIndex = newOuterItemPaths
		indexOf: newOuterItemPaths.detect({|p| p == newOuterItemPath});
		outerItem = outerList[outerIndex];
		this.changed(\everything);
	}

	// Single point of entry for users.
	// Ensure that the user is working with an existing folder.
	*gui {
		this.withFolder({ | p |
			this.new.setCurrentRoot(PathName(p)).gui;
		})
	}

	setCurrentRoot2 { | p |
		postln("the new root will become" + p);
		currentRoot = PathName(p);
	}

	setCurrentRoot { | argNewRoot |
		if (argNewRoot.folders.size == 0) {
			postln("Cannot zoom into this folder");
			postln(argNewRoot);
			"Because it has no subfolders".postln;
			^nil;
		};
		currentRoot = argNewRoot;
		this.getOuterItems;
	}

	entries { | pathName |
		^pathName.entries
		.select({ |p| p.extension != "wav" and: { p.extension != "aiff" } });
	}

	bookmarkAction { ^{ this.save } }
	bookmarksbutton {
		^Button().states_([["save"]]).maxWidth_(40)
		.action_(this.bookmarkAction)
	}

	openInFinder {
		unixCmd("open" + outerItem.fullPath);
	}
	gui {
		{ this.getOuterItems }.defer(0.1);
		this.vlayout(
			HLayout(
				Button().states_([["*"]]).maxWidth_(10)
				.action_({ this.openInFinder }),
				this.bookmarksbutton,
				Button().states_([["recall"]]).maxWidth_(50)
				.action_({ this.load }),
				Button().states_([["<"]]).maxWidth_(30)
				.action_({ this.zoomOut }),
				Button().states_([[">"]]).maxWidth_(30)
				.action_({ this.zoomIn }),
				Button().states_([["info"]]).maxWidth_(40)
				.action_({ this.info }),
				Button().states_([["edit"]]).maxWidth_(40)
				.action_({
					if (innerItem.isFolder) {
						innerItem.folderName.post;
						" is a folder".postln;
						"Select a file to open instead".postln;
					}{
						Document.open(innerItem.fullPath);
					}
				}),
				Button().states_([["browse"]]).maxWidth_(50)
				.action_({ this.openInnerItem; }),
				Button().states_([["export"]]).maxWidth_(50)
				.action_({ Menu(
					MenuAction("export code", { this.exportCode }),
					MenuAction("export code experimental", { this.exportCodeExperimental }),
					MenuAction("export messages", { this.exportMessages }),
					MenuAction("export all", { this.exportAll })
				).front }),
				Button().states_([["load"]]).maxWidth_(40)
				.action_({ this.loadSfSelections })
			),
			HLayout(
				this.outerListView,
				this.innerListView
				// ListView()
				// .items_((1..5))
				// .selectionMode_(\contigous)
				// this.innerListView .selectionMode_(\contiguous)
				// this.innerListView .selectionMode_(\contiguous)
			)
		).bounds_(this.bounds)
		.addNotifier(this, \innerItems, { | n |
			var p;
			// postln("debugging naming of window");
			// postln("currentRoot is" + currentRoot);
			// postln("homeDir is" + homeDir);
			// postln("relative path is" + currentRoot.asRelativePath(homeDir));
			p = currentRoot.asRelativePath(this.homeDir);
			if (p.size == 0) { p = "./" };
			n.listener.name = p;
		});
		{ this.load; }.defer(1.1); // load last saved path from preferences
	}

	loadSfSelections {
		if (innerItem.isFolder) {
			innerItem.folderName.post;
			" is a folder".postln;
			"Select a file to open instead".postln;
		}{
			innerItem.fullPath.doIfExists({ | p |
				if ( p.isSfSelection ) {
					SoundBufferGui.loadFile(p);
				}{
					postln("not a sound file selections file:" + p)
				}
			},{ | p |
				postln("File not found:" + p);
			})
		}
	}

	bounds { ^Rect(0, 230, 350, 180) }
	outerListView {
		^ListView()
		.palette_(QPalette.light
					.highlight_(Color(1.0, 0.9, 0.4))
					.highlightText_(Color(0.0, 0.0, 1.0))
		)
		.enterKeyAction_({ this.openAllFiles; })
		.addNotifier(this, \everything, { | n |
			n.listener.items = outerList collect: _.shortName;
			n.listener.value = outerIndex;
		})
		.addNotifier(this, \setOuterListAndIndex, { | n, list, outerIndex |
			if (list.size == 0) {
				"Outer list is empty. Cannot refresh.".postln;
				"Please use the opened dialog to select a new project root".postln;
				"or save the current position for next time."
			}{
			outerList = list;
			n.listener.items = outerList collect: _.shortName;
			n.listener.value = outerIndex;
			outerItem = outerList[outerIndex];
			innerList = this entries: outerItem;
			}
			// NOTE: deferred selectionAction triggers update of inner list!!!
		})
		.addNotifier(this, \outerItems, { | n |
			var index;
			// restore index overwritten by selectionAction!
			index = outerIndex;
			// {
			// 	outerIndex = index;
			// 	this.changed(\outerItem);
			// }.defer(0.01);
			n.listener.items = this.outerListNames;
		})
		.addNotifier(this, \outerItem, { | n |
			n.listener.value = outerIndex;
		})
		.selectionAction_({ | me |
			this.outerIndex = me.value;
		})
		.keyDownAction_(this.keyboardShortcutAction);
	}

	openAllFiles {
		outerItem.postln;
		"Testing".postln;
		outerItem.files.postln;
		outerItem.files do: { | p |
			SnippetData([p.fullPath]).gui
		};
	}

	outerIndex_ { | val |
		outerIndex = val;
		outerItem = this.outerList[outerIndex];
		this.changed(\outerItem);
		this.getInnerItems;
	}

	outerListNames {
		^(outerList ? []) collect: _.shortName;
	}

	innerListViewDebug {
		^ListView()
		.items_((1..5))
		.selectionMode_(\contiguous)
		.palette_(
			QPalette.light
			.highlight_(Color(0.4, 0.9, 1.0))
			.highlightText_(Color(1.0, 0.0, 0.0))
		)
	}

	innerListView {
		^ListView()
		.palette_(
			QPalette.light
			.highlight_(Color(0.4, 0.9, 1.0))
			.highlightText_(Color(1.0, 0.0, 0.0))
		)
		.selectionMode_(\contiguous) // does not work. Why?
		.addNotifier(this, \everything, { | n |
			n.listener.items = innerList collect: _.shortName;
			n.listener.value = innerIndex;
		})
		.addNotifier(this, \innerItems, { | n |
			// restore index overwritten by selectionAction!
			var index;
			index = innerIndex;
			// {
				// innerIndex = index;
				// this.changed(\innerItem);
			// }.defer(0.01);
			n.listener.items = this.innerListNames;
			n.listener.value = innerIndex;
		})
		.addNotifier(this, \innerItem, { | n |
			n.listener.value = innerIndex;
		})
		.action_{ | me |
			innerIndex = me.value;
			innerItem = innerList[innerIndex];
			selection = me.selection;
			innerList[selection].postln;
		}
		.enterKeyAction_({ this.openInnerItem })
		.keyDownAction_(this.keyboardShortcutAction);
	}

	innerIndex_ { | val |
		val ?? {
			"No items found in this folder".postln;
			outerItem.postln;
			^"Exiting. Please select a different folder".postln;
		};
		innerIndex = val;
		innerItem = innerList[innerIndex];
		this.changed(\innerItem);
	}

	innerListNames {
		^(innerList ? []) collect: _.shortName;
	}
	keyboardShortcutAction {
		^{ | me, char, modifiers, unicode, keycode, key |
			// [me, char, modifiers, unicode, keycode, key].postln;
			if (modifiers == 1048576 and: { key == 65 }) {
				selection = (0..(me.items.size - 1));
			};
			switch (char,
				$., { this.saveBookmark; },
				$,, { this.gotoBookmark; },
				$/, { this.setProjectHomeDialog; },
				$!, { this.goHome; },
				$>, { this.zoomIn; },
				$<, { this.zoomOut; },
				$^, { this.zoomOut; },
				$?, { this.help; },
				$x, { this.openInnerItem },
				me.defaultKeyDownAction(char, modifiers, unicode, keycode, key);
			)
		}
	}

	openInnerItem { // experimental: open in oscdata type gui
		var selectedPaths;
		if (innerItem.isFolder) {
			innerItem.folderName.post;
			" is a folder".postln;
			"Select a file to open instead".postln;
		}{
			// selection.postln;
			selectedPaths = this.selectedPaths;
			// selectedPaths do: _.postln;
			// this.mergeablePaths.postln;
			// /*
			if (selectedPaths.size == 1) {
				"debugging openInnerItem".postln;
				postln("selectedPaths is:" + selectedPaths);
				postln("innerItem is:" + innerItem);
				innerItem.fullPath.doIfExists({ | p |
					case
					{ p.isCode }{ OscDataScore([p]).gui }
					{ p.hasTimestamps }{ OscData([p]).gui; }
					{ true }{ SnippetScore([p]).gui };
					// { true }{SnippetScore([innerItem.fullPath]).gui};
				},{ | p |
					postln("File not found:" + p);
				})
			}{
				OscData(this.mergeablePaths).gui;
			}
			//	*/
		}
	}

	selectedPaths {
		selection ?? { selection = [0] };
		// postln("debugging selectedPaths innerList" + innerList + "selection" + selection);
		^innerList[selection];
	}
	mergeablePaths {
		^this.selectedPaths.collect(_.fullPath) select: { | p |
			 p.isCode.not and: { p.hasTimestamps }
		}
	}
	saveBookmark { this.save }
	gotoBookmark { this.load }

	info {
		var all, code;
		all = this.collectSnippets;
		code = this.selectCode(all);
		ok("Selection contains" + all.size + "snippets\nand" + code.size + "code messages")

	}
	exportCode {
		this.export(this.selectCode(this.collectSnippets), "exports/code");
		// this.exportAsCode(this.selectCode(this.collectSnippets), "exports/code");
	}


	exportCodeExperimental {
		var snippets, headers, times, messages;
		snippets = this.selectCode(this.collectSnippets);
		times = snippets collect: _.time;
		times = times.differentiate.put(0, 0).rotate(-1); // .postln;
		snippets collect: { | x, i |
			x.codeReplaceTimeStamp(times[i])
		} do: _.postln;
	}

	exportCodeExperimentalOLD {
		var ods, snippets;
		"this is exportCodeExperimental.".postln;
		"these are the snippets:".postln;
		this.collectSnippets.postln;
		"these are the code snippets".postln;
		this.selectCode(this.collectSnippets).postln;
		"Making OscDataScore".postln;
		ods = OscDataScore.newCopyArgs(
			this.selectedPaths, nil, this selectCode: this.collectSnippets
		);
		postln("OscDataScore is:" + ods);
		"Next I will convertTimesMessages --- PREPARING".postln;
		ods.convertTimesMessages;
		postln("Now testing selectedMessages. segmentMin"
			+ ods.timeline.segmentMin
			+ "segmentMax" + ods.timeline.segmentMax);
		postln("the export path is:" + ods.codeExportPath);
		postln("timeline durations:" + ods.timeline.durations);
		postln("timeline durations first:" + ods.timeline.durations.first);
		postln("timeline durations first class:"
			+ ods.timeline.durations.first.class
		+ "size" + ods.timeline.durations.first.size);
		postln("times" + ods.times + "messages" + ods.messages);
		// postln("selectedMessages:" + ods.selectedMessages);
		// postln("messages:" + ods.messages);
		// "Next I will actually try to export".postln;
		// ods.exportCode;
	}

	exportMessages { this.export(this.selectMessages(this.collectSnippets), "exports/messages") }
	exportAll { this.export(this.collectSnippets, "exports/all") }
	collectSnippets {
		var snippets;
		// selection.postln;

		innerList[selection ?? { [0] }] do: { | i |
			File.use(i.fullPath, "r", { | f |
				var string, delimiters;
				string = f.readAllString;
				delimiters = string.findAll("\n//:");
				delimiters do: { | b, i |
					var end, entry;
					end = delimiters[i + 1];
					if (end.notNil) {
						entry = string.copyRange(b + 1, end);
					}{
						entry = string.copyRange(b + 1, string.size - 1)
					};
					snippets = snippets add: entry;
				}
			})
		};
		^snippets; //.postln;
	}

	selectCode { | snippets |
		^snippets select: _.hasCode;
	}

	selectMessages { | snippets |
		^snippets reject: _.hasCode;
	}

	exportAsCode { | snippets, folder |
		// experimental

	}

	export { | snippets, folder |
		var filename;
		filename = (this.homeDir +/+ folder +/+ Date.getDate.stamp).fullPath ++ ".scd";
		File.use(filename, "w", { | f |
			f.write("// Exporting" + snippets.size + "snippets" + "on" + Date.getDate.stamp ++ "\n" );
			snippets do: { | x | f write: x };
			f write: "\n//the end\n\n";
		});
		"Export completed".postln;
	}
}