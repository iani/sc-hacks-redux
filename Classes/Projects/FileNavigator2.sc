/*  3 Jan 2023 23:44
See intro in FileNavigator.sc
*/

FileNavigator2 {
	var >homeDir;  // top folder holding all items.
	var >currentRoot; // path for getting outerList items
	var <outerList, <outerItem, <outerIndex = 0;
	var <innerList, <innerItem, <innerIndex = 0;

	getItems {
		outerList = this.getOuterListItesm;
		if (outerList.size == 0) {
			^postln("No items found in" + currentRoot.fullPath);
		};
		outerIndex = 0;
		outerItem = outerList[outerIndex];
		this.changed(\outerList);
		this.getInnerList;
	}

	// subclasses may overwrite this:
	getOuterListItesm { ^this.currentRoot.folders; }
	// provide defaults for homeDir and currentRoot
	currentRoot { ^currentRoot ?? { currentRoot = this.homeDir } }
	homeDir { ^homeDir ?? { homeDir = (PathName(Platform.userHomeDir) +/+ "sc-projects").asDir; } }


	getInnerList {
		if (outerItem.isNil) {
			^postln("No outer item is selected. cannot get linner list");
		};
		innerList =  this.getInnerListItems;
		innerIndex = 0;
		innerItem = innerList[innerIndex];
		this changed: \innerList;
	}

	getInnerListItems { ^outerItem.entries; }

	// make outerItem the currentRoot
	zoomIn {
		currentRoot = outerItem;
		this.getItems;
	}

	// make currentRoot's superfolder the currentRoot;
	zoomOut {
		if (this.currentRoot.asDir.fullPath == this.homeDir.asDir.fullPath) {
			^postln("Cannot go above the homeDir: " + homeDir)
		};
		currentRoot = currentRoot.up;
		this.getItems;
	}

}