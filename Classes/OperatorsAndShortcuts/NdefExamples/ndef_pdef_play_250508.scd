
\x.play;
\x.ndef(\default);
\x.ndef({ WhiteNoise.ar(0.1) });
\x.ndef({ WhiteNoise.ar(0.1) }, [freq: 50]);
\x.ndef({ WhiteNoise.ar(0.1) }, [freq: 5000]);
\x.ndef({ WhiteNoise.ar(0.1) }, outbus: 1);

Synth(\default);

//:
\x.ndef({ | amp = 0.1 | WhiteNoise.ar(amp)}, outbus: 1);
\x.ndef({ | amp = 0.1 | PinkNoise.ar(amp)}, outbus: 1);
\x.set(\out, 0);
//:
~xchan = ~x.process;
~xchan.set(\amp, 0.01);
~xchan.set(\outbus, 1);
~xchan.set(\outbus, 0);
\x.ndef({ SinOsc.ar(400, 0, 0.2) });
\x.ndef({ SinOsc.ar(400, 0, 0.2) });
\x.ndef({ SinOsc.ar(\freq.kr(400).lag(2), 0, 0.2) });
\x.ndef({ SinOsc.ar(\freq.kr(400).lag(0.25), 0, 0.2) }, [freq: 50.rrand(500)]);
\x.ndef({ SinOsc.ar(\freq.kr(300).lag(0.25), 0, 0.2) }, [freq: 50.rrand(500)]);
//:
\x.ndef({ SinOsc.ar(\freq.kr(300).lag(0.25), 0, 0.2) },
	[freq: 50.rrand(500)], outbus: 1);
//:
\x.ndef({ WhiteNoise.ar(0.2) });
\x.ndef({ WhiteNoise.ar(0.2) });
\x.play;
\x.stop;
// The above are ok.
// now these:
~y;
~y.isPlaying;
\y.play;
currentEnvironment[\y];
currentEnvironment[\y].isPlaying;
\y.play;
\y.play({ WhiteNoise.ar(0.1) });
\y.play({ SinOsc.ar(400, 0, 0.2) });
\y.play({ SinOsc.ar(400, 0, 0.2) });
\y.play({ SinOsc.ar(\freq.kr(400).lag(2), 0, 0.2) });
\y.play({ SinOsc.ar(\freq.kr(400).lag(2), 0, 0.2) }, [freq: 50.rrand(500)]);
\y.play(\default);
\y.play({ WhiteNoise.ar(0.2) });
\y.play({ WhiteNoise.ar(0.2) });
~y;

\y.ndef({ WhiteNoise.ar(0.2) });
//: ========= pdef, EventStream, PatternTemplate

\y.pdef;
\y.play;
\y play: (dur: 0.1);


\z.pdef;
\z.play;
\z play: (dur: 0.1);
\z.play({ WhiteNoise.ar(0.1) }, (outbus: 1));
\z.pdef()
\z.clear;
~z.source.stream[\dur] = nil;
