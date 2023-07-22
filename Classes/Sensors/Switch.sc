/* 22 Jul 2023 11:17
Encapsulate on-off and muting control from sensor input.
*/

Switch {
	var <model;
	var <sensorid, <control;

	*new { | model |
		^this.newCopyArgs(model).init
	}

	init {

	}


}