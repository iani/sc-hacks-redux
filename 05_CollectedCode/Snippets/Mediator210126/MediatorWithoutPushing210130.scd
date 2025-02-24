/* 30 Jan 2021 17:27
Testing how to use without pushing: In order to use as envir
in method code.
*/
//:+
a = Mediator();
{ | i |
	i.postln;
	a.put(\a, { SinOsc.ar(2000 + 1000.rand, 0, 0.3).dup }.play);
	a.postln;
	0.5.rrand(1.4).wait;
} ! 10;
a.put(\a, nil);
"done".postln;
a.postln;
