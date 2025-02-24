/* 28 Jan 2023 22:19
Map a single numerical value input from a sensors and set a bus.

see file SensorMapper
*/

/*
ScalarMap {
	var message, sensorindex, valueindex, <valuename = \x, <>min = -1, <>max = 1;
	var <sensorname, <envir, <val = 0, <bus, <busindex, <>server;

	*new { | message = '/ahrsdata', sensorindex = 0, valueindex = 1, valuename = \x,
		min = -1, max = 1 |
		^this.newCopyArgs(message, sensorindex, valueindex, min, max).init;
	}

	init {
		sensorname = format("%%", message, sensorindex).asSymbol;
		envir = sensorname.envir;
		bus = valuename.bus(nil, sensorname);
		busindex = bus.index;
		server = Server.default;
	}
}
*/