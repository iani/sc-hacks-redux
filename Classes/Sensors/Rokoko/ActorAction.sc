// 月 12  5 2025 15:29
// Poll a bus with all controllers of an Actor.
// Add actions for modifying data from rokoko.

ActorAction {
	classvar <dataIndexDict, busIndexDict;
	classvar <busIndex = 0; // increment to calculate absolute bus indices
	var <actor, actions;
	var pollRoutine;

	*new { | actor |
		^this.newCopyArgs(actor).init;
	}

	init { // create index dictionaries if needed
		dataIndexDict ?? { this.class.makeIndexDicts; };
		this.poll;
	}

	*makeIndexDicts {
		var dataIndex = 3;
		dataIndexDict = IdentityDictionary();
		busIndexDict = IdentityDictionary();
		// busIndex = actor.bus.index;
		/*
		[Actor2.jointNames, Actor2.shortJointNames].flop do: { | j, i |
			var di, bi;
			di = i * 8 + dataIndex;
			bi = i * 7 + busIndex;
			dataIndexDict[j[0]] = di;
			dataIndexDict[j[1]] = di;
			busIndexDict[j[0]] = bi;
			busIndexDict[j[1]] = bi;

		}
		*/
		^dataIndexDict;
	}

	// start polling. Ensure that bus exists
	poll {
		if (actor.bus.isNil) {
			this.addNotifier(actor, \madeBus, { this.prPoll })
		}{ this.prPoll }
	}

	prPoll { // start polling routine
		CmdPeriod add: this;
		busIndex = actor.bus.index; // duplicating. not DRY. OK.
		pollRoutine = fork {
			loop {
				this.poll1;
				30.reciprocal.wait;
			}
		}
	}

	poll1 {
		var bus, indata;
		bus = actor.bus;
		bus ?? { ^postln("Actor" + actor + "has no bus to poll.") };
		indata = actor.indata.copy; // leave original idata intact for others
		bus.getn(actor.numControls, { | vals | actions do: { | f |
			f.(actor, indata, vals);
		} })
	}

	stopPolling { pollRoutine.stop; pollRoutine = nil }

	isPolling { ^pollRoutine.isPlaying }

	doOnCmdPeriod { this.poll; } // keep polling

	// adding actions
	addAction { | func, key = \default | this.actions[key] = func; }
	actions { ^actions ?? { actions = IdentityDictionary() } }
	removeAction { | key = \default | this.actions[key] = nil }
	// custom actions;
	simplePost {
		^{ | a, indata, vals | postln("bus data are:" + vals);}
	}

	// adding custom actions

	// adding control synths with output to selected busses

	addSynth { | f, joint = \hip, key = \default |
		var busIndex;
		busIndex = this getJointIndex: joint;
	}
}
