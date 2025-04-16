/*  7 Jun 2023 22:33

Map the value input from a sensor from a range of min-max to a range of 0-1, and then
set your bus to this value.

Used by Minibee.

*/

BusMapper {
	var busname, min, max, bus;

	makeBus {
		bus = busname.sensorbus;
	}
	map { | value |
		value = value.linlin(min, max, 0.0, 1.0);
		bus.set(value);
		^value;
	}
}