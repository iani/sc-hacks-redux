// 木 22  5 2025 11:28
// Playback data from TypingRecorder, in a Task.
// Multiple TypingPlayers can play at the same time.
// Use a filter function to customize the data received from
// TypingRecorder, and/or provide extra data via extra arguments.
// Change the playing function and the filter function at any time,
// while playing or while not playing.

TypingPlayer {
	var <recorder, <key, <data;
	var <>playFunc, <>filterFunc;
	var <task, <index = 0;
	var <times, <chars;

	*new { | recorder, key = \defalt |
		^this.newCopyArgs(recorder, key);
	}

	data_ { | argData |
		data = argData;
		this.makeTimesChars;
	}

	makeTimesChars {
		var rawTimes;
		#rawTimes, chars = data.clump(2).flop;
		times = rawTimes.differentiate;
		times[0] = 0;
	}

	start {
		if (task.isPlaying) {
			^postln("Player" + key + "continues playing. No restart.")
		};
		this.prStart;
	}

	prStart {
		index = 0;
		this.makeTimesChars;
		task = Task({
			while { index < (data.size - 1) }
			{
				var unfiltered, filtered;
				times[index].wait;
				unfiltered = chars[index];
				filtered = filterFunc.(unfiltered);
				postln("playing unfiltered" + unfiltered
					+ "filtered" + filtered);
				playFunc.(filtered);
				index = index + 1;
			};
		});
		task.start;
	}

	stop { task.stop; }

}