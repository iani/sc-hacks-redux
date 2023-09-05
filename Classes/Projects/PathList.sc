/*  5 Sep 2023 15:35
One more attempt to save and retrieve a list of paths ...
*/

PathList {
	classvar dict; // one list per key;


	*get { | key | ^this.dict[key.asSymbol] ? []; }

	*put { | key, value |
		this.dict[key] = value;
		this.save;
	}

	*dict {
		dict ?? { dict = Object.readArchive(this.prefsPath) };
		dict ?? { dict = IdentityDictionary() };
		^dict;
	}

	*prefsPath {
		^Platform.userAppSupportDir +/+ "PathLists.scd";
	}

	*save {
		this.dict.writeArchive(this.prefsPath);
	}

	*window { | key, customView |
		// actions: 1 add path, 2 delete path, 3 do with selected paths.
		// customView is created by the requesting object.
		key = key.asSymbol;
		^PathListView(key)
	}

	*addPaths { | key, paths | this.put(key, this.get(key) ++ paths); }
	*pathAt { | key, index = 0 |
		if (index >= this.get(key).size) {
			postln("index" + 0 + "is out of range of the path list");
			^"";
		}{
			^this.get(key)[index];
		}
	}
	*removePathAt { | key, index = 0 |
		var list;
		list = this.get(key);
		if (index >= list.size) {
			postln(
				"PathList removePathsAt error: index"
				+ index + "is bigger than list size" + list.size
			);
		}{
			list.removeAt(index);
		};
		this.put(key, list);
	}
}