a = Synth(\pinch, [freq: 900, ffreq: 300, dur: 3]);

\test play: (instrument: \pinch, freq: 59*3, ffreq: 59 / 3, dur: 0.1);

\test play: (instrument: \pinch, freq: 59*3, ffreq: 59 / 3, dur: 0.1);

\test2 play: (instrument: \pinch);
\test2 play: (freq: (100..500).pseq(1), dur: 0.03);
\test2 play: (freq: (100..500).pseq(1), dur: 0.03);
\test2 play: (ffreq: (100..500).reverse.pseq(1));
