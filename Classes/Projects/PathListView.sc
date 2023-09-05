/*  5 Sep 2023 16:27
A window for PathList.

actions: 1 add path, 2 delete path, 3 do with selected paths.
customView is created by the requesting object.

*/

PathListView {
	var key, customView, customAction, listView;
	*new { | key, view, action |
		key = key.asSymbol;
		^Registry(\pathlistview, key, {this.newCopyArgs(key, view, action)})
		.postln
		.makeWindow;
	}

	makeWindow {
		if (customView.notNil) {
			customView.action_({ customAction.(this) })
		};
		^key.vlayout(
			listView = ListView().selectionMode_(\extended).items_(this.getItems),
			HLayout(
				Button().states_([["add"]]).action_({ this.addPathFromUser }),
				Button().states_([["remove"]]).action_({ this.removePath }),
				Button().states_([["show path"]]).action_({ this.showPath }),
				customView ?? {
					Button().states_([["experimental"]])
					.action_({
						"action should be provided when making me.".postln;
					})
				}
			)
		)
	}

	testListSelection { // experimental
		"Testing PathListView:testListSelection".postln;
		listView.selection.postln;
		PathList.testSelection(key, listView.selection);
	}

	getSelection { ^PathList.getSelection(key, listView.selection); }

	refreshList { listView.items = this.getItems; }
	getItems { ^PathList.get(key) collect: _.name; }

	addPathFromUser {
		{ | paths |
			paths.postln;
			PathList.addPaths(key, paths);
			this.refreshList;
		}.getFilePathMultiple("Click OK to choose some files and add them.");
	}

	removePath {
		listView.value ?? {
			^"There is no selected path to remove".postln;
		};
		PathList.removePathAt(key, listView.value);
		this.refreshList;
	}

	showPath { PathList.pathAt(key, listView.value ? 0).postln; }
}