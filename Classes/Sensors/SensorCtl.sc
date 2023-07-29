/* 22 Jul 2023 11:17
Encapsulate on-off and muting as well as parameter bus setting control from sensor input.

Uses Integer methods: off, lx, lz, gx, gz, xyz, x, z as shortcuts to evaluate/share code with 'share'.

When a parameter is set, immediately play the new code. This replaces the previous synth
with the new one.  Exception: setting player or param:

Starting a new synth replaces the previous synth only when it is playing in the same player
environment and same parameter.  Therefore, when changing the player or parameter, the
old synth is explicitly stopped before starting the new synth.


*/
SensorCtl {
	var <player; // When setting, stop previous synth before starting new one????? !!!!!
	var <param;  // When setting, stop previous synth before starting new one!!!!!
	var <id = 1, <ctl = \off;
	var <lo = 0, <hi = 1, <map = \lin;
	var <>c1 = "x.lag(1).exp(500, 1000)", <>c2 =  "(1 - z.lag(1)).exp(500, 1000)";
	var <>c3 = "(x + y + z).lag(1).exp(500, 1000)";

	*new { | player, param = \amp, id = 1, ctl = \xyz, lo = 0, hi = 1, map = \lin,
		c1 = "x.lag(1).exp(500, 1000)", c2 =  "(1 - z.lag(1)).exp(500, 1000)",
		c3 = "(x + y + z).lag(1).exp(500, 1000)" |
		// "Creating newe sensorctl".postln;
		^this.newCopyArgs(player, param, id, ctl, lo, hi, map, c1, c2, c3);
	}

	player_ { | argPlayer | this.stop; player = argPlayer; this.start; }
	param_ { | argParam | this.stop; param = argParam; this.start; }
	id_ { | argId | this.stop; id = argId.clip(1, 12); this.start; }
	ctl_ { | argCtl | ctl = argCtl; this.start; }
	lo_ { | argLo |
		lo = argLo;
		format("%.envir.set(%, %, %)", player.slash, param.slash, \lo.slash, lo).postln.share;
		// id.perform(\lo, player, param, lo);
	}
	hi_ { | argHi | hi = argHi; id.perform(\hi, player, param, hi); }
	map_ { | argMap | map = argMap; this.start; }

	stop {
		// "Debugging SensorCtl.stop".postln;
		format("nil @>. % %", player, param.slash).postln.share; }
	start {
		postln("!!!!!! debuggging start of Sensor for param" + param);
		// postln("debuggging SensorCtl ranges. lo is:" + lo + "hi is:" + hi);
		"I will stop this param before starting, just for sure".postln;
		postln("sending off to sensor" + id + "of player" + player + "and param" + param);
		id.perform(\off, player, param, lo, hi, map, c1, c2, c3);
		{
			id.perform(ctl, player, param, lo, hi, map, c1, c2, c3);
		}.defer(0.1);
	}

	saveParams { // of pa rams for saving as code.
		^[player, param, id, ctl, lo, hi, map, c1, c2, c3];
	}

	*fromParams { | params | // recreate from parameters saved as code
		^this.newCopyArgs(*params)
	}

	customize {
		{ | answers |
			#c1, c2, c3 = answers;
			[c1, c2, c3] do: { | a, i |
				postln("c" ++ (i + 1) + "is now:" + a);
			}
		}.editTexts([c1, c2, c3]);
	}
}