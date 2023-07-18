/* 18 Jul 2023 09:46
Simplify the saving of a string to files ind a directory.

string: the string to save

Object must provide default path in response to message "defaultPath".

Both Save and Load classes will use class FolderPreferences

*/

Save {
	*new { | object, string, message = "save string to path:" |
		// FolderPreferences.folderFor(object.class);
	}
}