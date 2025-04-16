//ambient textures. Very kitchy - but still 2 study
//https://sccode.org/1-5el#c1034
//:
(
s.options.memSize_(65536 * 10);
s.reboot;
s.waitForBoot({
    var bass_pattern, lead_pattern, lead_pattern_var, seq_pattern, noise_pattern, kick_pattern, hihat_pattern, pattern;
    var reverb_out = Bus.audio(s, 2);

    ~group1 = Group();
    ~group2 = Group(~group1, \addAfter);

    SynthDef(\drone, {
        | out,freq, dur, amp, pan, width |
        var env = EnvGen.kr(Env.new([0, 1, 1, 0], [1.0, dur, 3.0]), doneAction:Done.freeSelf);
        var sig = Array.fill(20, {
            var multiplier = [
                0.99, 1, 1.01,
                1.99, 2, 2.01,
                3.99, 4, 4.01].choose;
            var ampmult = multiplier.reciprocal;
            RLPF.ar(
                in: LFSaw.ar(
                    freq:LFNoise1.kr(0.5.rrand(1.0)).range(freq*(1-width), freq*(1+width))*multiplier,
                    iphase:0.0.rrand(2pi)),
                rq: LFNoise1.kr(0.5).range(0.1,0.6),
                mul:LFNoise0.kr(2.0).range(16.reciprocal, ampmult));
        });
        var finalsig = Splay.ar(env*sig);
        var finalsig_panned = Balance2.ar(
            finalsig[0],
            finalsig[1],
            pan*SinOsc.kr(LFNoise1.kr(1).range(0.05,0.2)));
        Out.ar(out, amp*finalsig_panned);
    },
    metadata: (
        credit: "Stefaan Himpe",
        category: \pad,
        tags: [\pitched]
    )).add;

    s.sync;

    SynthDef(\lead, {
        | out, freq, dur, amp, pan, wobblelow=0.3, wobblehigh=1.0 |
        var atk = 0.5;
        var env = EnvGen.kr(Env([0, 1, 1, 0], [atk, dur-atk, 10]), doneAction:Done.freeSelf);
        var freqenv = EnvGen.kr(Env([0.7, 1, 1], [0.3, dur]), doneAction:Done.none);
        var sharpness = freq.cpsmidi.linlin(0,127,1,15);
        var wobble = SinOsc.kr(6).range(wobblelow, wobblehigh);
        var sig = RLPF.ar(
            in: LFSaw.ar(freqenv*freq*LFNoise1.kr(1.0).range(0.995,1.005)),
            freq:freq*sharpness,
            rq:LFNoise1.kr(3).range(0.2,0.5),
            mul:wobble);
        var panned_sig;
        var n = 10;
        n.do({ sig = AllpassN.ar(sig, 0.050, [Rand(0, 0.05), Rand(0, 0.05)], 1) });
        panned_sig = Pan2.ar(sig/n, pan);
        Out.ar(out, amp*env*panned_sig);
    },
    metadata: (
        credit: "Stefaan Himpe",
        category: \pad,
        tags: [\pitched]
    )).add;

    s.sync;

    SynthDef(\seq, {
        | out, freq, dur, amp, pan, glissdir, gliss |
        var laggliss = Lag.kr(gliss, 0.1);
        var env = EnvGen.kr(Env.perc(0.01, 2.0), doneAction:Done.freeSelf);
        var freqenv = EnvGen.kr(Env([1 + (glissdir*laggliss), 1 - (glissdir*laggliss), 1, 1], [0.07, 0.07, 1]), doneAction:Done.none);
        var filtenv = EnvGen.kr(Env([0.5, 1, 1], [0.15, dur]), doneAction:Done.none);
        var sig = LPF.ar(Pulse.ar(freqenv*freq), freq*filtenv*3);
        var panned_sig = Pan2.ar(sig, pan);
        Out.ar(sig, amp*env*panned_sig);
    },
    metadata: (
        credit: "Stefaan Himpe",
        category: \bell,
        tags: [\pitched]
    )).add;

    s.sync;

    SynthDef(\noise, {
        | out, dur, amp, pan |
        var env = EnvGen.kr(Env.perc(1.0, dur*0.8, curve:10.neg), doneAction:Done.freeSelf);
        var sig = LPF.ar(WhiteNoise.ar()*LFPulse.kr(LFNoise1.kr(0.5).range(3,12)), LFNoise1.kr(2).range(2, 1)*4000);
        Out.ar(out, Pan2.ar(env*amp*sig, pan));
    }, metadata: (
        credit: "Stefaan Himpe",
        category: \noise,
        tags: [\unpitched]
    )).add;

    s.sync;

    SynthDef(\kick, {
        | out = 0, pan = 0, amp = 1, curve = -4, drumAmp = 1, beaterAmp = 0.02,
        drumFreq = 50, drumHarmonic = 2, drumSweep = 0.02, drumAtt = 0.005, drumRel = 0.4,
        drumFilter = 1000, modIndex = 6.5, modFreq = 5,
        beaterFreq = 500, beaterHarmonic = 12, beaterSweep = 0.03, beaterAtt = 0.01, beaterRel = 0.3|

        var drumEnv, drumContour, drum, beaterContour, beaterEnv, beater, snd;
        drumEnv = Env.perc(attackTime: drumAtt, releaseTime: drumRel, level: drumAmp, curve: curve).kr;
        drumContour = Line.kr(start: drumFreq * drumHarmonic, end: drumFreq, dur: drumSweep);
        drum = PMOsc.ar(
            carfreq: drumContour,
            modfreq: modFreq,
            pmindex: modIndex);
        drum = LPF.ar(in: drum, freq: drumFilter, mul: drumEnv);
        beaterEnv = Env.perc(attackTime: beaterAtt, releaseTime: beaterRel, level: beaterAmp, curve: curve).kr;
        beaterContour = Line.kr(start: beaterFreq * beaterHarmonic, end: beaterFreq, dur: beaterSweep);
        beater = HPF.ar(in: WhiteNoise.ar, freq: beaterFreq);
        beater = LPF.ar(in: beater, freq: beaterContour, mul: beaterEnv);
        snd = Mix.ar(drum + beater) * amp;
        DetectSilence.ar(in: snd, doneAction: 2);
        Out.ar(out, Pan2.ar(snd, pan));
    },
    metadata: (
        credit: "Renick Bell",
        category: \drums,
        tags: [\pitched, \kick, \sos]
    )
    ).add;

    s.sync;

    SynthDef(\reverb, {
        var in = In.ar(\in.kr(),2);
        var out = \out.ar();
        10.do({ in = AllpassN.ar(in, 0.050, [Rand(0, 0.05), Rand(0, 0.05)], 1) });
        Out.ar(out, in);
    }).add;

    s.sync;

    // define a 2d markov chain (pseudo-)object
    ~markov2d = (
        \safeReg : {
            // adding methods to the event using safeReg will warn you if you overwrite methods
            // that already have a pre-defined meaning (like: "next").
            | self, name, implementation, strict=true, verbose=0 |
            var symbolicname = name.asSymbol;
            if (self.respondsTo(symbolicname)) {
                var txt = "Error! Registering" + "\\" ++ symbolicname + "would overwrite a member that exists already.";
                txt.postln;
                if (strict) {
                    Exception(txt).throw;
                }
            } {
                if (self.keys.includes(symbolicname)) {
                    var txt = "Error! Cannot register" + "\\" ++ symbolicname + "twice.";
                    txt.postln;
                    if (strict) {
                        Exception(txt).throw;
                    }
                } {
                    if (verbose != 0) {
                        ("Registered" + "\\" ++ symbolicname ++ ".").postln;
                    };
                    self[symbolicname] = implementation;
                };
            };
        },
        \storage : (
            // state to track 2nd last and last state, needed to select the next state
            \previous : nil,
            \previous_previous : nil
        );
    );
    ~markov2d.safeReg(\setup, {
        // function called to setup the next-state table
        | self, first, second, list_of_pair_of_next_prob |
        var key = (""++first++"_"++second).asSymbol;
        if (self.storage[key].isNil) {
            var next = [];
            var prob = [];
            list_of_pair_of_next_prob.do({
                |el|
                next = next.add(el[0]);
                prob = prob.add(el[1]);
            });
            self.storage[key] = [next, prob.normalizeSum];
        };
    });
    ~markov2d.safeReg(\getnext, {
        // selects the next state, given the previous state
        // internally, it will also track the 2nd last state and use both
        // 2nd last and last state to select the next state
        | self, previous |
        var next_prob, key, next;
        self.storage.previous_previous = self.storage.previous;
        self.storage.previous = previous;
        if (self.storage.previous.isNil) {
            next = 1;
        } {
            if (self.storage.previous_previous.isNil) {
                next = [3, 4, 5, 6].choose;
            } {
                key = (""++self.storage.previous_previous++"_"++previous).asSymbol;
                if (self.storage[key].isNil) {
                    ("Warning:"+key+"not found in markov table!").debug;
                    [1, 2, 3, 4, 5, 6].choose;
                } {
                    next_prob = self.storage[key];
                    next = Pwrand(next_prob[0], next_prob[1], 1).asStream.next;
                }
            };
        };
    });

    // generate slightly better than random harmonic progressions using a 2nd order markov chain (initialized "by ear")
    // table is defined as follows:
    // setup(2nd_last tonal function, last tonal function, [ [next tonal function 1, prob1], [next2, prob2], ... ]
    ~markov2d.setup(1, 2, [[4, 0.5], [5, 1], [6, 0.5]]);
    ~markov2d.setup(1, 3, [[5, 1], [1, 1], [4, 0.7], [6, 1.5]]);
    ~markov2d.setup(1, 4, [[5, 1], [3, 0.5], [6, 0.5], [2, 0.4]]);
    ~markov2d.setup(1, 5, [[1, 1], [4, 1], [3, 0.6], [6, 0.5], [2, 0.5]]);
    ~markov2d.setup(1, 6, [[5, 1], [4, 1], [3, 0.6], [1, 0.5], [2, 0.5]]);
    ~markov2d.setup(2, 1, [[5, 1],[4,1],[6,0.5]]);
    ~markov2d.setup(2, 3, [[6, 1],[4,0.3],[5,0.3],[1,0.3]]);
    ~markov2d.setup(2, 4, [[5, 1], [1, 0.7], [3, 0.5], [6, 0.4]]);
    ~markov2d.setup(2, 5, [[1, 1], [3, 0.2], [6, 0.2]]);
    ~markov2d.setup(2, 6, [[5,1]]);
    ~markov2d.setup(3, 1, [[4, 1], [6, 1]]);
    ~markov2d.setup(3, 2, [[1, 1], [6, 1], [5, 1], [4, 1]]);
    ~markov2d.setup(3, 4, [[5, 1], [1, 1]]);
    ~markov2d.setup(3, 5, [[1, 1], [2, 0.3], [6, 0.5]]);
    ~markov2d.setup(3, 6, [[2, 1], [5, 1], [1, 1], [4, 1]]);
    ~markov2d.setup(4, 1, [[4, 1], [5, 1], [3, 1], [2, 1], [6, 0.3]]);
    ~markov2d.setup(4, 2, [[1, 1], [5, 1], [3, 1], [6, 1]]);
    ~markov2d.setup(4, 3, [[6, 1], [1, 1], [5, 1], [2, 0.3]]);
    ~markov2d.setup(4, 5, [[1, 2], [6, 1], [3, 0.3], [2, 0.1]]);
    ~markov2d.setup(4, 6, [[5, 1], [1, 1], [4, 0.5]]);
    ~markov2d.setup(5, 1, [[4, 1], [3, 1], [2, 1]]);
    ~markov2d.setup(5, 2, [[1, 1], [4, 1], [3, 1], [4, 1], [5, 1]]);
    ~markov2d.setup(5, 3, [[1,1]]);
    ~markov2d.setup(5, 4, [[1,1], [5, 0.5], [6, 1], [2,1]]);
    ~markov2d.setup(5, 6, [[3,1], [4,0.5], [2, 0.5], [1, 0.3]]);
    ~markov2d.setup(6, 1, [[3, 1], [5,1], [2, 0.3], [4, 0.3]]);
    ~markov2d.setup(6, 2, [[5,1], [3,1]]);
    ~markov2d.setup(6, 3, [[4,1], [2,1], [1,1], [5,1]]);
    ~markov2d.setup(6, 4, [[5,1], [2, 0.7], [1, 0.5], [3, 0.5]]);
    ~markov2d.setup(6, 5, [[4,1], [1,1], [2,0.3], [3,0.5]]);


    ~rev = Synth(\reverb, [\in, reverb_out, \out, 0], ~group2);
    ~previous_tonal_function = nil;
    ~tonal_function_just_changed = true;
    ~duration_scaler = 3;
    ~scale = [ Scale.ionian(\just), Scale.aeolian(\just) ].choose;
    ~gliss_amt = 0;
    ("\nSet scale to:"+~scale.name+"and gliss_amt to:" + ~gliss_amt).debug;

    bass_pattern = Pbind(
        \instrument, \drone,
        [\degree,\octave, \tonalfunction], Pfunc({
            | event |
            ~previous_tonal_function = ~markov2d.getnext(~previous_tonal_function);
            [~previous_tonal_function-1, [2,3].choose, ~previous_tonal_function]
        }),
        \dur, Pfunc { var dur = [5,10,20,25].choose; ("\nfor the next"+dur+"beats, ").debug; dur},
        \amp, 0.9,
        \out, reverb_out,
        \width, Pbrown(0.01, 0.04, 0.005, inf),
        \pan, Pbrown(-0.7,0.7,0.1,inf),
        \group, ~group1,
        \scale, Pfunc { ~scale },
        \settonalfunction, Pfunc{
            |event|
            var new_tonal_function = event[\tonalfunction];

            if (0.03.coin) {
                ~gliss_amt = if (0.5.coin) { 0.0 } { 0.0.rrand(0.4); };
                ("changed gliss_amt to:" + ~gliss_amt).debug;
            };

            new_tonal_function.debug("tonal function switched to");
            if (new_tonal_function == 1) {
                ~allowed_lead_degrees = [0, 2, 4, 11, Rest()];
                ~allowed_lead_probabilities = [1, 1, 1, 0.1, 3].normalizeSum;
                if (~previous_tonal_function != 1) {
                    ~previous_tonal_function = 1;
                    ~tonal_function_just_changed = true;
                };
            };
            if (new_tonal_function == 2) {
                ~allowed_lead_degrees = [1, 3, 5, 7, Rest()];
                ~allowed_lead_probabilities = [1, 1, 1, 0.3, 3].normalizeSum;
                if (~previous_tonal_function != 2) {
                    ~previous_tonal_function = 2;
                    ~tonal_function_just_changed = true;
                };

            };
            if (new_tonal_function == 3) {
                ~allowed_lead_degrees = [2, 4, 6, Rest()];
                ~allowed_lead_probabilities = [1, 1, 1, 3].normalizeSum;
                if (~previous_tonal_function != 3) {
                    ~previous_tonal_function = 3;
                    ~tonal_function_just_changed = true;
                };

            };
            if (new_tonal_function == 4) {
                ~allowed_lead_degrees = [3, 0, 5, Rest()];
                ~allowed_lead_probabilities = [1, 1, 1, 3].normalizeSum;
                if (~previous_tonal_function != 4) {
                    ~previous_tonal_function = 4;
                    ~tonal_function_just_changed = true;
                };
            };
            if (new_tonal_function == 5) {
                ~allowed_lead_degrees = [4, 3, 1, 6, Rest()];
                ~allowed_lead_probabilities = [1, 1, 1, 0.3, 3].normalizeSum;
                if (~previous_tonal_function != 5) {
                    ~previous_tonal_function = 5;
                    ~tonal_function_just_changed = true;
                };
            };
            if (new_tonal_function == 6) {
                ~allowed_lead_degrees = [5, 2, 0, Rest()];
                ~allowed_lead_probabilities = [1, 1, 1, 3].normalizeSum;
                if (~previous_tonal_function != 6) {
                    ~previous_tonal_function = 6;
                    ~tonal_function_just_changed = true;
                };
            };
            0.01
        }
    );

    lead_pattern = Pbind(
        \instrument, \lead,
        \degree, Pfunc {
            |event|
            if (~tonal_function_just_changed == true) {
                ~tonal_function_just_changed = false;
                Pseq([~allowed_lead_degrees[0]], 1).asStream.next;
            } /* else */ {
                Pwrand(~allowed_lead_degrees, ~allowed_lead_probabilities, inf).asStream.next;
            };
        },
        \octave, Prand([4,5], inf),
        \dur, Prand([0.25, 0.5, 0.75, 1.0]*~duration_scaler, inf),
        \amp, 1.0,
        \pan, Pwhite(-0.5, 0.5, inf),
        \wobblelow, Pbrown(0.0, 0.2, 0.05, inf),
        \legato, 0.7,
        \scale, Pfunc { ~scale },
        \group, ~group1,
        \out, reverb_out
    );

    lead_pattern_var = Pbind(
        \instrument, \lead,
        \degree,
        Pseq([
            Pseq([Rest()], 50),
            Prand([
                Pseq([Rest()], 50),
                Pfunc {
                    |event|
                    if (~tonal_function_just_changed == true) {
                        ~tonal_function_just_changed = false;
                        Pseq([~allowed_lead_degrees[0]], 1).asStream.next;
                    } /* else */ {
                        if (0.01.coin) {
                            nil;
                        } {
                            Pwrand(~allowed_lead_degrees, ~allowed_lead_probabilities, 50).asStream.next;
                        };
                    };
                },
            ], inf),
        ], 1),
        \octave, Prand([4,5,6,Rest()], inf),
        \dur, Prand([0.25, 0.5, 0.75, 1.0], inf),
        \amp, 1.0,
        \pan, Pwhite(-0.7, 0.7, inf),
        \wobblelow, 0.0,
        \legato, 0.7,
        \scale, Pfunc { ~scale },
        \group, ~group1,
        \out, reverb_out
    );

    seq_pattern = Pbind(
        \instrument, \seq,
        \degree, Pseq([
            Pseq([Rest()], 400),
            Prand([
                Pfunc { if (0.01.coin) { nil; } { ~allowed_lead_degrees.choose}; },
                Pseq([Rest()], 200)], inf),
        ], inf),
        \octave, Prand([5, 6, 7, Rest(), Rest(), Rest()],inf),
        \dur, Prand([0.125], inf),
        \amp, Pbrown(0.05, 0.1, 0.01, inf)*5,
        \scale, Pfunc { ~scale },
        \pan, Pwhite(-0.7,0.7,inf),
        \glissdir, Pwhite(-1.0,1.0,inf),
        \gliss, Plazy { Pbrown(0.0, ~gliss_amt, 0.1, inf) },
        \out, 0,
    );

    noise_pattern = Pbind(
        \instrument, \noise,
        \degree, Pseq([0, Rest()], inf),
        \dur, Prand([5.0, 10.0, 20.0], inf),
        \amp, 0.3,
        \pan, 0,
        \out, 0,
    );

    kick_pattern = Pbind(
        \instrument, \kick,
        \deg, Pseq([
            Pseq([0], 50),
            Pseq([0], 100),
            Pseq([0], 100),
            Pseq([0], 100),
            Pseq([0], 100),
            Pseq([0], 100)
        ], inf),
        \dur, Pseq([
            Pseq([Rest()], 50),
            Pseq([1], 100),
            Pseq([0.5], 100),
            Pseq([0.75], 100),
            Pseq([0.5], 100),
            Pseq([1], 100),
        ], inf),
        \amp, Pfunc { 0.7 + (0.0.rrand(0.15)); }
    );

    pattern = Ptpar([
        0.0, bass_pattern,
        0.1, lead_pattern,
        0.1, seq_pattern,
        0.1, noise_pattern,
        0.1, kick_pattern,
        100.1, lead_pattern_var,
        100.1, lead_pattern_var,
    ],
    inf);

    ~player = pattern.play(quant:0.25);

});

)