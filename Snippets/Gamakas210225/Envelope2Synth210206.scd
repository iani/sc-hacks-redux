/*  6 Feb 2021 13:15
Playing different envelopes in one synth.
*/
//:================================================================
//:From the doc (https://doc.sccode.org/Classes/EnvGen.html)
SynthDef(\help_Env_newClear, { |out = 0|
    var env, envctl;
    env = Env.newClear(4);
    envctl = \env.kr(env.asArray);
    Out.ar(out, SinOsc.ar(EnvGen.kr(envctl, \gate.tr, doneAction: 0), 0, 0.03).dup);
}).add;
/* Note: 
Synths made with this synthdef are not releasable.
- Mediator will not stop them automatically
- Stopping them with free produces a click. 
*/
//:Releasable variant of above example
SynthDef(\chainenv, { |out = 0|
    var env, envctl;
    env = Env.newClear(4);
    envctl = \env.kr(env.asArray);
    Out.ar(out,
		SinOsc.ar(EnvGen.kr(envctl, \gate2.tr, doneAction: 0), 0,
			Env.fade * 0.03
		).dup
	);
}).add;

//:
a = Synth(\chainenv, [\gate2, 1, \env, Env([700,900,900,800], [1,1,1], \exp)]);
//:
a release: 0.4;
//:+trigger the frequency env later
a = Synth(\chainenv, [\env, Env([700,900,900,800], [1,1,1], \exp)]);
2.wait;
a.set(\gate2, 1);
//:*Create synths in a aloop. Use Mediator to release replaced synth
~a = Synth(\chainenv, [\gate2, 1, \env, Env(Array.rand(4, 40, 90).midicps, [1,1,1], \exp)]);
4.wait;
//:+change the envelope without starting new syths
~a = Synth(\chainenv, [\gate2, 1, \env, Env(Array.rand(4, 40, 90).midicps, [1,1,1], \exp)]);
4.wait;
10 do: { | i |
	postf("playing env % of 10\n", i + 1);
	~a.set(\env, Env({ rrand(50, 80).midicps } ! 4, [0.2,0.5,0.25], \exp), \gate2, 1);
	2.5.wait;
};
~a.release;
// ================================================================
//:play an indefinite sequence of breakpoints by looping
//:Prevent jumps: first point of next env has same value as last of previous env.
~levels = [400, 200, 600, 300, 100, 500, 200, 1200, 550, 1600, 150];
~times = Array.rand(~levels.size - 1, 0.1, 2);
~groups = ~levels
.collect({ | l, i | if (i > 0 and: { i % 3 == 0 }) { l.dup } { l }})
.flat.clump(4);
[~groups, ~times.clump(3)].flop;
~envs = [~groups, ~times.clump(3)].flop.collect({ | specs | Env(*specs)});
//:
~a = Synth(\chainenv, [\env, envs[0], \gate2, 1]);
//:


~times = Array.rand(~levels.size - 1, 0.1, 2);
~head = ~levels[..3];
~tail = ~levels[4..].clump(3);
//:
//:*
~a = Synth(\help_Env_newClear, [\gate, 1, \env, Env({ rrand(50, 90).midicps } ! 4, [1,1,1], \exp)]);
3.wait;
~a.free;
//: the same written as an event:
(instrument: \help_Env_newClear, gate: 1, env: Env({ rrand(60, 70).midicps } ! 4, [1,1,1], \exp)).play;


//:
currentEnvironment;
//:
~