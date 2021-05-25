/* 24 May 2021 20:48
Simple way to check whether a service has been alive for the most recent
time interval dt.

Not tested.

*/

Ping : NamedSingleton {
	var <dt = 5;
	var pingRoutine; // the routine that does the pinging

	prInit { | ... args |
		postf("prInit args are: %\n", args);
	}
	
	cmdPeriod {
		"i should restart my routine - but i am not implemented".postln;
		
	}
	
	stop {
		"stop not yet implemented".postln;
		
	}
	
}