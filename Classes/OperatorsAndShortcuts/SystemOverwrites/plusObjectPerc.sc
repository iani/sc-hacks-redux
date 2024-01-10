/*  1 Sep 2023 11:53
Enable perc shortcut for any object in a play function.
e.g.:

{ \freq.bus.set(1000).perc; } +>.test \fmod;


*/

+ Object {
	perc { // return a very short silent envelope
		^Silent.ar.perc(0.001, 0.001);
	}
}