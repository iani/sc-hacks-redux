// 木  8  5 2025 18:52
// Redo of RokokoData for simpler implementation and usage.
// NOTE: You should not make+play many instances of RokokoData2,
// because each instance allocates 161 control buses.
RokokoData2 : NamedSingleton2 {
	var <>path; // the 1 path originally chosen by the user
	var <>paths; // paths of all other scd files in the same folder
	var <oscdata;
	var times; // times from oscdata
	var dtimes; // dt from times
	var controlValues; // all control values as nested numerical array
	var <routine; // playback routine
	var <stack; // hold last 100 vectors received from playback
	var <entry; // last played entry
	var <sendPort = 22244;
	var <sendAdress;
	var <filters, filterDict;
	var <buses; // 161 buses.  Allocated by method play.
	var <pollArray; // data from the latest bus poll
	var <pollRoutine; // the routine polling the buses

	init {
		postln("initing RokokoData2" + name);
		stack = Stack(100, false);
		this.sendPort = 22244; // initialize and create sendAdress
		this.load;
	}

	sendPort_ { | argPort = 22244 |
		// set port number + make sendAdress
		sendPort = argPort;
		sendAdress = NetAddr("127.0.0.1", sendPort);
	}
	load {
		Paths.doGetPath({ | p, argPaths |
			path = p;
			paths = argPaths;
			oscdata = OscData(paths);
		}, name)
	}

	gui { oscdata.gui; }
	data { ^oscdata.parsedEntries }
	times { ^times ?? { times = this.data.flop.first } }
	entries { ^this.data.flop[1] }
	controlValues {
		^controlValues ?? {
			controlValues =	this.entries collect: { | e |
				Rokoko.getControlValues(e.interpret)
			};
		}
	}

	playSegment { | begin = 0, end |
		var entries, dt; // own dtimes
		end ?? { end = oscdata.parsedEntries.size - 1 };
		end = oscdata.parsedEntries.size - 1 min: end; // constrain
		entries = oscdata.parsedEntries[begin..end];
		dt = entries.flop[0].differentiate[0] = 0;

		routine.stop;
		stack.clear;
		routine = fork {
			dt do: { | delay, i |
				delay.wait;
				entry = entries[i][1].interpret;
				filters do: { | f |
					entry = f.(entry);
				};
				stack.push(entry);
				this.changed(\play, entry, i);
			}
		}
	}

	loopSegment { | begin = 0, end |
		var entries, dt; // own dtimes
		end ?? { end = oscdata.parsedEntries.size - 1 };
		end = oscdata.parsedEntries.size - 1 min: end; // constrain
		entries = oscdata.parsedEntries[begin..end];
		dt = entries.flop[0].differentiate[0] = 0.3;

		routine.stop;
		stack.clear;
		routine = fork {
			loop {
				dt do: { | delay, i |
					delay.wait;
					entry = entries[i][1].interpret;
					filters do: { | f |
						entry = f.(entry);
					};
					stack.push(entry);
					this.changed(\play, entry, i);
				}
			}
		}
	}

	stop { routine.stop }
	// transforming data with filter functions
	filterDict { ^filterDict ?? { filterDict = () } }
	// NOTE: filter functions are added in sorted order by key!
	// Use _01, _02, _03 etc in beginning of key name to ensure ordering
	addFilter { | key, func | // use keys to remove filters by name
		this.filterDict[key] = func;
		this.makeFilters;
	}
	removeFilter { | key |
		this.filterDict[key] = nil;
		this.makeFilters;
	}
	makeFilters {
		filters = this.filterDict.keys.asArray.sort collect: { | k |
			filterDict[k]
		}
	}
	// ============= playback behaviors ===========
	addPlayAction { | action, key = \default |
		key.addNotifier(this, \play, action);
	}

	removePlayAction { | key = \default |
		key.removeNotifier(this, \play);
	}

	addPostData {
		this.addPlayAction({ | n, data |
			data.postln;
		}, \postData);
	}

	removePostData { this.removePlayAction(\postData) }

	addPostIndex {
		this.addPlayAction({ | n, data, index |
			index.postln;
		}, \postIndex);
	}

	removePostIndex { this.removePlayAction(\postIndex) }

	addSendOscGroups {
		this.addPlayAction({ | n, data |
			sendAdress.sendMsg(*data);
		}, \sendOscGroups);
	}

	removeSendOscGroups { this.removePlayAction(\sendOscGroups) }

	addSendOscLocal {
		var localAddr;
		localAddr = NetAddr.localAddr;
		this.addPlayAction({ | n, data |
			localAddr.sendMsg(*data);
		}, \sendOscLocal);
	}

	removeSendOscLocal { this.removePlayAction(\sendOscLocal) }

	// playing multiple streams from the same data concurrently
	clone { ^this.copy }

	// ====== synth control ========
	poll {
		ServerQuit add: { buses = nil };
		CmdPeriod add: this;
		buses.isNil.if {
			Server.default.waitForBoot({
				this.makeBuses;
				Server.default.sync;
				this.startPollRoutine;
			})
		}{
			postln("Buses already exist for" + this + "Skipping this.");
		}
	}

	doOnCmdPeriod { this.startPollRoutine }

	makeBuses {
		postln("Making buses for" + this);
		buses = Bus.control(Server.default, 161);
		postln("Made buses" + buses);
		// NOTE: these could also be put in a dictionary
		// for access by name.
	}

	startPollRoutine {
		"Starting poll routine".postln;
		pollRoutine = fork {
			loop {
				buses.getn(buses.numChannels, { | a |
					pollArray = a;
					this.changed(\poll, pollArray);
				});
				30.reciprocal.wait; // 30 times per second
			}
		};
	}

	// ============= polling behaviors ===========
	addPollAction { | action, key = \default |
		key.addNotifier(this, \poll, action);
	}

	removePollAction { | key = \default |
		key.removeNotifier(this, \poll);
	}

	// add polled bus values to latest
	addSumPost {
		this.addPollAction({ | n ... data |
			// for now we just post the first 5 values
			data[..5].postln;
		}, \pollData);
	}

	removeSumPost { this.removePollAction(\pollData) }

	addSumSend { | joint = \rightHand, dim = 0, dindex = 0 |
		this.addPollAction({ | n ... data |
			var all, header, joints, index;
			all = entry;
			header = all[..2];
			joints = all[3..].clump(8);
			index = joints.flop[0] indexOf: joint;
			joints[index][dim + 1] =
			joints[index][dim + 1] + pollArray[dindex];
			sendAdress.sendMsg(*(header ++ joints.flat));
		}, \pollSumSend);
	}

	removeSumSend { this.removePollAction(\pollSumSend) }

	addSumSendTest {
		this.addPollAction({ | n ... data |
			// for now we just add the first bus to hip x
			var cache; // safety for Osc sending delay
			cache = entry;
			cache[4] = cache[4] + data[0];
			sendAdress.sendMsg(*cache);
		}, \pollSumSendTest);
	}

	removeSumSendTest { this.removePollAction(\pollSumSendTest) }

	// ======= reacting to incoming /rokoko/ from OSCGroups =======
	postRokokoOscTest {
		OSC.add('/rokoko/', { | ... args |
			postln("ROKOKO RECEIVED" + args);
		})
	}

	postRokokoOsc {
		OSC.add('/rokoko/', { | n, data |
			postln("size:" + data.size + "data" + data[..5]);
			this.changed('\play', data);
		})
	}
	muteRokokoOsc {
		OSC.remove('/rokoko/');
	}

	sendTest { // send a message to test reception.
		NetAddr.localAddr.sendMsg('/rokoko/', 1, 2, 3);
	}

	// send playback and poll data locally and to OSC Groups
	sendPlaybackLocally {
		var localAddr;
		localAddr = NetAddr.localAddr;
		this.addPlayAction({ | n, data |
			localAddr.sendMsg(*data);
		}, \playbackLocally)
	}
	mutePlaybackLocally { this.removePlayAction(\playbackLocally) }

		// send playback and poll data locally and to OSC Groups
	sendPlaybackGroups {
		this.addPlayAction({ | n, data |
			sendAdress.sendMsg(*data);
		}, \playbackGroups)
	}
	mutePlaybackGroups { this.removePlayAction(\playbackGroups) }

	sendPollLocally {
		this.addPollAction({ | n, data |
			sendAdress.sendMsg(*data);
		}, \sendPollLocally)

	}
	mutePollLocally { this.removePlayAction(\sendPollLocally) }

	localSendTest {

	}

	remoteSendTest {

	}
}
