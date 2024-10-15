/* 21 Oct 2022 14:03
Testing flexible bus connections.
*/

{ WhiteNoise.ar(0.1) * Decay.kr(In.kr(\cycle1.kr(0))) } +> \cycle1;
{ WhiteNoise.ar(0.1) * Decay.kr(\cycle1.krin(0)) } +> \cycle1;
{ Out.kr(0, Impulse.kr(1).kdsr) } +> \x;
{ Out.kr(1, Impulse.kr(10).kdsr) } +> \y;

(cycle1: 1) ++> \cycle1;
(cycle1: 0) ++> \cycle1;
//:
{ WhiteNoise.ar(0.1) * Decay.kr(\trig.krin(0)) } +> \test;
(trig: 1) ++> \test;
(trig: 0) ++> \test;

{ WhiteNoise.ar(0.1).dup } +>.z \test;

\test.at(\z);




