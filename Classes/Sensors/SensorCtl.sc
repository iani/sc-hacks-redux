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

	*new { | player, param = \amp, id = 1, ctl = \xyz, lo = 0, hi = 1, map = \lin |
		^this.newCopyArgs(player, param, id, ctl, lo, hi, map) // .init
	}

	player_ { | argPlayer | this.stop; player = argPlayer; this.start; }
	param_ { | argParam | this.stop; param = argParam; this.start; }
	id_ { | argId | id = argId; this.start; }
	ctl_ { | argCtl | ctl = argCtl; this.start; }
	lo_ { | argLo | lo = argLo; this.start; }
	hi_ { | argHi | hi = argHi; this.start; }
	map_ { | argMap | map = argMap; this.start; }

	stop { format("nil @>. % %", player, param.slash).share; }
	start { id.perform(ctl, player, param, lo, hi, map); }

	saveParams { // of params for saving as code.
		^[player, param, id, ctl, lo, hi, map;]
	}

	*fromParams { | params | // recreate from parameters saved as code
		^this.newCopyArgs(*params)
	}


}