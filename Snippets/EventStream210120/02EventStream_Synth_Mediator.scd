/* 31 Jan 2021 14:17
Alternating Events, EventStreams, and Synths on one environment variable,
using Mediator.push;
*/
//:Run this first to push a Mediator on the currentEnvironment:
Mediator.push;
//:Then run any of these in any order, any number of times""
~a = { SinOsc.ar(1000 rrand: 2000, 0, 0.1).dup }.play;
~a = { SinOsc.ar(1000 rrand: 2000, 0, Env.perc.kr(doneAction: 2)).dup }.play;
~a = { WhiteNoise.ar(0.1).dup }.play;
~a = (dur: 0.1, degree: 30, amp: 0.5).play;
~a = (dur: 0.1, degree: Pbrown(20, 30, 1), amp: 0.1).splay;
~a = { GrayNoise.ar(0.1).dup }.play;
//:================================================================
