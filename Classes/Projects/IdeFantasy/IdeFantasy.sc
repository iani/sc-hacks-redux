/*  8 Sep 2021 05:40

*/
IdeFantasy {
	// computers sending sensor data to OSCGroups:
	classvar <nodes = #['/corfu', '/sapporo'];
	classvar <localNode = '/corfu';
	classvar <remoteNode; // later this will be a list of remote nodes
	// we will make an OSCFunc for each of these nodes.
	classvar <remoteResponder, <localResponder; // OSCFuncs

	*gui {
		this.tr_.v(
			StaticText().string_("Select the name of your node:")
			.font_(Font("Helvetica", 24)),
			ListView()
			.font_(Font("Helvetica", 24))
			.items_(nodes)
			.action_({ | me |
				localNode = me.items[me.value];
				remoteNode = me.items.allBut(localNode).first;
				postf("local node: %, remote node: %\n", localNode, remoteNode);
			})
			.valueAction_(0)
		);
	}

	*start {
		this.makeRemoteResponder;
		this.makeLocalResponder;
	}

	*makeRemoteResponder {
		// broadcast received data internally with \changed
		// + send received data locally to openFrameworks
		remoteResponder !? {
			"freeing oscGroupResponder".postln;
			remoteResponder.free;
		};
		remoteResponder = OSCFunc({
			"nothing here yet".postln;
		}, remoteNode)
	}

	*makeLocalResponder {
		// broadcast received data iternally with \changed
		// + send received data locally to openFrameworks @ 12345
		// + send received data to osdGroups @ 22244

	}
}