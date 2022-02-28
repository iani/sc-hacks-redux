/* 20 Feb 2022 15:43
Simple way to post / stop posting update messages emitted by any object
*/

Trace {
	*update { | ... args | args.postln; }
}


+ Object {
	trace { this addDependant: Trace }
	untrace { this removeDependant: Trace }
}