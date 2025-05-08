\x.play;
\x.ndef(\default);
\x.ndef({ WhiteNoise.ar(0.1) });
\x.ndef({ WhiteNoise.ar(0.1) }, outbus: 1);
//:
\x.ndef({ | amp = 0.1 | WhiteNoise.ar(amp)}, outbus: 1);
\x.ndef({ | amp = 0.1 | PinkNoise.ar(amp)}, outbus: 1);
\x.set(\out, 0);
