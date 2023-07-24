/* 24 Jul 2023 00:04
Shortcuts for playing control functions with input from sensors.
Called by SensorCtl in Param, SoundParams.
*/

+ Integer {
	sensorClip { ^this.clip(1, 12) } // constrain to existing sensor ids in current systsm
	off {  | player, param, lo, hi, map | // turn control synth off.
		format("nil @>.% %", player, param.slash).share;
	}

	lx { | player, param, lo, hi, map |
		// this.sensorClip.
		format("{ (%.sr < 0.5).lag(0.5) } @>.% %",
			\x.slash, this.sensorClip, player, param.slash
		).share;
	}
	lz { | player, param, lo, hi, map |
		format("{ (%.sr < 0.5).lag(0.5) } @>.% %",
			\z.slash, this.sensorClip, player, param.slash
		).share;
	}
	gx { | player, param, lo, hi, map |
		format("{ (%.sr > 0.5).lag(0.5) } @>.% %",
			\x.slash, this.sensorClip, player, param.slash
		).share;
	}
	gz { | player, param, lo, hi, map |
		format("{ (%.sr > 0.5).lag(0.5) } @>.% %",
			\z.slash, this.sensorClip, player, param.slash
		).share;
	}
	xyz { | player, param, lo, hi, map | // this may be tweaked later...
		format("XyzGt(%) @>.% %", this.sensorClip, player, param.slash).share;
	}

	x { | player, param, lo, hi, map |
		format("{ %%.sr.%(%, %) } @>.% %",
			\z.slash, this.sensorClip, map, lo, hi, player, param.slash
		).share
		// // var sensor;
		// // sensor = "\\" + "x" + this.sensorClip;
		// // sensor.postln;
		// // format("{ %.sr.%(%, %) } @>.% %",
		// // 	sensor, map, lo, hi, player, param.slash
		// ).share;
	}

	z { | player, param, lo, hi, map |
		format("{ %%.sr.%(%, %) } @>.% %",
			\z.slash, this.sensorClip, map, lo, hi, player, param.slash
		).share;
	}

}