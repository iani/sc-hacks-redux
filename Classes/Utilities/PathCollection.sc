// 火  3  6 2025 19:50
// Manage a collection of paths

PathCollection : NamedSingleton2 {
	var <>paths;

	*initClass { StartUp add: { this.init; }; }
	*init { this.makeBaseDirectory; }

	*makeBaseDirectory {
		if (File.exists(this.baseDirectory).not) {
			File.mkdir(this.baseDirectory)
		}
	}

	*baseDirectory { ^Platform.userAppSupportDir +/+ "PathCollections"; }

	init { this.readPaths; }

	readPaths {
		var homePath;
		homePath = this.homePath;
		if (File exists: homePath) {
			paths = homePath.load;
		}{
			paths = []
		}
	}

	homePath { ^this.class.baseDirectory +/+ name ++ ".scd"; }

	gui {
		var window, pathList, index = 0;
		var selectedPathField, deleteButton, addButton;
		window = this.vlayout(
			pathList = ListView(),
			HLayout(
				deleteButton = Button().maxWidth_(55).states_([["Delete:"]]),
				selectedPathField = TextField(),
				addButton = Button().maxWidth_(55).states_([["Add new"]]),
			)
		);
		window.bounds = Rect(0, 0, 600, 200);
		this.addNotifier(window, \objectClosed, { this.save; });
		pathList.hiliteColor = Color.grey(0.8);
		pathList.selectedStringColor = Color.red;
		pathList.items = paths;
		pathList.action = { | me |
			index = me.value;
			selectedPathField.string = paths[index];
		};
		window.addNotifier(this, \paths, {
			pathList.items = paths;
			index = 0;
			selectedPathField.string = paths[index];
		});
		selectedPathField.string = paths[index];
		addButton.action = { this.readPathFromUser };
		deleteButton.action = {
			this.window({ | w |
				w.layout = VLayout(
					StaticText().string_("Really delete this path?:"),
					TextField().string_(paths[index]),
					HLayout(
					Button().states_([["DELETE"]])
						.action_({
							paths remove: paths[index];
							this.save;
							pathList.items = paths;
							index = 0;
							if (pathList.size > 0) {
								selectedPathField.string = pathList[index];
							}{ selectedPathField.string = "" };
							w.close;
						}),
					Button().states_([["CANCEL"]])
						.action_({ w.close })
					)
				);
			}, \confirmDelete);
		};
	}

	readPathFromUser {
		FileDialog({ | path |
			paths = paths add: path[0];
			this.save;
			this.changed(\paths);
		});
	}

	save {
		paths writeArchive: this.homePath;
		postln("Saved" + paths.size + "paths.");
	}

}