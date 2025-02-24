/*  9 Jun 2021 09:32
test how replacemenet of synths and event streams works.
*/

~a = { WhiteNoise.ar(0.9).dup }.play;

~a = { GrayNoise.ar(0.9).dup }.play;

~a = { SinOsc.ar(1400, 0, 0.9).dup }.play;
//:
~a = (amp: 0.5, dur: 0.05, degree: Pbrown(10, 20, 1)).splay;
//:
~a = (amp: 0.5, dur: 0.1, midinote: Prand((60..100), inf)).splay;
