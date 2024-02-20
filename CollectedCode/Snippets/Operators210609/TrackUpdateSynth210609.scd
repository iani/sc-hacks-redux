/*  9 Jun 2021 10:45
Detailed check of Synth state monitoring and updates from Mediator.
*/

//:
~a = { WhiteNoise.ar(0.1).dup }.play;
~a.isPlaying;
//:+
~a = { WhiteNoise.ar(0.1).dup }.play;
NodeWatcher.register(~a);
~a.isPlaying.postln; // false. Must wait for start notification from Server
0.1.wait;
~a.isPlaying.postln; // true
1.wait;
~a.release;
~a.isPlaying.postln; // true. Must wait for free + notification from Server
0.5.wait;
~a.isPlaying.postln; // false
// ================================================================
//:
~a = { Mix(SinOsc.ar(\freq.kr(400) * (1..20), 0, 0.01)) }.scope;
NodeWatcher.register(~a);
currentEnvironment.addDependant({ | envir, param, val |
	postf("envir: %\nparam: %, val: %\n", envir, param, val);
	~a.set(param, val);
})
//:
~freq = 1000;
//:*
~freq = 100 rrand: 1000.0;
0.1.wait;
//: Note: setting parameter that does not exist will not issue an error.
~notfreq = 1000;
//:*
~notfreq = 100 rrand: 1000.0;
0.1.wait;
//================================================================
//: Using register (see Node:onFree) !!!
