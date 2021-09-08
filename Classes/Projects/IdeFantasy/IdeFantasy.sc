/*  8 Sep 2021 05:40

*/
IdeFantasy {
	// computers sending sensor data to OSCGroups:
	classvar <nodes = #['/corfu', '/sapporo'];
	classvar <localNode = '/corfu';
	classvar <remoteNode; // later this will be a list of remote nodes
	// we will make an OSCFunc for each of these nodes.
	classvar <remoteResponder, <localResponder; // OSCFuncs
	classvar <ofAddress, <oscGroupsAddress;

	*classInit {
		StartUp add: {
			ofAddress = NetAddr("127.0.0.1", 12345);
			oscGroupsAddress = NetAddr("127.0.0.1", 22244);
		}
	}

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
			.valueAction_(0),
			Button()
			.font_(Font("Helvetica", 24))
			.states_([["Start IDE-Fantasy", nil, Color.green],
				["Stop IDE-Fantasy", nil, Color.red]])
			.action_({ | me |
				this.perform([\stop, \start][me.value])
			}),
			Button()
			.font_(Font("Helvetica", 24))
			.states_([["Start OSC Tracing", Color.red, Color(0.0, 0.9, 0.9)],
				["Stop OSC Tracing", Color.red, Color(0.9, 0.9, 0.9)]])
			.action_({ | me |
				this.perform([\stopOscTrace, \startOscTrace][me.value])
			}),
			//		CheckBox()
			// .font
		);
	}

	*start {
		"Starting IDE Fantasy".postln;
		this.makeRemoteResponder;
		this.makeLocalResponder;
	}

	*makeRemoteResponder {
		// broadcast received data internally with \changed
		// + send received data locally to openFrameworks
		remoteResponder !? {
			"freeing remoteResponder".postln;
			remoteResponder.free;
		};
		remoteResponder = OSCFunc({ | msg |
			this.changed(remoteResponder);
			ofAddress.sendMsg(*msg);
		}, remoteNode)
	}

	*makeLocalResponder {
		// broadcast received data iternally with \changed
		// + send received data locally to openFrameworks @ 12345
		// + send received data to osdGroups @ 22244

		localResponder !? {
			"freeing localResponder".postln;
			localResponder.free;
		};
		localResponder = OSCFunc({ | msg |
			this.changed(localResponder);
			ofAddress.sendMsg(*msg);
			oscGroupsAddress.sendMsg(*msg);
		}, remoteNode)
	}

	*stop {
		"Stopping IDE Fantasy".postln;
	}

	*startOscTrace {
		"Starting OSC Tracing".postln;
		OSCFunc.trace(true);
	}

	*stopOscTrace {
		"Stopping OSC Tracing".postln;
		OSCFunc.trace(false);
	}
}