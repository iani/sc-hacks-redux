/* 22 Jul 2023 11:17
Encapsulate on-off and muting as well as parameter bus setting control from sensor input.
*/

SensorCtl {
	var <model;
	var <sensorNum = 1, <ctl;
	// var <player, <param;
	var <lo = 0, <hi = 1, <maptype = \exp;


	*new { | model |
		^this.newCopyArgs(model).init
	}

	init {

	}

	sensorNum_ { | argid |

	}

	ctl_ { | argctl |

	}

	play { // draft!
		sensorNum.perform(ctl, model.player, model.param, lo, hi, maptype);
	}
}