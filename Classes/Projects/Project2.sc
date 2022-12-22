/* 20 Dec 2022 18:17
Redoing project from scratch.
Sorting out default root project folder path vs. selected project path, and
adding synthdefRoot and audiofileRoot.  Objective: make remote loading of
project, synthdef and audiofile scripts possible by sending relative paths
over OSC.

projectRoot: root folder. Default: PathName("~/projectRoot")
selectedProject: path to selected project, relative to root folder.
The selectedProject path is the full Pathname of the most recently selected project.
It is saved onto file between sessions and can be retrieved when a new gui is made.
It is created by selecting via index from PathName.folders (from the ListView of folders).

When sharing over OSC groups, its component relative to projectRoot is extracted for sending.
The receiver re-composes the path with their own project root.

Sender:
input: selectedProject (full path)
processing:
	1. decompose to folders: PathName(selectedProject).allFolders.
	2. delete from beginning of array those folders which belong to PathName("~projectRoot").allFolders
output sent via OSC: array of remaining folders (without the projectRoot folders)

Receiver:
input: array of remaining folders (without the projectRoot folders)
processing:
	1. add to array of folders of receiver's projectRoot. Store in variable pathArray.
	2. concatenate pathArray into new pathname inserting +/+ between folders
	(implementation: use custom String method: concatFolders)

synthdefRoot: root folder of synthdefs. String.
audiofileRoot: root folder of audiofiles.  String.

======= earlier simpler version - under discussion:

Under revision (no longer valid:)
If Project has never been used, there is no selectedProject value. In that case,
selectedProject path defaults to PathName("~/sc-projects");
If no such folder exists, then Projects gets a root folder from the user via File Dialog.

*/

Project2 {
	classvar preferences; // Dictionary with: root, rootProject, selectedProject, audiofile, synthdef
	classvar <projects, <projectIndex = 0;
	classvar <projectItems, <projectItemIndex = 0;
	// classvar <selectedProject; // path relative to projectRoot
	classvar projectRoot;      // path relative to "~/"
	classvar <selectedProjectPath;

	*savePrefs { Preferences.put(this.asSymbol, this.preferences); }

	*preferences { ^preferences ?? { this.getPreferences } }
	*getPreferences { ^preferences = Preferences.get(this.asSymbol) }
	*projectRoot {
		^projectRoot ?? {
			projectRoot = this.preferences[\projectRoot] ?? { PathName("~/sc-projects") }
		}
	}

	// *projectFallbackRoot {
	// 	^this.preferences[\projectRootFallback] ?? { PathName("~/sc-projects") }
	// }

	*setProjectRoot { | argPath |
		projectRoot = argPath;
		this.preferences.put(\projectRoot, argPath);
		this.savePrefs;
		this.getProjects;
		this changed: \projectRoot;
	}

	*getProjects {
		projects = this.projectRoot.folders;
		projectItemIndex = 0;
		this changed: \projects;
		this.getProjectItems;
	}

	*getProjectItems {
		selectedProjectPath = projects[projectIndex];
		projectItems = selectedProjectPath.entries;
		projectItemIndex = 0;
		this changed: \projectItems;
	}

	*broadcastSelectedProject {
		"broadcastSelectedProject method is not implemented.".postln;
	}

	*gui {
		{
			var w;
			w = this.makeWindow;
			0.1.wait;
			this.getProjects;
			w.name = "Projects in: " + this.projectRoot.fullPath;
			OscGroups changed: \status;
		}.fork(AppClock);
	}

	*makeWindow {
		^this.hlayout(
			ListView()
			.addNotifier(this, \projects, { | n |
				n.listener.items = projects collect: _.folderName;
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
					$., { this.enterFolder; },
					$>, { this.enterFolder; },
					$<, { this.exitFolder; },
					$^, { this.exitFolder; },
					$!, { this.resetProjectRoot; }, // reset to ~/sc-projects
					// $@, { this.resetProjectFallbackRoot; }, // reset to fallback root
					$?, { this.help; },
					$/, { this.setProjectRootDialog; },
					// $/, { this.setProjectRootFallbackDialog; },
					me.defaultKeyDownAction(char, modifiers, unicode, keycode, key);
				)
			}),
			ListView()
			.addNotifier(this, \projectItems, { | n |
				n.listener.items = projectItems collect: _.shortName;
			})
		)
	}

	*enterFolder { this setProjectRoot: selectedProjectPath; }
	*exitFolder { this setProjectRoot: this.checkUpPath(projectRoot); }
	*checkUpPath { | argPath |
		var newPath;
	}
	*resetProjectRoot { this setProjectRoot: PathName("~/sc-projects") }
	// *resetProjectFallbackRoot {
	// 	this setProjectRoot: this.projectFallbackRoot;
	// }
	*setProjectRootDialog {
		FileDialog({ | path |
			this setProjectRoot: PathName(path[0]);
		}, fileMode: 2)
	}

	// *setProjectRootFallbackDialog {
	// 	FileDialog({ | path |
	// 		path = PathName(path[0]);
	// 		this.preferences[\projectRootFallback] = path;
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