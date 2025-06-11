// Poll buses with one polling routine
// Store the polled values in a dictionary for access by key.
// Also notify with the key of each polled bus when it is polled,
// as an alternative way for other objects to update.

// Since this is a NamedSingleton, you can run
// instance methods by messages to the class.
// They run on instance named \default
// One can also create other named instances.

PollBus : NamedSingleton2 {
	var <pollRoutine, <>dt = 0.0333333;
	var buses, values;

	buses { ^buses ?? { buses = IdentityDictionary() } }
	values { ^values ?? { values = IdentityDictionary() } }

	addBus { | key, bus | this.buses[key] = bus; }

	start {
		pollRoutine !? {
			^"Cannot re-start a running PollBus".postln;
		};
		this.makePollRoutine;
	}

	makePollRoutine {
		pollRoutine = fork {
			loop {
				this.buses keysValuesDo: { | argName, argBus |
					this.poll(argName, argBus);
				};
				dt.wait;
			}
		};
		CmdPeriod add: this;
	}

	poll { | argName, argBus |
		argBus.getn(argBus.numChannels, { | vals |
			this.values[argName] = vals;
			this.changed(argName, vals);
		});
	}

	doOnCmdPeriod { this.makePollRoutine }

	stop {
		pollRoutine.stop;
		pollRoutine = nil;
		CmdPeriod remove: this;
	}
}