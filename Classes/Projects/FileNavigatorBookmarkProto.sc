/* 23 Jul 2023 12:34
	Prototype for implementing bookmarks.
	FileNavigatorB.gui;

Temporarily discontinued.
*/

FileNavigatorB : FileNavigator {
	bookmarksbutton {
		^Button().states_([["Bookmark"]]).maxWidth_(50)
		.action_({ Menu(
			MenuAction("Make Bookmark", { this.addBoomark }),
			*(bookmarks.marks.collect({ |m| MenuAction(m[0], { this.gotoBookmark(m[1]) }) }))
		).front })
		.addNotifier(this, \bookmarks, { | n |
			// n.listener.items = ["add new bookmark"] ++ this.bookmarks.names;
			"I will update my menu button here".postln;
		})
	}
	bookmarkAction {
		^{
			"Blah saving".postln; this.save

		}
	}

	addBookmark {
		this.bookmarks.promptAdd(this);
	}

	makeBookMark {
		^(
			homeDir: homeDir,
			currentRoot: currentRoot,
			outerItem: outerItem,
			outerIndex: outerIndex,
			innerItem: innerItem,
			innerIndex: innerIndex,
		)
	}

	addedBookmarks {
		this.changed(\bookmarks);
		"saving bookmarks".postln;
		this.save;
	}
	loadBookmark { | index |
		bookmarks.at(index).postln;
	}

}