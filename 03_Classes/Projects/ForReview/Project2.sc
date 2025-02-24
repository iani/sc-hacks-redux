/* 20 Dec 2022 18:17

*/

Project2 {
	classvar homeFolder;  // folder containing all project folders
	classvar projectFolder; // current project folder
	classvar projectSuperfolder; //  immediate super-folder of projectFolder
	//========================================
	classvar <projects, <projectIndex = 0;
	classvar <projectItems, <projectItemIndex = 0;
	classvar <preferences; // Dictionary


	*homeFolder {
		homeFolder ?? { this.getPreferences };
		^homeFolder;
	}

	*projectSuperfolder {
		projectSuperfolder ?? { this.getPreferences };
		^projectSuperfolder;
	}

	*projectFolder {
		projectFolder ?? { this.getPreferences };
		^projectFolder;
	}

	*getPreferences {
		preferences = Preferences.get(this.asSymbol);
		homeFolder = preferences[\homeFolder] ?? { PathName("~/sc-projects") +/+ "" };
		projectSuperfolder = preferences[\projectSuperfolder] ?? { homeFolder };
		projects = projectSuperfolder.entries;
		if (projectSuperfolder.entries.size == 0) {
			postln("WARNING: No projects fouund in:" + projectSuperfolder.fullPath);
		};
		projectFolder = preferences[\projectFolder] ?? { projects[0] };
		^preferences;
	}

	*savePreferences {
		preferences[\homeFolder] = homeFolder;
		preferences[\projectFolder] = projectFolder;
		preferences[\projectSuperfolder] = projectSuperfolder;
		// implicitly saves Preferences to file:
		Preferences.put(this.asSymbol, this.preferences);
	}

	*getProjects {
		projects = this.projectSuperfolder.folders;
		if (projects.size == 0) {
			^postln("No folders found in" + this.projectSuperfolder.fullPath);
		};
		projectItemIndex = 0;
		this changed: \projects;
		this.getProjectItems;
	}

	*getProjectItems {
		projectFolder = projects[projectIndex];
		if (projectFolder.isNil) {
			^postln("No projects at" + projectIndex + "\n Projects are:" + projects);
		};
		projectItems = projectFolder.entries;
		projectItemIndex = 0;
		// this.savePreferences;
		this changed: \projectItems;
	}

	*enterFolder {
		projectSuperfolder = projectFolder;
		this.getProjects;
	}

	*exitFolder {
		if ((projectSuperfolder +/+ "").fullPath == (homeFolder +/+ "").fullPath) {
			postln("Current superFolder is home folder:" + projectSuperfolder.fullPath);
			"Cannot go further up".postln;
		}{
			projectSuperfolder = projectSuperfolder.up;
			this.getProjects;
		}
	}

	*resetHomeFolder {
		this setHomeFolder: PathName("~/sc-projects");
	}

	*setHomeFolder { | argPath |
		projectSuperfolder = homeFolder = argPath;
		this.getProjects;
		this.savePreferences;
	}

	*goHome {
		projectSuperfolder = homeFolder;
		this.getProjects;
	}


	*setProjectHomeDialog {
		FileDialog({ | path |
			this setHomeFolder: PathName(path[0]);
		}, fileMode: 2)
	}


	*broadcastSelectedProject {
		"broadcastSelectedProject method is not implemented.".postln;
	}

	*setup {
		"setup method is not implemented.".postln;
	}

	*saveBookmark {
		this.savePreferences;
		postln("Bookmark saved: " + projectFolder.fullPath.userRelative);
	}


	*getProjectIndex { | argPath |
		var index;
		this.getProjects;
		argPath = argPath.fullPath;
		index = projects.collect({ | p | p.fullPath == argPath }) indexOf: true;
		if (index.isNil) {
			postln("No project found at:" + argPath);
		}{
			projectIndex = index;
			this.changed(\projectIndex);
		}
	}

	*goto { | relPath |
		projectFolder = this.composePath(relPath.asString);
		projectSuperfolder = projectFolder.up;
		postln("Going to: " + projectFolder.fullPath.userRelative);
		this.getProjectIndex(projectFolder);
	}

	*gotoBookmark {
		this.getPreferences;
		postln("\nloaded bookmark.  Going to:" + projectFolder);
		// postln("projectSuperfolder is: " + projectSuperfolder);
		this.getProjectIndex(projectFolder);
	}

	*innerPath { | argPath | // the path minus initial homeFolder component;
		^argPath.fullPath[homeFolder.fullPath.size..];
	}

	*composePath { | argPath | ^homeFolder +/+ argPath; }

	*loadLocalBuffers {

	}

	*projectName {
		^projectFolder.fileNameWithoutExtension;
	}

	*gui {
		this.getPreferences;
		\goToProject >>>.projects { | n, msg | this.goto(msg[1]) };
		{
			var w;
			w = this.makeWindow;
			w.addNotifier(this, \projects, { | n |
				n.listener.name =  projectSuperfolder.fullPath.userRelative;
			});
			0.1.wait;
			this.getProjects;
			this.gotoBookmark;
			OscGroups changed: \status;
		}.fork(AppClock);
	}

	*makeWindow {
		^this.hlayout(
			VLayout(
				HLayout(
					Button().states_([["-*-"]]).maxWidth_(30)
					.action_({ this.loadProjectPath }),
					Button().states_([[">*<", Color.red]]).maxWidth_(30)
					// replace broadcastProject with inline code to avoid
					// running this code from the interpreter. !
					.action_({ this.broadcastSelectedProject }),
					Button().states_([["Setup"]]).maxWidth_(50)
					.action_({ this.setup }),
					Button()
					.maxWidth_(50)
					.canFocus_(false)
					.states_([["menu", Color.red, Color.white]])
					.action_({ Menu(
						MenuAction("OSC Monitor+Recorder", { OscMonitor.gui }),
						MenuAction("Open Snippet Gui", { SnippetGui.gui }),
						MenuAction("Go to subfolder", { this.enterFolder }),
						MenuAction("Go to superfolder", { this.exitFolder }),
						MenuAction("Refresh Project Window", { this.getProjects }),
						MenuAction("Load Audio Files", { AudioFiles.loadFiles })
					).front })
				),
				ListView()
				.palette_(QPalette.dark
					.highlight_(Color(0.1, 0.1, 0.7))
					.highlightText_(Color(0.9, 0.8, 0.7))
				)
				.addNotifier(this, \projects, { | n |
					n.listener.items = projects collect: _.folderName;
				})
				.addNotifier(this, \projectIndex, { | n |
					n.listener.value = projectIndex;
				})
				.selectionAction_({ | me |
					projectIndex = me.value;
					this.getProjectItems;
				})
				.enterKeyAction_({
					// this.enterFolder;
				})
				.keyDownAction_({ | me, char, modifiers, unicode, keycode, key |
					switch (char,
						$., { this.saveBookmark; },
						$,, { this.gotoBookmark; },
						$/, { this.setProjectHomeDialog; },
						$!, { this.goHome; },
						$>, { this.enterFolder; },
						$<, { this.exitFolder; },
						$^, { this.exitFolder; },
						$?, { this.help; },
						me.defaultKeyDownAction(char, modifiers, unicode, keycode, key);
					)
				})
			),
			ListView()
			.palette_(QPalette.dark
				.highlight_(Color(0.1, 0.1, 0.7))
				.highlightText_(Color(0.9, 0.8, 0.7))
			)
			.addNotifier(this, \projectItems, { | n |
				n.listener.items = projectItems collect: _.shortName;
			})
		)
	}

	// *setProjectRootFallbackDialog {
	// 	FileDialog({ | path |
	// 		path = PathName(path[0]);
	// 		this.preferences[\homeFolderFallback] = path;
	// 		this setProjectRoot: path;
	// 	}, fileMode: 2)
	// }
	/*
	*guiOld {
		{
			this.window({ | w |
				w.bounds = w.bounds.height_(300);
				w.name = "Projects in ~/" ++ this.selectedProject.fullPath;
				w.layout = HLayout(
					VLayout(
						HLayout(
							Button().states_([["-*-"]]).maxWidth_(30)
							.action_({ this.loadProjectPath }),
							Button().states_([[">*<", Color.red]]).maxWidth_(30)
							// replace broadcastProject with inline code to avoid
							// running this code from the interpreter. !
							.action_({ this.broadcastProject }),
							Button().states_([["Setup"]]).maxWidth_(50)
							.action_({ this.setup }),
							Button()
							.maxWidth_(50)
							.canFocus_(false)
							.states_([["menu", Color.red, Color.white]])
							.action_({ Menu(
								MenuAction("OSC Monitor+Recorder", { OscMonitor.gui }),
								MenuAction("Open Snippet Gui", { SnippetGui.gui }),
								MenuAction("Go to subfolder", { this.goDownAFolder }),
								MenuAction("Go to superfolder", { this.goUpAFolder }),
								MenuAction("Refresh Project Window", { this.getProjects }),
								MenuAction("Load Local Audio Files", { this.loadLocalBuffers })
							).front })
						),
						ListView()
						.palette_(QPalette.dark
							.highlight_(Color(0.1, 0.1, 0.7))
							.highlightText_(Color(0.9, 0.8, 0.7))
						)
						// .hiliteColor_(Color(0.9, 0.9, 1.0))
						.addNotifier(this, \projects, { | n |
							n.listener.items = this.projectFolders collect: { | p | p.folderName.asSymbol };
							this.selectProject;
						})
						.addNotifier(this, \selectedProject, { | n |
							n.listener.value_(
								n.listener.items.indexOf(selectedProjectName) ? 0
							);
						})
						.selectionAction_({ | me |
							this.selectProject(projectFolders[me.value], true);
						})
						.enterKeyAction_({ this.broadcastSelectedProject })
						.keyDownAction_({ | me, char |
							if (char === Char.space) {
								this.selectProject(selectedProject, true);
							};
						})
						.mouseDownAction_{ | me |
							// "Mouse down selected this: ".post;
							// me.items[me.value].postln;
							userSelectedProject = true;
							// this.selectProject(projects[me.value], true);
							// me.value.postln;
							// me.items[me.value].postln;
							{ this.selectProject(projects[me.value], true); }.defer(0.01);
						}
						.addNotifier(this, \navigationStatus, { | n, goUp, goDown |
							var menuActions = [];
							if (goUp) {
								menuActions = menuActions add: MenuAction("go up", { this.goUpAFolder });
							};
							if (goDown) {
								menuActions = menuActions add: MenuAction("go to subproject", { this.goDownAFolder; });
							};
							n.listener.setContextMenuActions(*menuActions)
						}),
						HLayout(
							Button().states_([["-"]])
							.addNotifier(this, \selectedProject, { | n |
								n.listener.states_([[selectedProject]]);
							})
							.action_({
								this.broadcastSelectedProject;
							}),
							Button().maxWidth_(30).states_([["<"]])
							.action_({ | me |
								this.goUpAFolder
							})
							.addNotifier(this, \navigationStatus, { | n, goUp, goDown |
								n.listener.enabled = goUp;
							}),
							Button().maxWidth_(30).states_([[">"]])
							.action_({ | me |
								this.goDownAFolder
							})
							.addNotifier(this, \navigationStatus, { | n, goUp, goDown |
								n.listener.enabled = goDown;
							})
						),
						HLayout(
							StaticText().string_("OscGroups:"),
							Button()
							.states_([["On"], ["Off"]])
							.action_({ | me |
								OscGroups.perform([
									\enable,
									\disable
								][me.value]);
							})
							.addNotifier(OscGroups, \status, { | n |
								// "Checking OscGroups gui status button".postln;
								// n.notifier.postln;
								postf(
									"osc groups enabled? %\n",
									n.notifier.isEnabled
								);
								if (n.notifier.isEnabled) {
									n.listener.value = 0
								}{
									n.listener.value = 1
								}
							})
						)
					),
					VLayout(
						HLayout(
							StaticText().string_("Scripts").maxWidth_(70),
							Button().states_([["Snippets"]]).maxWidth_(60)
							.action_({ SnippetGui.read(this.selectedProjectItem.fullPath).gui }),
							Button()
							.maxWidth_(50)
							.states_([
								["Cmd-.", Color.black, Color.white]
							])
							.action_({ CmdPeriod.run }),
						),
						ListView()
						.palette_(QPalette.dark
							.highlight_(Color(0.1, 0.1, 0.7))
							.highlightText_(Color(0.9, 0.8, 0.7))
						)
						.addNotifier(this, \projectItems, { | n |
							n.listener.items = projectItems.collect({ | i |
								if (i.isFolder) {
									i.folderName
								}{
									i.fileNameWithoutExtension
								}
							})
						})
						.selectionAction_({ | me |
							if (me.value.isNil) {
								postln("project" + selectedProject + "has no scripts");
							}{
								this.selectProjectItem(projectItems[me.value]);
							}
						})
						.enterKeyAction_({ | me |
							postf("Loading selected item: %\n", projectItems[me.value]);
							this.loadSelectedProjectItem;
						})
						.setContextMenuActions(
							MenuAction("load script", { this.loadSelectedProjectItem; }),
							MenuAction("play script score", { this.playSelectedProjectItem; }),
							MenuAction("edit script", { this.openSelectedProjectItem; }),
							MenuAction("open snippet gui", { this.guiSelectedProjectItem; }),
						),
						HLayout(
							Button().states_([["-"]])
							.addNotifier(this, \selectedProject, { | n |
								n.listener.states_([["-"]]);
							})
							.action_({ this.loadSelectedProjectItem })
							.addNotifier(this, \selectedProjectItem, { | n |
								n.listener.states_([[this.selectedProjectItemName]]);
							}),
							Button().maxWidth_(30).states_([["O"]])
							.action_({ this.openSelectedProjectItem })
						),
						HLayout(
							StaticText().string_("Broadcasting:"),
							Button()
							.states_([["On"], ["Off"]])
							.action_({ | me |
								OscGroups.perform([
									\enableCodeForwarding,
									\disableCodeForwarding
								][me.value]);
							})
							.addNotifier(OscGroups, \status, { | n |
								 if (n.notifier.isEnabled) {
									n.listener.states_([
										["On", Color.red, Color.black],
										["Off", Color.gray, Color.black]
									])
								}{
									n.listener.states_([
										// ["On", Color.gray, Color.white],
										["Off", Color.gray, Color.white]
									])
								};
								if (n.notifier.isForwarding) {
									n.listener.value = 0
								}{
									n.listener.value = 1
								};
							})
						)
					);
				);
				{
					OscGroups.changed(\status);
					// "polled oscgroups status now".postln;
				}.defer(0.15);
				2 do: { this.getProjects; 1.wait; };
			});
		}.fork(AppClock);
	}
	*/
}