/* 21 Feb 2022 06:01
Run a routine that broadcasts beats for global synchronization.
*/

BeatCounter {
	classvar <beatMessage = \beat;
	var <>count = 0, <>dt = 1;
	var <routine;
	var <lastBeatTime; // use to restart on time after cmd period.

	*new { | count, dt = 1 |
		^this.newCopyArgs(
		count ?? { Pseries() },
		dt)
	}

	isRunning { ^routine.notNil }

	start {
		if (this.isRunning) {
			postf("% is running. Skippig start\n", this);
		}{
			this.hardStart;
		}
	}

	hardStart {
		routine = {
			loop {
				lastBeatTime = Main.elapsedTime;
				this.changed(beatMessage, count);
				dt.wait;
				count = count + 1;
			};
		} .fork;
		CmdPeriod.add(this);
	}


	stop {
		if (this.isRunning) {
			routine.stop;
			routine = nil;
			CmdPeriod remove: this;
		};
	}

	cmdPeriod { // restart at time of next beat!
		if (this.isRunning) { // only restart routine if running
			{
				// wait for the remaining time to the next beat before restarting:
				(dt - (Main.elapsedTime - lastBeatTime)).wait; // FIXME: check and test this
				this.hardStart;
			}.fork;
		}
	}

	add { | listener, action |
		listener.addNotifier(this, beatMessage, action);
	}

	remove { | listener |
		listener.removeNotifier(this, beatMessage);
	}

	beat { ^this }
}
