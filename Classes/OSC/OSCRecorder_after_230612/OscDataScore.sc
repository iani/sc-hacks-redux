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

	makeTimeline { | argTimes |
		timeline = Timeline(this).setDurations(argTimes);
	}

	convertTimes {
		durations = times;
		times = ([0] ++ times).integrate.butLast;
	}

	makePlayFunc {
		var localaddr, oscgroupsaddr;
		localaddr = LocalAddr();
		OscGroups.enable(verbose: false);
		oscgroupsaddr = OscGroups.sendAddress;
		^{
			postln("debugging playfunc. message class is:" + ~message.class);
			localaddr.sendMsg('/code', ~message);
			oscgroupsaddr.sendMsg('/code', ~message);
		}
	}

	sendItemAsOsc { | string | // OscDataScore prepends '/code' here
		localAddr.sendMsg('/code', string);
		oscgroupsAddr.sendMsg('/code', string);
	}

	formatTimeIndex { | t, i | // include header comments
		var m;
		m = messages[timeline.segmentMin + i];
		^(t.asString + m.copyRange(m.indexOf($]) + 1, m.indexOf(Char.nl) - 1))
	}
}
