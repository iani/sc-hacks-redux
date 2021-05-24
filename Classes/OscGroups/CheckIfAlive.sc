/* 24 May 2021 20:48
Simple way to check whether a service has been alive for the most recent
time interval dt.

Not tested.

*/

CheckIfAlive {
	var <>dt = 5;
	var <latestAlive;
	var <isAlive = false;

	setAlive {
		// call this when you receive a signal that the service is alive
		isAlive = true;
	}

	start {
		isAlive = false; // must be set to true with setAlive
		// within the dt interval.
		{
			dt.wait; // wait an initial interval before starting loop
			while {
				"testing whether to continue the alive loop now".postln;
				isAlive
			}{
				"Testing : process is alive".postln;
				isAlive = false; // someone must call setAlive while waiting
				// if nobody does it, then the process is not alive.
				postf("will check in % secs whether still alive\n", dt).postln;
				dt.wait;
			};
			this.signalProcessNotAlive;
		}.fork(AppClock);

	}

	signalProcessNotAlive { "the process is no longer alive".postln; }
	
}