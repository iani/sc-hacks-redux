// 木  8  5 2025 18:52
// Redo of RokokoData for simpler implementation and usage.

RokokoData2 : NamedSingleton2 {
	var <>path; // the 1 path originally chosen by the user
	var <>paths; // paths of all other scd files in the same folder
	var <oscdata;
	var times; // times from oscdata
	var dtimes; // dt from times
	var controlValues; // all control values as nested numerical array
	var <routine; // playback routine

	init {
		postln("initing RokokoData2" + name);
		this.load;
	}
	load {
		Paths.doGetPath({ | p, argPaths |
			path = p;
			paths = argPaths;
			oscdata = OscData(paths);
		}, name)
	}

	gui { oscdata.gui; }
	data { ^oscdata.parsedEntries }
	times { ^times ?? { times = this.data.flop.first } }
	entries { ^this.data.flop[1] }
	controlValues {
		^controlValues ?? {
			controlValues =	this.entries collect: { | e |
				Rokoko.getControlValues(e.interpret)
			};
		}
	}
}
