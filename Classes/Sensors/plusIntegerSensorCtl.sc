/* 24 Jul 2023 00:04

Sat 29 Jul 2023 08:52 - transferring this code to SensorCtl
For use with SensorCtl!
See SensorCtl:start.

Shortcuts for playing control functions with input from sensors.
Called by SensorCtl in Param, SoundParams.
*/

+ Integer {
	sensorClip { ^this.clip(1, 12) } // constrain to existing sensor ids in current systsm
	off {  | player, param, lo, hi, map | // turn control synth off.
		postln("Debugging off method for param" + param + "BEFORE");
		player.envir.synthReport;
		format("nil @>.% %", player, param.slash).postln.share;
		{

			postln("Debugging off method for param" + param + "AFTER");
			"after the off!".postln; player.envir.synthReport }.defer(0.5);
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
		this.off(player, param, lo, hi, map);
		{
			format("{ %%.sr.%(\\lo.kr(%), \\hi.kr(%)) } @>.% %",
			\x.slash, this.sensorClip, map, lo, hi, player, param.slash
		).postln.share;
			{ "synth report after x".postln;
				player.envir.synthReport;
			}.defer(0.1);
		}.defer(0.1);
	}

	z { | player, param, lo, hi, map |
		"Performing interger z. Now I WILL TURN OFF@!!!!!!!!!!!!!!!!!!!!!".postln;
		this.off(player, param, lo, hi, map);
		{
		format("{ %%.sr.%(\\lo.kr(%), \\hi.kr(%)) } @>.% %",
			\z.slash, this.sensorClip, map, lo, hi, player, param.slash
		).postln.share;
		}.defer(0.1);
	}

	// ===== using custom code templates: ===== STILL TESTING!
	// SensorCtl allways passes all parameters.
	// The template is passed at a different parameter position for cx, cz and c3!
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
	hi { "hi and lo methods are implemented in SensorCtl as hi_, lo_".postln }
	lo { "hi and lo methods are implemented in SensorCtl as hi_, lo_".postln }
}