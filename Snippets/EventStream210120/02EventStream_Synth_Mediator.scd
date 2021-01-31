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
~crazyTrio = { | player |
	var players;
	players = [
		{ SinOsc.ar(1000 rrand: 2000, 0, 0.1).dup },
		{ SinOsc.ar(1000 rrand: 2000, 0, 0.3 * Env.perc.kr(doneAction: 2)).dup },
		{ WhiteNoise.ar(0.01).dup * Decay.kr(Impulse.kr(8)) },
		(dur: 0.1, degree: Pbrown(20, 30, 1), amp: 0.3),
		(dur: 0.15, degree: Pseries(-10, 2, 10), amp: 0.3),
		(dur: 0.5, degree: Pseries(10, -2, 10), amp: 0.3),
		{ GrayNoise.ar(0.01).dup * Decay.kr(Impulse.kr(5)) }
	];
	loop {
		postf("playing new %\n", player);
		currentEnvironment[player] = players.choose.xplay;
		0.5.rrand(3).wait;
	}	
};

[\voice1, \voice2, \voice3] do: { | v | { ~crazyTrio.(v) }.fork }; 
	
