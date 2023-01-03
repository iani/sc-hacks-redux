/* 26 Dec 2022 17:34

Creates listviews for navigating the contents of folders
inside a home folder.

Based on the new version of Project.
!!!!!!!!!!!!!!!!!!!!!!!
See FileNavigator2

*/

FileNavigator {
	var <>homeFolder;  // folder containing all project folders
	var <>projectFolder; // current project folder
	var <>projectSuperfolder; //  immediate super-folder of projectFolder
	//========================================
	var <projects, <projectIndex = 0;
	var <projectItems, <projectItemIndex = 0;
	var <preferences; // Dictionary

	// for testing only!
	*new {
		^this.newCopyArgs(\myHomeFolder, (1..3), \x);
	}

	getProjects {
		projects = this.projectSuperfolder.folders;
		if (projects.size == 0) {
			^postln("No folders found in" + this.projectSuperfolder.fullPath);
		};
		projectItemIndex = 0;
		this changed: \projects;
		this.getProjectItems;
	}

	getProjectItems {
		projectFolder = projects[projectIndex];
		if (projectFolder.isNil) {
			^postln("No projects at" + projectIndex + "\n Projects are:" + projects);
		};
		projectItems = projectFolder.entries;
		projectItemIndex = 0;
		// this.savePreferences;
		this changed: \projectItems;
	}

	saveBookmark {

	}

	gotoBookmark {

	}
	setProjectHomeDialog {

	}

	goHome {

	}

	enterFolder {

	}

	exitFolder {

	}

	help {

	}

	parentListView {
		^ListView()
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
	}

	childListView {
		^ListView()
		.palette_(QPalette.dark
			.highlight_(Color(0.1, 0.1, 0.7))
			.highlightText_(Color(0.9, 0.8, 0.7))
		)
		.addNotifier(this, \projectItems, { | n |
			n.listener.items = projectItems collect: _.shortName;
		})
	}
}

// testing FileNavigator
Fntest : Preferences {

	classvar <>navigator;
	classvar <>projects;
	*makeDefaults {
		^(
			navigator: FileNavigator(),
			projects: []
		)
	}

}