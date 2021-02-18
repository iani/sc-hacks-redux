/* 18 Feb 2021 17:23
Shortcut for releasable adsr envelope ugen.
TODO: Add arguments and/or controls to adjust attack/release times?
Alternnative name: wrap?
*/

+ Env {
	*fade { | fadeTime = 0.02 |
		^Env.adsr(fadeTime, fadeTime, 1).kr(2, \gate.kr(1))
	}

	dur { ^times.sum }
}