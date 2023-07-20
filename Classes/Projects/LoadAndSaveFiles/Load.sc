/* 18 Jul 2023 09:41
Simplify the loading of files from a directory, keeping
- action: the action to run with the result of the file loaded
  defaults to { | result | result.postln; }

Possible other method names:

loadLastSaved
stringLastSaved
pathLastSaved
---
loadAllFromFolder
pathsAllFromFolder
loadFromHistory
stringFromHistory
loadFromFileDialog
pathFromFileDialog
*/

Load {
	// Do we need a "new" method like this? Meaning of method?
	// what does the method do?
	*new { | object, action, message = "load files from path:" |
		///		Preferences2 ....
		// object.defaultpath, object.class ...
	}

	*lastSaved { | object, action, message = "choose a file" |
	}

	// ppaths of all files from last stored default directory (matching ... ".scd" ???)
	*allFromFolder { | object, action, message = "choose a folder" |
	}

	*fromHistory { | object, action, message = "choose a folder" |
	}

	*fromFileDialog {  | object, action, message = "choose a file" |
	}

}

