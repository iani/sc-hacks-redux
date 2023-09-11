

s.waitForBoot {
    SynthDef(\synth1, {
        |out=0, freq=440, dur=0.2, amp=0.5|
        var sig = Saw.ar(freq, 0.3) * Line.kr(amp, 0, dur);
        Out.ar(out, sig);
    }).add;

    SynthDef(\synth2, {
        |out=0, freq=220, dur=0.3, amp=0.3|
        var sig = Pulse.ar(freq, 0.3) * Line.kr(amp, 0, dur);
        Out.ar(out, sig);
    }).add;

    SynthDef(\drums, {
        |out=0, dur=0.4, amp=0.6|
        var sig = Mix.fill(4, { WhiteNoise.ar * EnvGen.kr(Env.perc(0.01, 0.1), doneAction: 2) });
        Out.ar(out, sig * amp);
    }).add;

    SynthDef(\bass, {
        |out=0, freq=110, dur=0.5, amp=0.6|
        var sig = SinOsc.ar(freq, 0, amp) + Saw.ar(freq * 0.5, 0.2);
        Out.ar(out, sig);
    }).add;

    SynthDef(\piano, {
        |out=0, freq=60, dur=0.4, amp=0.5|
        var sig = SinOsc.ar(freq, 0, amp);
        Out.ar(out, sig);
    }).add;

    Pbind(
        \instrument, \synth1,
        \degree, Pseq([0, 2, 4, 5, 7, 9, 11, 12], inf),
        \dur, 0.5,
        \amp, 0.4
    ).play;

    Pbind(
        \instrument, \synth2,
        \degree, Pseq([7, 6, 5, 3], inf),
        \dur, 0.4,
        \amp, 0.5
    ).play;

    Pbind(
        \instrument, \synth1,
        \degree, Pseq([0, 2, 4, 5], inf),
        \dur, 0.3,
        \amp, 0.6
    ).play;

    Pbind(
        \instrument, \drums,
        \dur, Pseq([0.2, 0.2, 0.2, 0.2], inf),
        \amp, 0.7
    ).play;

    Pmono(
        \instrument, \bass,
        \degree, Pseq([0, 3, 5, 7], inf),
        \legato, 0.4,
        \amp, 0.6
    ).play;

    Pbind(
        \instrument, \piano,
        \degree, Pseq([0, 4, 7, 3], inf),
        \dur, 0.6,
        \amp, 0.4
    ).play;

    60.wait;

    Pdef.all.do(_.stop);
};