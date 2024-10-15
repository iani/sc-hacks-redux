/*  5 Sep 2023 15:35
One more attempt to save and retrieve a list of paths ...
*/

PathList {
	classvar dict; // one list per key;

	*get { | key, set | ^this.dict.at(key, set) ? []; }

	*put { | key, set, value |
		this.dict.put(key, set, value);
		this.save;
	}

	*dict {
		dict ?? { dict = Object.readArchive(this.prefsPath) };
		dict ?? { dict = MultiLevelIdentityDictionary() };
		^dict;
	}

	*prefsPath {
		^Platform.userAppSupportDir +/+ "PathLists.scd";
	}

	*save {
		this.dict.writeArchive(this.prefsPath);
	}

	*window { | key, customView, customAction |
		// actions: 1 add path, 2 delete path, 3 do with selected paths.
		// customView is created by the requesting object.
		key = key.asSymbol;
		^PathListView(key, customView, customAction);
	}

	*addPaths { | key, set, paths |
		this.put(key, set, this.get(key, set) ++ paths);
	}

	*pathAt { | key, set, index = 0 |
		if (index >= this.get(key, set).size) {
			postln("index" + 0 + "is out of range of the path list");
			^"";
		}{
			^this.get(key, set)[index];
		}
	}
	*removePathAt { | key, set, index = 0 |
		var list;
		list = this.get(key, set);
		if (index >= list.size) {
			postln(
				"PathList removePathsAt error: index"
				+ index + "is bigger than list size" + list.size
			);
		}{
			list.removeAt(index);
		};
		this.put(key, set, list);
	}

	*getSelection { | key, set, selection |
		selection = selection ?? { [] };
		^this.get(key, set)[selection ?? { [] }].select(_.notNil);
	}

	*testSelection { | key, set, selection | // experimental
		postln("testing PathList selection. key"
			+ key + "set" + set + "selection" + selection);
		this.get(key, set)[selection].select(_.notNil) do: _.postln;
	}


	*addSet { | key, setName |
		setName = setName.asSymbol;
		if (this.setNames includes: setName) {
			postln("There is already a set named" + setName);
		}{
			this.dict.put(key, setName, []);
			this.save;
		}
	}

	*removeSet { | key, set |
		this.dict.removeEmptyAt(key, set);
	}

	*setPaths { | key |
		^this.setNames(key) collect: { | n | this.get(key, n) }
	}
	*setNames { | key | ^this.sets(key).keys.asArray.sort }
	*sets { | key | ^this.dict.at(key) ?? { IdentityDictionary() }}
}
