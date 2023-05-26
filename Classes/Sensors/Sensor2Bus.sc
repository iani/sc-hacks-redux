/*  7 May 2023 19:19

(renamed from ScalarMap)

*/

Sensor2Bus {
	var <sensorname, valueindex, <valuename = \x, <>min = -1, <>max = 1;
	var <val = 0, <bus, <busindex;
	// var <>server; // may be used for faster bus setting method

	*new { | sensorname, valueindex = 1, valuename = \x,
		min = -1, max = 1 |
		^this.newCopyArgs(sensorname, valueindex, valuename, min, max).init;
	}

	init {
		bus = valuename.bus(nil, sensorname);
		busindex = bus.index;
		// server = Server.default; // may be used for faster bus setting method
	}

	input { | args |
		// this.adaptBounds(args[valueindex]);
		val = args[valueindex].linlin(min, max, 0, 1);
		bus.set(val);
		^val;
	}

	adaptBounds { | inputValue | // adapt min or max if required
		min = min min: inputValue;
		max = max max: inputValue;
	}
}