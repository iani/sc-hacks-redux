/*  5 Sep 2023 16:27
A window for PathList.

actions: 1 add path, 2 delete path, 3 do with selected paths.
customView is created by the requesting object.
*/

PathListView {
	var key, customView, customAction, setView, pathView;
	var set, setNameField;
	*new { | key, view, action |
		^Registry(\pathlistview, key,  { this.newCopyArgs(key, view, action) })
		.makeWindow;
	}

	makeWindow {
		this.init;
		if (customView.notNil) {
			customView.action_({ customAction.(this) })
		};
		^key.vlayout(
			this.setPane,
			pathView = ListView().selectionMode_(\extended)
			.enterKeyAction_({ | me |
				me.items[me.selection] do: { | p |
					OscData.fromPath(p);
				};
			})
			.lightPalette.items_(this.getItems),
			this.innerListButtons
		).name_("Path Sets for" + key);
	}

	init {
		set = PathList.setNames(key).first;
	}

	setPane {
		^HLayout(
			setView = ListView().lightPalette.maxWidth_(250)
			.selectionAction_({ | me | this.activateSet(me.item); })
			.items_(PathList.setNames(key)),
			VLayout(
				StaticText().string_("new set...:"),
				setNameField = TextField()
				.action_({ | me | this addSet: me.string; }),
				Button().states_([["remove"]])
				.action_({ this.confirmRemoveSet })
			)
		)
	}

	addSet { | setName |
		PathList.addSet(key, setName.asSymbol);
		this.refreshViews;
	}

	refreshViews {
		setView.items = PathList.setNames(key);
		this.activateSet(setView.items.first);
	}

	activateSet { | argSet |
		set = argSet.asSymbol;
		pathView.items = PathList.get(key, set);
	}

	confirmRemoveSet {
		{
			this.removeSet;
		}.confirm("Are you sure that you want to remove set" + set + "?");
	}

	removeSet {
		PathList.removeSet(key, set);
		this.refreshViews;
	}

	innerListButtons {
		// postln("innerListButtons customView" + customView);
		// postln("innerListButtons customAction" + customAction);
		^HLayout(
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
	}

	testListSelection { // experimental
		"Testing PathListView:testListSelection".postln;
		pathView.selection.postln;
		PathList.testSelection(set, pathView.selection);
	}

	getSelection { ^PathList.getSelection(key, set, pathView.selection); }

	refreshList { pathView.items = this.getItems; }
	getItems {
		// "Testing PathListView testItems".postln;
		// postln("key" + key + "set" + set);
		// postln("get key set:" + PathList.get(key, set));
		// postln("PathList dict" + PathList.dict);
		^PathList.get(key, set) collect: _.name; }

	addPathFromUser {
		{ | paths |
			paths.postln;
			PathList.addPaths(key, set, paths);
			this.refreshList;
		}.getFilePathMultiple("Click OK to choose some files and add them.");
	}

	removePath {
		pathView.value ?? {
			^"There is no selected path to remove".postln;
		};
		PathList.removePathAt(key, set, pathView.value);
		this.refreshList;
	}

	showPath { PathList.pathAt(key, set, pathView.value ? 0).postln; }
}