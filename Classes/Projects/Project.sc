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
	classvar <>rootFolder = "sc-projects";
	classvar <projects, <selectedProject;
	classvar <projectItems, <selectedProjectItem;
	classvar <userSelectedProject = false;

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
			// this selectProject: selectedProject;
			this.notifyNavigationStatus;
		}
	}

	*prGetProjects {
		^this.projectHomePath.folders collect: { | p | p.folderName.asSymbol };
	}

	*projectHomePath { ^PathName(Platform.userHomeDir +/+ startupFolder); }
	*globalHomePath { ^PathName(Platform.userHomeDir +/+ rootFolder); }
	*globalProjectPath { ^this.globalHomePath +/+ globalFolder }
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
		postf("loading local buffers from: %\n", this.externalAudiofilePath);
		this.loadAudioFiles(this.externalAudiofilePath);
	}

	*externalAudiofilePath {
		^PathName(Platform.userHomeDir +/+ "sc-audiofiles" +/+ Project.selectedProject);
	}

	*loadGlobalSynthdefs {
		"loading global synthdefs".postln;
		if (OscGroups.isEnabled) { OscGroups.disableCodeForwarding; };
		this.loadScdFiles(this.globalSynthdefPath, false);
		if (OscGroups.isEnabled) { OscGroups.enableCodeForwarding; };
	}

	*loadLocalSynthdefs {
		if (this.projectFileNames ?? { [] } includes: 'autoload_synthdefs.scd') {
		"loading local synthdefs".postln;

		OscGroups.disableCodeForwarding; // TODO: Remove this line after checking
		if (OscGroups.isEnabled) { OscGroups.disableCodeForwarding; };
		this.loadScdFiles(this.localSynthdefPath);
		if (OscGroups.isEnabled) {
			OscGroups.enableCodeForwarding;
		};
		}{
			"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!".postln;
			postln("Synthdef loading for project" + selectedProject + "is disabled.");
			"To enable autoload synthdefs, add a file named autoload_synhdefs.scd in the project's folder".postln;
			"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!".postln;
		}
	}

	*projectFileNames {
		^Project.projectItems collect: { | p | p.fileName.asSymbol };
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
			\goToProject >>>.projects { | n, msg |
				// Message and arguments are, in order:
				// [\gotoProject, selectedProject, startupFolder]
				~test = (
					\selectedProject: msg[1],
					\startupFolder: msg[2]
				);
				this.gotoSelectedProject(
					(
						\selectedProject: msg[1],
						\startupFolder: msg[2]
					)
				)
			};
			// // ShutDown add: { this.saveProjectPath };
			this.window({ | w |
				w.bounds = w.bounds.height_(300);
				w.name = "Projects in ~/" ++ startupFolder;
				w.userCanClose = false;
				w.layout = HLayout(
					VLayout(
						HLayout(
							// StaticText().string_("Projects").maxWidth_(150),
							Button().states_([["-*-"]]).maxWidth_(30)
							.action_({ this.loadProjectPath }),
							Button().states_([[">*<", Color.red]]).maxWidth_(30)
							// replace broadcastProjectt with inline code to avoid
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
							// postln("current projects are:" + projects);
							n.listener.items = projects;
							// postln("setting" + n.listener + "items to " + projects);
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
						.mouseDownAction_{ | me |
							// "Mouse down selected this: ".post;
							// me.items[me.value].postln;
							userSelectedProject = true;
							// this.selectProject(projects[me.value], true);
							// me.value.postln;
							// me.items[me.value].postln;
							{ this.selectProject(projects[me.value]); }.defer(0.01);
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

	*goDownAFolder {
		var targetFolder, fullPath;
		// go down to selected project folder making it root of projects.
		// If selected project folder has no subfolders, then just issue an error.
		"Going down to folder: ".post;
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
			postln("The new project folder is:" + targetFolder);
			// targetFolder.postln;
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
			postln("Already at the root of home directory. Cannot go up any further.");
		}{
			postln("Going down to this folder:" + targetFolder);
			// targetFolder.postln;
			startupFolder = targetFolder;
			this.getProjects;
		}
	}

	*goToFolder { | targetFolder |
			startupFolder = targetFolder;
			this.getProjects;
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
	/* // obsolete. please delete
	*broadcastSelectedProject {
		postf("\n**** Broadcasting selected project % ****\n\n", selectedProject);
		// Send to OscGroups net address even if OscGroups is disabled:
		OscGroups.forceBroadcastCode(
			format("Project.selectProject(%)", selectedProject.asCompileString)
		);
	}
	*/

	*selectProject { | projectName |
		selectedProject = projectName ? selectedProject;
		// postf("Selecting project: %\n", selectedProject);
		if (userSelectedProject) {
			this.saveProjectPath;
			userSelectedProject = false;
		};
		this.getProjectItems;
		// projectItems.postln;
		this.changed(\selectedProject);
		this.notifyNavigationStatus;
	}


	*saveProjectPath {
		(
			startupFolder: startupFolder,
			selectedProject: selectedProject
		).writeArchive(this.selectedProjectArchivePath);
		post("*saving path* : ");
		postln("folder:" + startupFolder + "project:" + selectedProject);
	}

	*loadProjectPath { | dict |
		var newSelectedProject;
		dict ?? {
			dict = Object.readArchive(this.selectedProjectArchivePath);
		};
		startupFolder = dict[\startupFolder].asString;
		selectedProject = dict[\selectedProject].asSymbol;
		newSelectedProject = selectedProject;
		this.getProjects;
		postln("Restoring last project selection from archive:" + newSelectedProject);
		userSelectedProject = true;
		this.selectProject(newSelectedProject);
		^dict;
	}

	*getProjectPath { // just get the project path - for various purposes
		^Object.readArchive(this.selectedProjectArchivePath);
	}
	// evaluate this to broadcast project change over OscGroups
	// This must be run by the sender only.
	// So the sender must activate it from an interface that
	// then sends an OSC message with the relevant project info to OscGroups
	// Do not call this method by evaluating code because you do not want
	// others to send *their* selected project.  Instead, run this method
	// only from a GUI.
	// The code of this method will be copied into a gui button action.
	// Then the method will be deleted in order to prevent it from
	// being evaluated by accident - because this could lead to
	// an OscGroup sending loop flooding the network.
	// First write the method and test it, then copy the code
	// into the button action and test it, then erase this method.
	// *sendSelectedProject { // do not evaluate this method manually.

	// }
	// remove this method when done building it inline in button above ">*<":
	*broadcastProject {
		this.selectedProject.postln;
		this.startupFolder.postln;
		postln("I will broadcast project" + this.selectedProject + "from folder" + this.startupFolder);
		OscGroups.send([\goToProject, this.selectedProject, this.startupFolder]);
	}

	*goto { | argProject, argFolder |
		this gotoSelectedProject: (
			selectedProject: argProject,
			startupFolder: argFolder
		);
	}

	*gotoSelectedProject { | dict |
		var newSelectedProject;
		startupFolder = dict[\startupFolder].asString; // convert received from OSC
		selectedProject = dict[\selectedProject].asSymbol;
		// startupFolder.class.postln;
		// selectedProject.class.postln;
		newSelectedProject = selectedProject;
		this.getProjects;
		postln("Restoring last project selection from archive:" + newSelectedProject);
		userSelectedProject = true;
		this.selectProject(newSelectedProject);
	}

	*selectedProjectArchivePath {
		^(PathName(Platform.userAppSupportDir) +/+ "SelectedProject.scd").fullPath
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