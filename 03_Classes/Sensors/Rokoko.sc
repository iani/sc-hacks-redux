//: 月 14  4 2025 19:47
//: Receive data messages from Rokoko suit and write the values into busses.
//: How many busses and channels per Rokoko actor?
/*
~numChans = 23 * 7
A 5 actor scenne would have:
 23 * 7 * 5
805 channels

Server.default.options.numControlBusChannels

Max number of
16384 / 161 actors
 101.76397515528

*/

Rokoko {
	var <name;
	var <envir;
	var <bus1; /// busses by name, one bus per joint
	/// each bus has 7 channeels.

	enable {
		// >>> {}
		// <<<
	}

	disable {

	}

	getEnvir {

	}

	makeBusses {

	}

	/*
	*openPlayer { | argKey = \rokoko |
		PathAction({ | p, paths |
			"-----------------".postln;
			postln("path" + p);
			"================== PATHS:".postln;
			paths do: _.postln;
			postln("paths size" + paths.size);
			// OscData(paths).gui;
		}, argKey);
	}
	*/
	*openPlayer { | argKey = \rokoko |
		var dataRef;
		Paths.doGetPath({ | p, paths |
			dataRef = Ref(OscData(paths).gui)
		}, argKey);
		^dataRef;
	}

	*resetPath { | argKey = \rokoko |
		Paths.resetPath(argKey);
	}
}