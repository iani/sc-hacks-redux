/* 30 Jun 2023 13:57

Hold an array of onsets and an array of durations in a timeline.
Encapsulate the methods needed to convert durations to onsets and vice versa.

Also:
	- calculate total duration
	- map range-slider values to time points proportionate to the total duration
	- find minimum index and maximum index of a time line closest to a start time and
	  end time given by the user.
	- Store data representing a segment of self, to permit the incremental
	  definition of segments from the user.
	- map time values received from a range slider to duration, and find the
	  indices of the items corresponding to the range of the slider.

*/

Timeline {
	var <onsets, <durations;
	var <totalDuration;
	var <>minIndex = 0, <>maxIndex;
	var <segmentMin, <segmentMax;
	var <segmentOnsets, <segmentDurations;
	var <segmentTotalDur;

	*fromOnsets { | onsets |
		^this.newCopyArgs(onsets).makeDurations.init;
	}

	makeDurations { // from onsets.
		// make sure first onset starts at 0:
		onsets = onsets - onsets[0];
		durations = onsets.differentiate.rotate(-1);
	}

	*fromDurations { | durations |
		^this.newCopyArgs(([0] ++ durations).integrate.butLast, durations).init;
	}

	init {
		totalDuration = durations.sum;
		maxIndex = durations.size - 1;
		segmentMin = 0;
		segmentMax = maxIndex;
		segmentOnsets = onsets;
		segmentDurations = durations;
		segmentTotalDur = totalDuration;
	}

	// Select subsegments
	selectAll { this.indexSegment(0, durations.size - 1) }
	indexSegment { | argMin, argMax |
		segmentMin = argMin;
		segmentMax = argMax;
		// postln("indexSegment segmentMin" + segmentMin + "segmentMax" + segmentMax);
		this.makeSegment;
	}

	makeSegment {
		segmentOnsets = onsets.copyRange(segmentMin, segmentMax);
		segmentDurations = segmentDurations.copyRange(segmentMin, segmentMax);
		segmentTotalDur = segmentDurations.sum;
		this.changed(\segment);
	}

	clipTime { | minTime, maxTime |
		segmentMin = this.findIndex(minTime);
		segmentMax = this.findIndex(maxTime);
		this.makeSegment;

	}

	clipMinTime { | minTime |
		// postln("debuggging mintime. minTime is" + minTime);
		// "Now findIndex mintime".postln;
		segmentMin = this.findIndex(minTime) ? 0;
		// postln("segmentMin is" + segmentMin);
		this.makeSegment;
	}

	clipMaxTime { | maxTime |
		segmentMax = this.findIndex(maxTime) ?? { onsets.size - 1 };
		this.makeSegment;
	}

	setMinIndex { | argIndex |
		this.indexSegment(argIndex, segmentMax)
	}

	setMaxIndex { | argIndex |
		this.indexSegment(segmentMin, argIndex)
	}

	// Useful calculations
	segmentSize { ^segmentOnsets.size }
	duration { ^durations[segmentMax] + this.maxTime - this.minTime }
	maxDuration { ^totalDuration - this.minTime; }
	minTime { ^onsets[segmentMin] }
	maxTime { ^onsets[segmentMax] }
	mapTime { | argTime | // map from 0-1 to 0-totalDuration;
		^argTime * totalDuration;
	}

	unmapTime { | argTime | // map from 0-totalDuration to 0-1;
		^argTime.clip(0, totalDuration) / totalDuration;
	}

	unmapTimeSpan {
		^[this.unmapTime(onsets[minIndex]), this.unmapTime(onsets[maxIndex])]
	}

	findIndex { | argTime |
		// find index of onset closest to argTime
		^onsets.indexOf(onsets detect: { | o | o >= argTime })
	}

	range { | minTime, maxTime |
		var min, max;
		min = this.findIndex(minTime) ? 0;
		max = this.findIndex(maxTime) ? maxIndex;
		^this.fromDurations(durations.copyRange(min, max)).minIndex_(min).maxIndex_(max);
	}

}
