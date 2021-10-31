/*  8 Sep 2021 05:40
TODO: Enable choice of different config/share/project folders with new class Project.
	All class methods of IdeFantasy can be rewritten as scripts
	which are placed in startup, serverboot, start and quit subfolders
	of the share/project folder.
	TODO: fix gui for selecting remote node correctly.
	TODO: Multiple remote nodes????
*/
IdeFantasy {
	// computers sending sensor data to OSCGroups:
	classvar <localNode = '/corfu';
	// 31 Oct 2021: all nodes will now create responders
	classvar <nodes = #['/corfu', '/athens'];
	classvar <remoteNode; // 31 Oct 2021: obsolete. all nodes create responders.
	classvar <dataMessage; // osc message sent by sensestage
	classvar <localResponder; // OSCFunc listening to local data from sensors
	classvar <remoteResponders; // Array of OSCFuncs listening to messages from all nodes
	classvar <ofAddress, <oscGroupsAddress;

	*initClass {
		StartUp add: {
			ofAddress = NetAddr("127.0.0.1", 12345);
			oscGroupsAddress = NetAddr("127.0.0.1", 22244);
			dataMessage = '/minibee/data';
			Config.projectName = "ide_fantasy_211029";
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
			Button()
			.font_(Font("Helvetica", 24))
			.states_([["Start Project Trace", Color.blue, Color(0.0, 0.9, 0.9)],
				["Stop Project Trace", Color.blue, Color(0.9, 0.9, 0.9)]])
			.action_({ | me |
				this.perform([\stopProjectTrace, \startProjectTrace][me.value])
			})
			//		CheckBox()
			// .font
		);
	}

	*start {
		"Starting IDE Fantasy".postln;
		thisProcess.openUDPPort(22245);
		this.makeRemoteResponders;
		this.makeLocalResponder;
		// load piece from scripts.
		Config.startProject;
	}

	*makeRemoteResponders { | argResponder |
		this.freeRemoteResponders;
		// make new responders;
		remoteResponders = nodes collect: { | name |
			OSCdef(name, { | msg |
				this.changed(*msg);
				ofAddress.sendMsg(*msg);
			}, name).fix;
		}
	}

	*freeRemoteResponders {
		"Freeing remote responders".postln;
		remoteResponders do: _.free; // free remote responders, if any
	}

 	*makeLocalResponder {
		// make received data known to local system using this.changed
		// + send received data locally to openFrameworks
		localResponder !? {
			"freeing localResponder".postln;
			localResponder.free;
		};
		postf("addding responder listening to: %\n", localNode);
		localResponder = OSCFunc({ | msg |
			// postf("received local sensestage data: %\n", msg);
			msg[0] = localNode;
			this.changed(*msg);
			ofAddress.sendMsg(*msg);
			oscGroupsAddress.sendMsg(*msg);
		}, '/minibee/data').fix;
	}

	*stop {
		"Stopping IDE Fantasy".postln;
		this.freeRemoteResponders;
		localResponder !? {
			"freeing localResponder".postln;
			localResponder.free;
		};
		Config.stopProject;
	}

	*startOscTrace {
		"Starting OSC Tracing".postln;
		OSCFunc.trace(true);
	}

	*stopOscTrace {
		"Stopping OSC Tracing".postln;
		OSCFunc.trace(false);
	}
	*startProjectTrace {
		"Starting Project Tracing".postln;
		this addDependant: this;
	}
	*stopProjectTrace {
		"Stopping OSC Tracing".postln;
		this removeDependant: this;
	}

	*update { | ... args |
		postf("% received update with: %\n", this, args);
	}
}