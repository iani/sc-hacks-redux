/* 26 Sep 2022 07:11

*/
//:
(play: { ~word.scramble.postln }, word: "hello word") +> \test;
//:
(
play: { ~bus.set(~freq); }, bus: \freq.bus,
freq: (40..80).midicps.pseq,
dur: 0.1
) +> \test;
//:
{ SinOsc.ar(\freq.bin, 0, 0.1).dup } +> \x;
//:
\test.stop;
//: ============================================================
(
play: { ~bus.set(~freq.value); }, bus: \freq.bus,
degree: (0..15).pseq,
dur: 0.1
) +> \test
//: ============================================================
(
play: { ~bus.set(
	currentEnvironment[\freq].value
); }, bus: \freq.bus,
degree: (0..15).pseq,
dur: 0.1
) +> \test
//:------------------------------------------------------------
(dur: 0.2, val: (40..80).midicps.pseq) @>.test \freq;
//:------------------------------------------------------------
(dur: 0.25, val: (81..100).midicps.pseq) @> \freq;
//:
nil @>.test \freq;
//:
nil @> \freq;
//: