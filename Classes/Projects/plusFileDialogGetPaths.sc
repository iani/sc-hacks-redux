/* 11 Jul 2023 15:01
Utility shortcuts for opening dialogs that choose a single file or folder.
*/

+ FileDialog {

	*getFile { | action, message | this.getPath(action, message, \fileDialog, "file") }

	*getFolder { | action, message | this.getPath(action, message, \folderDialog, "folder") }

	*getPath { | action, message, method, type |
		// If message provided, then display an informative message before the opening dialog
		if (message.notNil) {
			{ this.perform(method, action)	}.confirm(message, "Choose a" + type);
		}{
			this.perform(method, action);
		}
	}

	*fileDialog { | action |
		this.new(action, nil, 1, 0, true)
	}

	*folderDialog { | action |
		this.new(action, nil, 2, 0, true)
	}
}