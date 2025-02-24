/* 17 Nov 2023 18:24
doWhenReallyBooted

Preliminary test

*/


var synthcopyfunc;

synthcopyfunc = {
	"adding output copy synth - v 2".postln;
	{
		Out.ar(2, In.ar([0, 1]));
	}.play(target: nil.asTarget, addAction: 'addAfter')
};

ServerBoot add: {
	{
		synthcopyfunc.value;
	}.defer(1.0)
}