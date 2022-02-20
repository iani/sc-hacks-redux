/* 20 Feb 2022 15:30
Just testing new ways to make patterns.

These patterns could provide extra functionality.
*/

PreturnANumber : Pattern {
	asStream { ^PreturnANumberStream() }
}

PreturnANumberStream : Stream {
	next { ^pi }
	reset { }
	storeArgs { ^[] }
}
