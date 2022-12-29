/* 25 Dec 2022 11:04
Handle loading of audio files from disk.
*/

AudioFiles : Preferences {
	// classvar preferences;
	classvar <>homeFolder; // where all audiofiles are stored
	classvar <>projects;   // dictionary of <projectName>->[pathnames]
	classvar <>selectedProject; // name of currently selected project;
	classvar <>autoload = true; // if true, then load when the server boots;

	*makeDefaults {
		^(
			homeFolder: PathName("~/sc-audofiles").fullPath,
			projects: (default: []),
			selectedProject: \default,
			autoload: true
		)
	}

	*projectFolder { ^homeFolder +/+ selectedProject  }

	*filePaths { | pathName |
		^pathName.deepFiles select:
		{ | p | ['wav', 'WAV', 'aif', 'aiff'] includes: p.extension.asSymbol }
	}

	*loadFiles {

	}

	*gui {
		^this.vlayout(
			StaticText().string_("Projects:"),
			HLayout(
				ListView(), // projects
				ListView()  // contents of selected project
			),
			StaticText().string_("Folders and files:"),
			HLayout(
				ListView(), // folders
				ListView()  // contents of selected folder
			)
		)
	}
}