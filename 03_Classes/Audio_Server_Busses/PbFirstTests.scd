/* 30 Nov 2022 11:45

*/
(bus: Pb(), play: { ~bus.postln }) +> \test;
//:
(dur: 1/3) ++> \test;

//:
r = {
	loop {
		10.rand @>.pb \bus;
		1.wait;
	}

}.fork;
//:
r.stop;
//:
{ LFSaw.kr(0.01).range(0, 10) } @>.pb \bus;
//:
nil @>.pollbusses \bus;
//:============================================================
//:
{ LFNoise0.kr(8).range(60, 90).round(1).midicps } @>.pb \freq;
//:
(\freq Pb(\freq)) +> \test
//:Shortcut for Pb(...):
(\freq: \freq.pb) +> \test
//:
(dur: 0.1) ++> \test;
//:
{ SinOsc.kr(0.1).range(60, 90).round(1).midicps } @>.pb \freq
//:
{ SinOsc.kr(0.1).range(60, 90).midicps } @>.pb \freq
//:
{ LFSaw.kr(0.1).range(60, 90).round(1).midicps } @>.pb \freq
//:
{ SinOsc.kr(10).range(60, 90).round(1).midicps } @>.pb \freq
//:
{ SinOsc.kr(10).range(60, 90).midicps } @>.pb \freq
//:
(dur: 0.05) ++> \test;
