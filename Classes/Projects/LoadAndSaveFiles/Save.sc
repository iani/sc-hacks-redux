/* 18 Jul 2023 09:46
Simplify the saving of a string to files ind a directory.

string: the string to save
filename: The filename where the file should be saved
defaultFolder: a method in object which provides the default folder
where the file should be saved.  If the folder does not exist, then
the user is prompted to choose another folder, and tha folder
gets stored as the folder for storing the files from then on.

Object must provide default path in response to message "defaultPath".

Both Save and Load classes will use class FolderPreferences

*/

Save {
	*new { | object, string, message = "save string to path:" |
		// FolderPreferences.folderFor(object.class);
	}
}