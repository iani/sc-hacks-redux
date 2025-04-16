/* 20 Jun 2023 23:29

Subclass of OscDataScore that uses absolute onset timestamps instead
of durations timestamps.
To calculate times, this class uses the methods of OscData.:

As in OscDataScore, the contents of the message are treated implicitly as code.
//:--[1.5]
{ WhiteNoise.ar(0.1).dup } +> \test;

Is equivalent to:
//:--[1.5]
['/code', "{ WhiteNoise.ar(0.1).dup } +> \test;"]

*/

OscDataOnsetScore : OscDataScore {
	checkFileType { | string, path |
		if ("//onsetcode" == string[..string.find("\n")-1]) {
		}{
			Error("File" + path + "is not anOnset code file. Use OscData.").throw
		};
	}

	makeTimeline { | argTimes |
		timeline = Timeline(this).setOnsets(argTimes); // use onsets method from OscData
	}

	convertTimes {
		times = times - times.first;
		postln("original times" + times);
		postln("difftimes" + times.differentiate);
		durations = times.differentiate;
	}

	// USE THE SAME METHODS AS OscDataScore:
	/*
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

	formatTimeIndex { | t, i | // include header comments
		var m;
		m = messages[timeline.segmentMin + i];
		^(t.asString + m.copyRange(m.indexOf($]) + 1, m.indexOf(Char.nl) - 1))
	}
	*/
}
