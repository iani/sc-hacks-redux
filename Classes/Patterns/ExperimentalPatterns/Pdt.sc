/* 29 Mar 2022 20:35
Under development
Store an array of absolute time values (in ascending order).
Return the difference between the current value and the next,
to use as duration when playing a pattern.

For use when playing back data collected from OSC messages
recorded with OSCData.

Modeled after Pseq - with reverse removed.
*/

Pdt {
	var <list, <startPos, <endPos, <>repeats = 1, increment = 1;
	var <>pos;
	var <repeated = 0, <ended = false;
	var <prevTime = 0, <nextTime; // cache it for repeats
	*new { | list, startPos = 0, endPos, repeats = 1, increment = 1 |
		^super.newCopyArgs(
			list, startPos, endPos ?? { list.size }, repeats, increment, startPos
		);
	}

	//	asStream { ^this }

	next {

		^prevTime;
	}
}