/* 20 Feb 2022 15:30
Just testing new ways to make patterns.

These patterns could provide extra functionality.
*/


/*
Here are some initial tests
*/

PreturnANumber : Pattern {
	asStream { ^PreturnANumberStream() }
}

PreturnANumberStream : Stream {
	next { ^pi }
	reset { }
	storeArgs { ^[] }
}

PthisEvent : Pattern {
	asStream { ^PthisEventStream() }
}

PthisEventStream : Stream {
	next { ^currentEnvironment.postln }
	reset { }
	storeArgs { ^[] }
}
