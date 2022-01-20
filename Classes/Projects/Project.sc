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
	classvar >startupFolder = "sc-projects", >globalFolder = "global";
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
			}
	 	}
	}

	*matchingFilesDo { | pathName, func ... types |
		types = types collect: _.asSymbol;
		if (pathName.isNil) { ^nil };
		pathName filesDo: { | p | // ! filesDo recurses over subfolders!
			// postf("types is: %, extension is: %\n", types, p.extension);
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

	*loadScdFiles { | pathName |
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
		"loading local buffers".postln;
		this.loadAudioFiles(this.localAudiofilePath);
	}

	*loadGlobalSynthdefs {
		"loading global synthdefs".postln;
		this.loadScdFiles(this.globalSynthdefPath);
	}

	*loadLocalSynthdefs {
		"loading local synthdefs".postln;
		this.loadScdFiles(this.localSynthdefPath);
	}

	*projectHomePath { ^PathName(Platform.userHomeDir +/+ startupFolder); }
	*globalProjectPath { ^this.projectHomePath +/+ globalFolder }
	*globalAudiofilePath { ^this.globalProjectPath +/+ "audiofiles" }
	*globalSynthdefPath { ^this.globalProjectPath +/+ "synthdefs" }

	*localAudiofilePath {
		if (selectedProject.isNil) { ^nil };
		^selectedProject +/+ "audiofiles";
	}

	*localSynthdefPath {
		if (selectedProject.isNil) { ^nil };
		^selectedProject +/+ "synthdefs";
	}

	*getAudioFilePaths { | pathName |
		// return PathNames of all audiofiles
		// contained in folder pathName or its subfolders
		var types;
		types = ['wav', 'WAV', 'aif', 'aiff'];
		^pathName.deepFiles select: { | p | types includes: p.extension.asSymbol }
	}


	*scdFilesDo { | pathName, func |

	}

	*globalAudioFiles {

	}

	*globalSynthdefFiles {

	}

	*localAudioFiles {

	}

	*localSynthdefFiles {

	}

	*gui {
		this.window({ | w |
			w.name = "Projects in ~/" ++ startupFolder;
			w.layout = HLayout(
				VLayout(
					StaticText().string_("Projects"),
					ListView()
					.hiliteColor_(Color(0.9, 0.9, 1.0))
					.addNotifier(this, \projects, { | n |
						// postf("% will set my items to projects: %\n", n.listener, projects);
						n.listener.items =  projects.collect(_.folderName);
					})
					.enterKeyAction_({ | me |
						this.selectProject(projects[me.value]);
					}),
					Button().states_([["-"]])
					.addNotifier(this, \selectedProject, { | n |
						n.listener.states_([[selectedProject.folderName]])
					})
				),
				VLayout(
					StaticText().string_("Project Items"),
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
					.enterKeyAction_({ | me |
						postf("Loading selected item: %\n", projectItems[me.value]);
						this.runProjectItem(projectItems[me.value]);
					}),
					Button().states_([["-"]])
					.addNotifier(this, \selectedProject, { | n |
						n.listener.states_([["-"]])
					})
					.addNotifier(this, \selectedProjectItem, { | n |
						n.listener.states_([[this.selectedProjectItemName]])
					})
				)
			);
			this.getProjects;
		});
	}

	*getProjects {
		projects = this.projectHomePath.entries.select(_.isFolder);
		this.changed(\projects);
	}
	*selectProject { | projectPathName |
		selectedProject = projectPathName;
		this.getProjectItems;
		this.changed(\selectedProject);
	}
	*getProjectItems {
		postf("testing getProjectItems.  selectedProject is: %\n", selectedProject);
		projectItems = selectedProject.entries.reject({ | e | e.isFile and: { e.extension != "scd" } });
		this.changed(\projectItems);
	}
	*runProjectItem { | projectItem |
		selectedProjectItem = projectItem;
		if(selectedProjectItem.isFolder) {
			postf("folder name is: %, and audiofiles match is: %\n",
			projectItem.folderName, projectItem.folderName == "audiofiles"
			);
			if (projectItem.folderName == "audiofiles") {
				this.audiofilesGui(projectItem)
			}{
			postf("Running items in folder: %\n", selectedProjectItem.fullPath);
			this.loadScdFiles(selectedProjectItem);
			}
			// selectedProjectItem.filesDo({ | f |
			// 	postf("loading file: %\n", f.fileName);
			// 	f.fullPath.load;
			// })
	}{
			postf("loading file: %\n", selectedProjectItem.fileName);
			selectedProjectItem.fullPath.load;
		};
		this.changed(\selectedProjectItem);
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
				.enterKeyAction_({ | me |
					// must write method to play the file from disk
					// to enable browsing audio files even if they are not yet loaded.
				})
			)
		});
	}

}