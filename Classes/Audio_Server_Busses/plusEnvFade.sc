/* 18 Feb 2021 17:23
Shortcut for releasable adsr envelope ugen.
TODO: Add arguments and/or controls to adjust attack/release times?
Alternnative name: wrap?
*/

+ Env {
	*fade {
		^Env.adsr.kr(2, \gate.kr(1))
	}	
}