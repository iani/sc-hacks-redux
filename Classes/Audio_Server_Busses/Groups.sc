/*  5 Dec 2022 10:30
Manage groups. Keep groups in order by re-making them on ServerTree
*/

Groups { // We don't use instances of Groups
	classvar <order = #[], dict;
	classvar names; // to display names on plot

	*initClass {
		ServerTree add: this;
	}

	*doOnServerTree {
		this.remakeGroups;
	}

	// free all groups and add new array
	*set { | groups |
		this.free;
		this.add(groups);
	}

	// free all groups
	*free {
		this.dict do: _.free;
		order = [];
		dict = ();
		names = ();
	}

	// add an array of groups
	// Note: a group that already exists is not added again
	*add { | groups |
		groups.asArray.copy.reverse do: this.makeGroup(_)
	}

	// remake all groups (on ServerTree!)
	*remakeGroups {
		var makeOrder;
		// postln("REMAKING GROUPS");
		makeOrder = order.copy.reverse;
		// postln("Make order is:" + makeOrder);
		order = [];
		dict = ();
		makeOrder do: { | key | this.makeGroup(key) }
	}

	*makeGroup { | key |
		var new;
		if (key == \default_group) {
			^Server.default.defaultGroup; // Groups never touches this group
		};
		new = this.dict[key];
		// postln("makeGroup search for group at" + key + "found" + new);
		if (new.isNil) {
			new = Group();
			this.names[new.nodeID] = key;
			// postln("made new group:" + new);
			this.dict[key] = new;
			order = [key] ++ order;
		};
		^new;
	}

	// dict { ^this.class.dict }
	*dict { ^dict ?? { dict = () }; }
	*names { ^names ?? { names = () } }

	*plot { Server.default.plotTree2 } // plot with group names
}

+ Symbol {
	asTarget { ^this.group }
	group { ^Groups.makeGroup(this) }
}

+ Array {
	groups { ^this.addGroups }
	addGroups { Groups.add(this) }
	setGroups { Groups.set(this); }
}