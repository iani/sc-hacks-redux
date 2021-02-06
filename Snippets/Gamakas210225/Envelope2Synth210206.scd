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

//:
a = Synth(\help_Env_newClear, [\gate, 1, \env, Env([700,900,900,800], [1,1,1], \exp)]);
//:*
a.set(\env, Env({ rrand(50, 80).midicps } ! 4, [0.2,0.5,0.25], \exp), \gate, 1);
1.5.wait;
//:

//:reset then play again:
a.free;

Synth(\help_Env_newClear, [\gate, 1, \env, Env({ rrand(60, 70).midicps } ! 4, [1,1,1], \exp)]);

//: the same written as an event:
(instrument: \help_Env_newClear, gate: 1, env: Env({ rrand(60, 70).midicps } ! 4, [1,1,1], \exp)).play;


