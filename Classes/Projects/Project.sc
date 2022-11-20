/* 15 Sep 2021 18:26
Load project scripts and audio files from subfolders of a folder.
Default folder path is: ~/sc-projects.

Any subfolder of this folder is a project folder.
The folder named "global" has special status:
audio files and synthdefs contained in that folder are also
used in all other projects.

Special subfolders:

audiofiles: Audio files (wav, aiff) contained in this folder are
always loaded to the server when it boots.

synthdefs: All scd files contained in this folder are always
loaded to the server when it boots.

GUI interface:

window:

|  project list     | file and folder list |
|  selected project | selected file/folder |

Clicking on a project or file / folder from
one of the above lists selects the that item
and shows its name on the selected project or selected
file / folder button.

Clcking on the selected project makes that project the current projecet.
Clicking on the selected file/folder button executes the corresponding file(s)

*/

Project {
	classvar <>startupFolder = "sc-projects", <>globalFolder = "global";
	classvar <projects, <selectedProject;
	classvar <projectItems, <selectedProjectItem;

	*initClass {
		StartUp add: {
			Server.default doWhenReallyBooted:  { | server |
		 		this.loadGlobalBuffers;
				server.sync;
				this.loadLocalBuffers;
				server.sync;
				postf("% finished loading buffers\n", server);
				this.loadGlobalSynthdefs;
				server.sync;
				this.loadLocalSynthdefs;
				server.sync;
				postf("% finished loading synthdefs\n", server);
				this.loadLocalSetupFolder;
			};
			ServerQuit add: {
				"====== The default server quit ======".postln;
				"Removing buffer entries from lib".postln;
				Library.put(Buffer, nil);
			};

	 	}
	}

	*getProjects {
		projects = this.prGetProjects;
		if (projects.size == 0) {
			Warn(format("ERROR: No projects found in %\n", this.projectHomePath.fullPath))
		}{
			this changed: \projects;
			this.notifyNavigationStatus;
		}
	}

	*prGetProjects {
		^this.projectHomePath.folders collect: { | p | p.folderName.asSymbol };
	}

	*projectHomePath { ^PathName(Platform.userHomeDir +/+ startupFolder); }
	*globalProjectPath { ^this.projectHomePath +/+ globalFolder }
	*globalAudiofilePath { ^this.globalProjectPath +/+ "audiofiles" }
	*globalSynthdefPath { ^this.globalProjectPath +/+ "synthdefs" }
	*localSynthdefPath { ^this.selectedProjectPath +/+ "synthdefs" }
	*localSetupPath { ^this.selectedProjectPath +/+ "setup" }
	*globalStartupFilePath { ^this.globalProjectPath +/+ "startup.scd" }

	*matchingFilesDo { | pathName, func ... types |
		types = types collect: _.asSymbol;
		postln("Looking for files in" + pathName.fullPath + "...");
		postln("... found:" + (pathName +/+ "*").fullPath.pathMatch
			.collect({|p| PathName(p).fileName})
		);
		pathName filesDo: { | p | // ! filesDo recurses over subfolders!
			if (types includes: p.extension.asSymbol) {
				func.(p.fullPath)
			}{
				postf("Skipping non-matching file type: %\n", p.fileName);
			}
		}
	}

	*loadAudioFiles { | pathName |
		this.matchingFilesDo(
			pathName,
			{ | p | p.loadAudiofile },
			"wav", "WAV", "aiff", "aif"
		)
	}

	*loadScdFile { | pathName |
		if (pathName.extension == "scd") {
			pathName.fullPath.load;
		}{
			postf("% is not an scd file.\n", pathName.fileName);
		}
	}

	*loadScdFiles { | pathName |
		var restoreBroadcasting;
		this.matchingFilesDo(
			pathName,
			{ | p |
				postf("Loading : %\n", p);
				p.load;
			},
			"scd"
		);
	}

	*loadGlobalBuffers {
		"loading global buffers".postln;
		this.loadAudioFiles(this.globalAudiofilePath);
	}

	*loadLocalBuffers {
		postf("loading local buffers from: %\n", this.localAudiofilePath);
		this.loadAudioFiles(this.localAudiofilePath);
	}

	*loadGlobalSynthdefs {
		"loading global synthdefs".postln;
		if (OscGroups.isEnabled) { OscGroups.disableCodeForwarding; };
		this.loadScdFiles(this.globalSynthdefPath, false);
		if (OscGroups.isEnabled) { OscGroups.enableCodeForwarding; };
	}

	*loadLocalSynthdefs {
		"loading local synthdefs".postln;
		OscGroups.disableCodeForwarding; // TODO: Remove this line after checking
		if (OscGroups.isEnabled) { OscGroups.disableCodeForwarding; };
		this.loadScdFiles(this.localSynthdefPath);
		if (OscGroups.isEnabled) {
			OscGroups.enableCodeForwarding; };
	}

	*loadLocalSetupFolder {
		"loading setup folder".postln;
		OscGroups.disableCodeForwarding;  // TODO: Remove this line after checking
		if (OscGroups.isEnabled) { OscGroups.disableCodeForwarding; };
		this.loadScdFiles(this.localSetupPath);
		if (OscGroups.isEnabled) {
			OscGroups.enableCodeForwarding; };
	}

	*localAudiofilePath { ^this.selectedProjectPath +/+ "audiofiles"; }
	// *localSynthdefPath { ^this.selectedProjectPath +/+ "synthdefs"; }

	*getAudioFilePaths { | pathName |
		// return PathNames of all audiofiles
		// contained in folder pathName or its subfolders
		var types;
		types = ['wav', 'WAV', 'aif', 'aiff'];
		^pathName.deepFiles select: { | p | types includes: p.extension.asSymbol }
	}

	*gui {
		{
			this.getProjects;
			this.window({ | w |
				w.bounds = w.bounds.height_(300);
				w.name = "Projects in ~/" ++ startupFolder;
				w.userCanClose = false;
				w.layout = HLayout(
					VLayout(
						HLayout(
							StaticText().string_("Projects").maxWidth_(150),
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
							).front })
						),
						ListView()
						.palette_(QPalette.dark.highlight_(Color(0.9, 0.9, 0.7)))
						// .hiliteColor_(Color(0.9, 0.9, 1.0))
						.addNotifier(this, \projects, { | n |
							n.listener.items = projects;
							this.selectProject;
						})
						.addNotifier(this, \selectedProject, { | n |
							n.listener.value_(
								projects.indexOf(selectedProject) ? 0
							);
						})
						.selectionAction_({ | me |
							this.selectProject(projects[me.value]);
						})
						.enterKeyAction_({ this.broadcastSelectedProject })
						.keyDownAction_({ | me, char |
							if (char === Char.space) {
								this.selectProject(selectedProject);
							};
						})
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
						.palette_(QPalette.dark.highlight_(Color(0.9, 0.9, 0.7)))
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
					"polled oscgroups status now".postln;
				}.defer(0.15);
				2 do: { this.getProjects; 1.wait; };
			});
		}.fork(AppClock);
	}

	*goDownAFolder {
		var targetFolder, fullPath;
		// go down to selected project folder making it root of projects.
		// If selected project folder has no subfolders, then just issue an error.
		"I will go down a folder. The current project folder I will go down to is:".postln;
		selectedProject.postln;
		targetFolder = PathName(startupFolder) +/+ selectedProject;
		postln("The new projectfolder will become:" + targetFolder);
		fullPath = PathName(Platform.userHomeDir)  +/+ targetFolder;
		postln("The full path is " + fullPath);
		postln("There are " + fullPath.folders.size + "subfolders to work with.");
		if (fullPath.folders.size == 0) {
			postln("Cannot use " + selectedProject + "as root folder because it has no subfolders");
			"Skipping this.".postln;
			"Please select a different folder as project root".postln;
		}{
			"Here will be the glorious new project root folder!!!!!".postln;
			targetFolder.postln;
			startupFolder = targetFolder.fullPath;
			this.getProjects;
		};
	}

	*goUpAFolder {
		var targetFolder;
		// if current startupFolder is root project folder, then just issue an error.
		postln("I will go up a folder. Current startup folder is: " + startupFolder);
		startupFolder.postln;

		targetFolder = PathName(PathName(startupFolder).parentPath).fullPath.postln; // hack ...
		postln("The new target folder is:" + targetFolder);
		if (targetFolder.size == 0) {
			postln("hey you have moved to the root of home directory. This is not good. ABORTING");
		}{
			"Well yes, we may be able to go to this folder:".postln;
			targetFolder.postln;
			startupFolder = targetFolder;
			this.getProjects;
		}
	}

	*startProjectInGroup {
		{
			OscGroups.enable; // so we are ready here for what comes next
			this.broadcastSelectedProject; // works also without enable. Just making sure
			0.1.wait; // wait for everyone to switch project before booting;
			Server.default.reboot;
			OscGroups.forceBroadcastCode(
				"Server.default.reboot;"
			);
		}.fork;
	}

	*setup { // simpler setup method
		var setupPath, file, code;
		setupPath = Project.projectItems.select({ | i | i.fileName.asSymbol == 'setup.scd' }).first;
		if (setupPath.isNil) {
			postln("There is no setup.scd file in project:" + selectedProject);
		}{
			postln("Loading setup.scd file for project:" + selectedProject);
			code = File.readAllString(setupPath.fullPath);
			code.interpret; // this always runs locall only as it does not use the preprocessor.
		}
	}

	*setupProjectInGroup {
		// OBSOLETE - NEEDS CHECKING!
		// Load local project setup folder.
		// Set local project of all group members to your local project
		// Make all local project members load their local project setup folder
		{
			this.loadLocalSetupFolder;
			OscGroups.enable; // so we are ready here for what comes next
			this.broadcastSelectedProject; // works also without enable. Just making sure
			0.1.wait; // wait for everyone to switch project before booting;
			OscGroups.forceBroadcastCode(
				"Project.loadLocalSetupFolder;"
			);
		}.fork;
	}

	*broadcastSelectedProject {
		postf("\n**** Broadcasting selected project % ****\n\n", selectedProject);
		// Send to OscGroups net address even if OscGroups is disabled:
		OscGroups.forceBroadcastCode(
			format("Project.selectProject(%)", selectedProject.asCompileString)
		);
	}

	*selectProject { | projectName |
		selectedProject = projectName;
		postf("Selecting project: %\n", selectedProject);
		this.getProjectItems;
		// projectItems.postln;
		this.changed(\selectedProject);
		this.notifyNavigationStatus;
	}

	*notifyNavigationStatus {
		this.changed(\navigationStatus, this.canGoUpAFolder, this.canGoDownAFolder);
	}

	*canGoDownAFolder {
		Project.selectedProjectItem ?? { ^false };
		// postln("Checking if i can go down on this project:" + this.selectedProject);
		// postln("FOLDERS: " + Project.selectedProjectPath.folders.size);
		// postln("FILES: " + Project.selectedProjectPath.files.size);
		^this.selectedProjectPath.folders.size > 0;
	}

	*canGoUpAFolder {
		// "can go up a folder?????".postln;
		// "startupFolder should have more than 1 folder:".postln;
		// this.projectHomePath.postln;
		// startupFolder.postln;
		// startupFolder.numFolders.postln;
		// postln("the answer is : " + (startupFolder.numFolders > 1));
		^(startupFolder.numFolders > 1);
	}

	*getProjectItems {
		projectItems = this.selectedProjectPath.entries.reject({ | e |
			e.isFile and: { e.extension != "scd" }
		});
		this.changed(\projectItems);
	}

	*selectedProjectPath {
		^this.projectHomePath +/+ selectedProject.asString;
	}

	*selectProjectItem { | projectItem |
		if (selectedProjectItem !== projectItem) {
			selectedProjectItem = projectItem;
			this.notifyProjectItem;
		}
	}

	*notifyProjectItem { this.changed(\selectedProjectItem); }

	*loadSelectedProjectItem {
		postf("loading project item: %\n", selectedProjectItem);
		if (selectedProjectItem.isFolder) {
			"Loading folder".postln;
			if (this.isAudioFileFolder(selectedProjectItem)) {
				"Loading audio files".postln;
				this.loadAudioFiles(selectedProjectItem);
			}{
				"loading scd files".postln;
				this.loadScdFiles(selectedProjectItem);
			}
		} {
			this.loadScdFile(selectedProjectItem);
		}

	}

	*playSelectedProjectItem {
		if (selectedProjectItem.isFolder) {
			postln("This is a folder and I cannot play it:" + selectedProjectItem);
		}{
			postln("playing: " + selectedProjectItem.fullPath);
			ScriptDataReader(selectedProjectItem.fullPath).play;
		}
	}

	*guiSelectedProjectItem {
		if (selectedProjectItem.isFolder) {
			postln("Gui for Folders not implemented:" + selectedProjectItem);
		}{
			postln("opening snippet gui for" + selectedProjectItem.fullPath);
			SnippetGui.gui(selectedProjectItem.fullPath);
		}
	}
	*isAudioFileFolder { | pathName |
		// TODO: check implementation!
		^pathName.isFolder and: {
			pathName.folderName == "audiofiles"
		};
	}

	*loadProjectBuffers {
		postf("Loading project buffers from: %\n", selectedProjectItem.fullPath);
		"WARNING: This method is not implemented!".postln;
	}

	*openSelectedProjectItem {
		// var path;
		// path = selectedProjectItem.fullPath;
		postf("opening: %\n", selectedProjectItem);
		if (selectedProjectItem.isFolder) {
			this.openFolder(selectedProjectItem);
		}{
			this.openFile(selectedProjectItem);
		}
	}

	*openFolder { | path |
		if (this.isAudioFileFolder(path)) {
			"audiofiles folder contains the following files:".postln;
			path filesDo: { | f |
				f.postln
			};
			"Loading audio files now:".postln;
			this.loadSelectedProjectItem;
		}{
			"Cannot open a folder containing scripts".postln;
		}
	}

	*openFile { | path |
		if (Platform.ideName == "scel") {
			ScelDocument.open(path.fullPath);
		}{
			Document.open(path.fullPath)
		}
	}

	*selectedProjectItemName {
		if(selectedProjectItem.isFolder) {
			^selectedProjectItem.folderName
		}{
			^selectedProjectItem.fileNameWithoutExtension
		}
	}

	*audiofilesGui { | projectItem |
		var localAudioFiles, chan, frames, rate, dur;
		localAudioFiles = this.getAudioFilePaths(projectItem);
		projectItem.fullPath.asSymbol.tl_.window({ | w |
			w.layout = VLayout(
				ListView()
				.hiliteColor_(Color(0.9, 0.9, 1.0))
				.items_(
					localAudioFiles.collect({ | p |
						SoundFile.use(p.fullPath, { | file |
							rate = file.sampleRate;
							frames = file.numFrames;
							chan = file.numChannels;
						});
						format("% : % ch, % Hz, % sec",
							p.fileName, chan, rate, ((frames/rate) round: 0.001).formatTime
						)
					})
				)
				.enterKeyAction_({ | me ... args |
					// must write method to play the file from disk
					// to enable browsing audio files even if they are not yet loaded.
					SoundFile(localAudioFiles[me.value].fullPath).postln.cue(playNow: true);
				})
				.keyDownAction_({ | me key |
					if (key === Char.space) { CmdPeriod.run }
				}),
				Button().states_([["Stop Synths and Routines (space)"]])
				.action_({ CmdPeriod.run })
			)
		});
	}

	// some early drafts.
	*scdFilesDo { | pathName, func | }
	*globalAudioFiles { }
	*globalSynthdefFiles { }
	*localAudioFiles { }
	*localSynthdefFiles { }

}