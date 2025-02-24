//:  9 Aug 2021 16:08
/*
	Configure Server Options
	Load synthdefs and audio files
	As of 17 Jan 2022 01:35 this class is superseeded by class Project.
*/
Config {
	classvar >startupFolder = "~/sc-projects";
	classvar <projectName = "";
	classvar <projectPath;
	*initClassDisabled {
		StartUp add: {
			// this.serverConfig;
			Server.default doWhenReallyBooted:  { | server |
				this.loadBuffers;
				server.sync;
				postf("% finished loading buffers\n", server);
				this.loadSynthDefs;
				server.sync;
				postf("% finished loading synthdefs\n", server);
				this.loadProjectServerScripts;
				this.loadPostload;
			};
			ServerQuit.add({
				Library.put(Buffer, nil);
				Library.put(\synthdefs, nil);
			}, Server.default);
		}
	}

	*startupFolder { ^PathName(startupFolder) }
	*projectListPaths { ^this.startupFolder.entries.select(_.isFolder).collect(_.fullPath).postln }
	*serverConfig {
		this.subdirDo(
			"Loading server config scripts...",
			"... server config done",
			"serveroptions",
			{ | p |
				postf("loading: %\n", p);
				p.load;
			},
			"scd"
		);
	}

	*subdirDo { | startmessage, endmessage, subfolder, action ... filetypes |
		startmessage.postln;
		(startupFolder +/+ subfolder).filesFoldersDo(
			action, *filetypes
		);
		endmessage.postln;
	}

	*loadBuffers {
		// "loading buffers ...".postln;
		this.subdirDo(
			"loading global audio files ...",
			"... global buffers loaded",
			"global/audiofiles",
			{ | p |
				postf("loading: %\n", p);
				p.loadAudiofile;
				// p.load;
			},
			"wav",
			"WAV",
			"aif",
			"aiff"
		)
	}

	*loadSynthDefs {
		var def, name;
		this.subdirDo(
			"loading global synthdefs ...",
			"... global synthdefs loaded",
			"global/synthdefs",
			{ | p |
				postf("loading: %\n", p);
				name = PathName(p).fileNameWithoutExtension.asSymbol;
				def = p.load;
				def = def.asSynthDef(name: name);
				def.add;
				Library.put(\synthdefs, name, def);
			},
			"scd"
		)
	}

	*loadPostload {
		this.loadScScriptFiles(
			"postload",
			"loading postload ...",
			"... postload loaded")
	}

	*loadScScriptFiles { | subdir,
		beforeMessage = "loading scripts ...",
		afterMessage = "... scripts loaded" |
		this.subdirDo(
			beforeMessage,
			afterMessage,
			subdir,
			{ | p |
				postf("loading: %\n", p);
				p.load;
			},
			"scd"
		)
	}

	*start {
		"starting Project".postln;
		this.startProject;
	}

	*stop {
		"stopping Project".postln;
		this.stopProject;
	}
	*startProject {
		this.loadScScriptFiles(
			(projectName +/+ "start"),
			// ("share/projects" +/+ projectName +/+ "start"),
			format("loading start scripts for: % ...", projectName),
			format("... start scripts for % loaded", projectName)
		)
	}

	*stopProject {
		this.loadScScriptFiles(
			(projectName +/+ "stop"),
			format("loading stop scripts for: % ...", projectName),
			format("... stop scripts for % loaded", projectName)
		)
	}

	*loadProjectServerScripts {
		this.loadScScriptFiles(
			("share/projects" +/+ projectName +/+ "server_boot"),
			format("loading server scripts for: % ...", projectName),
			format("... server scripts for % loaded", projectName)
		);
	}
	//========== utilities
	*bufferNames {
		^Library.at(Buffer).keys.asArray.sort;
	}
	*projectListGui {
		/* open a panel with a list view,
			listing all project folders.
			Selecting a project and pressing RETURN sets it as current project.
			A start-stop button runs the scripts contained in the start and stop folder
			A "scripts" button opens a different gui list for selecting and running scripts.
		*/
		postf("Startup folder: %, project name: %\n",
			startupFolder, projectName
		);
		this.window({ | w |
			var projectNameWidget;
			w.name = "Project List";
			w.layout = VLayout(
				ListView()
				.items_(this.projectListPaths)
				.action_({ | me |
					me.item.postln;
				})
				.keyDownAction_({ | me, char |
					if (char === Char.ret) {
						me.item.postln;
						projectPath = me.item;
						projectName = this.folderName(projectPath);
						projectNameWidget.string = projectName;
						postf("Switched to project: %\n", projectName);
					}
				}),
				projectNameWidget = StaticText().string_("-"),
				Button()
				.states_([["start", Color.red, Color.green], ["stop", Color.green, Color.red]])
				.action_({ | me |
					this.perform([\stop, \start][me.value].postln);
				})
			)
		}, \projects);
	}

	*scriptListGui { // Under development!
		/*  Open a panel listing all scripts contained
			in the first level of the current project's folder.
			Selecting a script and pressing "return" runs that script.
		*/
	}



	*folderName { | path |
		postf("the path is: %\n", path);
		^PathName(path).folderName;

	}

}