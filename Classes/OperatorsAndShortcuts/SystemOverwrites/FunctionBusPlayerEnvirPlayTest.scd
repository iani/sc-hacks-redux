/* 28 Aug 2023 11:15

*/

{ SinOsc.ar(\freq.br(400), 0, 0.1).pan } +> \test;
//:
{ LFNoise1.kr(1/3).range(400, 800) } @>.test \freq;
//:
{ SinOsc.kr(15).range(1, 50) } @@>.freq1 [\freq, \test];
//:
