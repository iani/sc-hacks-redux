// 木 22  5 2025 11:28
// Playback data from TypingRecorder, in a Task.
// Multiple TypingPlayers can play at the same time.
// Use a filter function to customize the data received from
// TypingRecorder, and/or provide extra data via extra arguments.
// Change the playing function and the filter function at any time,
// while playing or while not playing.

TypingPlayer {
	var <recorder, <key, <>from, <>to, <>data;
	var <>playFunc, <>filterFunc;
	var <task;

	*new { | recorder, key = \defalt |
		^this.newCopyArgs(recorder, key);
	}

	start {

	}

	stop {

	}

}