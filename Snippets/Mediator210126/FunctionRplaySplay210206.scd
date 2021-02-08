/*  6 Feb 2021 17:36
Examples of Function tplay, splay.
And alternative syntax using binary operators +>, *>
*/

//:
{ SinOsc.ar(440 rrand: 1300, 0, 0.1).dup }.splay;
//:
{ WhiteNoise.ar(0.1).dup }.splay;
//: ================ The above are equivalent to:
{ SinOsc.ar(440 rrand: 1300, 0, 0.1).dup } +> \default;
//:
{ WhiteNoise.ar(0.1).dup } +> \default;
//: ================================================================
//: Routines
{ loop { (dur: 0.1, degree: 0.rrand(10)).play; 0.1.wait } }.tplay;
//:
{ loop { (dur: 2.5, degree: -10.rrand(0) + ((0..3) * (1..4).choose)).play; 2.wait } }.tplay;
//:
{ loop { (dur: 0.1, degree: 0.rrand(10)).play; 0.1.wait } } *> \default;
//:
{ loop { (dur: 2.5, degree: -10.rrand(0) + ((0..3) * (1..4).choose)).play; 2.wait } } *> \default;
//:
{ inf do: { | d | (dur: 0.1, degree: d % 15).play; 0.1.wait } }.tplay;
//:
\default.stop;
//:Try stop-start, pause-resume, reset;
~default;
~default.stop;
~default.start; 
~default.pause;
~default.resume;
~default.reset; 
~default.start; 
//:
t = Task({ 3 do: { | i | i.postln; 0.25.wait; } });
t.play;
t.stop;
t.reset;
