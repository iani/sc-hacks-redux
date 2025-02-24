// frequency modulation
(
play {
    DynKlang.ar(`[
        [800, 1000, 1200] + SinOsc.kr([2, 3, 4.2], 0, [13, 24, 12]),
        [0.3, 0.3, 0.3],
        [pi,pi,pi]
    ]
) * 0.1
};
)
//:

//: building new synths every 2 seconds
(
fork {
    loop {
        play {
            var mod = SinOsc.kr(Rand(0.1, 0.9), 0, Rand(5, 20));
            Pan2.ar(DynKlang.ar(`[ Array.rand(12, 200.0, 2000.0), 1, mod ]), 1.0.rand)
                * EnvGen.kr(Env.sine(4), 1, 0.02, doneAction: Done.freeSelf)
        };
        2.wait;
    }
}
)

//:

// resetting the frequencies and amplitudes after the synth has been created
(
SynthDef('help-dynKlang', { |out,
    freqs=#[220, 440, 880, 1760],
    amps=#[0.35, 0.23, 0.12, 0.05],
    phases=#[1, 1.5, 2, 2.5]|

    Out.ar(out, DynKlang.ar(`[freqs, amps, phases]))
}).add
)
//:
a = Synth('help-dynKlang');

a.setn(\freqs, Array.rand(4, 500, 2000));
a.setn(\amps, Array.rand(4, 0.01, 0.25));