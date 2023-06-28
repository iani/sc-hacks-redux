/* 24 May 2021 20:48
Simple way to check whether a service has been unavailable for a given interval.

Not tested.

*/

Ping {
	var <>dt = 5;
	var <lastPinged = 0;
	var pingRoutine; // the routine that does the pinging

	*new { | dt = 5 |
		^this.newCopyArgs(dt).init;
	}

	init {
		CmdPeriod add: this;
	}
	
	add { | listener |
		this addDependant: listener;
		this.start;
	}

	markNow {
		lastPinged = Main.elapsedTime;		
	}
	start {
		if (this.isRunning.not) { this.makeRoutine };
	}

	isRunning { ^pingRoutine.notNil }

	makeRoutine {
		pingRoutine = {
			loop {
				dt.wait;
				this.changed(\ping);
			}
		}
		
	}
	
	cmdPeriod {
		"i should restart my routine - but i am not implemented".postln;
		
	}
	
	stop {
		"stop not yet implemented".postln;
		
	}
	
}