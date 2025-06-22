//: 日 22  6 2025 15:28
//Adapt AnimationPlayer to work with live OSC data from
//Rokoko suit (instead of sound-file playback).

LiveMocapPlayer : AnimationPlayer {
	var <id; // id for
	init { | ... args |
		postln("Initing" + this + args);
	}
}