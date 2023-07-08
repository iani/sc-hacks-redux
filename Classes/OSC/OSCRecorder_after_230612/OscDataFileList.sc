/* 14 Jun 2023 01:03

*/

OscDataFileList {
	classvar >fileListHistoryPath;
	classvar fileListHistory;
	classvar <selectedList, <selectedPath;

	*openCurrentDocument { // TODO!
		Document.current.path.postln;
		this.makeOscDataGui([Document.current.path]);
	}

	*gui {
		fileListHistory ?? { this.readFileListHistory; };
		this.vlayout(
			HLayout(
				Button().states_([["add filelist"]])
				.action_({ this.addListFromUser }),
				Button().states_([["edit"]])
				.action_({ this.changed(\editSelection) }),
				Button().states_([["rename"]]).action_({ this.rename }),
				Button()
				.maxWidth_(50)
				.canFocus_(false)
				.states_([["browse", Color.blue, Color.white]])
				.action_({ Menu(
					MenuAction("code", { this.changed(\cloneCode) }),
					MenuAction("messages", { this.changed(\cloneMessages) }),
					MenuAction("all", { this.changed(\viewSelection) }),
				).front }),
				Button()
				.maxWidth_(50)
				.canFocus_(false)
				.states_([["export", Color.red, Color.white]])
				.action_({ Menu(
					MenuAction("code", { this.changed(\exportCode) }),
					MenuAction("messages", { this.changed(\exportMessages) }),
					MenuAction("all", { this.changed(\exportSelection) }),
				).front })
			),
			HLayout(
				ListView() // List of lists
				.minWidth_(250)
				.palette_(QPalette.light
					.highlight_(Color(1.0, 0.9, 0.7))
					.highlightText_(Color(0.0, 0.0, 0.0))
				)
				.addNotifier(fileListHistory, \history, { | n |
					n.listener.items = fileListHistory.lists collect: _.asString;
					selectedList = fileListHistory.lists.first;
	 				this.changed(\selectedList);
				})
				.action_({ | me |
					selectedList = fileListHistory.lists[me.value];
					fileListHistory.changed(\fileList, me.value);
					this.changed(\selectedList);
				})
				.enterKeyAction_({ this.browseList })
				.keyDownAction_({ | me, char ... args |
					case
					{ char == 127.asAscii } {
							{ fileListHistory removeAt: me.value; }
							.confirm("Do you really want to delete" + me.item + "?")
					}
					{ char == $r } { this.rename; }
					{ char == $m } { this.exportListMessages; }
					{ char == $c } { this.exportListCode; }
					{ char == $a } { this.exportListAll; }
					{ true }{ me.keyDownAction(me, char, *args)};
				})
				.addNotifier(this, \mainList, { | n |
					n.listener.items = fileListHistory.lists collect: _.asString
				}),
				ListView() // list of files in a list
				.palette_(QPalette.light
					.highlight_(Color(0.7, 1.0, 0.9))
					.highlightText_(Color(0.0, 0.0, 0.0))
				)
				.selectionMode_(\contiguous)
				.addNotifier(this, \selectedList, { | n |
					n.listener.items = selectedList.paths collect: _.basename;
					selectedPath = selectedList.paths.first;
				})
				.enterKeyAction_({ | me |
					// me.selection.postln;
					// selectedList.paths[me.selection].postln;
					this.makeOscDataGui(selectedList.paths[me.selection]);
					// OscData(selectedList.paths[me.selection]).gui;
				})
				.keyDownAction_({ | me, char ... args |
					case

					{ char == 127.asAscii } {
						// me.selection.postln;
						// me.items[me.selection].postln;
						{
							selectedList.removePathsAt(me.selection);
							fileListHistory.save;
							this changed: \selectedList;
						}.confirm(
							"Do you really want to remove" +
							me.items[me.selection] +
							"?"
						)
						// { fileListHistory removeAt: me.value; }
						// .confirm("Do you really want to delete" + me.item + "?")
					}
					{ char == $+ } {
						// "I will add some files to this slist".postln;
						var thePaths;
						thePaths = selectedList.paths;
						Dialog.openPanel({ | argPaths |
							argPaths do: { | p |
								thePaths = thePaths addString: p
							};
							selectedList.paths = thePaths;
							fileListHistory.save;
							postln("Added" + argPaths);
						}, multipleSelection: true)
					}
					{ char == $e } {
						this.changed(\editSelection);
					}
					{ char == $v } {
						this.changed(\viewSelection);
					}
					{ true } {
						me.keyDownAction(me, char, *args)
					};
				})
				.addNotifier(this, \viewSelection, { | n |
					this.makeOscDataGui(selectedList.paths[n.listener.selection]);
				})
				.addNotifier(this, \editSelection, { | n |
					selectedList.paths[n.listener.selection] do: Document.open(_);
				})
				.addNotifier(this, \cloneCode, { | n |
					this.cloneCode(selectedList.paths[n.listener.selection]).gui;
				})
				.addNotifier(this, \cloneMessages, { | n |
					this.cloneMessages(selectedList.paths[n.listener.selection]).gui;
				})
				.addNotifier(this, \exportCode, { | n |
					this.cloneCode(selectedList.paths[n.listener.selection]).export;
				})
				.addNotifier(this, \exportMessages, { | n |
					this.cloneMessages(selectedList.paths[n.listener.selection]).export;
				})
				.addNotifier(this, \exportSelection, { | n |
					this.parsePaths(selectedList.paths[n.listener.selection]).export;
				})
			)
		);
		{ fileListHistory.changed(\history) }.defer(0.1);
	}

	*browseList {
		// selectedList.postln;
		// selectedList.paths.postln;
		this.parsePaths(selectedList.paths).gui(selectedList.name);
	}

	*exportListCode {
		{ | window |
			window !? { window.close };
			this.exportCode(selectedList.paths);
		}.confirm("Export code for " + selectedList.name + "?")
	}

	*exportListMessages {
		{ | window |
			window !? { window.close };
			this.exportMessages(selectedList.paths);
		}.confirm("Export messages for " + selectedList.name + "?")
	}

	*exportListAll {
		{ | window |
			window !? { window.close };
			this.exportSelection(selectedList.paths);
		}.confirm("Export all for" + selectedList.name + "?")
	}

	*rename {
		{ | name |
			selectedList.name = name;
			fileListHistory.save;
			this.changed(\mainList);
		}.inputText(
			selectedList.name,
			"Enter a new name for " + selectedList
		)
	}
	*parsePaths { | paths |
		// decide whether to use OscData or OscDataScore,
		// based on the header of the first file.
		var isCode;
		File.use(paths.first,"r", { | f |
			var h;
			h = f.getLine(1024); // .postln;
			if (h == "//code") {
				isCode = true;
			}{
				isCode = false;
			};
		});
		if (isCode) {
			^OscDataScore(paths);
		}{
			^OscData(paths);
		}
	}

	*makeOscDataGui { | paths, name | this.parsePaths(paths).gui(name); }
	*cloneCode { | paths | ^this.parsePaths(paths).cloneCode; }
	*cloneMessages { | paths | ^this.parsePaths(paths).cloneMessages; }
	*exportCode { | paths | this.parsePaths(paths).cloneCode.exportCode; }
	*exportMessages { | paths | this.parsePaths(paths).cloneMessages.export; }
	*exportSelection { | paths | this.parsePaths(paths).export; }

	*addListFromUser {
		this.fileListHistory.addListFromUser;
	}

	*fileListHistory {
		fileListHistory ?? { this.readFileListHistory };
		^fileListHistory;
	}

	*readFileListHistory {
		fileListHistory = Object.readArchive(this.fileListHistoryPath);
		fileListHistory ?? {
			fileListHistory = FileListHistory(this.fileListHistoryPath);
			this.writeFileListHistory;
		}
	}

	*fileListHistoryPath {
		fileListHistoryPath ?? {
			fileListHistoryPath = Platform.userAppSupportDir +/+ "OscDataFileLists.scd";
		};
		^fileListHistoryPath;
	}

	*writeFileListHistory {
		fileListHistory.writeArchive(this.fileListHistoryPath);
	}

	// not yet used.
	*fileListHistoryPathDialog {
		FileDialog({ | path |
			fileListHistoryPath = path;
			this.readFileListHistory;
		})
	}
}