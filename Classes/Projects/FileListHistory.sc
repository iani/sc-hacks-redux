/* 20 Jun 2023 12:27

*/

FileListHistory {
	// Hols a list of FileLists, where each filelist is loaded
	// by a user by selecting the files over file dialog.
	// The list is saved on file so that user selections persist across
	// working sessions.
	// THe list can only hold up to maxitems FileLists.
	// When this size limit is reached, adding a new FileList
	// will discard the oldest filelist in the list.
	var <path; // path where I save myself
	var <maxitems = 100; // maximum number of items permitted
	var <lists;

	*new { | path, maxitems = 100 |
		^this.newCopyArgs(path, maxitems, [])
	}

	save { this.writeArchive(path); }

	// Note: Wed 14 Jun 2023 13:48 runnning SC version 3.13.0,
	// Dialog.openpanel and FileDialog()
	// generate a warning on the post window:
	// 2023-06-14 13:46:26.267 sclang[9564:285464] +[CATransaction synchronize] called within transaction

	addListFromUser {
		Dialog.openPanel({ | argPaths |
			this add: FileList(argPaths);
		}, multipleSelection: true)
	}

	addListFromUser2 { // generates strange warning.
		FileList.fromUser({ | list | this add: list })
	}

	add { | list |
		lists = lists add: list;
		if (lists.size > maxitems) { lists = lists[1..] };
		this.save;
		this changed: \history
	}

	removeAt { | index |
		this.remove(lists[index]);
	}
	remove { | list |
		lists remove: list;
		this.save;
		this changed: \history;
	}
}