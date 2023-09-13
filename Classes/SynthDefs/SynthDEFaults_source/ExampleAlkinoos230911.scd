(
s.waitForBoot {
    // SynthDef for the melody
    SynthDef(\melody, {
        |freq = 440, duration = 1, amp = 0.3|
        var env, sig;

        env = EnvGen.kr(Env.perc(0.01, duration - 0.1, 0.1), doneAction: 2);
        sig = SinOsc.ar(freq, mul: env * amp);

        Out.ar(0, sig);
    }).add;


    SynthDef(\bass, {
        |freq = 220, duration = 1, amp = 0.2|
        var env, sig;

        env = EnvGen.kr(Env.perc(0.01, duration - 0.1, 0.1), doneAction: 2);
        sig = Saw.ar(freq, mul: env * amp);

        Out.ar(0, sig);
    }).add;


    SynthDef(\kick, {
        |freq = 60, dur = 0.5, amp = 0.4|
        var env, sig;

        env = EnvGen.kr(Env.perc(0.01, dur * 0.8, 0.1), doneAction: 2);
        sig = RLPF.ar(Pulse.ar(freq, 0.3), 100, 0.1);

        Out.ar(0, sig * env * amp);
    }).add;
    SynthDef(\hihat, {
        |dur = 0.25, amp = 0.2|
        var env, sig;

        env = EnvGen.kr(Env.perc(0.01, dur * 0.5, 0.1), doneAction: 2);
        sig = WhiteNoise.ar * env * amp;

        Out.ar(0, sig);
    }).add;


    Pbind(\instrument, \melody,
          \degree, Pseq([0, 2, 4, 5, 7, 9, 11], inf),
          \scale, Scale.major,
          \dur, 0.5,
          \amp, 0.3
    ).play;


    Pbind(\instrument, \bass,
          \degree, Pseq([0, 2, 4, 5], inf),
          \scale, Scale.major,
          \dur, 1,
          \amp, 0.2
    ).play;


    Pbind(\instrument, \kick,
          \dur, 1,
          \amp, 0.4,
          \group, s.defaultGroup
    ).play;


    Pbind(\instrument, \hihat,
          \dur, 0.25,
          \amp, 0.2,
          \legato, 0.9,
          \group, s.defaultGroup
    ).play;
}
)