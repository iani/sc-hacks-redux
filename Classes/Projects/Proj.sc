/* 24 Nov 2022 13:59
Experimental:
Run scripts from the currently selected sc-projects folder by their filename.

Example:

Proj.test; // run test.scd from the project folder currently seleced in Project gui.
*/

Proj {
	*doesNotUnderstand { | methodName |
		// run the currently selected file locally
		var path, code;
		path = (Project.selectedProjectPath +/+ methodName.asString).fullPath ++ ".scd";
		postln("Loading:" + path);
		code = File.readAllString(path);
		code.interpret; // this does not forward the code to OscGroups
	}

	*getPath { | filename |
		// get absolute path for this filename
		^(Project.selectedProjectPath +/+ filename).fullPath;
	}

	*gui { this.doesNotUnderstand('gui') }
}