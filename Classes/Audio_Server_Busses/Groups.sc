/*  5 Dec 2022 10:30
Manage groups. Keep groups in order by re-making them on ServerTree
*/

Groups { // We don't use instances of Groups
	classvar <order = #[], dict;

	*initClass {
		ServerTree add: this;
	}

	*doOnServerTree {
		this.remakeGroups;
	}

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
		new = this.dict[key];
		// postln("makeGroup search for group at" + key + "found" + new);
		if (new.isNil) {
			new = Group();
			// postln("made new group:" + new);
			this.dict[key] = new;
			order = [key] ++ order;
		};
		^new;
	}

	// dict { ^this.class.dict }
	*dict { ^dict ?? { dict = () }; }

	*plot { Server.default.plotTree }
}

+ Symbol {
	asTarget { ^this.group }
	group { ^Groups.makeGroup(this) }
}

+ Array {
	groups { ^this.reverse collect: _.group }
}