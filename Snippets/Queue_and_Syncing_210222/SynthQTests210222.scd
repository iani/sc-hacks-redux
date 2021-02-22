/* 22 Feb 2021 15:03
	Testing new method q for Synth creation
*/

var defname; // make sure to create new synthdef for each test.
defname = format("synctest%", UniqueID.next);
SynthDef(defname, { | freq = 440 |
	var src;
	src = SinOsc.ar(freq, 0, 0.1).dup;
	Out.ar(0, src * Env.adsr.kr(2, \gate.kr(1)));
}).add;
a = Synth.q(defname, [freq: 500.rrand(1600)]);
//:
a.release;