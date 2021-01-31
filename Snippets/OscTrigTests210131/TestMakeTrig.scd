/* 31 Jan 2021 12:36
Test releasing function maketrig.
*/
//:

a = { Impulse.kr(0.5) }.makeTrig;
b = a.play;
b.release;
//:
Mediator.push;
//:
~a = {
	Out.ar(0, SinOsc.ar(1000 rrand: 2000, 0, 0.1).dup;);
	Impulse.kr(0.5)
}.makeTrig.play;
//:
~release = 0.0;
//:
~a.release(0.00);