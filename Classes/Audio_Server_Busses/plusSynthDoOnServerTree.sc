/* 28 Nov 2022 22:17
Experimental: avoid error message on CmdPeriod when running a pattern
	which contains a Pdefn

*/

+ Synth {
	doOnServerTree {
		postln("CmdPeriod on Pdefn" + this + "received doOnServerTree");
	}
}