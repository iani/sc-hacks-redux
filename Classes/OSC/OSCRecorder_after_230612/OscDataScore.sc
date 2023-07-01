/* 20 Jun 2023 23:29

Subclass of OscData that introduces 2 differences:

1. treats relative time values instead of absolute ones:

//:--[0.1]
// Here 0.1 is a time relative to the time of the previous message.

2. The contents of the message are treated implicitly as code.
//:--[1.5]
{ WhiteNoise.ar(0.1).dup } +> \test;

Is equivalent to:
//:--[1.5]
['/code', "{ WhiteNoise.ar(0.1).dup } +> \test;"]

*/

OscDataScore : OscData {
	var durations;
	checkFileType { | string, path |
		if ("//code" == string[..string.find("\n")-1]) {
		}{
			Error("File" + path + "is not a code file. Use OscData.").throw
		};
	}

	makeTimeline { | argTimes | timeline = Timeline.fromDurations(argTimes); }

	convertTimes {
		durations = times;
		times = ([0] ++ times).integrate.butLast;
		totalOnsetsDuration = times.last;
		totalDuration = durations.sum;
		selectedDuration = totalDuration;
	}

	makePlayFunc {
		var localaddr, oscgroupsaddr;
		localaddr = LocalAddr();
		OscGroups.enable(verbose: false);
		oscgroupsaddr = OscGroups.sendAddress;
		^{
			localaddr.sendMsg('/code', ~message);
			oscgroupsaddr.sendMsg('/code', ~message);
		}
	}

	sendItemAsOsc { | string | // OscDataScore prepends '/code' here
		localAddr.sendMsg('/code', string);
		oscgroupsAddr.sendMsg('/code', string);
	}

	selectedTimesItems {
		// "onset times are".postln;
		// selectedTimes.postln;
		// "Differentiated times are:".postln;
		// selectedTimes.rotate(-1).differentiate.postln;
		// "durations are".postln;
		// durations.postln;
		// postln("minIndex" + minIndex + "maxIndex" + maxIndex);
		^[selectedTimes, durations.copyRange(minIndex, maxIndex)].flop collect:
		{ | bd | format("beg: % | dur: %", bd[0], bd[1]) }
	}

	updateSelectedDuration {
		selectedDuration = durations.copyRange(minIndex, maxIndex).sum;
	}
}
