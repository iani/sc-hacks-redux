/* 9 Jan 2023 09:55

Utility for creating groups (arrays or "sets") of files and folders
and storing each set under name so that it can be retrieved
to load (or remove) its elements. Use:

Navigate in a IdentityDictionary using 2 panes.
Add and remove items in this dictionary.

Values of the Dictionary are Arrays.
The elements of the Arrays are PathNames (Folders or Files).

Each array is an "asset" or just "set", and can be accessed by the name (key)
under which it is stored in the Dictionary.  Get the array
stored under a key, and then load its elements.

*/

DictNavigator {
	var <>key = \default; // for storing the settings in Preferences
	var >dict;  // top folder holding all items.
	var <>outerList, <>outerItem, <outerIndex = 0;
	var <>innerList, <>innerItem, <innerIndex = 0;

	var >currentRoot; // not needed! (TODO: remove this variable)

	*new { | key = \default | ^this.newCopyArgs(key).getOuterItems }

	save {
		postln("Saving preferences for:" + this.class.name + "at key" + key + "prefs" + this.prefs);
		Preferences.put(this.class.name, key, this.prefs);
	}

	prefs {
		// save info needed to regenerate state,
		// plus human-readable relevant info.
		^(
			dict: dict,
			currentRoot: currentRoot,
			outerItem: outerItem, // human readable
			outerIndex: outerIndex,
			innerItem: innerItem, // human readable
			innerIndex: innerIndex
		)
	}

	help {
		"Keyboard shortcuts on list views:".postln;
		". : save bookmark".postln;
		", : go to bookmark".postln;
		"/ : set project home file dialog".postln;
		"! : go home (home root folder)".postln;
		"> : zoom in".postln;
		"< : zoom out".postln;
		"^ : zoom out".postln;
		"? : help".postln;
	}

	// Load and go to last saved bookmark position from preferences.
	load {
		var prefs;
		prefs = Preferences.get(this.class.name, key);
		prefs ?? {
			^postln("Could not get preferences for" + this.class.name + "at" + key);
		};
		dict = prefs[\dict];
		currentRoot = prefs[\currentRoot];
		outerIndex = prefs[\outerIndex];
		innerIndex = prefs[\innerIndex];
		this.changed(\setOuterListAndIndex,
			currentRoot.folders,
			outerIndex
		);
	}

	goHome {
		currentRoot = this.dict;
		this.getOuterItems;
	}

	setProjectHomeDialog {
		FileDialog({ | path |
			dict = PathName(path[0]).asDir;
			this.goHome;
		}, {}, 2)
	}

	getOuterItems {
		outerList = this.getOuterListItems;
		if (outerList.size == 0) {
			^postln("No items found in" + currentRoot.fullPath);
		};
		outerIndex = 0;
		outerItem = outerList[outerIndex];
		this.changed(\outerItems);
		// when updating outer items, always update inner items too
		innerIndex = 0;
		this.getInnerItems;
	}

	// update contents of inner list when selecting new outer item
	getInnerItems {
		innerList = this.getInnerListItems;
		innerItem = innerList[innerIndex];
		this.changed(\innerItems);
	}

	// subclasses may overwrite this:
	getOuterListItems { ^this.dict.keys.asArray.sort; }
	getInnerListItems { ^outerItem.entries; }
	// provide defaults for dict and currentRoot
	currentRoot { ^currentRoot ?? { currentRoot = this.dict } }
	dict { ^dict ?? { dict = IdentityDictionary(); } }

	// make outerItem the currentRoot
	zoomIn {
		this.setCurrentRoot(outerItem);
	}

	setCurrentRoot { | argNewRoot |
		if (argNewRoot.folders.size == 0) {
			postln("Cannot zoom into this folder");
			postln(argNewRoot);
			"Because it has no subfolders".postln;
			^nil;
		};
		currentRoot = argNewRoot;
		this.getOuterItems;
	}

	// make currentRoot's superfolder the currentRoot;
	zoomOut {
		if (this.currentRoot.asDir.fullPath == this.dict.asDir.fullPath) {
			^postln("Cannot go above the dict: " + dict)
		};
		currentRoot = currentRoot.up;
		this.getOuterItems;
	}

	*gui {
		var dn;
		dn = this.new;
		{ dn.getOuterItems }.defer(0.1);
		^dn.hlayout(
			dn.outerListView,
			dn.innerListView
		)
	}

	outerListView {
		^ListView()
		.addNotifier(this, \setOuterListAndIndex, { | n, list, outerIndex |
			outerList = list;
			n.listener.items = outerList collect: _.shortName;
			n.listener.value = outerIndex;
			outerItem = outerList[outerIndex];
			innerList = outerItem.entries;
			// NOTE: deferred selectionAction triggers update of inner list!!!
		})
		.addNotifier(this, \outerItems, { | n |
			var index;
			// restore index overwritten by selectionAction!
			index = outerIndex;
			{
				outerIndex = index;
				this.changed(\outerItem);
			}.defer(0.01);
			n.listener.items = this.outerListNames;
		})
		.addNotifier(this, \outerItem, { | n |
			n.listener.value = outerIndex;
		})
		.selectionAction_({ | me |
			this.outerIndex = me.value;
		})
		.keyDownAction_(this.keyboardShortcutAction);
	}

	outerIndex_ { | val |
		outerIndex = val;
		outerItem = this.outerList[outerIndex];
		this.changed(\outerItem);
		this.getInnerItems;
	}
	outerListNames {
		^(outerList ? []) collect: _.shortName;
	}

	innerListView {
		^ListView()
		.addNotifier(this, \innerItems, { | n |
			// restore index overwritten by selectionAction!
			var index;
			index = innerIndex;
			{
				innerIndex = index;
				this.changed(\innerItem);
			}.defer(0.01);
			n.listener.items = this.innerListNames;
		})
		.addNotifier(this, \innerItem, { | n |
			n.listener.value = innerIndex;
		})
		.selectionAction_({ | me |
			this.innerIndex = me.value;
		})
		.keyDownAction_(this.keyboardShortcutAction);
	}

	innerIndex_ { | val |
		val ?? {
			"No items found in this folder".postln;
			outerItem.postln;
			^"Exiting. Please select a different folder".postln;
		};
		innerIndex = val;
		innerItem = innerList[innerIndex];
		this.changed(\innerItem);
	}

	innerListNames {
		^(innerList ? []) collect: _.shortName;
	}
	keyboardShortcutAction {
		^{ | me, char, modifiers, unicode, keycode, key |
			switch (char,
				$., { this.saveBookmark; },
				$,, { this.gotoBookmark; },
				$/, { this.setProjectHomeDialog; },
				$!, { this.goHome; },
				$>, { this.zoomIn; },
				$<, { this.zoomOut; },
				$^, { this.zoomOut; },
				$?, { this.help; },
				me.defaultKeyDownAction(char, modifiers, unicode, keycode, key);
			)
		}
	}
	saveBookmark { this.save }
	gotoBookmark { this.load }

	addFileDialog {

	}

	addFolderDialog {

	}

	deleteSubtree {

	}

	deleteEntry {

	}
}