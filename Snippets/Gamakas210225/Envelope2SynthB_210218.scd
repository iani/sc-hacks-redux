/*  6 Feb 2021 13:15
Playing different envelopes in one synth.
Using an expanded version of the Documentation on SynthDef,
with a scheme for chaining envelopes for playing level arrays
of indefinite length.


*/
//:================================================================
//:Create SynthDef and create test data
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

~levels = [400, 200, 600, 300, 100, 500, 200, 1200, 550, 1600, 150];
~times = Array.rand(~levels.size - 1, 0.01, 0.5);
~groups = ~levels
.collect({ | l, i | if (i > 0 and: { i % 3 == 0 }) { l.dup } { l }})
.flat.clump(4);
[~groups, ~times.clump(3)].flop;
~envs = [~groups, ~times.clump(3)].flop.collect({ | specs | Env(*specs)});
//:
~a = Synth(\chainenv, [\env, ~envs[0], \gate2, 1]);
//:
~a.release;
//:+one synth per envelopw.
~envs do: { | env |
	~a = Synth(\chainenv, [\env, env, \gate2, 1]);
	env.dur.wait;
	1.wait;
};
1.wait;
~a.release;
//:+one continuous synth playing all envelopes sequentially
~a = Synth(\chainenv, [\env, ~envs[0]]);
~envs do: { | env |
	~a.set(\env, env, \gate2, 1);
	env.dur.wait;
	// 3.wait;
};
1.wait;
~a.release;

