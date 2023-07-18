/* 18 Jul 2023 09:41
Simplify the loading of files from a directory, keeping
- action: the action to run with the result of the file loaded
  defaults to { | result | result.postln; }

*/

Load {
	// Load all .scd files from last stored default directory
	// ???? do we want this method like this? Meaning of method?
	// what does the method do?
	*new { | object, action, message = "load files from path:" |
		///		Preferences2 ....
		// object.defaultpath, object.class ...
	}

	*lastSaved { | object, action, message = "choose a file" |
	}

	*allFromFolder { | object, action, message = "choose a folder" |
	}

	*fromHistory { | object, action, message = "choose a folder" |
	}

	*fromFileDialog {  | object, action, message = "choose a file" |
	}

}

