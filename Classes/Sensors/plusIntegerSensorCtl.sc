/* 24 Jul 2023 00:04

For use with SensorCtl!
See SensorCtl:start.

Shortcuts for playing control functions with input from sensors.
Called by SensorCtl in Param, SoundParams.
*/

+ Integer {
	sensorClip { ^this.clip(1, 12) } // constrain to existing sensor ids in current systsm
	off {  | player, param, lo, hi, map | // turn control synth off.
		format("nil @>.% %", player, param.slash).share;
	}

	sensor { | prefix = \x |
		^"\\" ++ prefix ++ this.sensorClip ++ ".sr";
	}
	lx { | player, param, lo, hi, map |
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
	}

	z { | player, param, lo, hi, map |
		format("{ %%.sr.%(%, %) } @>.% %",
			\z.slash, this.sensorClip, map, lo, hi, player, param.slash
		).share;
	}

	// ===== using custom code templates: ===== STILL TESTING!
	// SensorCtl allways passes all parameters.
	cx { | player, param, lo, hi, map, template, c2, c3 |
		format("{ var x; x = %%.sr; % } @>.% %",
			\x.slash, this.sensorClip, template, player, param.slash
		).postln.share;
	}

	cz { | player, param, lo, hi, map, c1, template, c3 |
		format("{ var z; z = %%.sr; % } @>.% %",
			\z.slash, this.sensorClip, template, player, param.slash
		).postln.share;
	}

	// experimental - checking!
	c3 { | player, param, lo, hi, map, c1, c2, template |
		format("{ var x, y, z; x = %; y = %; z = %; % } @>.% %",
			this sensor: \x, this sensor: \y, this sensor: \z, template, player, param.slash
		).postln;
	}
}