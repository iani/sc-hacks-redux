/*  6 Feb 2021 17:36
Examples of Function rplay, splay.
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
{ loop { (dur: 0.1, degree: 0.rrand(10)).play; 0.1.wait } }.rplay;
//:
{ loop { (dur: 2.5, degree: -10.rrand(0) + ((0..2) * (1..4).choose)).play; 2.wait } }.rplay;
//:
~default;
~default.stop;