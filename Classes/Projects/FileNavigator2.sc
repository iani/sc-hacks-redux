/*  3 Jan 2023 23:44
See intro in FileNavigator.sc
*/

FileNavigator2 {
	var >homeDir;  // top folder holding all items.
	var >currentRoot; // path for getting outerList items
	var <outerList, <outerItem, <outerIndex = 0;
	var <innerList, <innerItem, <innerIndex = 0;

	// provide defaults for homeDir and currentRoot
	homeDir {
		^homeDir ?? { homeDir = PathName(Platform.userHomeDir) +/+ "sc-projects"; }
	}

	currentRoot {
		^currentRoot ?? { currentRoot = this.homeDir }
	}

	getItems {
		outerList = this.currentRoot.folders;
		if (outerList.size == 0) {
			^postln("No items found in" + currentRoot.fullPath);
		};
		outerIndex = 0;
		this.changed(\outerList);
		this.getInnerList;
	}

	getInnerList {
		outerItem = outerList[outerIndex];
		if (outerItem.isNil) {

		};
		innerList = outerItem.entries;
		innerIndex = 0;
		this changed: \innerList;
	}

	// make outerItem the currentRoot
	enter {
		currentRoot = outerItem;
		this.getItems;
	}

	// make currentRoot's superfolder the currentRoot;
	exit {
		if (currentRoot == this.homeDir) {
			^postln("Cannot go above the homeDir: " + homeDir)
		};
		currentRoot = currentRoot.up;
		this.getItems;
	}

}