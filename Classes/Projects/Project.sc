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
			OscGroups runLocally: {
				this.globalStartupFilePath.fullPath.pathMatch do: _.load
			};
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
		projects = this.projectHomePath.folders collect: { | p | p.folderName.asSymbol };
		if (projects.size == 0) {
			Warn(format("ERROR: No projects found in %\n", this.projectHomePath.fullPath))
		}{
			this changed: \projects;
		}
	}

	*projectHomePath { ^PathName(Platform.userHomeDir +/+ startupFolder); }
	*globalProjectPath { ^this.projectHomePath +/+ globalFolder }
	*globalAudiofilePath { ^this.globalProjectPath +/+ "audiofiles" }
	*globalSynthdefPath { ^this.globalProjectPath +/+ "synthdefs" }
	*localSynthdefPath { ^this.selectedProjectPath +/+ "synthdefs" }
	*localSetupPath { ^this.selectedProjectPath +/+ "setup" }
	*globalStartupFilePath { ^this.globalProjectPath +/+ "startup.scd" }
	*oscDataPath { ^this.projectHomePath +/+ "oscdata" }

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
			this.window({ | w |
				w.bounds = w.bounds.height_(300);
				w.name = "Projects in ~/" ++ startupFolder;
				w.layout = HLayout(
					VLayout(
						HLayout(
							StaticText().string_("Projects"),
							Button()
							.maxWidth_(50)
							.canFocus_(false)
							.states_([["setup", Color.black, Color.green]])
							.action_({ | me | this.setupProjectInGroup }),
							 Button()
							.maxWidth_(50)
							.canFocus_(false)
							.states_([["start"]])
							.action_({ | me | this.startProjectInGroup })
						),
						ListView()
						.hiliteColor_(Color(0.9, 0.9, 1.0))
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
						}),
						Button().states_([["-"]])
						.addNotifier(this, \selectedProject, { | n |
							n.listener.states_([[selectedProject]]);
						})
						.action_({
							this.broadcastSelectedProject;
						}),
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
							StaticText().string_("Project Items"),
							Button()
							.maxWidth_(50)
							.states_([
								["Cmd-.", Color.black, Color.white]
							])
							.action_({ CmdPeriod.run }),
						),
						ListView()
						.hiliteColor_(Color(0.9, 0.9, 1.0))
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
							this.selectProjectItem(projectItems[me.value]);
						})
						.enterKeyAction_({ | me |
							postf("Loading selected item: %\n", projectItems[me.value]);
							this.loadSelectedProjectItem;
						}),
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
				} .defer(0.15);
				this.getProjects;
			});
		}.fork(AppClock);
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

	*setupProjectInGroup {
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
		selectedProjectItem = projectItem;
		this.changed(\selectedProjectItem);
	}

	*loadSelectedProjectItem {
		postf("loading project item: %\n", selectedProjectItem);
		if (selectedProjectItem.isFolder) {
			// selectedProjectItem.folderName.postln;
			if (this.isAudioFileFolder(selectedProjectItem)) {
				this.loadAudioFiles(selectedProjectItem);
			}{
				this.loadScdFiles(selectedProjectItem);
			}
		} {
			this.loadScdFile(selectedProjectItem);
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

	}

	*openSelectedProjectItem {
		var path;
		path = selectedProjectItem.fullPath;
		postf("opening: %\n", path);
		if (selectedProjectItem.isFolder) {
			this.openFolder(selectedProjectItem.fullPath);
		}{
			this.openFile(path);
		}
	}

	*openFolder { | path |
		if (this.isAudioFileFolder(path)) {
			this loadAudioBuffers: path;
		}{
			this loadScdFiles: path;
		}
	}

	*openFile { | path |
		if (Platform.ideName == "scel") {
			ScelDocument.open(path);
		}{
			Document.open(path)
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