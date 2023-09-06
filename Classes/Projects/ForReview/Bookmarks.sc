/* 23 Jul 2023 12:11

Draft for saving bookmarks.
In inconsistent state!!!!!!!!!!!!!!!

Serves a dictionary of settings required to recall a bookmark for an object.
See for example FileNavigator - the preferences save several path objects.

The okAction provided by the calling object must provide the states.
//: Example:
Bookmark.prompt({ | newbookmark |
	newbookmark.states = (path: "a path", index: 15);
	bookmarks = bookmarks add: newbookmark;
	this.save;
	postln("the new bookmark is:" + newbookmark) })
//:
*/

Bookmarks {
	var <>marks;
	// <>name, <>states;

	names { ^marks collect: _.first }
	at { | index | ^marks[index][1] }

	add { | name, object |
		marks = marks add: [name, object];
	}

	removeAt { | index |
		marks remove: marks[index]
	}

	promptAdd { | object |
		{ | name |
			this.add(name, object.makeBookMark);
			object.addedBookmark;
		}
		.inputText(
			Date.getDate.stamp,
			"Enter the name for the new bookmark",
		);
	}

	promptRemove {

	}
}