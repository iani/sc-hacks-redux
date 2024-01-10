/*  3 Jan 2023 23:44
Navigate in a File Directory using 2 panes.
Saving / reloading the current position on disc, with bookmarks (TODO).

Creates 2 ListViews (inner / outer) that can be built into other guis.

*/

FileNavigator {
	classvar bookmarks; // TODO: Impement methods to use this.
	var <>key = \default;
	var >homeDir;  // top folder holding all items.
	var >currentRoot; // path for getting outerList items
	var <>outerList, <>outerItem, <outerIndex = 0;
	var <>innerList, <>innerItem, <innerIndex = 0;
	var <selection; // currently selected indices of inner list (for export/browsing)

	*browseHacksScores {
		this.new(\oscscores, PathName(Scores.parentPath)).gui
	}
	homeDir { ^homeDir ?? { homeDir = PathName(this.class.defaultPath); } }
	// homeDir { ^homeDir ?? { homeDir = this.class.defaultHomeDir; } }
	*defaultHomeDir { ^(PathName(Platform.userHomeDir) +/+ "sc-projects").asDir; }
	*defaultPath { ^(PathName(Platform.userHomeDir) +/+ "sc-projects").asDir.fullPath; }
	*new { | key = \default, homedir | ^this.newCopyArgs(key, homedir) /* .getOuterItems */ }

	save {
		postln("Saving preferences for:" + this.class.name + "at key" + key + "prefs" + this.prefs);
		Preferences.put(this.class.name, key, this.prefs);
	}

	bookmarks { ^this.class.bookmarks }

	*bookmarks { // incomplete
		^bookmarks ?? { bookmarks = this.class.prefs }
	}

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
			// currentRoot.folders,
			this entries: this.currentRoot,
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
			if (currentRoot != this.homeDir) {
				// currentRoot = this.homeDir;
				this.goHome;
			};
		};
		outerIndex = outerIndex ? 0;
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
		^this entries: this.currentRoot;
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
		// { "ZOOMING OUT - 10 x post for emphasis".postln; } ! 10;
		if (this.currentRoot.asDir.fullPath == this.homeDir.asDir.fullPath) {
			^postln("Cannot go above the homeDir: " + homeDir)
		};
		innerList = outerList;
		innerIndex = outerIndex;
		innerItem = outerItem;
		currentRoot = currentRoot.up;
		// { "WILL NOW CALL this entries! - 10 x post for emphasis".postln; } ! 10;
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
		// OscData.fromPath(outerItem.fullPath).gui;
	}

	entries { | pathName |
		^pathName.entries
		// .select({ |p| p.extension == ".scd" });
		.select({ |p|
			p.extension != "wav"
			and: { p.extension != "aiff" }
			and: { p.extension != "txt" }
			and: { p.extension != "org" }
			and: { p.extension != "sc" }
			and: { p.extension != "pdf" }
			and: { p.extension != "doc" }
			and: { p.extension != "docx" }
		});
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
				.menuActions([
					["select new home folder", { this.setHomeFolderFromUser }],
					["reread folders", { this.reread }]
				]),
				this.bookmarksbutton,
				Button().states_([["recall"]]).maxWidth_(50).action_({ this.load }),
				Button().states_([["<"]]).maxWidth_(30).action_({ this.zoomOut }),
				Button().states_([[">"]]).maxWidth_(30).action_({ this.zoomIn }),
				Button().states_([["browse", Color.red]]).maxWidth_(50).action_({ this.openInnerItem; }),
				Button().states_([["info"]]).maxWidth_(40).action_({ this.info }),
				Button().states_([["edit"]]).maxWidth_(40)
				.action_({
					this.edit;
				}),
				Button().states_([["export"]]).maxWidth_(50)
				.action_({ Menu(
					MenuAction("export code", { this.exportCode }),
					MenuAction("export code as ...", { this.exportCodeAs }),
					MenuAction("export messages", { this.exportMessages }),
					MenuAction("export messages as ...", { this.exportMessagesAs }),
					MenuAction("export all", { this.exportAll }),
					MenuAction("export all as ...", { this.exportAllAs }),
					MenuAction("export code/messages/all as", { this.exportMultiAs })
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

	reread {
		"reread - testing .  go home".postln;
		this.goHome;
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
		// "Testing".postln;
		// outerItem.files.postln;
		if (outerItem.isFolder) {
			outerItem.files do: { | p |
				// SnippetData([p.fullPath]).gui
				OscData.fromPath(p.fullPath).gui
			};
		}{
			postln("What is wrong with this outer item:\n" + outerItem);
			OscData.fromPath(outerItem.fullPath).gui;
		}
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
		innerItem ?? {
			// postln("openInnerItem item nil. outeritem is:" + outerItem);
			// "getting outer item".postln;
			this.getOuterItems;
			// "checking outer item again".postln;
			// postln("after get OuterItems outerItem is:" + outerItem);
			^OscData.fromPath(outerItem.fullPath).gui;
		};
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
				innerItem.fullPath.doIfExists({ | p |
					case
					{ p.isCode }{ OscDataScore([p]).gui }
					{ p.isOnsetCode }{ OscDataOnsetScore([p]).gui }
					{ p.hasTimestamps }{ OscData([p]).gui; }
					{ true }{ SnippetScore([p]).postln };
					// { true }{SnippetScore([innerItem.fullPath]).gui};
				},{ | p |
					postln("File not found:" + p);
				})
			}{
				OscData(this.mergeablePaths).gui;
			}
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
		if (selection.isNil) {
			this.getOuterItems;
			if (
				outerItem.notNil and: { outerItem.isFile; }
			) {
				all = this collectSnippets: [outerItem];
			}{
				ok("Select at least one file and try again");
				^"Select at least one file and try again".postln;
			};
		}{
			all = this.collectSnippets;
		};
		code = this.selectCode(all);
		ok("Selection contains" + all.size + "snippets\nand" + code.size + "code messages")
	}

	edit {
		var item;
		if (innerItem.isNil) {
			if (innerList.size == 0) {
				"the inner list is nil and I should try the outer list".postln;
				this.getOuterItems;
				item = outerItem;
			}{
				item = innerList[0];
			};
			// postln("the item chosen is" + item);
			Document.open(item.fullPath)
		}{
			// postln("the inner item is" + innerItem);
			Document.open(innerItem.fullPath);
		}
	}
	// replaced by improved version below
	// exportCode {
	//	this.export(this.selectCode(this.collectSnippets), "exports/code");
		// this.exportAsCode(this.selectCode(this.collectSnippets), "exports/code");
	// }

	codeFolder { ^"exports/code" }

	exportCode { | argFilename |
		var snippets, headers, times, messages, filename, folder;
		folder = this.codeFolder;
		snippets = this.selectCode(this.collectSnippets(separator: "\n//:--["));
		times = snippets collect: _.time;
		// postln("Debugging export code. Times:" + times);
		if (times.size == 0) {
			"There is no code to export in these files".warnWindow;
			^"There is no code to export in these files".postln;

		};
		times = [0] ++ (times - times[0]).rotate(-1).butLast; // .postln;
		if (argFilename.notNil) {
			filename = argFilename.scd; // provide scd if missing:  ++ ".scd";
		}{
			filename = (this.homeDir +/+ folder +/+ Date.getDate.stamp).fullPath ++ ".scd";
		};
		postln("Exporting code in:" + filename);
		File.use(filename, "w", { | f |
			f.write("//onsetcode\n"); // filetype compatible with timestamp format!!!
			f.write("//Exporting" + snippets.size + "code snippets" + "on" + Date.getDate.stamp ++ "\n" );
			f.write("//Source:" + (innerList[selection ?? { [0] }].collect(_.name)) ++ "\n");
			snippets collect: { | x, i |
				x.codeReplaceTimeStamp(times[i]).ensureNL;
			} do: { | x |
				// "\n======== checking how snippet is formatted. this is what I write: ===== ".postln;
				// x.postln;
				f write: x;
			};
			f write: "\n//the end\n\n";
		});
		"Export completed".postln;
	}

	exportMessages { this.export(this.selectMessages(this.collectSnippets), "exports/messages") }
	exportAll { this.export(this.collectSnippets, "exports/all") }

	exportMessagesAs { this.exportAs(this.selectMessages(this.collectSnippets), "messages") }
	exportCodeAs {
		{ | name |
			{ | p |
				postln("Exporting all messages in:" + p.first +/+ name ++ ".scd");
				this.exportCode(p.first +/+ name);
			}.getFolderPath("Click OK to choose a folder to export the data in.");
		}.inputText(this.makeFilename("code"), "Filename to save in:");
	}
	exportAllAs { this.exportAs(this.collectSnippets, "all") }

	exportMultiAs { // export messages, code and all in the same folder
		// "NOT YET IMPLEMENTED".postln;
		{ | name |
			// postln("experimental. will be multi-exporting with this base name:" + name);
			{ | p |
				var messagepath, codepath, allpath;
				messagepath = p.first +/+ name ++ "messages" ++ ".scd";
				codepath = p.first +/+ name ++ "code" ++ ".scd";
				allpath = p.first +/+ name ++ "all" ++ ".scd";
				postln("I will use the following three paths for exports of 3 kinds");
				[messagepath, codepath, allpath] do: _.postln;
				this.export(this.collectSnippets, nil, allpath);
				this.export(this.selectMessages(this.collectSnippets), nil, messagepath);
				// Use exportCode instead: Produce human editable //code file
				this.exportCode(codepath);
			}.getFolderPath("Click OK to choose a folder to export the data in.");
		}.inputText(this.makeFilename(""), "Enter the filename without the .scd extension")
	}

	exportAs { | collectedSnippets, type = "all" |
		{ | name |
			{ | p |
				postln("Exporting all messages in:" + p.first +/+ name);
				this.export(collectedSnippets, nil, p.first +/+ name);
			}.getFolderPath("Click OK to choose a folder to export the data in.");
		}.inputText(this.makeFilename(type), "Filename to save in:")
	}

	// collect snippet substrings, based on separator
	// default separator does not require timestamp
	// To ensure that all snippets have a timestamp, use
	// as separator this: "\n//:--["
	// see also method  String:snippets in plusStringSnippets.
	collectSnippets { | pathnames, separator = "\n//:" |
		var snippets;
		pathnames ?? { pathnames = innerList[selection ?? { [0] }] };
		pathnames do: { | i |
			File.use(i.fullPath, "r", { | f |
				var string, delimiters;
				string = f.readAllString;
				delimiters = string.findAll(separator);
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

	getSelection {
		// TODO: Refactor FileNavigator to use separate classes for the two list views.
		postln("testing getSelection. selection is:" + selection);
		if (selection.isNil) {
			if (innerList.isNil) {

			}{

			}
		}
	}

	selectCode { | snippets |
		^snippets select: _.hasCode;
	}

	selectMessages { | snippets |
		^snippets reject: _.hasCode;
	}

	exportAsCode { | snippets, folder |
		// TODO: experimental - export as code file with relative timestamps

	}

	export { | snippets, folder, fullpath, type = "all" |
		// if fullpath is given, use it.
		// else create full pat from home + folder + timestamp
		fullpath ?? {
			fullpath = (this.homeDir +/+ folder +/+ this.makeFilename(type)).fullPath + ".scd";
		};
		postln("Exporting in:" + fullpath);
		File.use(fullpath, "w", { | f |
			f.write("// Exporting" + snippets.size + "snippets" + "on" + Date.getDate.stamp ++ "\n" );
			f.write("//Source:" + (innerList[selection ?? { [0] }].collect(_.name)) ++ "\n");
			snippets do: { | x | f write: x.ensureNL };
			f write: "\n//the end\n\n";
		});
		"Export completed".postln;
	}

	makeFilename { | type = "all" |
		^(this.selectedPaths.first.fileNameWithoutExtension[..12] ++ "_" ++ type).postln;
		// (PathName(selection.first).fileNameWithoutExtension[..5] ++ "_" ++ type ++ ".scd").postln;
		// ^Date.getDate.stamp ++ ".scd";
	}

	check {
		var snippets, faulty;
		snippets = this.collectSnippets;
		postln("checking export integrity. num snippets:" + snippets.size);
		{ snippets do: {|s, i|
			0.01.wait;
			postln("-------" + i);
			s.postln;
			(s.last == Char.nl).postln;
			if ((s.last == Char.nl).not) {
				faulty = faulty add: [i, s];
			};
			postln("-------");
		};
			faulty do: _.postln;
			faulty;
		}.fork();
	}

	setHomeFolderFromUser {
		{ | p |
			p = p.first;
			postln("Setting home folder to: " + p);
			homeDir = PathName(p);
			this.goHome;
			this.save;
		}.getFolderPath("Click OK to select a new home folder");
	}
}