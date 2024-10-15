/*  6 Jul 2023 23:42
An envelope with fixable duration and constant attack / decay curves.
*/

DurEnv {
	*kr { | dur = 1 |
		var env, trig, adjustedDur, ad = 0.2;
		adjustedDur = dur - (ad * 2 max: 0.01);
		env = Env([0.0, 1.0, 1.0, 0.0], [ad, adjustedDur, ad], -4, releaseNode: 2);
		trig = Trig.kr(1, adjustedDur - ad max: ad /* adjustedDur */);
		// trig = Impulse.kr(\trig.kr(1) * adjustedDur - ad max: ad);
		^EnvGen.kr(env, trig, doneAction: Done.freeSelf);
	}
}